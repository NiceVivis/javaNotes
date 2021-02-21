package com.vivi.string;

/**
 * 字符串压缩。利用字符重复出现的次数，编写一种方法，实现基本的字符串压缩功能。
 * 比如，字符串aabcccccaaa会变为a2b1c5a3。若“压缩”后的字符串没有变短，则返回原先的字符串。
 * 你可以假设字符串中只包含大小写英文字母（a至z）
 * 示例1:
 *  输入："aabcccccaaa"
 *  输出："a2b1c5a3"
 *
 * 链接：https://leetcode-cn.com/problems/compress-string-lcci
 * @author yangwei
 * @date 2021/2/20 9:25 上午
 */
public class CompressString_Solution {

    /**
     * 方法一，暴力算法
     * @param S
     * @return
     */
    public static String compressString1(String S) {
        if (S == null || S.length() == 0){
            return "";
        }
        char[] chars = S.toCharArray();
        String str = "";
        int i = 0;
        for ( i = 0; i < chars.length; i++) {
            int temp = 1;
            for (int j = i+1; j < chars.length; j++) {
                if (chars[i] == chars[j]){
                    temp++;
                    i++;
                }else {
                    break;
                }
            }

            str += String.valueOf(chars[i]).concat(String.valueOf(temp));
        }
        if (S.length() > str.length() ){
            return str;
        }else {
            return S;
        }
    }

    /**
     * 双指针法,方法是使用双指针，移动两个下标 i 和 j两个指针。第一个指针指向数组第一位，第二个指针不断后移找到相同的字符知道不相同，
     * 然后将i置为不相同的的下标的j，j继续后移找相同字符
     * @param S
     * @return
     */
    public static String compressString2(String S) {
        if (S == null || S.length() == 0){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        char[] chars = S.toCharArray();
        int i = 0;
        while (i<chars.length){
            int j = i+1;
            int temp = 1;
            while (j<chars.length && chars[i] == chars[j]){
                j++;
                temp++;
            }
            sb.append(chars[i]).append(temp);
            i = j;
        }
        if (S.length() > sb.length() ){
            return sb.toString();
        }else {
            return S;
        }
    }

    public static void main(String[] args) {
        String str = "aabcccccaaa";
        String string = compressString2(str);
        System.out.println(string);
    }
}
