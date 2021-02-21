package com.vivi.array;

/**
 * 给定一个包含了一些 0 和 1 的非空二维数组 grid 。
 * 一个 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在水平或者竖直方向上相邻。你可以假设 grid 的四个边缘都被 0（代表水）包围着。
 * 找到给定的二维数组中最大的岛屿面积。(如果没有岛屿，则返回面积为 0 。)
 * 示例 1:
 *  [[0,0,1,0,0,0,0,1,0,0,0,0,0],
 *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 *  [0,1,1,0,1,0,0,0,0,0,0,0,0],
 *  [0,1,0,0,1,1,0,0,1,0,1,0,0],
 *  [0,1,0,0,1,1,0,0,1,1,1,0,0],
 *  [0,0,0,0,0,0,0,0,0,0,1,0,0],
 *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 *  [0,0,0,0,0,0,0,1,1,0,0,0,0]]
 *  返回的是6，注意答案不应该是11，因为岛屿只能包括水平或垂直的四个方向的1。
 */
public class MaxAreaOfIsland_Solution {

    /**
     * DFS深度优先搜索，思路：从第一个开始开始搜索，寻找连在一起的岛屿，更新最大值。
     * @param grid
     * @return
     */
    public static int MaxAreaOfIsland(int[][] grid){
        int max = 0;
        for (int i = 0;i<grid.length;i++){
            for (int j = 0;j<grid[i].length;j++){
                if (grid[i][j] == 1){
                    max = Math.max(dfs(grid,i,j),max);
                }
            }
        }
        return max;
    }

    public static int dfs(int[][] grid,int i,int j){
        //坐标不合法，则直接返回
        if (i<0 || i>=grid.length || j<0 || j >= grid[0].length || grid[i][j] == 0){
            return 0;
        }
        grid[i][j] = 0;   //将方格标记为"已遍历"
        int count = 1;

        //基本的DFS框架，每次搜索四个相邻的方格
        count += dfs(grid,i+1,j);
        count += dfs(grid, i-1, j);
        count += dfs(grid, i, j+1);
        count += dfs(grid, i, j-1);
        return count;
    }

    public static int MaxAreaOfIsland1(int[][] grid){
        int ans = 0;
        for (int i = 0;i<grid.length;i++){
            for (int j = 0;j<grid[i].length;j++){
                ans = Math.max(ans,area(grid,i,j));
            }
        }
        return ans;
    }

    public static int area(int[][] grid,int i,int j){
        if ( i<0 || j<0 || i == grid.length || j==grid[i].length || grid[i][j] == 0) {
            return 0;
        }
        grid[i][j] = 0;
        // 上下左右寻找连在一起的岛屿
        return area(grid,i-1,j)+area(grid, i+1, j)+area(grid,i,j-1)+area(grid, i, j+1)+1;
    }

    public static void main(String[] args) {
        int [][] grid= {{0,0,1,0,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,1,1,0,1,0,0,0,0,0,0,0,0},
                {0,1,0,0,1,1,0,0,1,0,1,0,0},
                {0,1,0,0,1,1,0,0,1,1,1,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,0,0,0,0,0,0,1,1,0,0,0,0}
        };

        int max = MaxAreaOfIsland1(grid);
        System.out.println("最多岛屿个数"+max);
        int ans = MaxAreaOfIsland(grid);
        System.out.println("最多岛屿个数"+ans);
    }


}
