/**
 * @author xdwang
 * @create 2020-12-01 19:58
 */
public class BSNearRight {
    public static int nearestIndex(int sortedArr[] , int value){
        if (sortedArr == null || sortedArr.length == 0) return -1;
        int left = 0;
        int right = sortedArr.length - 1;
        int index = -1;
        while (left <= right){
            int mid = left + ((right - left)>>1);
            if (sortedArr[mid] <= value){
                index = mid;
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,1,2,2,3,4,6,8};
        System.out.println(nearestIndex(arr,5));
    }
}
