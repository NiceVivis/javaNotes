package com.vivi.search;

/**
 * 二分查找
 * 在有序数组中查找某已特定元素的搜索算法，搜索过程从数组中间元素开始，如果搜索中间元素正好是查找的元素，则搜索过程结束
 * 如果某一特定元素大于或者小于中间元素，则在数组大于或者小于中间元素的那一半中查找，跟开始一样从中间元素开始比较，如果某一步骤为空，
 * 则代表找不到，这种搜索算法每一次比较都使搜索范围缩小一半
 */
public class BinarySearch {

    /**
     * 递归方法实现
     * @param data  已排序的数组
     * @param from  起始位置
     * @param to    终止位置
     * @param target 要查询的值
     * @return
     */
    public static int binarySearch1(int[] data,int from,int to,int target){
        if (from <= to){
            int mid = from + (to-from)/2;
            if (data[mid] < target){
                return binarySearch1(data,mid+1,to,target);
            }else if (data[mid] > target){
                return binarySearch1(data,from,mid-1,target);
            }else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 非递归方法实现
     * @param data
     * @param from
     * @param to
     * @param target
     * @return
     */
    public static int binarySearch2(int[] data,int from,int to,int target){
        while (from <= to){
            //  1,2,3,4,5,6,7,8,9,0
            int mid = from + (to-from)/2;
            if (data[mid] < target){
                from = mid+1;
            }else if (data[mid] > target){
                to = mid-1;
            }else {
                return mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] data = {1,5,6,12,15,19,23,26,30,33,37,42,53,60};
        int target = 19;
        int key = binarySearch1(data,0,data.length,target);
        System.out.println("target"+key);
    }
}
