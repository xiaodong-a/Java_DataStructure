package com.leetcode;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xdwang
 * @create 2021-03-10 16:58
 */
public class LeetCode20210310 {
    //278. 第一个错误的版本
    boolean isBadVersion(int version) {
        if (version > 3) return true;
        return false;
    }

    public int firstBadVersion(int n) {
        int l = 1;
        int r = n;
        while (l < r) {
            int m = l + ((r - l) >> 1);
            if (isBadVersion(m)) {
                r = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

    @Test
    public void test() {
        firstBadVersion(5);
    }

    //283. 移动零
    public void moveZeroes(int[] nums) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0)
                swap(nums, i, index++);
        }
    }

    public void swap(int[] nums, int i, int j) {
        if (i == j) return;
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    //290. 单词规律
    public boolean wordPattern(String pattern, String s) {
        char[] chars = pattern.toCharArray();
        String[] s1 = s.split(" ");
        if (chars.length != s1.length) return false;
        HashMap<Character, String> map1 = new HashMap<>();
        HashMap<String, Character> map2 = new HashMap<>();

        for (int i = 0; i < chars.length; i++) {
            if (map1.containsKey(chars[i]) && !map1.get(chars[i]).equals(s1[i]))
                return false;
            if (map2.containsKey(s1[i]) && map2.get(s1[i]) != chars[i])
                return false;
            map1.put(chars[i], s1[i]);
            map2.put(s1[i], chars[i]);
        }
        return true;
    }

    public boolean wordPattern2(String pattern, String s) {
        char[] chars = pattern.toCharArray();
        String[] s1 = s.split(" ");
        if (chars.length != s1.length) return false;
        int[] map = new int[26];

        for (int i = 0; i < chars.length; i++) {
            int num = chars[i] - 'a';
            if (map[num] == 0) {
                map[num] = i + 1;
            } else {
                if (!s1[i].equals(s1[map[num] - 1])) return false;
            }
        }

        Set<String> set = new HashSet<>();
        for (int i = 0; i < map.length; i++) {
            if (map[i] != 0 && !set.add(s1[map[i] - 1]))
                return false;
        }
        return true;
    }

    //292. Nim 游戏
    public boolean canWinNim(int n) { // 超时
        if (n < 4) return true;
        return !(canWinNim(n-1) && canWinNim(n-2) && canWinNim(n-3));
    }

    public boolean canWinNim2(int n) { // 超时
        if (n < 4) return true;
        boolean n1 = true;
        boolean n2 = true;
        boolean n3 = true;
        boolean ans;
        for (int i = 4; i <= n; i++) {
            ans = !(n1&&n2&&n3);
            n1 = n2;
            n2 = n3;
            n3 = ans;
        }
        return n3;
    }

    //开始数学分析
    public boolean canWinNim3(int n){
        return n%4 != 0;
    }
    // 1 1 1 0 1 1 1 0 1 1 1 0

//303. 区域和检索 - 数组不可变
    class NumArray {
        int[] sums;
        public NumArray(int[] nums) {
            int n = nums.length;
            sums = new int[n + 1];
            for (int i = 0; i < n; i++) {
                sums[i + 1] = sums[i] + nums[i];
            }
        }

        public int sumRange(int i, int j) {
            return sums[j + 1] - sums[i];
        }
    }

    //
}
