package com.vivi.array.easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 面试题 01.02. 判定是否互为字符重排
 * 给定两个字符串 s1 和 s2，请编写一个程序，确定其中一个字符串的字符重新排列后，能否变成另一个字符串。
 * 输入: s1 = "abc", s2 = "bca"
 * 输出: true
 * https://leetcode-cn.com/problems/check-permutation-lcci/
 * @author yangwei
 * @date 2021/2/8 11:19 上午
 */
public class CheckPermutation_Solution {

    public static boolean CheckPermutation(String s1, String s2) {
        char[] char1 = s1.toCharArray();
        char[] char2 = s2.toCharArray();
        if (char1.length != char2.length){
            return false;
        }

        Arrays.sort(char1);
        Arrays.sort(char2);

        for (int i = 0; i < char1.length; i++) {
            if (char1[i] != char2[i]){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        boolean permutation = CheckPermutation("abc", "bca");
        System.out.println(permutation);
    }
}
