import java.util.Arrays;

/**
 * @author xdwang
 * @create 2020-12-01 15:00
 */
public class sortTest {
    public static void main(String[] args){
        int  testTime = 800000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize,maxValue);
            int[] arr2 = copyArray(arr1);
            insertSort(arr1);
            Arrays.sort(arr2);
           if (!isEqual(arr1,arr2)) {
               succeed = false;
               printArray(arr1);
               printArray(arr2);
               break;
           }
        }
            System.out.println(succeed);
            int[] arr1 = generateRandomArray(maxSize,maxValue);
            printArray(arr1);
            selectSort(arr1);
            printArray(arr1);

    }
    public static int[] generateRandomArray(int maxSize,int maxValue){
        int[] arr = new int[(int) ((maxSize+1)*Math.random())] ;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random()*(maxValue+1)) -  (int) (Math.random()*maxValue);
        }
        return  arr;
    }

    public static int[] copyArray(int[] arr){
        if (arr == null) return null;

        int[] res =new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }
    public static void selectSort(int[] array){
        if (array==null || array.length < 2) return;

        for (int i = 0; i < array.length-1; i++) {
            int minIndex = i;
            for(int j = i + 1; j<array.length; j++){
                minIndex = array[j]<array[minIndex]? j:minIndex;
            }
            swap1(array,i,minIndex);
        }
    }
    public static void swap1(int[] array,int i,int minIndex){
        int temp = array[i];
        array[i] = array[minIndex];
        array[minIndex] = temp;
    }
    public static void bubbleSort(int[] array){
        if (array==null || array.length < 2) return;


        for (int i = array.length-2; i >= 0 ; i--) {
            for(int j = 0 ; j <= i;j++){
                if (array[j]>array[j+1])swap2(array,j,j+1);
            }
        }
    }

    public static void swap2(int[] array,int i,int j){
        array[i] = array[i]^array[j];
        array[j] = array[i]^array[j];
        array[i] = array[i]^array[j];
    }

    public static void insertSort(int[] array){
        if (array==null || array.length < 2) return;

        for(int i = 1; i<=array.length-1; i++){
            for(int j = i-1;j >= 0; j--){
                if (array[j] > array[j+1]){
                    swap2(array,j,j+1);
                }else{
                    break;
                }
            }
        }
    }

    public static boolean isEqual(int[] arr1,int[] arr2){
        if (arr1 == null && arr2 == null) return true;
        if (arr1 == null || arr2 == null) return false;
        if (arr1.length != arr2.length ) return false;

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) return false;
        }
        return  true;
    }

    public static void printArray(int[] arr){
        if (arr == null) return;

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
