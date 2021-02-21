package com.vivi.array;

public class MaxProfit_Solution {

    public static void main(String[] args) {
        int [] prices={7,5,5,3,6,4,10,1,7};
        int [] prices1={3,9,1,2};
        int [] num= {-2,1,-3,4,-1,2,1,-5,4};
        int [] price = {7,1,5,3,6,4};
        //System.out.println(maxProfit2(prices1));
        int n = maxProfit1(prices1);
        //System.out.printf("n="+n);
        System.out.println(""+maxProfit(price));
        //System.out.printf(""+maxSubArray(num));
    }

    /**
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
     * 输入: [7,1,5,3,6,4]
     * 输出: 7
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     * 随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {
        int max = 0;
        for (int i = 0;i < prices.length-1;i++){
            if(prices[i] <prices [i+1]){
                max += prices[i+1]-prices[i];
            }
        }
        return max;
    }

    /**
     * 买卖股票的最佳时机 plan2
     * @param prices
     * @return
     */
    public static int maxProfit2(int[] prices) {
        int max = 0;
        int min = Integer.MAX_VALUE;
        //2 9 1 3
        for (int temp : prices) {
            if (min > temp) {
                min = temp;
            } else if (temp - min > max) {
                max = temp - min;
            }
        }
        return max;
    }

    /**
     * 买卖股票的最佳时机 1
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 如果你最多只允许完成一笔交易（即买入和卖出一支股票一次），设计一个算法来计算你所能获取的最大利润。
     * 输入: [7,1,5,3,6,4]
     * 输出: 5
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     * 注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
     * @param prices
     * @return
     */
    public static int maxProfit1(int[] prices) {

        int tempMax=Integer.MIN_VALUE;
        for(int i=0;i<prices.length;i++){
            int temp=0;
            for(int j=i+1;j<prices.length;j++){
                if((prices[j]-prices[i])>temp){
                    temp=prices[j]-prices[i];
                }
            }
            if(tempMax<temp){
                tempMax=temp;
            }
        }
        return tempMax;
    }

}
