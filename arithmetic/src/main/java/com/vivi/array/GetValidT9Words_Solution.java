package com.vivi.array;

import java.util.ArrayList;
import java.util.List;

/**
 * 在老式手机上，用户通过数字键盘输入，手机将提供与这些数字相匹配的单词列表。每个数字映射到0至4个字母。给定一个数字序列，实现一个算法来返回匹配单词的列表。你会得到一张含有有效单词的列表。
 * 示例 1:
 * 输入: num = "8733", words = ["tree", "used"]
 * 输出: ["tree", "used"]
 * 示例 2:
 * 输入: num = "2", words = ["a", "b", "c", "d"]
 * 输出: ["a", "b", "c"]
 */
public class GetValidT9Words_Solution {

    public static List<String> getValidT9Words(String num, String[] words) {
        List<String> res = new ArrayList<>();
        int[] key = {2,2,2,3,3,3,4,4,4,5,5,5,6,6,6,7,7,7,7,8,8,8,9,9,9,9};
        boolean flag;
        for (String word : words){
            flag = true;
            for (int i = 0;i<num.length();i++){
                //字母和数字的
                if (num.charAt(i) != key[word.charAt(i)-'a']+'0'){
                    flag =false;
                    break;
                }
            }
            if (flag){
                res.add(word);
            }
        }
        return res;
    }

//    public static List<String> getValidT9Words2(String num, String[] words) {
//    }

    public static void main(String[] args) {
        String num = "8733";
        String[] words = {"tree", "used"};
        System.out.println(getValidT9Words(num,words));

        String num1 = "2";
        String[] words1 = {"a", "b", "c", "d"};
        System.out.println(getValidT9Words(num1,words1));
        System.out.println(('c'-'a')+'0');
    }
}
