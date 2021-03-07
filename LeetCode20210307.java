package com.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * @author xdwang
 * @create 2021-03-07 15:29
 */
public class LeetCode20210307 {
    public <E> E getValue() {
        return (E) "1";
    }

    @Test
    public void test() {
        Integer value = getValue();
        System.out.println(value.getClass());
    }

    //242. 有效的字母异位词
    //1 只包含小写字母
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] count = new int[26];
        char[] schars = s.toCharArray();
        char[] tchars = t.toCharArray();
        for (int i = 0; i < schars.length; i++) {
            count[schars[i] - 'a']++;
            count[tchars[i] - 'a']--;
        }
        for (int i = 0; i < 26; i++) {
            if (count[i] != 0) return false;
        }
        return true;
    }

    //2
    public boolean isAnagram2(String s, String t) {
        if (t.length() != s.length()) return false;
        char[] schars = s.toCharArray();
        char[] tchars = t.toCharArray();
        HashMap<Character, Integer> count = new HashMap<>();

        for (int i = 0; i < schars.length; i++) {
            count.put(schars[i], count.getOrDefault(schars[i], 0) + 1);
        }
        for (int i = 0; i < schars.length; i++) {
            count.put(tchars[i], count.getOrDefault(tchars[i], 0) - 1);
            if (count.get(tchars[i]) < 0) return false;
        }
        return true;
    }

    //257. 二叉树的所有路径
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> list = new ArrayList<>();
        if (root == null) return list;
        dfs(new ArrayList<Integer>(), root, list);
        return list;
    }

    public void dfs(ArrayList<Integer> stack, TreeNode root, List<String> list) {
        stack.add(root.val);
        if (root.left == null && root.right == null) {
            StringBuffer sb = new StringBuffer();
            sb.append(stack.get(0));
            for (int i = 1; i < stack.size(); i++) {
                sb.append("->");
                sb.append(stack.get(i));
            }
            list.add(sb.toString());
            stack.remove(stack.size() - 1);
            return;
        }
        if (root.left != null) dfs(stack, root.left, list);
        if (root.right != null) dfs(stack, root.right, list);
        stack.remove(stack.size() - 1);
    }

    List<String> ans = new ArrayList<>();
    List<Integer> list = new ArrayList<>();

    public List<String> binaryTreePaths2(TreeNode root) {
        if (root == null) return ans;
        list.add(root.val);
        if (root.left == null && root.right == null) {
            StringBuffer sb = new StringBuffer();
            sb.append(list.get(0));
            for (int i = 1; i < list.size(); i++) {
                sb.append("->");
                sb.append(list.get(i));
            }
            ans.add(sb.toString());
        }
        if (root.left != null) binaryTreePaths(root.left);
        if (root.right != null) binaryTreePaths(root.right);
        list.remove(list.size() - 1);
        return ans;
    }

    //258. 各位相加
    public int addDigits(int num) {
        return (num - 1) % 9 + 1;
    }

    //263. 丑数
    public boolean isUgly(int num) {
        if (num < 1) return false;
        while (num % 5 == 0) num /= 5;
        while (num % 3 == 0) num /= 3;
        while (num % 2 == 0) num /= 2;
        if (num == 1) return true;
        return false;
    }

    //268. 丢失的数字
    public int missingNumber(int[] nums) {
        int len = nums.length;
        int sum = (1+len)*len/2;
        for (int num : nums) {
            sum -= num;
        }
        return sum;
    }
}
