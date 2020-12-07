
import java.util.ArrayList;

/**
 * @author xdwang
 * @create 2020-12-07 23:58
 */
public class HeapSort {
    public static void heapSort(int[] arr){
        if (arr == null || arr.length == 0) return;
        int size = arr.length;
        for (int i = arr.length - 1; i >= 0;i--){
            heapify(arr,i,size);
        }
        while(size>1){
            pop(arr,size--);
        }
    }
    private static void heapify(int[] arr,int index,int size){
        int left = index*2+1;
        while (left <= size - 1){
            int largest = left < size -1 && arr[left] < arr[left + 1] ? left + 1:left;
            if(arr[largest] <= arr[index]) break;
            swap(arr,largest,index);
            index = largest;
            left = index*2 + 1;
        }
    }
    private static void swap(int[] arr,int i,int j){
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
    private static void pop(int[] arr,int size){
        swap(arr,0,size-1);
        heapify(arr,0,size - 1);
    }
    public static void info(int[] arr){//不检查null或空了
        System.out.print("[");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i]+",");
        }
        System.out.print(arr[arr.length-1]+"]");
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,3,5,9,0,14};
        HeapSort.info(arr);
        HeapSort.heapSort(arr);
        HeapSort.info(arr);
    }
}
