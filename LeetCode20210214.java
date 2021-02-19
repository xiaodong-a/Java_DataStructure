package com.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xdwang
 * @create 2021-02-14 10:12
 */

public class LeetCode20210214 {
    //26. 删除排序数组中的重复项
    /*
    给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     */
    public int removeDuplicates(int[] nums) {
        int start = 0,mid = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i-1]){
                move(nums,start,mid,i-1);
                start = i - mid + start;
                while (++i<nums.length&&nums[i]==nums[i-1]);
                mid = i;
            }
        }
        move(nums,start,mid,nums.length-1);
        return nums.length - mid + start;
    }

    public void move(int[] nums,int start,int mid,int end){
        if (start == mid) return;
        for (int i = mid; i <= end; i++) {
            nums[start++] = nums[i];
        }
    }

    public int removeDuplicates02(int[] nums) {
        int index = 0;
        for (int i = 1; i < nums.length; i++) {
            if(nums[index] != nums[i]){
                nums[++index]=nums[i];
            }
        }
        return ++index;
    }

    //27. 移除元素
    /*
    给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     */
    public int removeElement(int[] nums, int val) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if(val != nums[i]){
                nums[index++]=nums[i];
            }
        }
        return index;
    }

    //28. 实现 strStr()
    /*
    实现 strStr() 函数。
给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第
一个位置 (从0开始)。如果不存在，则返回  -1。
     */
    //1、暴力法
    public int strStr(String haystack, String needle) {
        int m = haystack.length();
        int n = needle.length();
        for (int i = 0; i <= m - n; i++) {
            if (haystack.substring(i,i+n).equals(needle)) return i;
        }
        return -1;
    }

    //2、KMP
    public int[] getNext(String s){
        char[] str = s.toCharArray();
        int len = str.length;
        int[] next = new int[len];
        int j = -1,i = 0;
        next[0] = -1;
        while (i<len-1){
            if (j==-1||str[i]==str[j]){
                i++;
                j++;
                next[i]=j;
            }else {
                j = next[j];
            }
        }
        //next数组的优化
        for (i = 1; i < next.length; i++) {
            if (str[i] == str[next[i]]) next[i] = next[next[i]];
        }
        return next;
    }

    public int strStr02(String haystack, String needle){
        int m = haystack.length();
        int n = needle.length();
        char[] hay = haystack.toCharArray();
        char[] nee = needle.toCharArray();
        if (n==0) return 0;
        if (m<n) return -1;
        int[] next = getNext(needle);
        int i = 0,j=0;
        while (i<m&&j<n){
            if (j==-1||hay[i]==nee[j]){
                i++;
                j++;
            }else j = next[j];
        }
        if (j==n)return i - n;
        return -1;
    }


    @Test
    public void test28(){
        int[] googles = getNext("google");
        for (int i = 0; i < googles.length; i++) {
            System.out.println(googles[i]);
        }
    }

    //29. 两数相除
    /*
    给定两个整数，被除数 dividend 和除数 divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
返回被除数 dividend 除以除数 divisor 得到的商。
整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2
     */
    public int divide(int dividend, int divisor) {
        return 0;
    }

    public static void main(String[] args) {
        Fa s = new son();
        s.test();
    }

}
class Fa{
    public static void test(){
        System.out.println("f");
        ArrayList<? extends String> strings = new ArrayList<>();
        String s = strings.get(0);
    }
}
class son extends Fa{
    public static void test(){
        System.out.println("s");
    }
}