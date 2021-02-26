package com.leetcode;

/**
 * @author xdwang
 * @create 2021-02-26 15:35
 */
public class LeetCode20210226 {

    //70. 爬楼梯
    public int climbStairs(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        int num1 = 1;
        int num2 = 2;
        for (int i = 3; i <= n; i++) {
            num2 = num1 + num2;
            num1 = num2 - num1;
        }
        return num2;
    }

    //83. 删除排序链表中的重复元素
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) return head;
        ListNode cur = head;
        while (cur.next != null) {
            if (cur.val == cur.next.val) cur.next = cur.next.next;
            else cur = cur.next;
        }
        return head;
    }

    //88. 合并两个有序数组
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int l = n + m;
        l--;
        n--;
        m--;
        while (n >= 0) {
            if (m < 0 || nums1[m] < nums2[n]) {
                nums1[l--] = nums2[n--];
            } else {
                nums1[l--] = nums1[m--];
            }
        }
    }

    //100. 相同的树
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null || q.val != p.val) return false;
        return isSameTree(p.right, q.right) && isSameTree(p.left, q.left);
    }

    //101. 对称二叉树
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isSameTree2(root.left, root.right);
    }

    public boolean isSameTree2(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null || q.val != p.val) return false;
        return isSameTree2(p.right, q.left) && isSameTree2(p.left, q.right);
    }

    //104. 二叉树的最大深度
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(maxDepth(root.left),maxDepth(root.right))+1;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
