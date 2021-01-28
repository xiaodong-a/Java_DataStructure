/**
 * @author xdwang
 * @create 2020-12-01 19:35
 */
public class BSExist {
    public static boolean exist(int[] sortedArr, int num){
        if (sortedArr == null || sortedArr.length == 0) return false;
        int left = 0;
        int right = sortedArr.length-1;
        while (left < right){
            int mid =left + ((right- left)>>1);
            if (sortedArr[mid] == num){
                return true;
            }else if(sortedArr[mid] > num){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return sortedArr[left] == num;
    }
}
