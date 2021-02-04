package com.xdwang;

import com.sun.source.tree.Tree;

import java.util.List;
import java.util.Stack;

/**
 * @author xdwang
 * @create 2021-01-31 20:30
 */
class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}

public class Demo20210131 {
    //14、倒数第k个
    public ListNode FindKthToTail1(ListNode head, int k) {
        if (k < 1) return null;
        ListNode p = head;
        ListNode q = head;
        int count = 0;
        while (p != null) {
            p = p.next;
            if (++count > k) q = q.next;
        }
        if (count >= k) return q;
        return null;
    }


    //15、反转链表
    public ListNode ReverseList(ListNode head) {
        ListNode pre = null;
        ListNode next = head;
        while (next != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    //16、合并两个排序的链表
    public ListNode Merge1(ListNode list1, ListNode list2) {
        ListNode head = new ListNode(0);
        ListNode cur = head;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                cur.next = new ListNode(list1.val);
                list1 = list1.next;
            } else {
                cur.next = new ListNode(list2.val);
                list2 = list2.next;
            }
            cur = cur.next;
        }
        cur.next = list1 == null ? list2 : list1;
        return head.next;
    }

    //递归版本
    public ListNode Merge(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        ListNode head;
        if (list1.val < list2.val) {
            head = new ListNode(list1.val);
            head.next = Merge(list1.next, list2);
        } else {
            head = new ListNode(list2.val);
            head.next = Merge(list1, list2.next);
        }
        return head;
    }

    //递归的优化

    public ListNode Mergem(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        if (list1.val < list2.val) {
            list1.next = Mergem(list1.next, list2);
            return list1;
        } else {
            list2.next = Mergem(list1, list2.next);
            return list2;
        }
    }

    class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }
    }

    //17、树的子结构
    public boolean HasSubtree(TreeNode root1, TreeNode root2) {
        if (root2 == null || root1 == null) return false;
        return dfs(root1, root2) || HasSubtree(root1.left, root2) || HasSubtree(root1.right, root2);
    }

    public boolean dfs(TreeNode root1, TreeNode root2) {
        if (root2 == null) return true;
        if (root1 == null) return false;
        return root1.val == root2.val && dfs(root1.left, root2.left) && dfs(root1.right, root2.right);
    }

    //非递归
    public boolean HasSubtree3(TreeNode root1, TreeNode root2) {
        if (root2 == null) return false;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        while (root1 != null || !stack.isEmpty()) {
            if (root1 != null) {
                stack.add(root1);
                root1 = root1.left;
            } else {
                root1 = stack.pop();
                if (dfs3(root1, root2)) return true;
                root1 = root1.right;
            }
        }
        return false;
    }

    public boolean dfs3(TreeNode root1, TreeNode root2) {
        Stack<TreeNode> stack1 = new Stack<TreeNode>();
        Stack<TreeNode> stack2 = new Stack<TreeNode>();
        while (root2 != null || !stack2.isEmpty()) {
            if (root2 != null) {
                stack2.add(root2);
                if (root1 == null || root1.val != root2.val) return false;
                root2 = root2.left;
                stack1.add(root1);
                root1 = root1.left;

            } else {
                root2 = stack2.pop();
                root1 = stack1.pop();
                root2 = root2.right;
                root1 = root1.right;
            }
        }
        return true;
    }

    //如果是判断子树的话
    public boolean HasSubtree2(TreeNode root1, TreeNode root2) {
        if (root2 == null || root1 == null) return false;
        return theSame(root1, root2) || HasSubtree2(root1.left, root2) || HasSubtree2(root1.right, root2);
    }

    public boolean theSame(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) return true;
        if (root1 == null || root2 == null || root1.val != root2.val) return false;
        return theSame(root1.left, root2.left) && theSame(root1.right, root2.right);
    }


}
