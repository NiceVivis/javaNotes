package com.vivi.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * jdk1.8后中的stream对集合数据进行操作，
 * stream 流操作
 * filter：过滤流中某些元素
 * limit：获取n个元素
 * skip：跳过n元素，配合limit(n)可实现分页
 * distinct：通过流中元素的 hashCode() 和 equals() 去除重复元素
 */
public class ListStreamDemo {
    public static void main(String[] args) {
        List<String> lists = new ArrayList<>();
        lists.add("A");
        lists.add("2");
        lists.add("b");
        lists.add("4");
        lists.add("1");
        lists.add("C");
        lists.add("2");
        lists.add("5");

        lists.parallelStream().forEach(s -> {
            System.out.print(""+s+",");
        });

        System.out.println("\n");

        lists.parallelStream().forEachOrdered(s -> {
            System.out.print(""+s+",");
        });

        System.out.println("\n");

        //
        lists.forEach(list ->
        {
            System.out.print(list+",");
        });

        System.out.println("\n");

        //流的中间操作
        //filter方法过滤掉空字符串
        lists.stream().filter(list -> !list.isEmpty()).forEach(System.out::print);

        System.out.println("\n");

        //map方法 映射每个元素到对应的结果
        //判断每个元素是否为空
        lists.stream().map(String::isEmpty).forEach(System.out::print);

        System.out.println("\n");
        lists.stream().map(i ->i.toLowerCase()).forEach(System.out::print);

        System.out.println("\n");

        //limit返回stream的前面n个元素，返回前3个元素
        lists.stream().limit(4).forEach(list->{
            System.out.print(list+",");
        });
        System.out.println("\n");

        //skip 跳过前n个元素
        lists.stream().skip(2).forEach(list->{
            System.out.print(list+",");
        });
        System.out.println("\n");

        //sorted  方法对流进行自然排序
        lists.stream().sorted().forEach(list->{
            System.out.print(list+",");
        });
        System.out.println("\n");

        //distinct 用于去重
        lists.stream().distinct().forEach(list->{
            System.out.print(list+",");
        });

        //count 用于统计流中元素的个数
        System.out.println(lists.stream().count());

        //collect 就是一个归约的操作，可以接受各种做法作为参数，将流中的元素累计成一个汇总的数据
        List<Integer> map = Arrays.asList(5,2,3,7,6,9,2,5,7,8);

        map = map.stream().filter(i -> i>5)   //获取大于5的
                .distinct()
                .sorted()
                .collect(Collectors.toList()); //将数据汇总成list

        System.out.println(map);
    }


}
