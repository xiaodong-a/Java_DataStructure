package com.leetcode;

import java.util.*;

/**
 * @author xdwang
 * @create 2021-02-27 19:58
 */
public class LeetCode20210227 {
    //108. 将有序数组转换为二叉搜索树
    public TreeNode sortedArrayToBST(int[] nums) {
        return dfs(nums, 0, nums.length - 1);
    }

    public TreeNode dfs(int[] nums, int left, int right) {
        if (left > right) return null;
        if (left == right) return new TreeNode(nums[left]);
        int mid = (left + right) / 2;
        TreeNode ans = new TreeNode(nums[mid]);
        ans.left = dfs(nums, left, mid - 1);
        ans.right = dfs(nums, mid + 1, right);
        return ans;
    }

    //110. 平衡二叉树
    public boolean isBalanced(TreeNode root) {
        return isB(root) != -1;
    }

    public int isB(TreeNode root) {
        if (root == null) return 0;
        int left = isB(root.left);
        int right = isB(root.right);
        if (left == -1 || right == -1 || Math.abs(left - right) > 1) return -1;
        return Math.max(left, right) + 1;
    }

    //111. 二叉树的最小深度
    public int minDepth(TreeNode root){
        if (root == null) return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int count = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0){
                root = queue.poll();
                if (root.left == null && root.right == null) return count;
                if (root.left != null) queue.add(root.left);
                if (root.right != null) queue.add(root.right);
            }
            count++;
        }
        return count;
    }

    public int minDepth_dfs(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null) return minDepth_dfs(root.right)+1;
        if (root.right == null) return minDepth_dfs(root.left)+1;
        return Math.min(minDepth_dfs(root.left), minDepth_dfs(root.right)) + 1;
    }

    //112. 路径总和
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        if (root.left == null && root.right == null) return root.val == targetSum;
        boolean l = root.left == null ? false:hasPathSum(root.left,targetSum-root.val);
        boolean r = root.right == null ? false:hasPathSum(root.right,targetSum-root.val);
        return l||r;
    }
    //118. 杨辉三角
    public List<List<Integer>> generate(int numRows) {
        if (numRows == 0) return new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> line = new ArrayList<>();
        line.add(1);
        ans.add(line);
        for (int i = 2; i <= numRows; i++) {
            List<Integer> next = new ArrayList<>();
            next.add(1);
            for (int j = 0; j < line.size() - 1; j++) {
                next.add(line.get(j)+line.get(j+1));
            }
            next.add(1);
            ans.add(next);
            line = next;
        }
        return ans;
    }
}
