package com.vivi.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 39. 组合总和
 * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 * candidates 中的数字可以无限制重复被选取。
 *
 * 输入：candidates = [2,3,6,7], target = 7,
 * 所求解集为：
 * [[7],
 *   [2,2,3]
 * ]
 * 回溯算法，输出所有解,回溯算法模版：
 *  back
 */
public class CombinationSum_Solution {

    List<List<Integer>> lists = new ArrayList<>();
    public  List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates == null ||candidates.length == 0||target<0){
            return lists;
        }
        //先将candidates排序
        Arrays.sort(candidates);
        List<Integer> list = new ArrayList<>();
        solve(candidates,target,list);
        return null;
    }
    public void solve(int[] candidates, int target,List<Integer> list){
        //递归的终止条件
        if (target<0){
            return;
        }
        if (target == 0){
            lists.add(list);
        }
        else {
            for (int i = 0;i<candidates.length;i++){
                list.add(candidates[i]);
                //因为每个数字都可以使用无数次
            }
        }
    }

    public static void main(String[] args) {
        int[] num = {2,3,6,7};
    }
}
