package com.leetcode;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author xdwang
 * @create 2021-02-28 9:33
 */
public class LeetCode20210228 {
    //119. 杨辉三角 II
    public List<Integer> getRow(int rowIndex) {
        List<Integer> pre = new ArrayList<>();
        pre.add(1);
        for (int i = 0; i < rowIndex; i++) {
            List<Integer> next = new ArrayList<>();
            next.add(1);
            for (int j = 0; j < pre.size() - 1; j++) {
                next.add(pre.get(j) + pre.get(j + 1));
            }
            next.add(1);
            pre = next;
        }
        return pre;
    }

    public List<Integer> getRow2(int rowIndex) {
        List<Integer> pre = new ArrayList<>();
        pre.add(1);
        for (int i = 1; i <= rowIndex; i++) {
            pre.add((int) ((long) pre.get(i - 1) * (rowIndex - i + 1) / i));
        }
        return pre;
    }

    //121. 买卖股票的最佳时机
    public int maxProfit(int[] prices) {
        int max = 0;
        int minIndex = 0;
        for (int i = 1; i < prices.length; i++) {
            int temp = prices[i] - prices[minIndex];
            if (temp < 0) minIndex = i;
            else max = max > temp ? max : temp;
        }
        return max;
    }

    //122. 买卖股票的最佳时机 II
    public int maxProfit2(int[] prices) {
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            max += Math.max(0, prices[i] - prices[i - 1]);
        }
        return max;
    }

    //125. 验证回文串
    public boolean isPalindrome(String s) {
        char[] chars = s.toCharArray();
        for (int i = 0, j = chars.length-1; i < j;) {
            if (!isNumOrChar(chars[i])){
                i++;
            }else if(!isNumOrChar(chars[j])){
                j--;
            }else{
                if (toLowerChar(chars[i])!= toLowerChar(chars[j]))
                    return false;
                i++;
                j--;
            }
        }
        return true;
    }

    public boolean isNumOrChar(char c){
        if (c<='9' && c >= '0') return true;
        if (c<='Z' && c >= 'A') return true;
        if (c<='z' && c >= 'a') return true;
        return false;
    }

    public char toLowerChar(char c){
        if (c<='Z' && c >= 'A') return (char)(c - 'A' +'a');
        return c;
    }

    public static void main(String[] args) {
        System.out.println((char)53);
    }

    //136. 只出现一次的数字
    public int singleNumber(int[] nums) {
        int ans = 0;
        for(int i = 0; i < nums.length;i++){
            ans ^= nums[i];
        }
        return ans;
    }
}
