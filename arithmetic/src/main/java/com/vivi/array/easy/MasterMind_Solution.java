package com.vivi.array.easy;

/**
 * @author yangwei
 * @date 2021/2/8 9:58 上午
 * 面试题 16.15. 珠玑妙算
 * 珠玑妙算游戏（the game of master mind）的玩法如下。
 * 计算机有4个槽，每个槽放一个球，颜色可能是红色（R）、黄色（Y）、绿色（G）或蓝色（B）。
 * 例如，计算机可能有RGGB 4种（槽1为红色，槽2、3为绿色，槽4为蓝色）。
 * 作为用户，你试图猜出颜色组合。打个比方，你可能会猜YRGB。要是猜对某个槽的颜色，则算一次“猜中”；
 * 要是只猜对颜色但槽位猜错了，则算一次“伪猜中”。注意，“猜中”不能算入“伪猜中”。
 * 给定一种颜色组合solution和一个猜测guess，编写一个方法，返回猜中和伪猜中的次数answer，其中answer[0]为猜中的次数，answer[1]为伪猜中的次数。
 * 输入： solution="RGBY",guess="GGRR"
 * 输出： [1,1]
 * 解释： 猜中1次，伪猜中1次。
 * 链接：https://leetcode-cn.com/problems/master-mind-lcci

 */
public class MasterMind_Solution {

    /**
     * 题解思路：
     *  第一个字符 R 在guess出现过, total++,  把(首次)出现过的删了防止重复计算
     *  这时: solution = "GRB"   guess = "BGG"
     *
     *  接下来就是 G, 也在guess出现,total++, 把(首次)出现过的删除
     *  solution = "RB"   guess = "BG"
     *
     *  接下来就是 R, guess中没有, 继续
     *  solution = "B"   guess = "BG"
     *
     *  接下来就是 B, 也在guess出现, total++, 把(首次)出现过的删除
     *  solution = ""   guess = "G"
     *
     * @param solution
     * @param guess
     * @return
     */
    public static int[] masterMind(String solution, String guess) {
        int size = solution.length();
        char[] solutionStr = solution.toCharArray();
        char[] guessStr = guess.toCharArray();
        int guessTrue = 0;
        int guessNotTrue = 0;

        int[] guessSolu = new int[2];

        for (int i = 0; i < size; i++) {
            if (solutionStr[i] == guessStr[i]){
                guessTrue++;
                solutionStr[i] = guessStr[i] = 0; //标记，“猜中”不能算入“伪猜中”
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (solutionStr[i] == guessStr[j] && solutionStr[i] != 0) {
                    guessNotTrue++;
                    solutionStr[i] = guessStr[j] = 0;
                }
            }
        }
        guessSolu[0] = guessTrue;
        guessSolu[1] = guessNotTrue;
        return guessSolu;
    }

    public static void main(String[] args) {
        int[] masterMind = masterMind("BGBG", "RGBR");
        //"BGBG"
        //"RGBR"
        for (int i : masterMind) {
            System.out.println(i);
        }

    }
}
