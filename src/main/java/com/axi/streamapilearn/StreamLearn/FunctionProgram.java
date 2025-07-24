package com.axi.streamapilearn.StreamLearn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionProgram {
    public static void main(String[] args) {


        List<Student> list = List.of(new Student(10,"1"),new Student(20,"2"));
        //静态方法实现
        System.out.println(filter2(list, FunctionProgram::ageOr18));
        //对象的非静态方法实现
        //其实就是去实现filter2的predicate参数的具体执行
        System.out.println(filter2(list, Student::highOrder));
        ExecutorService pool = Executors.newSingleThreadExecutor();
        //闭包和柯里化
        for(int i = 0 ; i < 10; i++){
            int a = i;
            Runnable task = () -> System.out.println("执行任务" + a);
            pool.execute(task);
        }
        pool.shutdown();


    }
    record Student(int age, String name){
        public boolean highOrder(){
            return this.age > 18;
        }
    };
    static boolean ageOr18(Student s){
        return s.age > 18;
    }
    static List<Student> filter2(List<Student> list, Predicate<Student> predicate){
        List<Student> result = new ArrayList<>();
            for(Student i: list){
                if(predicate.test(i)){
                    result.add(i);
                }
            }
            return result;
    }
    static void print1(Object obj){
        System.out.println(obj);
    }
    static int print2(Object obj){
        System.out.println(obj);
        return 2;
    }

    static List<String> filter(List<Integer> list, Function transfer){
        List<String> result = new ArrayList<>();
        for(Integer i : list){
            result.add((String)transfer.apply(i));
        }
        return result;
    }
    interface Transfer<T,I>{
        T transfer(I t);
    }
    interface Filter<T>{
        T filter(T t);
    }

}
