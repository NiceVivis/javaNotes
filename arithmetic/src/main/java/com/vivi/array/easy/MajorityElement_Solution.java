package com.vivi.array.easy;

import java.util.Arrays;

/**
 * @author yangwei
 * @date 2021/2/5 10:28 上午
 *
 * 数组中占比超过一半的元素称之为主要元素。给定一个整数数组，找到它的主要元素。若没有，返回-1。
 * 输入：[1,2,5,9,5,9,5,5,5]
 * 输出：5
 *
 * https://leetcode-cn.com/problems/find-majority-element-lcci/
 */
public class MajorityElement_Solution {

    public static int majorityElement(int[] nums) {
        int size = nums.length/2;
        System.out.println(size);

        for (int i = 0; i < nums.length; i++) {
            int num = 0;
            for (int j = i+1; j < nums.length; j++) {
                if (nums[i] == nums[j]){
                    num++;
                }
            }

            if (num >= size){
                return nums[i];
            }

        }
        return -1;
    }

    /**
     * 投票算法加验证
     * 投票算法是在假设存在主要元素的前提下使用的，最后验证是否存在主要元素
     * @param nums
     * @return
     */
    public static int majorityElement2(int[] nums) {

        if (nums.length == 0){
            return -1;
        }

        int temp = nums[0];
        int count = 1;

//        Arrays.sort(nums);

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == temp){
                count ++;
            }else {
                count --;
            }

            if (count == 0){
                temp = nums[i];
                count = 1;
            }
        }

        int t = nums.length/2+1;

        count = 0;
        for (int i : nums) {
            if (i == temp){
                count++;
            }
            if (count == t){
                return temp;
            }
        }

        return  -1;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,5,9,5,9,5,5,5};
        int[] nums1 = {1,5,5,9,5,2,5,9,5};
        //int[] nums1 = {2,7,3,5,5};
        //int majorityElement = majorityElement(nums1);
        int majorityElement = majorityElement2(nums1);
        System.out.println(majorityElement);
    }

}
