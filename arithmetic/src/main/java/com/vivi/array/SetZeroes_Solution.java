package com.vivi.array;

/**
 * 编写一种算法，若M × N矩阵中某个元素为0，则将其所在的行与列清零。
 * 输入：
 * [[0,1,2,0],
 *   [3,4,5,2],
 *   [1,3,1,5]]
 *输出：
 * [[0,0,0,0],
 *   [0,4,5,0],
 *   [0,3,1,0]]
 */
public class SetZeroes_Solution {

    public static int[][] setZeroes(int[][] matrix) {
        boolean[] row = new boolean[matrix.length];
        boolean[] mol = new boolean[matrix[0].length];
        for (int i = 0;i<matrix.length;i++){
            for (int j = 0;j<matrix[i].length;j++){
                if (matrix[i][j] == 0){
                    //标记为0的下标
                    row[i] = true;
                    mol[j] = true;
                }
            }
        }
        //System.out.println(Arrays.toString(row));
        //System.out.println(Arrays.toString(mol));

        for (int i = 0;i<matrix.length;i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                //将为0的下标赋值为0
                if (row[i] || mol[j]){
                    matrix[i][j] = 0;
                }
            }
        }
        return matrix;
    }

    public static void main(String[] args) {
        int [][] grid= {{0,1,2,0},
                {3,4,5,2},
                {1,3,1,5},};
        int [][] num = {{1,2,3,4,0},
                        {3,4,2,5,8},
                        {2,0,4,5,6},
                        {9,3,8,5,0}};
        int [][] n = setZeroes(num);

        for (int i = 0;i<n.length;i++) {
            for (int j = 0; j < n[i].length; j++) {
                System.out.print(n[i][j]+",");
            }
            System.out.println("\n");
        }
    }
}
