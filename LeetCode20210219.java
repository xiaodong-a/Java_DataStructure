package com.leetcode;

import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.util.*;

/**
 * @author xdwang
 * @create 2021-02-19 13:36
 */
public class LeetCode20210219 {
    //29. 两数相除
    /*
    给定两个整数，被除数 dividend 和除数 divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
返回被除数 dividend 除以除数 divisor 得到的商。
整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2
     */
    public int divide(int dividend, int divisor) {
        int flag = 1;
        flag += dividend < 0 ? -1 : 1;
        flag += divisor < 0 ? -1 : 1;
        long num1 = Math.abs((long) dividend);
        long num2 = Math.abs((long) divisor);
        if (num2 == 1) {
            if (dividend == Integer.MIN_VALUE) {
                return flag == 1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;

            } else {
                return (int) (flag == 1 ? -num1 : num1);
            }
        }
        StringBuilder ans = new StringBuilder("0");
        long temp = num2;
        while (temp + temp <= num1) temp <<= 1;
        for (; temp >= num2; temp >>= 1) {
            if (num1 >= temp) {
                num1 -= temp;
                ans.append(1);
            } else ans.append(0);
        }
        int res = Integer.valueOf(ans.toString(), 2);
        return flag == 1 ? -res : res;
    }

    public int divide2(int dividend, int divisor) {
        int flag = 1;
        flag += dividend < 0 ? -1 : 1;
        flag += divisor < 0 ? -1 : 1;
        long num1 = Math.abs((long) dividend);
        long num2 = Math.abs((long) divisor);
        if (num2 == 1) {
            if (dividend == Integer.MIN_VALUE) {
                return flag == 1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;

            } else {
                return (int) (flag == 1 ? -num1 : num1);
            }
        }
        int ans = 0;
        long temp = num2;
        while (temp + temp <= num1) temp <<= 1;
        for (; temp >= num2; temp >>= 1) {
            ans <<= 1;
            if (num1 >= temp) {
                num1 -= temp;
                ans += 1;
            } else ans += 0;
        }
        return flag == 1 ? -ans : ans;
    }

    @Test
    public void test29() {
        System.out.println(Math.abs((long) Integer.MIN_VALUE));
    }

    //30、串联所有单词的子串
    /*
    给定一个字符串 s 和一些长度相同的单词 words。找出 s 中恰好可以由 words
    中所有单词串联形成的子串的起始位置。注意子串要与 words 中的单词完全匹配，
    中间不能有其他字符，但不需要考虑 words 中单词串联的顺序。
     */
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> ans = new LinkedList<>();
        HashSet<String> str = new HashSet<>();
        Permu(words, 0, str);
        Iterator<String> iterator = str.iterator();
        while (iterator.hasNext()) {
            ArrayList<Integer> integers = strStr(s, iterator.next());
            for (int i = 0; i < integers.size(); i++) {
                ans.add(integers.get(i));
            }
        }

        return ans;
    }

    public int[] getNext(String s) {
        char[] str = s.toCharArray();
        int len = str.length;
        int[] next = new int[len];
        int j = -1, i = 0;
        next[0] = -1;
        while (i < len - 1) {
            if (j == -1 || str[i] == str[j]) {
                i++;
                j++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
        //next数组的优化
//        for (i = 1; i < next.length; i++) {
//            if (str[i] == str[next[i]]) next[i] = next[next[i]];
//        }
        return next;
    }

    public ArrayList<Integer> strStr(String haystack, String needle) {
        ArrayList<Integer> ans = new ArrayList<>();
        int m = haystack.length();
        int n = needle.length();
        char[] hay = haystack.toCharArray();
        char[] nee = needle.toCharArray();
        if (n == 0) {
            ans.add(0);
            return ans;
        }
        if (m < n) {
            return ans;
        }
        int[] next = getNext(needle);
        int i = 0, j = 0;
        while (i < m) {
            if (j == n) {
                ans.add(i - n);
                j = next[n - 1];
                i--;
            } else if (j == -1 || hay[i] == nee[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == n) ans.add(i - n);
        return ans;
    }

    public void Permu(String[] chars, int pos, HashSet<String> arr) {
        if (pos + 1 == chars.length) {
            String temp = "";
            for (int i = 0; i < chars.length; i++) {
                temp += chars[i];
            }
            arr.add(temp);
            return;
        }
        for (int i = pos; i < chars.length; i++) {
            if (pos != i && chars[i].equals(chars[pos])) {
                continue;
            }
            swap(chars, pos, i);
            Permu(chars, pos + 1, arr);
            swap(chars, i, pos);
        }
    }

    public void swap(String[] chars, int i, int j) {
        String temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    @Test
    public void test30() {
        findSubstring("wordgoodgoodgoodbestword", new String[]{"good", "good", "best", "word"});
    }

}
