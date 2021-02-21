package com.vivi.array.easy;

import java.util.Map;

/**
 * 121. 买卖股票的最佳时机
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
 * 输入：[7,1,5,3,6,4]
 * 输出：5
 * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
 *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。

 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock
 * @author yangwei
 * @date 2021/2/19 1:51 下午
 */
public class MaxProfit_Solution {

    /**
     *  动态规划
     *  记录 今天之前买入的最小值
     *  计算 今天之前最小值买入，今天的获利
     *  比较 每天最大的获利，取最大值
     * @param prices
     * @return
     */
    public static int maxProfit1(int[] prices) {
        int max = 0,min = prices[0];
        for (int i = 0; i < prices.length; i++) {
            max = Math.max(max,prices[i]-min);
            min = Math.min(min,prices[i]);
        }
        return max;
    }


    public static int maxProfit2(int[] prices) {
        int max = 0;
        int min = Integer.MAX_VALUE;
        for (int price : prices) {
            if (min > price) {
                min = price;
            } else if (price - min > max) {
                max = price - min;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] num= {7,1,5,3,6,4};
        int profit = maxProfit1(num);
        System.out.println(profit);
    }
}
