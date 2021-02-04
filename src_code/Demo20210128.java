package com.xdwang;

import java.util.Stack;

/**
 * @author xdwang
 * @create 2021-01-28 17:45
 */

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

public class Demo20210128 {

    //4重建二叉树
    public static TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        if (pre == null || pre.length == 0 || in == null || in.length == 0 || pre.length != in.length) return null;
        return dfs(pre, 0, in, 0, in.length - 1);
    }

    public static TreeNode dfs(int[] pre, int start, int[] in, int l, int r) {
        if (l > r) return null;
        TreeNode head = new TreeNode(pre[start]);
        for (int i = l; i <= r; i++) {
            if (in[i] == pre[start]) {
                head.left = dfs(pre, start + 1, in, l, i - 1);
                head.right = dfs(pre, start + 1 + i - l, in, i + 1, r);
                break;
            }
        }
        return head;
    }


    //5、用两个栈实现队列
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
            stack1.add(node);
    }

    public int pop() {
            if (stack2.isEmpty()){
                while (!stack1.isEmpty()){
                    stack2.add(stack1.pop());
                }
            }
            return stack2.pop();
    }


    //6、旋转数组的最小数字
    public int minNumberInRotateArray(int [] array) {
        if (array == null || array.length == 0) return 0;
        int l = 0;
        int r = array.length - 1;
        while (l < r){
            if (array[l] < array[r]) return array[l];
            int mid = (l + r)/2;
            if (array[mid] < array[r]){
                r = mid;
            }else if(array[mid] > array[r]){
                l = mid + 1;
            }else{
                r--;
            }
        }
        return array[l];
    }
}
