package com.leetcode;

import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xdwang
 * @create 2021-02-09 18:34
 */
public class LeetCode20210209 {
    //5、最长回文子串
    /*
    给你一个字符串 s，找到 s 中最长的回文子串。
    提示：
    1 <= s.length <= 1000
    s 仅由数字和英文字母（大写和/或小写）组成
     */
    //中心法 题解
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    public int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            --left;
            ++right;
        }
        return right - left - 1;
    }


    //题解修改
    public String longestPalindrome01_2(String s) {
        int start = 0;
        int maxlen = 0;
        char[] str = s.toCharArray();
        for (int i = 0; i < str.length; i++) {
            for (int j = 0; j < 2; j++) {
                int left = i;
                int right = i + j;
                while (left >= 0 && right < str.length && str[left] == str[right]) {
                    right++;
                    left--;
                }
                int len = right - left - 1;
                if (maxlen < len) {
                    maxlen = len;
                    start = left + 1;
                }
            }
        }
        return s.substring(start, start + maxlen);
    }

    //动态规划 题解
    public String longestPalindrome02(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        String ans = "";
        for (int l = 0; l < n; ++l) {
            for (int i = 0; i + l < n; ++i) {
                int j = i + l;
                if (l == 0) {
                    dp[i][j] = true;
                } else if (l == 1) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[i][j] = (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]);
                }
                if (dp[i][j] && l + 1 > ans.length()) {
                    ans = s.substring(i, i + l + 1);
                }
            }
        }
        return ans;
    }

    //对题解的修改
    public String longestPalindrome02_2(String s) {
        char[] str = s.toCharArray();
        int len = str.length;
        int maxlen = 0;
        int start = 0;
        boolean[][] flag = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            for (int left = 0; left + i < len; left++) {
                int right = left + i;
                if (i == 0) {
                    flag[left][right] = true;
                } else if (i == 1) {
                    flag[left][right] = str[left] == str[right];
                } else {
                    flag[left][right] = (str[left] == str[right] && flag[left + 1][right - 1]);
                }
                if (flag[left][right] && i + 1 > maxlen) {
                    maxlen = i + 1;
                    start = left;
                }
            }
        }
        return s.substring(start, start + maxlen);
    }


    //第三种方法 Manacher 算法
    public String longestPalindrome03(String s) {
        int start = 0, end = -1;
        StringBuffer t = new StringBuffer("#");
        for (int i = 0; i < s.length(); ++i) {
            t.append(s.charAt(i));
            t.append('#');
        }
        t.append('#');
        s = t.toString();

        List<Integer> arm_len = new ArrayList<Integer>();
        int right = -1, j = -1;
        for (int i = 0; i < s.length(); ++i) {
            int cur_arm_len;
            if (right >= i) {
                int i_sym = j * 2 - i;
                int min_arm_len = Math.min(arm_len.get(i_sym), right - i);
                cur_arm_len = expand(s, i - min_arm_len, i + min_arm_len);
            } else {
                cur_arm_len = expand(s, i, i);
            }
            arm_len.add(cur_arm_len);
            if (i + cur_arm_len > right) { //其实这里的j选取不是最优的，只是i+cur最靠右边的
                j = i;
                right = i + cur_arm_len;
            }
            if (cur_arm_len * 2 + 1 > end - start) {
                start = i - cur_arm_len;
                end = i + cur_arm_len;
            }
        }

        StringBuffer ans = new StringBuffer();
        for (int i = start; i <= end; ++i) {
            if (s.charAt(i) != '#') {
                ans.append(s.charAt(i));
            }
        }
        return ans.toString();
    }

    public int expand(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            --left;
            ++right;
        }
        return (right - left - 2) / 2;
    }

    //改写后如下
    public String longestPalindrome03_m(String s) {
        int start = 0, len = -1;
        char[] str = new char[2 * s.length() + 1];
        str[0] = '#';
        for (int i = 0; i < s.length(); ++i) {
            str[2 * i+1] = s.charAt(i);
            str[2 * i + 2] = '#';
        }

        int[] arm_len = new int[str.length];
        int right = -1, j = -1;
        for (int i = 0; i < str.length; ++i) {
            int cur_arm_len;
            if (right >= i) {
                int i_sym = j * 2 - i;
                int min_arm_len = Math.min(arm_len[i_sym], right - i);
                cur_arm_len = expand(str, i - min_arm_len, i + min_arm_len);
            } else {
                cur_arm_len = expand(str, i, i);
            }
            arm_len[i] = cur_arm_len;
            if (i + cur_arm_len > right) {
                j = i;
                right = i + cur_arm_len;
            }
            if (cur_arm_len > len) {
                start = i;
                len = cur_arm_len;
            }
            if (len >= str.length - i) break;
        }

        int end = start + len;
        start -= len;
        start = str[start] == '#' ? start + 1 : start;
        end = str[end] == '#' ? end - 1 : end;
        return s.substring(start / 2, end / 2 + 1);
    }

    public int expand(char[] s, int left, int right) {
        while (left >= 0 && right < s.length && s[left] == s[right]) {
            --left;
            ++right;
        }
        return (right - left - 2) / 2;
    }

    //leetcode上最快方法,其实是中心扩展法如下，我一会要改写我的中心扩展法
    private int beginLongest = 0;
    private int lengthLongest = 1;
    private int length;
    private char[] chars;

    public String longestPalindrome04(String s) {
        length = s.length();
        if (length < 2) {
            return s;
        }
        chars = s.toCharArray();
        longest(length >> 1, 0);
        return s.substring(beginLongest, beginLongest + lengthLongest);
    }

    private void longest(int indexR, int flag) {
        int indexL = indexR;
        char c = chars[indexR];
        while (++indexR < length && c == chars[indexR]) {
        }
        int right = indexR;
        while (--indexL >= 0 && c == chars[indexL]) {
        }
        int left = indexL;
        while (left >= 0 && right < length && chars[left] == chars[right]) {
            --left;
            ++right;
        }
        if (right - left - 1 > lengthLongest) {
            beginLongest = left + 1;
            lengthLongest = right - beginLongest;
        }
        if (flag <= 0 && lengthLongest <= indexL << 1) {
            longest(indexL, -1);
        }
        if (flag >= 0 && lengthLongest <= (length - 1 - indexR) << 1) {
            longest(indexR, 1);
        }
    }

    //我的中心扩展法的修改
    public String longestPalindrome01_2m(String s) {
        int start = 0;
        int maxlen = 0;
        char[] str = s.toCharArray();
        for (int i = 0; i < str.length; i++) {
            for (int j = 0; j < 2; j++) {
                int left = i;
                int right = i + j;
                while (left >= 0 && right < str.length && str[left] == str[right]) {
                    right++;
                    left--;
                }
                int len = right - left - 1;
                if (maxlen < len) {
                    maxlen = len;
                    start = left + 1;
                }
            }
            if (maxlen / 2 >= str.length - i) break;
        }
        return s.substring(start, start + maxlen);
    }


    @Test
    public void test05() {
        System.out.println(longestPalindrome03_m("abb"));
    }
}
