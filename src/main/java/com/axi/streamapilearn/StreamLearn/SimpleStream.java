package com.axi.streamapilearn.StreamLearn;

import java.util.*;
import java.util.function.*;

public class SimpleStream<T> {

    public static void main(String[] args) {
        List<Integer> list = List.of(1,2,3,4,5,4,5);
        SimpleStream.of(list).filter(i -> i % 2==0).map(i->i*i).forEach(System.out::println);
        System.out.println(SimpleStream.of(list).reduce(list.get(0),Integer::sum));
        HashSet<Integer> collect = SimpleStream.of(list).collect(HashSet::new, HashSet::add);
        System.out.println(collect);
        System.out.println(SimpleStream.of(list).collect(()->new StringJoiner("-"), (joiner,t) -> joiner.add(String.valueOf(t))));
        System.out.println((HashMap)SimpleStream.of(list).collect(HashMap::new,(map,t)->map.put(t,map.containsKey(t)?(Integer)map.get(t)+1:1)));
    }
    private Collection<T> connection;
    public SimpleStream (Collection<T> collection){
        this.connection = collection;
    }

    public static <T> SimpleStream<T> of(Collection<T> collection){
        return new SimpleStream<>(collection);
    }
    public <C> C collect(Supplier<C> supplier, BiConsumer<C,T> consumer){
        C c = supplier.get();
        for(T element: connection){
            consumer.accept(c,element);
        }
        return c;
    }
    public SimpleStream<T> filter(Predicate<T> predicate){
        List<T> list =new ArrayList<>();
        for(T t: connection){
            if(predicate.test(t)){
                list.add(t);
            }
        }
        return new SimpleStream<>(list);
    }
    public T reduce(T t, BinaryOperator<T> binaryOperator){
        T o = t;
        for(T element: connection){
            o = binaryOperator.apply(o,element);
        }
        return o;
    }
    public <R> SimpleStream<R> map(Function<T,R> function){
        List<R> list = new ArrayList<>();
        for(T t: connection){
            list.add(function.apply(t));
        }
        return new SimpleStream<>(list);
    }

    public void forEach(Consumer<T> consumer){
        for(T t: connection){
            consumer.accept(t);
        }
    }



}
