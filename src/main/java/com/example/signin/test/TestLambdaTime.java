package com.example.signin.test;

import java.util.*;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:TestLambdaTime
 * @Description: TODO
 */
public class TestLambdaTime {

    public long iteratorFucTime(List<Integer> list){
        long start = System.currentTimeMillis();
        int max = Integer.MIN_VALUE;
        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()){
            max = Math.max(iterator.next(),max);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    public long forFucTime(List<Integer> list){
        long start = System.currentTimeMillis();
        int max = Integer.MIN_VALUE;
        for(int i = 0;i < list.size();i++){
            max = Math.max(max,list.get(i));
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    public long forEachFucTime(List<Integer> list){
        long start = System.currentTimeMillis();
        int max = Integer.MIN_VALUE;
        for(int i : list){
            max = Math.max(i,max);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }


    public long parallelStreamFucTime(List<Integer> list){
        long start = System.currentTimeMillis();
        Optional max = list.parallelStream().reduce(Integer::max);
        long end = System.currentTimeMillis();
        return end - start;
    }


    public long streamFucTime(List<Integer> list){
        long start = System.currentTimeMillis();
        Optional max = list.stream().reduce(Integer::max);
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for(int i = 0;i < 10000000;i++){
            list.add(random.nextInt());
        }
        TestLambdaTime testLambdaTime = new TestLambdaTime();
        System.out.println("111普通迭代器迭代寻找最大值耗时：" + testLambdaTime.iteratorFucTime(list));
        System.out.println("111普通for循环迭代寻找最大值耗时：" + testLambdaTime.forFucTime(list));
        System.out.println("111增强for循环迭代寻找最大值耗时：" + testLambdaTime.forEachFucTime(list));
        System.out.println("111普通stream寻找最大值耗时：" + testLambdaTime.streamFucTime(list));
        System.out.println("111并行stream寻找最大值耗时：" + testLambdaTime.parallelStreamFucTime(list));
        System.out.println("----------------------------------------------------------------------");
        System.out.println("222普通迭代器迭代寻找最大值耗时：" + testLambdaTime.iteratorFucTime(list));
        System.out.println("222普通for循环迭代寻找最大值耗时：" + testLambdaTime.forFucTime(list));
        System.out.println("222增强for循环迭代寻找最大值耗时：" + testLambdaTime.forEachFucTime(list));
        System.out.println("222普通stream寻找最大值耗时：" + testLambdaTime.streamFucTime(list));
        System.out.println("222并行stream寻找最大值耗时：" + testLambdaTime.parallelStreamFucTime(list));

    }


}
