package com.vivi.kaikeba;

/**
 * @author yangwei
 * @date 2020/10/19 10:29 上午
 *
 * 374. 猜数字大小( 二分查找)  简单
 * 猜数字游戏的规则如下：
 * 每轮游戏，我都会从 1 到 n 随机选择一个数字。 请你猜选出的是哪个数字。
 * 如果你猜错了，我会告诉你，你猜测的数字比我选出的数字是大了还是小了。
 * 你可以通过调用一个预先定义好的接口 int guess(int num) 来获取猜测结果，返回值一共有 3 种可能的情况（-1，1 或 0）：
 * -1：我选出的数字比你猜的数字小 pick < num
 * 1：我选出的数字比你猜的数字大 pick > num
 * 0：我选出的数字和你猜的数字一样。恭喜！你猜对了！pick == num
 * 示例 1：
 * 输入：n = 10, pick = 6
 * 输出：6
 * https://leetcode-cn.com/problems/guess-number-higher-or-lower
 */
public class GuessGame_Solution {

    /**
     * 暴力查找
     * @param n
     * @return
     */
    public static int guessNumber(int n) {
        for (int i = 0; i<n;i++) {
            if (guessNumber(i) == 0) {
                return i;
            }
        }
        return n;
    }

    /**
     * 二分查找实现
     * @param n
     * @return
     */
    public static int binarySearchguessNumber(int n){
        int low = 1;
        int hight = n;
        while (low <= hight){
            int mid = low+(hight-low)/2;
            int res = guess(mid);
            if (res == 0){
                return mid;
            }else if (res < 0){
                hight = mid-1;
            }else {
                low = mid+1;
            }
        }
        return -1;
    }

    private static int guess(int mid) {
        return 0;
    }


    public static void main(String[] args) {
        int n = 10;
        System.out.printf(""+guessNumber(n));

    }
}
