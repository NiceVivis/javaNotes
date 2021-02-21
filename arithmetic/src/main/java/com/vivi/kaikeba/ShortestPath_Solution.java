package com.vivi.kaikeba;

/**
 * @author yangwei
 * @date 2020/11/6 2:50 下午
 * 1293. 网格中的最短路径(广度优先搜索) 困难
 * 给你一个 m * n 的网格，其中每个单元格不是 0（空）就是 1（障碍物）。每一步，您都可以在空白单元格中上、下、左、右移动。
 * 如果您 最多 可以消除 k 个障碍物，请找出从左上角 (0, 0) 到右下角 (m-1, n-1) 的最短路径，并返回通过该路径所需的步数。
 * 如果找不到这样的路径，则返回 -1。
 *示例 1：输入：
 * grid =
 * [[0,0,0],
 *  [1,1,0],
 *  [0,0,0],
 *  [0,1,1],
 *  [0,0,0]],k = 1
 * 输出：6
 * 解释：不消除任何障碍的最短路径是 10。
 * 消除位置 (3,2) 处的障碍后，最短路径是 6 。该路径是 (0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2) -> (3,2) -> (4,2).
 * 链接：https://leetcode-cn.com/problems/shortest-path-in-a-grid-with-obstacles-elimination
 */
public class ShortestPath_Solution {
    public int shortestPath(int[][] grid, int k) {
        return 0;
    }
}
