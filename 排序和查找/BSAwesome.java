/**
 * @author xdwang
 * @create 2020-12-01 20:15
 */
public class BSAwesome {
    public static int getLessIndex(int[] arr) throws Throwable {
        if (arr == null || arr.length == 0) return -1;
        int left = 0;
        int right = arr.length - 1;
        if (arr.length == 1||arr[left]<arr[left + 1]) return left;
        if (arr[right]<arr[right-1]) return right;
        while (left < right){
            int mid = left + ((right - left)>>1);
            if (arr[mid]>arr[mid-1]){
                right = mid - 1;;
            }else if(arr[mid]>arr[mid+1]){
                left = mid + 1;
            }else{
                return mid;
            }
        }
        return left;
    }
}
