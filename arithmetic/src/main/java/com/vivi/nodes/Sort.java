package nodes;

public class Sort {
    public static void main(String[] args) {
        int[] n = {8,6,4,3,2,5,6,8,10,1};
        int[] arr = bubbleSort(n);
        for (int i : arr){
           //System.out.print(" "+ i);
        }
        int[] arrfast = fastSort(n,1,n.length-1);
        for (int i : arrfast){
            System.out.print(" "+ i);
        }
        bubbleSort(n);
    }

    /**
     * 快排
     * @param a
     * @param start
     * @param end
     * @return
     */
    public static int[] fastSort(int[] a ,int start,int end){
        if (start < end){
            int base = a[start];
            int left = start;
            int right = end;

            //从前往后找到比基准大的元素
            while (left < right && a[left] <= base){
                left++;
                a[right]=a[left];
            }

            //从后向前找到比基准小的元素
            while (left < right && a[right] >= base){
                right--;
                a[left] = a[right];
            }
            // 放置基准值，准备分治递归快排
            a[left] = base;
            fastSort(a,start,left-1);
            fastSort(a,left+1,right);
        }
        return a;
    }
    //冒泡排序
    public static int[] bubbleSort(int[] arr){
        //外层循环控制比较次数
        for (int i = 0;i < arr.length;i++){
            //内层循环控制到达的位置
            for (int k = 0;k < arr.length -i-1;k++){
                int temp;
                if (arr[k] > arr[k+1]){
                    temp = arr[k];
                    arr[k] = arr[k+1];
                    arr[k+1] = temp;
                }
            }
        }
        return arr;
    }

    /**
     * 归并排序
     * @param arr
     * @param left
     * @param right
     */
    public static void mergeSort(int arr[],int left,int right){
        if (left < right){
            int mid = (left+right)/2;
            mergeSort(arr,left,mid);
            mergeSort(arr,mid+1,right);
            merge(arr,left,mid,right);
        }
    }

    public static void merge(int[] arr,int left,int mid,int right){
        int[] temp = new int[arr.length];

        int i = left, j = mid+1 , k= left;
        while (i<=mid && j<=right){
            if (arr[i] <= arr[j]){
                temp[k++] = arr[i++];
            }else {
                temp[k++] = arr[j++];
            }
        }
        while (i <= mid)
            temp[k++] = arr[i++];
        while (j <=right)
            temp[k++] = arr[j++];
    }
}
