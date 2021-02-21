package com.vivi.array;

/**
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 * 上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）
 * 输入: [0,1,0,2,1,0,1,3,2,1,2,1]
 * 输出: 6
 */
public class Trap_Solution {
    public static int trap(int[] height) {
        int sum = 0;
        int i=0;
        for( i = 0;i<height.length-1;i++){
            int key = 0;
            if (height[i] < height[i+1]){
                key = i+1;
                continue;
            }
            for (key = i;key<height.length;key++){
                if (height[i] <= height[key] && key-i > 1){
                    System.out.println("height[i]*(key-i) "+height[i-1]+"*"+(key-i)+"="+height[i]*(key-i));
                    sum+=height[i-1]*(key-i);
                    i = key+1;
                    break;
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        int[] num = {0,1,0,2,1,0,1,3,2,1,2,};
        int n = trap(num);
        System.out.println(n);
    }
}
