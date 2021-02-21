package com.vivi.basic.basic;

public class IFDemo {
    public static void main(String[] args) {
        int sum = 0;
        for (int  i= 0;i<10;i++){
            for (int j = i;j<10;j++){
                if (i+j==5){
                    sum = j;
                    break;
                }
            }
            System.out.println("i="+i+",j="+sum);
        }

    }

}
