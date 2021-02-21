package com.vivi.array.greedy;

/**
 * https://leetcode-cn.com/problems/climbing-stairs/comments/
 * 70、假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * 输入： 3
 * 输出： 3
 * 解释： 有三种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶 + 1 阶
 * 2.  1 阶 + 2 阶
 * 3.  2 阶 + 1 阶
 */
public class ClimbStairs_Solution {

    public int climbStairs(int n) {
        int a = 1,b = 1,res = 1;
        for (int i = 2;i <=n;i++){
            res = a + b;
        }
        return 0;
    }

    public static void main(String[] args) {

    }

}
