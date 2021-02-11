package com.leetcode;

import org.junit.Test;

import java.awt.font.NumericShaper;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xdwang
 * @create 2021-02-11 10:01
 */
public class LeetCode20210211 {
    //对昨天的第10题，优化
    public boolean isMatch(String s, String p) {
        char[] str = s.toCharArray();
        char[] pp = p.toCharArray();
        int[][] flag = new int[str.length][pp.length];
        return match2(str, 0, pp, 0, flag);
    }

    public boolean match2(char[] str, int ss, char[] pattern, int sp, int[][] flag) {
        if (ss == str.length && sp == pattern.length) return true;
        if (sp == pattern.length) return false;
        if (ss == str.length) {
            if (((pattern.length - sp) & 1) == 1) return false;
            for (int j = sp + 1; j < pattern.length; j += 2) {
                if (pattern[j] != '*') return false;
            }
            return true;
        }
        if (flag[ss][sp] != 0) return flag[ss][sp] == 1;
        boolean ans;
        if (sp < pattern.length - 1 && pattern[sp + 1] == '*') {
            if (str[ss] == pattern[sp] || pattern[sp] == '.') {
                ans = match2(str, ss, pattern, sp + 2, flag) || match2(str, ss + 1, pattern, sp, flag);
            } else ans = match2(str, ss, pattern, sp + 2, flag);
        } else {
            if (str[ss] == pattern[sp] || pattern[sp] == '.') ans = match2(str, ss + 1, pattern, sp + 1, flag);
            else ans = false;
        }
        flag[ss][sp] = ans ? 1 : -1;
        return ans;
    }

    //11、盛最多水的容器
    /*
    给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n
    条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0) 。找出其中的两条线，使得它们与 
    x 轴共同构成的容器可以容纳最多的水。
    说明：你不能倾斜容器
     */
    public int maxArea01(int[] height) {
        int size = height.length;
        int max = 0;
        for (int i = 1; i < size; i++) {
            for (int j = 0; j + i < size; j++) {
                int num = Math.min(height[j], height[j + i]) * i;
                max = num > max ? num : max;
            }
        }
        return max;
    }

    public int maxArea02(int[] height) {
        int size = height.length;
        int max = 0;
        int i = 0;
        int j = size - 1;
        while (i < j) {
            int left = height[i];
            int right = height[j];
            max = Math.max(Math.min(left, right) * (j - i), max);
            if (left > right) i++;
            else j--;
        }
        return max;
    }

    public int maxArea02_m(int[] height) {
        int max = 0;
        int i = 0;
        int j = height.length - 1;
        while (i < j) {
            if (height[i] > height[j]) {
                int min = height[j];
                max = Math.max(max, min * (j - i));
                while (i < j && height[j] <= min) j--;
            } else {
                int min = height[i];
                max = Math.max(max, min * (j - i));
                while (i < j && height[i] <= min) i++;
            }
        }
        return max;
    }

    //12、整数转罗马
    /*
    罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。

字符          数值
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。

通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：

I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
     */

    public String intToRoman01(int num) {
        String[][] str = new String[][]{{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"}, {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"},
                {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"}, {"", "M", "MM", "MMM"}};
        StringBuilder sb = new StringBuilder();
        sb.append(str[3][num / 1000]);
        sb.append(str[2][num % 1000 / 100]);
        sb.append(str[1][num % 100 / 10]);
        sb.append(str[0][num % 10]);
        return sb.toString();
    }


    int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public String intToRoman02(int num) {
        StringBuilder sb = new StringBuilder();
        // Loop through each symbol, stopping if num becomes 0.
        for (int i = 0; i < values.length && num >= 0; i++) {
            // Repeat while the current symbol still fits into num.
            while (values[i] <= num) {
                num -= values[i];
                sb.append(symbols[i]);
            }
        }
        return sb.toString();
    }

    //13 罗马数字转整数， 借用上面的 values  symbols数组
    public int romanToInt01(String s) {
        int ans = 0;
        for (int i = 0; i < values.length && s.length() > 0; i++) {
            while (s.startsWith(symbols[i])) {
                ans += values[i];
                s = s.substring(symbols[i].length(), s.length());
            }
        }
        return ans;
    }

    public int romanToInt02(String s) {
        char[] str = s.toCharArray();
        int ans = 0;
        int prenum = getValue(str[0]);
        for (int i = 1; i < str.length; i++) {
            int num = getValue(str[i]);
            if (num > prenum) ans -= prenum;
            else ans += prenum;
            prenum = num;
        }
        return ans + prenum;
    }

    public int getValue(char ch) {
        switch (ch) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }

    //14 最长的公共前缀
    /*
    编写一个函数来查找字符串数组中的最长公共前缀。
    如果不存在公共前缀，返回空字符串 ""。
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        String ans = "";
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i >= strs[j].length() || strs[j].charAt(i) != c) return ans;
            }
            ans += c;
        }
        return ans;
    }


    public String longestCommonPrefix02(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        String res = strs[0];
        for (String s : strs) {
            while (!s.startsWith(res)) {
                res = res.substring(0, res.length() - 1);
            }
        }
        return res;
    }

    //15  三数之和
    /*
    给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
    注意：答案中不可以包含重复的三元组。
     */
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums.length < 3) return new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) return ans;
            if (i != 0 && nums[i] == nums[i - 1]) continue;
            int k = nums.length - 1;
            for (int j = i + 1; j < k; j++) {
                if (j != i + 1 && nums[j] == nums[j - 1]) continue;
                for (; k > j; k--) {
                    if (k != nums.length - 1 && nums[k] == nums[k + 1]) continue;
                    if (nums[i] + nums[j] + nums[k] < 0) break;
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        ArrayList<Integer> line = new ArrayList<>();
                        line.add(nums[i]);
                        line.add(nums[j]);
                        line.add(nums[k]);
                        ans.add(line);
                        break;
                    }
                }
            }
        }
        return ans;
    }


    public List<List<Integer>> threeSum02(int[] nums) {
        if (nums.length < 3) return new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) return ans;
            if (i != 0 && nums[i] == nums[i - 1]) continue;
            int k = nums.length - 1;
            int j = i + 1;
            while (j < k) {
                if (nums[i] + nums[j] + nums[k] < 0) j++;
                else if (nums[i] + nums[j] + nums[k] > 0) k--;
                else {
                    ArrayList<Integer> line = new ArrayList<>();
                    line.add(nums[i]);
                    line.add(nums[j]);
                    line.add(nums[k]);
                    ans.add(line);
                    while (j < k && nums[j] == nums[j + 1]) j++;
                    while (j < k && nums[k] == nums[k - 1]) k--;
                    j++;
                    k--;
                }
            }
        }
        return ans;
    }

    @Test
    public void test15() {
        List<List<Integer>> lists = threeSum(new int[]{-1, 0, 1, 2, -1, -4, -2, -3, 3, 0, 4});
        for (int i = 0; i < lists.size(); i++) {
            List<Integer> integers = lists.get(i);
            for (int i1 = 0; i1 < integers.size(); i1++) {
                System.out.print(integers.get(i1) + "\t");
            }
            System.out.println();
        }
    }
}
