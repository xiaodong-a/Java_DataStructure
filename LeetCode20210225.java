package com.leetcode;

import org.junit.Test;

/**
 * @author xdwang
 * @create 2021-02-25 14:39
 */
public class LeetCode20210225 {
    //35. 搜索插入位置
    /*
    给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。
    如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
    你可以假设数组中无重复元素
     */
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length;
        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] > target) {
                right = mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else return mid;
        }
        return left;
    }

    @Test
    public void test() {
        String s = String.valueOf(1242);
        System.out.println(s);
    }

    //38. 外观数列
    public String countAndSay(int n) {
        if (n == 1) return "1";
        String num = "1";
        for (int j = 2; j <= n; j++) {
            StringBuilder ans = new StringBuilder();
            int count = 1;
            char[] chars = num.toCharArray();
            int i;
            for (i = 1; i < chars.length; i++) {
                if (chars[i] == chars[i - 1]) {
                    count++;
                } else {
                    ans.append(count);
                    ans.append(chars[i - 1]);
                    count = 1;
                }
            }
            ans.append(count);
            ans.append(chars[i - 1]);
            num = ans.toString();
        }
        return num;
    }

    //53. 最大子序和
    public int maxSubArray(int[] nums) {
        int max = nums[0];
        int sum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum = sum < 0 ? nums[i] : sum + nums[i];
            max = max > sum ? max : sum;
        }
        return max;
    }

    //58. 最后一个单词的长度
    public int lengthOfLastWord(String s) {
        char[] chars = s.toCharArray();
        int ans = 0;
        int len = chars.length;
        while (--len > 0 && chars[len] == ' ') ;
        for (int i = len; i >= 0; i--) {
            if (chars[i] == ' ') return ans;
            ans++;
        }
        return ans;
    }

    //66. 加一
    public int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            int num = (1 + digits[i]);
            digits[i] = num % 10;
            if (num <= 9) return digits;
        }
        int[] ans = new int[digits.length + 1];
        ans[0] = 1;
        return ans;
    }

    //67. 二进制求和
    public String addBinary(String a, String b) {
        char[] ac = a.toCharArray();
        char[] bc = b.toCharArray();
        int al = ac.length;
        int bl = bc.length;
        int len = Math.max(al, bl);
        int[] nums = new int[len + 1];
        int add = 0;
        int i;
        for (i = 1; i <= len; i++) {
            int num;
            if (i > al) {
                num = add + bc[bl - i] - '0';
            } else if (i > bl) {
                num = add + ac[al - i] - '0';
            } else num = add + ac[al - i] - '0' + bc[bl - i] - '0';
            nums[len + 1 - i] = num % 2;
            add = num / 2;
        }
        nums[0] = add;
        if (add == 0) i = 1;
        else i = 0;
        StringBuilder ans = new StringBuilder();
        for (; i <= len + 1; i++) {
            ans.append(nums[i]);
        }
        return ans.toString();
    }

    public int mySqrt(int x) {
        if (x == 1) return 1;
        int left = 0;
        int right = x / 2;
        while (left < right) {
            int mid = (left + right + 1) / 2;
            double num = Math.pow(mid, 2);
            if (num > x) {
                right = mid - 1;
            } else if (num < x) {
                left = mid;
            } else {
                return mid;
            }
        }
        return left;
    }

    public int mySqrt2(int x) {
        if (x == 0) return 0;
        double x0 = x;
        while (Math.pow(x0, 2) - x > 1e-6) {
            x0 = 0.5 * (x0 + x / x0);
        }
        return (int) x0;
    }

    public int mySqrt3(int x) {
        if (x == 0) {
            return 0;
        }

        double C = x, x0 = x;
        while (true) {
            double xi = 0.5 * (x0 + C / x0);
            if (Math.abs(x0 - xi) < 1e-7) {
                break;
            }
            x0 = xi;
        }
        return (int) x0;
    }

    @Test
    public void test2() {
        mySqrt(214739);
    }
}
