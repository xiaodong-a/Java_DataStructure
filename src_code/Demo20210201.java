package com.xdwang;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author xdwang
 * @create 2021-02-01 9:25
 */
public class Demo20210201 {
    class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    //18、二叉树 镜像
    //01、递归1
    public void Mirror01(TreeNode root) {
        if (root == null) return;
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        Mirror01(root.left);
        Mirror01(root.right);
    }

    //02、递归2
    public void Mirror02(TreeNode root) {
        if (root == null) return;
        Mirror02(root.left);
        Mirror02(root.right);
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
    }


    //03、非递归 需要后序遍历
    public void Mirror03(TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        TreeNode pre = root;
        while (!stack.isEmpty()) {
            root = stack.peek();
            if (root.left != null && pre != root.left && pre != root.right) {
                root = root.left;
                stack.add(root);
            } else if (root.right != null && pre != root.right) {
                root = root.right;
                stack.add(root);
            } else {
                pre = root.left;
                root.left = root.right;
                root.right = pre;
                pre = root;
                stack.pop();
            }
        }
    }

    //4、非递归,层次遍历
    public void Mirror04(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur.left != null) queue.add(cur.left);
            if (cur.right != null) queue.add(cur.right);
            TreeNode temp = cur.left;
            cur.left = cur.right;
            cur.right = temp;
        }
    }


    //19、顺时针打印矩阵
    public ArrayList<Integer> printMatrix(int[][] matrix) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        int s_row = 0;
        int e_row = matrix.length - 1;
        int s_col = 0;
        int e_col = matrix[0].length - 1;
        while (s_row <= e_row && s_col <= e_col) {
            for (int i = s_col; i <= e_col; i++)
                res.add(matrix[s_row][i]);
            for (int i = s_row + 1; i <= e_row; i++)
                res.add(matrix[i][e_col]);
            if (s_row < e_row) {
                for (int i = e_col - 1; i >= s_col; i--)
                    res.add(matrix[e_row][i]);
            }
            if (s_col < e_col) {
                for (int i = e_row - 1; i > s_row; i--)
                    res.add(matrix[i][s_col]);
            }
            s_row++;
            e_row--;
            s_col++;
            e_col--;
        }
        return res;
    }

    //20、包含min函数的栈
//    Stack<Integer> stack1 = new Stack<Integer>();
//    Stack<Integer> stack2 = new Stack<Integer>();
//
//    public void push(int node) {
//        stack1.add(node);
//        if (!stack2.isEmpty() && stack2.peek() <= node) {
//            stack2.add(stack2.peek());
//        } else {
//            stack2.add(node);
//        }
//    }
//
//    public void pop() {
//        stack1.pop();
//        stack2.pop();
//    }
//
//    public int top() {
//        return stack1.peek();
//    }
//
//    public int min() {
//        return stack2.peek();
//    }

    //;省空间方法
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.add(node);
        if (stack2.isEmpty() || stack2.peek() >= node) {
            stack2.add(node);
        }
    }

    public void pop() {
        if (stack1.peek() == stack2.peek()) stack2.pop();
        stack1.pop();
    }

    public int top() {
        return stack1.peek();
    }

    public int min() {
        return stack2.peek();
    }

    //21、栈的压入、弹出序列
    public boolean IsPopOrder(int[] pushA, int[] popA) {
        Stack<Integer> stack = new Stack<>();
        int loc = 0;
        for (int i = 0; i < popA.length; i++) {
            if (stack.isEmpty() || stack.peek() != popA[i]) {
                while (loc < popA.length) {
                    if (pushA[loc] == popA[i]) break;
                    stack.add(pushA[loc]);
                    loc++;
                }
                if (loc == popA.length) return false;
                loc++;
            } else {
                stack.pop();
            }
        }
        return true;
    }


//    public boolean IsPopOrder2(int[] pushA, int[] popA) {
//        Stack<Integer> stack = new Stack<>();
//        int i = 0;
//        int j = 0;
//        while (i < pushA.length) {
//            if (stack.isEmpty() || stack.peek() != popA[i]) {
//                stack.add(pushA[j++]);  不好，后序还要pop
//            } else {
//                stack.pop();
//                i++;
//            }
//        }
//        return stack.isEmpty();
//    }

    public boolean IsPopOrder3(int[] pushA, int[] popA) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0, j = 0; i < pushA.length; i++) {
            stack.add(pushA[i]);
            while (!stack.isEmpty() && stack.peek() == popA[j]) {
                stack.pop();
                j++;
            }
        }
        return stack.isEmpty();
    }


    @Test
    public void test1() {
        int[] a = new int[]{1, 2, 3, 4};
        int[] b = new int[]{3, 2, 1, 4};
        System.out.println(IsPopOrder3(a, b));
    }

    //22、
    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root == null) return res;
        queue.add(root);
        while (!queue.isEmpty()){
            root = queue.poll();
            res.add(root.val);
            if (root.left != null) queue.add(root.left);
            if (root.right != null) queue.add(root.right);
        }
        return res;
    }
}
