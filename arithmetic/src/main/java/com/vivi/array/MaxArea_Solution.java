package com.vivi.array;

/**
 * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * 说明：你不能倾斜容器，且 n 的值至少为 2。
 *示例：
 * 输入：[1,8,6,2,5,4,8,3,7]
 * 输出：49
 */
public class MaxArea_Solution {

    /**
     * 暴力算法，两层遍历，控制外层遍历*内层遍历找出最大面积
     * 已知盛水的面积按最短的一端柱子 则min(height[i],height[j])找出两根柱子中短的那根，(j-i)获取两根柱子之间的距离
     * min(height[i],height[j])*(j-i)   长*宽
     * @param height
     * @return
     */
    public static int maxArea(int[] height) {
        int sum = 0;
        for (int i = 0; i<height.length;i++){
            for (int j = i;j<height.length;j++){
                System.out.println(""+Math.min(height[i],height[j])+"*"+(j-i) +"="+Math.min(height[i],height[j])*(j-i));
                sum = Math.max(sum,Math.min(height[i],height[j])*(j-i));
            }
        }
        return sum;
    }

    /**
     * 双指针解法
     * 定义两个指针，i从左往右遍历，j从右往左遍历，直到两个指针相遇
     * {1,8,6,2,5,4,8,3,7}
     * 已知盛水面积由最短的柱子决定，索引为0时 1*8=8时为本次遍历最大，往后都不会更大。则i++进行下一步计算。
     * @param height
     * @return
     */
    public static int maxArea2(int[] height){
        int i=0;
        //数组长度，数组下标从0开始
        int j=height.length-1;
        int key = 0;
        while ( i<j ){
            //计算盛水的面积
            int area = (j-i)*Math.min(height[i],height[j]);
            key = Math.max(key,area);
            if (height[i] < height[j]){
                i++;
            }else {
                j--;
            }
        }
        return key;
    }

    public static void main(String[] args) {
        int [] num = {1,8,6,2,5,4,8,3,7};
        int [] arr = {1,5,6,8,9,3,4,9,5};
        int key = maxArea(arr);
        int key2 = maxArea2(num);
        System.out.println(key);
        System.out.println("双指针解法"+key2);
    }
}
