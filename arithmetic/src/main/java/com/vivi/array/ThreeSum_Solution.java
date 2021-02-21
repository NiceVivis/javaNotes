package com.vivi.array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 * 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 * 满足要求的三元组集合为：
 * [  [-1, 0, 1],
 *   [-1, -1, 2]
 * ]
 */
public class ThreeSum_Solution {
    /**
     * 暴力解法
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<Integer> list = null;
        //利用set去重
        Set set = new HashSet();
        for (int i = 0;i<nums.length;i++){
            for (int j = i+1;j<nums.length;j++){
                for (int k = j+1;k<nums.length;k++){
                    if (nums[i]+nums[j]+nums[k] == 0){
                            list = new ArrayList<>();
                            list.add(nums[i]);
                            list.add(nums[j]);
                            list.add(nums[k]);
                            Collections.sort(list);
                            set.add(list);
                    }
                }
            }
        }
        return new ArrayList<>(set);
    }

    public static List<List<Integer>> threeSum2(int[] nums){
        return null;
    }

    public static void main(String[] args) {
        int [] num = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> lists = threeSum(num);
        for (List<Integer> list:lists){
            System.out.println(list);
        }
    }
}
