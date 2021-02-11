package com.leetcode;

import org.junit.Test;

import javax.xml.stream.FactoryConfigurationError;
import java.io.InterruptedIOException;
import java.util.NoSuchElementException;

/**
 * @author xdwang
 * @create 2021-02-10 12:22
 */
public class LeetCode20210210 {
    //继续把第5题优化 Manacher法 最终版，
    public String longestPalindrome(String s) {
        int start = 0, maxlen = 0;
        char[] str = s.toCharArray();
        float j = -1;
        int right = -1;
        int[] nlen = new int[s.length()];//长度总
        for (int i = 0; i < str.length; i++) {
            int len;
            int indexL = i - 1;
            int indexR = i;
            while (++indexR < s.length() && str[indexR] == str[indexR - 1]) ;
            float mid = (indexL + indexR) / 2.0f;
            if (indexR <= right) {
                int i_sym = (int) (j * 2) - indexR + 1;
                float mmlen = (nlen[i_sym] - 1) / 2.0f;
                if (mmlen < right - mid) {
                    len = nlen[i_sym];
                } else {
                    len = expand(str, (int) (2 * mid - right), right);
                }
            } else {
                len = expand(str, indexL, indexR);//总长度
            }
            float mmlen = (len - 1) / 2.0f;
            if (len > maxlen) {
                maxlen = len;
                start = (int) (mid - mmlen);
            }
            if (mid - mmlen > right) {
                right = (int) (mid + mmlen);
                j = mid;
            }
            nlen[i] = len;
            i = indexR - 1;
        }
        return s.substring(start, start + maxlen);
    }

    public int expand(char[] s, int left, int right) {
        while (left >= 0 && right < s.length && s[left] == s[right]) {
            --left;
            ++right;
        }
        return (right - left - 1);
    }

    //改不动了，这道题就这样吧
    public String longestPalindrome_m(String s) {
        int start = 0, maxlen = 0;
        char[] str = s.toCharArray();

        float j = -1;
        int right = -1;
        float[] nlen = new float[s.length()];//半个长度
        for (int i = 0; i < str.length; i++) {
            int len;
            int indexL = i - 1;
            int indexR = i;
            while (++indexR < s.length() && str[indexR] == str[indexR - 1]) ;
            float mid = (indexL + indexR) / 2.0f;
            if (indexR <= right) {
                int i_sym = (int) (j * 2) - indexR;
                if (nlen[i_sym] < right - mid) {
                    len = (int) (2 * nlen[i_sym]);
                } else {
                    len = expand(str, (int) (2 * mid - right), right);
                }
            } else {
                len = expand(str, indexL, indexR);//总长度
            }
            if (len > maxlen) {
                maxlen = len;
                start = (int) (mid - (len - 1) / 2.0f);
            }
            if (mid - (len - 1) / 2.0f > right) {
                right = (int) (mid + (len - 1) / 2.0f);
                j = mid;
            }
            nlen[i] = (len - 1) / 2.0f;
            i = indexR - 1;
        }
        return s.substring(start, start + maxlen);
    }

    //6、Z字形变换
    /*
    将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
     */
    public String convert01(String s, int numRows) {
        if (numRows == 1 || s.length() == 1) return s;
        StringBuilder[] sb = new StringBuilder[numRows];
        for (int i = 0; i < sb.length; i++) {
            sb[i] = new StringBuilder();
        }
        int currow = 0;
        boolean down = false;
        for (int i = 0; i < s.length(); i++) {
            sb[currow].append(s.charAt(i));
            if (currow == 0 || currow == numRows - 1) down = !down;
            currow += down ? -1 : 1;
        }
        String ans = "";
        for (int i = 0; i < sb.length; i++) {
            ans += sb[i].toString();
        }
        return ans;
    }

    public String convert01_2(String s, int numRows) {
        if (numRows == 1 || s.length() == 1) return s;
        char[] str = s.toCharArray();
        StringBuilder[] sb = new StringBuilder[numRows];
        for (int i = 0; i < sb.length; i++) {
            sb[i] = new StringBuilder();
        }
        int mod = 2 * numRows - 2;
        for (int i = 0; i < s.length(); i++) {
            int j = i % mod;
            if (j < numRows) sb[j].append(str[i]);
            else sb[mod - j].append(str[i]);
        }
        String ans = "";
        for (int i = 0; i < sb.length; i++) {
            ans += sb[i].toString();
        }
        return ans;
    }

    public String convert02(String s, int numRows) {
        if (numRows == 1) return s;
        StringBuffer sb = new StringBuffer();
        char[] str = s.toCharArray();
        int add = 2 * numRows - 2;
        int len = str.length;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j + i < len; j += add) {
                sb.append(str[j + i]);
                if (i != 0 && i != numRows - 1 && j + add - i < len)
                    sb.append(str[j + add - i]);
            }
        }
        return sb.toString();
    }

    public String convert02_2(String s, int numRows) {
        if (numRows == 1 || s.length() == 1) return s;
        char[] str = s.toCharArray();
        int add = 2 * numRows - 2;
        int len = str.length;
        char[] sb = new char[len];
        int id = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j + i < len; j += add) {
                sb[id++] = str[j + i];
                if (i != 0 && i != numRows - 1 && j + add - i < len)
                    sb[id++] = str[j + add - i];
            }
        }
        return new String(sb);
    }

    public String convert02_3(String s, int numRows) {
        if (numRows == 1 || s.length() == 1 || s.length() < numRows) return s;
        char[] str = s.toCharArray();
        int add = 2 * numRows - 2;
        int len = str.length;
        char[] sb = new char[len];
        int id = 0;
        int i = 0;
        int j = 0;
        for (j = 0; j + i < len; j += add) {
            sb[id++] = str[j + i];
        }
        for (i = 1; i < numRows - 1; i++) {
            sb[id++] = str[i];
            for (j = add; j + i < len; j += add) {
                sb[id++] = str[j - i];
                sb[id++] = str[j + i];
            }
            if (j - i < len) sb[id++] = str[j - i];
        }
        i = numRows - 1;
        for (j = 0; j + i < len; j += add) {
            sb[id++] = str[j + i];
        }
        return new String(sb);
    }

    @Test
    public void test06() {
        System.out.println(convert02_3("PAYPALISHIRING", 3));
    }

    //7、整数翻转
    /*
    给你一个 32 位的有符号整数 x ，返回 x 中每位上的数字反转后的结果。
    如果反转后整数超过 32 位的有符号整数的范围 [−2^31,  2^31 − 1] ，就返回 0。
    假设环境不允许存储 64 位整数（有符号或无符号）。
     */
    //时间紧张，不能每题都抓的很全面，现在一刷，只求简单和速度快
    // 2147483648   注意此题，输入的数也有限制，也是int，也是这个范围
    // 第一位数字4  最后一位6  4<6,  只要最后一位数字 大于等于第一位，都不用有特别的溢出判断,简单的足够
    public int reverse(int x) {
        int ans = 0;
        while (x != 0) {
            if (ans * 10 / 10 != ans) return 0;
            ans = ans * 10 + x % 10;
            x /= 10;
        }
        return ans;
    }

    public int reverse01_2(int x) {
        int the = 214748364;
        int ans = 0;
        while (x != 0) {
            if (Math.abs(ans) > the) return 0;
            ans = ans * 10 + x % 10;
            x /= 10;
        }
        return ans;
    }

    //8 字符串转整数
    /*
    请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数）。
函数 myAtoi(string s) 的算法如下：
读入字符串并丢弃无用的前导空格
检查第一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。 确定最终结果是负数还是正数。
如果两者都不存在，则假定结果为正。
读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。如果没有读入数字，则整数为 0 。
必要时更改符号（从步骤 2 开始）。
如果整数数超过 32 位有符号整数范围 [−231,  231 − 1] ，需要截断这个整数，使其保持在这个范围内。
具体来说，小于 −231 的整数应该被固定为 −231 ，大于 231 − 1 的整数应该被固定为 231 − 1 。
返回整数作为最终结果。
注意：
本题中的空白字符只包括空格字符 ' ' 。
除前导空格或数字后的其余字符串外，请勿忽略 任何其他字符。
 2147483647
-2147483648
     */

    public int myAtoi(String s) {
        boolean start = true;
        boolean sign = true;
        int flag = 1;
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            if (start && s.charAt(i) == ' ') {
                continue;
            } else if (sign && (s.charAt(i) == '-' || s.charAt(i) == '+')) {
                start = false;
                sign = false;
                if (s.charAt(i) == '-') flag = -1;
            } else if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                int num = s.charAt(i) - '0';
                start = false;   // 2147483647   -2147483648
                sign = false;
                if (ans > 214748364) return flag == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                if (ans == 214748364 && flag == 1 && num > 7) return Integer.MAX_VALUE;
                if (ans == 214748364 && flag == -1 && num > 8) return Integer.MIN_VALUE;
                ans = ans * 10 + num;
            } else {
                break;
            }
        }
        return ans * flag;
    }

    @Test
    public void test08() {
        myAtoi("42");
    }

    //9、回文数
    /*
    给你一个整数 x ，如果 x 是一个回文整数，返回 ture ；否则，返回 false 。
回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。例如，121 是回文，而 123 不是
     */
    public boolean isPalindrome01(int x) {
        if (x < 0) return false;
        int copy = x;
        int reverse = 0;
        while (copy != 0) {
            reverse = reverse * 10 + copy % 10;
            copy /= 10;
        }
        return reverse == x;
    }

    //上一版有溢出问题，溢出了当然也不是回文了
    public boolean isPalindrome01_m(int x) {
        if (x < 0 || (x != 0 && x % 10 == 0)) return false;
        int reverse = 0;
        while (reverse < x) {
            reverse = reverse * 10 + x % 10;
            x /= 10;
        }
        return reverse == x || reverse / 10 == x;
    }

    public boolean isPalindrome02(int x) {
        if (x < 0) return false;
        char[] chars = String.valueOf(x).toCharArray();
        for (int i = 0, j = chars.length - 1; i < j; i++, j--) {
            if (chars[i] != chars[j]) return false;
        }
        return true;
    }

    //10. 正则表达式匹配
    /*
    给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
    '.' 匹配任意单个字符
    '*' 匹配零个或多个前面的那一个元素
    所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
     */

    public boolean isMatch(String s, String p) {
        if (s == null || p == null) return false;
        char[] str = s.toCharArray();
        char[] pp = p.toCharArray();
        return match2(str, 0, pp, 0);
    }

    public boolean match2(char[] str, int ss, char[] pattern, int sp) {
        if (ss == str.length && sp == pattern.length) return true;
        if (sp < pattern.length - 1 && pattern[sp + 1] == '*') {
            if ((ss < str.length && str[ss] == pattern[sp]) || (ss < str.length && pattern[sp] == '.')) {
                return match2(str, ss + 1, pattern, sp + 2) || match2(str, ss, pattern, sp + 2) || match2(str, ss + 1, pattern, sp);
            }
            return match2(str, ss, pattern, sp + 2);
        } else {
            if (ss == str.length || sp == pattern.length) return false;
            if (str[ss] == pattern[sp] || pattern[sp] == '.') return match2(str, ss + 1, pattern, sp + 1);
            return false;
        }
    }

    public boolean isMatch2(String s, String p) {
        char[] str = s.toCharArray();
        char[] ptr = p.toCharArray();
        int m = str.length;
        int n = ptr.length;

        boolean[][] flag = new boolean[m + 1][n + 1];
        flag[0][0] = true;

        for (int i = 0; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (ptr[j - 1] == '*') {
                    flag[i][j] = flag[i][j - 2];
                    if (matchs(str,ptr,i,j-1)) flag[i][j] = flag[i][j]||flag[i-1][j];
                }else{
                    if (matchs(str,ptr,i,j)) flag[i][j] = flag[i-1][j-1];
                }
            }
        }

        return flag[m][n];
    }

    public boolean matchs(char[] s, char[] p, int i, int j) {
        if (i == 0) return false;
        if (p[j - 1] == '.') return true;
        return s[i - 1] == p[j - 1];
    }
}
