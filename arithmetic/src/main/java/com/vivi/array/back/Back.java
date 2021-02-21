package com.vivi.array.back;

/**
 * 回溯算法一般获取所有解
 * 回溯算法，解决一个回溯问题，实际就是一个决策树遍历的过程。需要考虑以下三个问题
 * 1、路径：也就是已经做出的选择
 * 2、选择列表：也就是你当前可以做出的选择
 * 3、结束条件：也就是到达决策树底层，无法再做出选择条件
 * 回溯算法框架：
 * result = []
 * def backtrack(路径,选择列表):
 *     if 满足结束条件:
 *        result.add(路径)
 *        return
 * for 选择 in 选择列表:
 *     做选择
 *     backtrack(路径,选择列表)
 *
 *回溯算法模版:
 *     back(res,path,列表s)
 *        if(满足条件)
 *           res.add(path)
 *        for(选择 in 列表s)
 *           做选择
 *           back
 *           撤销选择
 *
 */
public class Back {
}
