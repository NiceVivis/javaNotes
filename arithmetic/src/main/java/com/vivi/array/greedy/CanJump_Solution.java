package com.vivi.array.greedy;

/**
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 判断你是否能够到达最后一个位置。
 *
 * 输入: [2,3,1,1,4]
 * 输出: true
 * 解释: 我们可以先跳 1 步，从位置 0 到达 位置 1, 然后再从位置 1 跳 3 步到达最后一个位置。
 *
 * 贪心算法
 */
public class CanJump_Solution {

    public static boolean canJump(int[] nums) {
        int key = 0;
        for (int i=0;i<nums.length;i++){
            if (i <= key){
                //key记录了 i索引位置的数据，即可到达的最大数。
                key = Math.max(key,i+nums[i]);
                //比较key的最大值是否能到达数组的最后一个索引
                if (key >= nums.length -1){
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int [] num = {2,3,1,1,4};
        int [] test = {3,2,1,0,4};
        Boolean flag = canJump(num);
        System.out.println(flag);
    }
}
