package com.leetcode;

import java.util.List;
import java.util.Stack;

/**
 * @author xdwang
 * @create 2021-03-01 21:40
 */
public class LeetCode20210301 {
    //141. 环形链表
    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != slow) {
            if (fast == null || fast.next == null) return false;
            fast = fast.next.next;
            slow = slow.next;
        }
        return true;
    }

    //155. 最小栈
    Stack<Integer> stack = new Stack<>();
    Stack<Integer> minStack = new Stack<>();

    public void push(int x) {
        stack.push(x);
        if (minStack.isEmpty() || minStack.peek() > x) {
            minStack.push(x);
        } else {
            minStack.push(minStack.peek());
        }
    }

    public void pop() {
        stack.pop();
        minStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }

    //160. 相交链表
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        ListNode p = headA;
        ListNode q = headB;
        while (p != q) {
            q = q.next;
            p = p.next;
            if (p != q) {
                if (p == null) p = headB;
                if (q == null) q = headA;
            }
        }
        return p;
    }

    //167. 两数之和 II - 输入有序数组
    public int[] twoSum(int[] numbers, int target) {
        int[] ans = new int[2];
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int num = numbers[left] + numbers[right];
            if (num < target) {
                left++;
            } else if (num > target) {
                right--;
            } else {
                ans[0] = left + 1;
                ans[1] = right + 1;
                return ans;
            }
        }
        return ans;
    }

    //168. Excel表列名称
    public String convertToTitle(int n) {
        StringBuilder ans = new StringBuilder();
        while (n != 0) {
            ans.append((char) ((--n) % 26 + 'A'));
            n /= 26;
        }
        return ans.reverse().toString();
    }
}
