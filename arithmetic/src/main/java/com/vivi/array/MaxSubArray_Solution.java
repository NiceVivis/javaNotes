package com.vivi.array;

import java.util.ArrayList;
import java.util.List;

public class MaxSubArray_Solution {

    /**
     * 最大子序和
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * 输入: [-2,1,-3,4,-1,2,1,-5,4],
     * 输出: 6
     * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
     * @param nums
     * @return
     */
    public static int maxSubArray(int[] nums) {
        int max=nums[0];
        int temp =0;
        List list = null;
        for (int i=0;i<nums.length;i++){
            //list= new ArrayList();
            if (temp > 0){     //大于0   加上一个数才有可能继续增大
                temp +=nums[i];
            }
            if (temp <= 0){   //小于0  定位当前坐标
                temp = nums[i];
                //list = new ArrayList();
            }
            if (temp > max){
                max = temp;
                //list.add(nums[i]);
            }

        }
        System.out.println(""+list);
        return max;
    }

    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        int max = 0;
        List<Boolean> list = new ArrayList<>();
        for(int i=0;i<candies.length;i++){
//            if (max < candies[i]){
//                max = candies[i];
//            }
            max = Math.max(max,candies[i]);
        }
        for(int i=0;i<candies.length;i++){
            if (max < candies[i]+extraCandies){
                list.add(true);
            }else {
                list.add(false);
            }
        }
        return list;

    }


    public static void main(String[] args) {
        int [] prices={7,5,5,3,6,4,10,1,7};
        int [] prices1={3,9,1,2};
        int [] num= {-2,1,-3,4,-1,2,1,-5,4};
        int [] price = {7,1,5,3,6,4};
        System.out.printf(""+maxSubArray(num));
    }

}
