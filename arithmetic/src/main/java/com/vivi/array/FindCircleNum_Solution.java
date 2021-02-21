package com.vivi.array;

/**
 * https://leetcode-cn.com/problems/friend-circles/solution/peng-you-quan-by-leetcode/
 * 班上有 N 名学生。其中有些人是朋友，有些则不是。他们的友谊具有是传递性。如果已知 A 是 B 的朋友，B 是 C 的朋友，
 * 那么我们可以认为 A 也是 C 的朋友。所谓的朋友圈，是指所有朋友的集合。
 * 给定一个 N * N 的矩阵 M，表示班级中学生之间的朋友关系。如果M[i][j] = 1，表示已知第 i 个和 j 个学生互为朋友关系，
 * 否则为不知道。你必须输出所有学生中的已知的朋友圈总数。
 * 输入:[[1,1,0],
 *       [1,1,0],
 *       [0,0,1]]
 * 输出: 2
 * 说明：已知学生0和学生1互为朋友，他们在一个朋友圈。
 * 第2个学生自己在一个朋友圈。所以返回2。
 */
public class FindCircleNum_Solution {

    public static int findCircleNum(int[][] M) {
        int[] visited = new int[M.length];
        int count = 0;
        //遍历出3个数组，[1,1,0],[1,1,0],[0,0,1]
        for (int i = 0 ;i< M.length;i++){
            if (visited[i] == 0){
                System.out.println("i="+i+",visited[i]="+visited[i]);
                dfs(M,visited,i);
                count++;
            }
            System.out.println("visited[i]="+visited[i]);
        }
        return count;
    }

    private static void dfs(int[][] m, int[] visited, int i) {
        for (int j=0;j<visited.length;j++){
            System.out.println("i:"+i+",j:"+j+",m[i][j]:"+m[i][j]+",visited[j]:"+visited[j]);
            if (m[i][j] == 1 && visited[j] == 0){
                visited[j] = 1;
                dfs(m,visited,j);
            }
        }
    }

    public static void main(String[] args) {
        int [][] num = {{1,1,0},
                        {1,1,0},
                        {0,0,1},};

        int m = findCircleNum(num);
        System.out.printf("m:"+m);
    }
}
