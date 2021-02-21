package com.vivi.array.easy;

import java.util.Arrays;

/**
 * @author yangwei
 * @date 2021/2/6 9:37 上午
 * 数组nums包含从0到n的所有整数，但其中缺了一个。请编写代码找出那个缺失的整数。你有办法在O(n)时间内完成吗？
 * 注意：本题相对书上原题稍作改动
 * 输入：[3,0,1]
 * 输出：2
 * 输入：[9,6,4,2,3,5,7,0,1]
 * 输出：8
 * https://leetcode-cn.com/problems/missing-number-lcci/
 */
public class MissingNumber_Solution {

    public static int missingNumber(int[] nums) {

        if (nums.length == 1 && nums[0] == 0){
            return 0;
        }

        if(nums.length == 1){
            return 1;
        }

        Arrays.sort(nums);

        int temp = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (temp+1 != nums[i]){
                return temp+1;
            }else {
                temp = nums[i];
            }
        }

        return  nums[nums.length-1]+1;
    }

    /**
     * 数学饭方法，n*(n+1)/2,计算出 n个长度的有序数组之和（从1开始的），如此计算有序和减去当前数组数据可知缺少的数字
     * @param nums
     * @return
     */
    public static int missingNumber2(int[] nums){
        int n = nums.length;
        int sum = n*(n+1)/2;
        for (int num : nums) {
            sum -= num;
        }
        return  sum ;
    }

    public static void main(String[] args) {
        //int num[] = {9,6,4,2,3,5,7,0,1};
        //int num[] = {1,2};
        int num[] = {9,6,4,2,3,5,7,0,1};
        int number = missingNumber2(num);
        System.out.println(number);
    }

}
