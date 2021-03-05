package com.leetcode;

import javax.print.DocFlavor;
import java.util.*;

/**
 * @author xdwang
 * @create 2021-03-05 16:48
 */
public class LeetCode20210305 {
    //217. 存在重复元素
    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i])) return true;
            set.add(nums[i]);
        }
        return false;
    }

    //219. 存在重复元素 II
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        for (int i = 1; i < nums.length; i++) {
            for (int j = Math.max(i - k, 0); j < i; j++) {
                if (nums[j] == nums[i]) return true;
            }
        }
        return false;
    }

    public boolean containsNearbyDuplicate2(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i]) && i - map.get(nums[i]) <= k) return true;
            map.put(nums[i], k);
        }
        return false;
    }

    public boolean containsNearbyDuplicate3(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; ++i) {
            if (set.contains(nums[i])) return true;
            set.add(nums[i]);
            if (set.size() > k) {
                set.remove(nums[i - k]);
            }
        }
        return false;
    }
    //225. 用队列实现栈

    Queue<Integer> q1 = new LinkedList<>();
    Queue<Integer> q2 = new LinkedList<>();
    int top = 0;

    public void push(int x) {
        if (q1.isEmpty()) q2.add(x);
        else q1.add(x);
        top = x;
    }

    /**
     * Removes the element on top of the stack and returns that element.
     */
    public int pop() {
        Queue<Integer> p1 = q1.isEmpty() ? q2 : q1;
        Queue<Integer> p2 = q1.isEmpty() ? q1 : q2;
        while (p1.size() > 1) {
            top = p1.poll();
            p2.add(top);
        }
        return p1.poll();
    }

    /**
     * Get the top element.
     */
    public int top() {
        return top;
    }

    /**
     * Returns whether the stack is empty.
     */
    public boolean empty() {
        return q1.isEmpty() && q2.isEmpty();
    }

    //226. 翻转二叉树
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        TreeNode left = root.left;
        TreeNode right = root.right;
        root.left = right;
        root.right = left;
        invertTree(left);
        invertTree(right);
        return root;
    }

    //228. 汇总区间
    public List<String> summaryRanges(int[] nums) {
        if (nums == null || nums.length == 0) return new LinkedList<>();
        int pre = nums[0];
        List<String> ans = new LinkedList<>();
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) continue;
            StringBuilder sb = new StringBuilder();
            sb.append(pre);
            if (nums[i - 1] != pre) {
                sb.append("->");
                sb.append(nums[i - 1]);
            }
            ans.add(sb.toString());
            pre = nums[i];
        }
        StringBuilder sb = new StringBuilder();
        sb.append(pre);
        if (nums[nums.length - 1] != pre) {
            sb.append("->");
            sb.append(nums[nums.length - 1]);
        }
        ans.add(sb.toString());
        return ans;
    }
}
