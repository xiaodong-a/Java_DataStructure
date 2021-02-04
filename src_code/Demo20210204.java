package com.xdwang;

import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.util.*;

/**
 * @author xdwang
 * @create 2021-02-04 11:40
 */
public class Demo20210204 {
    //27
    public ArrayList<String> Permutation(String str) {
        if (str == null || str.length() == 0) return new ArrayList<String>();
        StringBuffer stb = new StringBuffer(str);
        ArrayList<String> list = new ArrayList<String>();
        if (stb.length() == 1) {
            list.add(str);
            return list;
        }
        TreeSet<String> set = new TreeSet<String>();
        ArrayList<String> list2 = Permutation(str.substring(0, str.length() - 1));
        char c = str.charAt(str.length() - 1);
        for (int i = 0; i < list2.size(); i++) {
            String temp = list2.get(i);
            for (int j = 0; j <= temp.length(); j++) {
                StringBuffer sb = new StringBuffer(temp);
                set.add(sb.insert(j, c).toString());
            }
        }
        for (String s : set) {
            list.add(s);
        }
        return list;
    }

    //swap 做法
    public ArrayList<String> Permutation2(String str) {
        if (str == null || str.length() == 0) return new ArrayList<String>();
        char[] chars = str.toCharArray();
        TreeSet<String> set = new TreeSet<>();
        Permu(chars, 0, set);
        ArrayList<String> res = new ArrayList<>(set);
        return res;
    }

    public void Permu(char[] chars, int pos, TreeSet<String> arr) {
        if (pos + 1 == chars.length) {
            arr.add(new String(chars));
            return;
        }
        for (int i = pos; i < chars.length; i++) {
            if (pos != i && chars[i] == chars[pos]) {
                continue;
            }
            swap(chars, pos, i);
            Permu(chars, pos + 1, arr);
            swap(chars, i, pos);
        }
    }

    public void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }


    public ArrayList<String> Permutation3(String str) {
        if (str == null || str.length() == 0) return new ArrayList<String>();
        char[] chars = str.toCharArray();
        ArrayList<String> res = new ArrayList<>();
        Permu3(chars, 0, res);
        Collections.sort(res);
        return res;
    }

    public void Permu3(char[] chars, int pos, ArrayList<String> arr) {
        if (pos + 1 == chars.length) {
            arr.add(new String(chars));
            return;
        }
        for (int i = pos; i < chars.length; i++) {
            if (pos != i && chars[i] == chars[pos]) {
                continue;
            }
            swap(chars, pos, i);
            Permu3(chars, pos + 1, arr);
            swap(chars, i, pos);
        }
    }

    //28
    //第一种排序法
    public int MoreThanHalfNum_Solution(int[] array) {
        if (array == null || array.length == 0) return 0;
        Arrays.sort(array);
        int num = array[array.length / 2];
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == num) count++;
        }
        return count > array.length / 2 ? num : 0;
    }

    //第二种 hash法
    public int MoreThanHalfNum_Solution2(int[] array) {
        if (array == null || array.length == 0) return 0;
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            if (hashMap.get(array[i]) == null) {
                hashMap.put(array[i], 1);
            } else {
                hashMap.put(array[i], hashMap.get(array[i]) + 1);
            }
        }
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            if (entry.getValue() > array.length / 2) return entry.getKey();
        }
        return 0;
    }

    //第三种，候选法
    public int MoreThanHalfNum_Solution3(int[] array) {
        int num = 0;
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (count == 0) {
                num = array[i];
                count++;
            } else {
                if (num == array[i]) count++;
                else count--;
            }
        }
        count = 0;
        for (int i = 0; i < array.length; i++) {
            if (num == array[i]) count++;
        }
        return count > array.length / 2 ? num : 0;
    }


    //29、最小的K个数
    //排序，用java自带的排序方法
    //使用arrays.sort
    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if (input == null || k < 1 || input.length < k) return res;
        Arrays.sort(input);
        for (int i = 0; i < k; i++) {
            res.add(input[i]);
        }
        return res;
    }

    //优先级队列,小根堆
    public ArrayList<Integer> GetLeastNumbers_Solution2(int[] input, int k) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if (input == null || k < 1 || input.length < k) return res;
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < input.length; i++) {
            queue.add(input[i]);
        }
        for (int i = 0; i < k; i++) {
            res.add(queue.poll());
        }
        return res;
    }

    //优先级队列，大根堆
    public ArrayList<Integer> GetLeastNumbers_Solution3(int[] input, int k) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if (input == null || k < 1 || input.length < k) return res;
        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        for (int i = 0; i < k; i++) {
            queue.add(input[i]);
        }
        for (int i = k; i < input.length; i++) {
            if (input[i] < queue.peek()) {
                queue.poll();
                queue.add(input[i]);
            }
        }
        res = new ArrayList<>(queue);
        return res;
    }

    //手动实现大根堆
    public ArrayList<Integer> GetLeastNumbers_Solution4(int[] input, int k) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if (input == null || k < 1 || input.length < k) return res;
        //从下往上，计算量少点
        for (int i = (k - 2) / 2; i >= 0; i--) {
            heapify(input, i, k - 1);
        }
        //大根堆完成
        for (int i = k; i < input.length; i++) {
            if (input[i] < input[0]) {
                swap(input, 0, i);
                heapify(input, 0, k - 1);
            }
        }
        for (int i = 0; i < k; i++) {
            res.add(input[i]);
        }
        return res;
    }

    public void heapify(int[] input, int index, int heapSize) {
        int left = index * 2 + 1;
        while (left <= heapSize) {
            int largest = left + 1 < heapSize && input[left] < input[left + 1] ? left + 1 : left;
            if (input[index] > input[largest]) break;
            swap(input, index, largest);
            index = largest;
            left = index * 2 + 1;
        }
    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    //30
    public int FindGreatestSumOfSubArray(int[] array) {
        if (array == null || array.length == 0) return 0;
        return Integer.parseInt(dst(array));
    }

    public String dst(int[] array) {
        if (array.length == 0) return "null";
        if (array.length == 1) return array[0] + "";
        int[] sum = new int[array.length + 1];
        sum[0] = 0;
        for (int i = 0; i < array.length; i++) sum[i + 1] = array[i] + sum[i];
        int max = sum[1];
        int indexMax = 1;
        for (int i = 2; i < sum.length; i++) {
            if (sum[i] >= max) {
                max = sum[i];
                indexMax = i;
            }
        }
        int min = sum[0];
        for (int i = 1; i < indexMax; i++) {
            if (sum[i] < min) {
                min = sum[i];
            }
        }
        int ret = max - min;
        String left = dst(Arrays.copyOfRange(array, indexMax, array.length));
        if (left == "null") return ret + "";
        return ret > Integer.parseInt(left) ? ret + "" : left;
    }

    @Test
    public void test() {
        for (int i = 0; i < 16; i++) {
            System.out.print((int) ((Math.random() * 15) - (Math.random() * 15)) + "\t");
        }
    }

    public int FindGreatestSumOfSubArray2(int[] array) {
        int max = array[0];
        int sum = array[0];
        for (int i = 1; i < array.length; i++) {
            sum = sum > 0 ? sum + array[i] : array[i];
            max = sum > max ? sum : max;
        }
        return max;
    }

    //31
    //字符串方法
    public int NumberOf1Between1AndN_Solution(int n) {
        int res = 0;
        for (int i = 0; i <= n; i++) {
            res += num1(i);
        }
        return res;
    }

    public int num1(int n) {
        String str = Integer.toString(n);
        int res = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') res++;
        }
        return res;
    }

    //统计各个数位上1出现的次数
    public int NumberOf1Between1AndN_Solution2(int n) {
        int res = 0;
        for (int m = 1; m <= n; m *= 10) {
            int high = n / m;
            int low = n % m;
            res += (high + 8) / 10 * m + (high % 10 == 1 ? low + 1 : 0);
        }
        return res;
    }
}
