package com.vivi;

public class Test {
    public static void main(String[] args) {
        long begin = System.currentTimeMillis();

        int key = 0;
        for (int i = 0;i<100;i++){
            key+=i;
            System.out.println("key:"+key);
        }
        System.out.println("\n");
        long end = System.currentTimeMillis();
        System.out.println("Time:"+(end-begin));
    }
}
