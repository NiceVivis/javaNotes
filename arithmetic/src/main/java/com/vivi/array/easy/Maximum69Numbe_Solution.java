package com.vivi.array.easy;

/**
 * @author yangwei
 * @date 2021/2/3 6:03 下午
 *
 * 1323. 6 和 9 组成的最大数字
 * 给你一个仅由数字 6 和 9 组成的正整数 num。你最多只能翻转一位数字，将 6 变成 9，或者把 9 变成 6 。
 * 请返回你可以得到的最大数字。
 * 示例 1：
 * 输入：num = 9669
 * 输出：9969
 * 解释：
 * 改变第一位数字可以得到 6669 。
 * 改变第二位数字可以得到 9969 。
 * 改变第三位数字可以得到 9699 。
 * 改变第四位数字可以得到 9666 。
 * 其中最大的数字是 9969 。

 * 链接：https://leetcode-cn.com/problems/maximum-69-number
 */
public class Maximum69Numbe_Solution {

    public static int maximum69Number (int num) {
        String str = String.valueOf(num);
        char[] strings = str.toCharArray();
        for (int i = 0; i < strings.length; i++) {
            if (strings[i] == '6'){
                strings[i]='9';
                break;
            }
        }
        return Integer.parseInt(String.valueOf(strings));
    }

    public static void main(String[] args) {
        System.out.println(maximum69Number(9669));
    }
}
