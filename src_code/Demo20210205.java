package com.xdwang;

import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.util.*;

/**
 * @author xdwang
 * @create 2021-02-05 10:36
 */
public class Demo20210205 {

    //32、 第一种
    public String PrintMinNumber(int[] numbers) {
        Integer[] numarr = new Integer[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            numarr[i] = numbers[i];
        }
        Arrays.sort(numarr, new Comparator<Integer>() {
            @Override
            public int compare(Integer t1, Integer t2) {
                return Integer.parseInt(t1 + "" + t2) - Integer.parseInt(t2 + "" + t1);
            }
        });
        String res = "";
        for (int i = 0; i < numarr.length; i++) {
            res += numarr[i];
        }
        return res;
    }

    //第二种 冒泡排序
    public String PrintMinNumber2(int[] numbers) {
        if (numbers == null || numbers.length == 0) return "";
        for (int i = 0; i < numbers.length - 1; i++) {
            for (int j = numbers.length - 2; j >= i; j--) {
                if (!compareStr(numbers[j], numbers[j + 1])) {
                    int temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }
        String res = "";
        for (int num : numbers) {
            res += num;
        }
        return res;
    }

    public boolean compareStr(int num1, int num2) {
        long a = Long.parseLong(num1 + "" + num2);
        long b = Long.parseLong(num2 + "" + num1);
        return a < b;
    }

    public boolean compareStr2(int num1, int num2) {
        String a = num1 + "" + num2;
        String b = num2 + "" + num1;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) < b.charAt(i)) return true;
            else if (a.charAt(i) > b.charAt(i)) return false;
        }
        return true;
    }

    //33丑数  第一种  遍历
    public int GetUglyNumber_Solution(int index) {
        int num = 1;
        while (index > 0) {
            if (isUglyNumber(num++)) index--;
        }
        return --num;
    }

    public boolean isUglyNumber(int num) {
        while (num % 5 == 0) num /= 5;
        while (num % 3 == 0) num /= 3;
        while (num % 2 == 0) num /= 2;
        if (num == 1) return true;
        return false;
    }

    //第二种方法
    public int GetUglyNumber_Solution2(int index) {
        if (index <= 0) return 0;
        int[] res = new int[index];
        res[0] = 1;
        int index2 = 0;
        int index3 = 0;
        int index5 = 0;
        for (int i = 1; i < index; i++) {
            int min = 2 * res[index2] > 3 * res[index3] ? 3 * res[index3] : 2 * res[index2];
            res[i] = min < 5 * res[index5] ? min : 5 * res[index5];
            if (res[i] == 2 * res[index2]) index2++;
            if (res[i] == 3 * res[index3]) index3++;
            if (res[i] == 5 * res[index5]) index5++;
        }
        return res[index - 1];
    }

    @Test
    public void test2() {
        System.out.println(GetUglyNumber_Solution(5));
    }

    //34
    public int FirstNotRepeatingChar(String str) {
        if (str == null || str.length() == 0) return -1;
        HashMap<Character, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (hashMap.get(c) == null) {
                hashMap.put(c, 1);
            } else {
                hashMap.put(c, 2);
            }
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (hashMap.get(c) == 1) return i;
        }
        return -1;
    }

    //第二种方法用数组,str仅仅由字母组成，0~127内，为了不再进行索引变换，
    public int FirstNotRepeatingChar2(String str) {
        boolean[] flag1 = new boolean[128]; //默认为false
        boolean[] flag2 = new boolean[128]; //默认为false
        for (int i = 0; i < str.length(); i++) {
            int loc = str.charAt(i);
            if (flag1[loc]) flag2[loc] = true;
            else flag1[loc] = true;
        }
        for (int i = 0; i < str.length(); i++) {
            int loc = str.charAt(i);
            if (!flag2[loc]) return i;
        }
        return -1;
    }

    @Test
    public void test3() {
        System.out.println('z' + 1);
    }

    //35 冒泡法排序
    public int InversePairs(int[] array) {
        if (array == null || array.length < 2) return 0;
        int res = 0;
        for (int i = array.length - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                if (array[j] > array[j + 1]) {
                    res++;
                    swap(array, j, j + 1);
                }
            }
        }
        return res % 1000000007;
    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    //35 归并排序
    public int InversePairs2(int[] array) {
        if (array == null || array.length < 2) return 0;
        return mergeSort2(array, 0, array.length - 1);
    }

    public int mergeSort2(int[] arr, int begin, int end) {
        if (begin == end) return 0;
        int mid = (begin + end) / 2;
        int res = 0;
        int left = mergeSort2(arr, begin, mid);
        int right = mergeSort2(arr, mid + 1, end);
        int[] arr2 = Arrays.copyOfRange(arr, begin, end + 1);
        int loc = end;
        for (int i = end - begin, j = mid - begin; i > mid - begin; i--) {
            while (j >= 0 && arr2[i] < arr2[j]) {
                arr[loc--] = arr2[j];
                j--;
            }
            arr[loc--] = arr2[i];
            res = (res + mid - begin - j) % 1000000007;
        }
        return (res + left + right) % 1000000007;
    }


    //一刷写的
    public int InversePairs3(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int[] copy = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i];
        }
        int count = InversePairsCore(array, copy, 0, array.length - 1);//数值过大求余
        return count;

    }

    private int InversePairsCore(int[] array, int[] copy, int low, int high) {
        if (low == high) {
            return 0;
        }
        int mid = (low + high) >> 1;
        int leftCount = InversePairsCore(array, copy, low, mid) % 1000000007;
        int rightCount = InversePairsCore(array, copy, mid + 1, high) % 1000000007;
        int count = 0;
        int i = mid;
        int j = high;
        int locCopy = high;
        while (i >= low && j > mid) {
            if (array[i] > array[j]) {
                count += j - mid;
                copy[locCopy--] = array[i--];
                if (count >= 1000000007)//数值过大求余
                {
                    count %= 1000000007;
                }
            } else {
                copy[locCopy--] = array[j--];
            }
        }
        for (; i >= low; i--) {
            copy[locCopy--] = array[i];
        }
        for (; j > mid; j--) {
            copy[locCopy--] = array[j];
        }
        for (int s = low; s <= high; s++) {
            array[s] = copy[s];
        }
        return (leftCount + rightCount + count) % 1000000007;
    }

    public int InversePairs4(int[] array) {
        if (array == null || array.length < 2) return 0;
        int[] aux = new int[array.length];
        return mergeSort(array, aux, 0, array.length - 1);
    }

    public int mergeSort(int[] array, int[] aux, int l, int r) {
        if (l >= r) return 0;
        int mid = (r - l) / 2 + l;
        int left = mergeSort(array, aux, l, mid);
        int right = mergeSort(array, aux, mid + 1, r);
        int res = 0;
        int i = l;
        int j = mid + 1;
        for (int k = l; k <= r; k++) aux[k] = array[k];
        for (int k = l; k <= r; k++) {
            if (i > mid) {
                array[k] = aux[j++];
            } else if (j > r) {
                array[k] = aux[i++];
            } else if (aux[i] <= aux[j]) {
                array[k] = aux[i++];
            } else {
                array[k] = aux[j++];
                res += (mid - i + 1);
                res = res % 1000000007;
            }
        }
        return (res + left + right) % 1000000007;
    }


    //二刷方法改
    public int InversePairs2_m(int[] array) {
        if (array == null || array.length < 2) return 0;
        int[] arr2 = new int[array.length];
        return mergeSort2_m(array, arr2, 0, array.length - 1);
    }

    public int mergeSort2_m(int[] arr, int[] arr2, int begin, int end) {
        if (begin == end) return 0;
        int mid = (begin + end) / 2;
        int res = 0;
        int left = mergeSort2_m(arr, arr2, begin, mid);
        int right = mergeSort2_m(arr, arr2, mid + 1, end);
        for (int i = begin; i <= end; i++) arr2[i] = arr[i];
        int loc = end;
        for (int i = end, j = mid; i > mid; i--) {
            while (j >= begin && arr2[i] < arr2[j]) {
                arr[loc--] = arr2[j];
                j--;
            }
            arr[loc--] = arr2[i];
            res = (res + mid - j) % 1000000007;
        }
        return (res + left + right) % 1000000007;
    }

    @Test
    public void test4() {
        int[] arr = new int[]{5, 1, 82, 0, 8, 2, 10};
        System.out.println(InversePairs2(arr));
    }

    //36
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null) return null;
        Stack<ListNode> stack1 = new Stack<>();
        Stack<ListNode> stack2 = new Stack<>();
        while (pHead1 != null) {
            stack1.add(pHead1);
            pHead1 = pHead1.next;
        }
        while (pHead2 != null) {
            stack2.add(pHead2);
            pHead2 = pHead2.next;
        }
        ListNode pre = null;
        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            if (stack1.peek() == stack2.peek()) {
                stack1.pop();
                pre = stack2.pop();
            } else {
                break;
            }
        }
        return pre;
    }

    public ListNode FindFirstCommonNode2(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null) return null;
        int count = 0;
        ListNode n1 = pHead1;
        ListNode n2 = pHead2;
        while (n1.next != null) {
            count++;
            n1 = n1.next;
        }
        while (n2.next != null) {
            count--;
            n2 = n2.next;
        }
        if (n1 != n2) return null;
        n1 = count > 0 ? pHead1 : pHead2;
        n2 = count > 0 ? pHead2 : pHead1;
        count = Math.abs(count);
        while (count != 0) {
            n1 = n1.next;
            count--;
        }
        while (n1 != n2) {
            n1 = n1.next;
            n2 = n2.next;
        }
        return n1;
    }

    public ListNode FindFirstCommonNode3(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null) return null;
        ListNode n1 = pHead1;
        ListNode n2 = pHead2;
        while (n1 != n2) {
            n1 = n1.next;
            n2 = n2.next;
            if (n1 != n2) {
                if (n1 == null) n1 = pHead2;
                if (n2 == null) n2 = pHead1;
            }
        }
        return n1;
    }

    //37
    public int GetNumberOfK(int[] array, int k) {
        if (array == null || array.length == 0 || array[0] > k || array[array.length - 1] < k) return 0;
        int loc = 0;
        int count = 0;
        for (; loc < array.length; loc++) {
            if (array[loc] == k) break;
        }
        for (; loc < array.length; loc++) {
            if (array[loc] == k) count++;
            else break;
        }
        return count;
    }

    // 二分法 ，>=k 的最左位置
    public int GetNumberOfK2(int[] array, int k) {
        if (array == null || array.length == 0 || array[0] > k || array[array.length - 1] < k) return 0;
        return nearLeft(array, k + 1) - nearLeft(array, k);
    }

    public int nearLeft(int[] arr, int k) {
        int left = 0;
        int right = arr.length - 1;
        int index = arr.length;   //1,1,2,2,3,4,6,8
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] >= k) {
                index = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return index;
    }

    public int nearestIndex(int sortedArr[], int value) {
        if (sortedArr == null || sortedArr.length == 0) return -1;
        int left = 0;
        int right = sortedArr.length - 1;
        int index = -1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (sortedArr[mid] >= value) {
                index = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return index;
    }

    //一刷的做法
    public int BinarySearch(int[] array, int k) {
        int low = 0;
        int high = array.length;
        while (high > low) {
            int mid = low + (high - low) / 2;
            if (array[mid] >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return high;
    }

    //nearRight
    public int nearRight(int[] array, int k) {
        int low = 0;
        int high = array.length - 1;
        int index = -1;
        while (high >= low) {
            int mid = low + (high - low) / 2;
            if (array[mid] <= k) {
                index = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return index;
    }

    // 。。。注意
    public int nearRight2(int[] array, int k) {
        int low = 0;
        int high = array.length;
        int index = -1;
        while (high > low) {
            int mid = low + (high - low) / 2;
            if (array[mid] <= k) {
                index = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    @Test
    public void test5() {
        int[] arr = new int[]{1, 1, 2, 2, 3, 4, 6, 8};
        System.out.println(nearRight(arr, 7));
        System.out.println(nearLeft(arr, 7));
    }

    //38
    public int TreeDepth(TreeNode root) {
        if (root == null) return 0;
        int left = TreeDepth(root.left);
        int right = TreeDepth(root.right);
        return left > right ? left + 1 : right + 1;
    }

    //层次遍历
    public int TreeDepth2(TreeNode root) {
        if (root == null) return 0;
        LinkedList<TreeNode> node = new LinkedList<>();
        node.add(root);
        int lev = 0;
        while (!node.isEmpty()) {
            int size = node.size();
            while (size-- > 0) {
                root = node.pop();
                if (root.left != null) node.add(root.left);
                if (root.right != null) node.add(root.right);
            }
            lev++;
        }
        return lev;
    }

    //后序遍历
    public int TreeDepth3(TreeNode root) {
        if (root == null) return 0;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode pre = root;
        stack.add(root);
        int maxdepth = 0;
        int count = 1;
        while (!stack.isEmpty()) {
            root = stack.peek();
            if (root.left != null && pre != root.left && pre != root.right) {
                stack.add(root.left);
                count++;
            } else if (root.right != null && pre != root.right) {
                stack.add(root.right);
                count++;
            } else {
                pre = stack.pop();
                maxdepth = maxdepth > count ? maxdepth : count;
                count--;
            }
        }
        return maxdepth;
    }

    //39
    public boolean IsBalanced_Solution(TreeNode root) {
        return isBalanced(root) != -1;
    }

    public int isBalanced(TreeNode root) {
        if (root == null) return 0;
        int left = isBalanced(root.left);
        if (left == -1) return -1;
        int right = isBalanced(root.right);
        if (right == -1) return -1;
        if (left - right > 1 || left - right < -1) return -1;
        return left > right ? left + 1 : right + 1;
    }

}
