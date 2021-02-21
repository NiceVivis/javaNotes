package com.vivi.basic.basic;

import com.google.common.collect.Lists;

import java.util.List;

public class WhereDemo {

    public static void main(String[] args) {
        int size = 5;
        int start = 0;
        int end = 0;
        Long totalOfRedis = 21L;
        List<String> idList = Lists.newArrayList();
        do{
            end += size * 2;
            start = end + 1;
            System.out.printf("end1="+end+" , start1="+start + ",");
        }while (idList.size() < size && end < totalOfRedis);

//        while (idList.size() < size && end < totalOfRedis){
//            end += size * 2;
//            start = end + 1;
//            System.out.printf("end2="+end+" , start2="+start + ",");
//        }
    }

}
