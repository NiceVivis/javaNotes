package com.vivi.kaikeba;

import javax.validation.constraints.Max;
import java.util.logging.Level;

/**
 * @author yangwei
 * @date 2020/10/19 10:48 上午
 *
 * 275. H 指数 II(二分查 找) 中等
 * 给定一位研究者论文被引用次数的数组（被引用次数是非负整数），数组已经按照 升序排列 。编写一个方法，计算出研究者的 h 指数。
 * h 指数的定义: “h 代表“高引用次数”（high citations），一名科研人员的 h 指数是指他（她）的 （N 篇论文中）
 * 总共有 h 篇论文分别被引用了至少 h 次。（其余的 N - h 篇论文每篇被引用次数不多于 h 次。）"
 * 输入: citations = [0,1,3,5,6]
 * 输出: 3
 * 解释: 给定数组表示研究者总共有 5 篇论文，每篇论文相应的被引用了 0, 1, 3, 5, 6 次。
 * 由于研究者有 3 篇论文每篇至少被引用了 3 次，其余两篇论文每篇被引用不多于 3 次，所以她的 h 指数是 3。
 * 说明:如果 h 有多有种可能的值 ，h 指数是其中最大的那个。
 * https://leetcode-cn.com/problems/h-index-ii
 */
public class HIndex_Solution {
    /**
     * //H指数含义:从后往前查，如果当前下标为j论文被引次数为h，则说明论文中有n-j篇b被引用了至少h。
     * //找到最大的一个数满足citations[i]>=(len-i)
     * h指数是指至多有h篇论文分别被引用了至少h次
     * @param citations
     * @return
     */
    public static int hIndex(int[] citations) {
        int size = citations.length;
        for (int i = 0;i<size;i++){
            int key = size-1;
          if (citations[i] >= key) {
              return key;
          }
        }
        return 0;
    }

    /**
     * 给出一个带注释的代码：我们需要找一个mid，从mid开始到最后的文章数记为key,
     * 这些论文的最低引用数量要大于等于key（hIndex定义），那么我们只看最低的引用数量是否大于key就行了，
     * 也就是只看citations[mid]是不是大于等于key就行了。
     * @param citations
     * @return
     */
    public static int hIndexs(int[] citations){
        int n = citations.length;
        int start = 0;
        int end = citations.length;
        while ( start < end ){
            int mid = start+(end-start)/2;
            int key = n-mid;
            if (citations[mid]<key){
                start = mid+1;
            }else{
                end = mid;
            }
        }
        return n-start;
    }

    /**
     * 数组复制，
     * 方法一、for循环复制
     * 方法二、 System.arraycopy(src, srcPos, dest, destPos, length)
     *     	src: 源数组
     *     	srcPos: 从源数组复制数据的起始位置
     *     	dest: 目标数组
     *     	destPos: 复制到目标数组的起始位置
     *     	length: 复制的长度
     * @param args
     */
    public static void main(String[] args) {
        int citations[] = {0,1,3,5,6};
//        int h[] = new int[3];
//        System.arraycopy(citations,0,h,0,3);

        //int key = hIndex(citations);
        int  key = hIndexs(citations);
        System.out.printf("key = "+key);
    }
}
