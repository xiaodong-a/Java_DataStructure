package com.leetcode;

/**
 * @author xdwang
 * @create 2021-03-02 18:46
 */
public class LeetCode20210302 {
    //169. 多数元素
    public int majorityElement(int[] nums) {
        int n = nums[0];
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (count == 0) {
                count++;
                n = nums[i];
            } else {
                if (n == nums[i]) count++;
                else count--;
            }
        }
        return n;
    }

    //171. Excel表列序号
    public int titleToNumber(String s) {
        char[] chars = s.toCharArray();
        int ans = 0;
        for (int i = 0; i < chars.length; i++) {
            ans = ans * 26 + chars[i] - 'A' + 1;
        }
        return ans;
    }

    //172. 阶乘后的零
    public int trailingZeroes(int n) {
        int ans = 0;
        while (n >= 5) {
            n /= 5;
            ans += n;
        }
        return ans;
    }

    //190. 颠倒二进制位
    public int reverseBits(int n) {
        int count = 32;
        int ans = 0;
        while (n != 0) {
            ans <<= 1;
            ans += n & 1;
            n >>>= 1;
            count--;
        }
        while (count != 0){
            ans <<= 1;
            count--;
        }
        return ans;
    }

    public int reverseBits2(int n){
        n = (n<<16)|(n>>>16);
        n = (n&0xff00ff)<<8|(n&0xff00ff00)>>>8;
        n = (n&0xf0f0f0f)<<4|(n&0xf0f0f0f0)>>>4;
        n = (n&0x33333333)<<2|(n&0xcccccccc)>>>2;
        n = (n&0x55555555)<<1|(n&0xaaaaaaaa)>>>1;
        return n;
    }

    //191. 位1的个数
    public int hammingWeight(int n) {  //100  011
        int count = 0;
        while (n != 0){
            n = (n-1)&n;
            count++;
        }
        return count;
    }
}
