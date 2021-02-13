package com.leetcode;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Predicate;

/**
 * @author xdwang
 * @create 2021-02-13 08:10
 */
class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

public class LeetCdde20210213 {
    //21. 合并两个有序链表
    public ListNode mergeTwoLists01(ListNode l1, ListNode l2) {
        ListNode ans = new ListNode(0);
        ListNode cur = ans;
        while (l1 != null && l2 != null) {
            if (l1.val > l2.val) {
                cur.next = l2;
                l2 = l2.next;
            } else {
                cur.next = l1;
                l1 = l1.next;
            }
            cur = cur.next;
        }
        cur.next = l1 == null ? l2 : l1;
        return ans.next;
    }

    //22、括号生成
    /*
    数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
     */
    public List<String> generateParenthesis(int n) {
        List<String> ans = new LinkedList<>();
        char[] chars = new char[2 * n];
        chars[0] = '(';
        dfs(chars, ans, 1, 1, 0, n);
        return ans;
    }

    public void dfs(char[] chars, List<String> ans, int index, int i, int j, int n) {
        if (index == 2 * n) {
            ans.add(String.valueOf(chars));
            return;
        }
        if (i < n) {
            chars[index] = '(';
            dfs(chars, ans, index + 1, i + 1, j, n);
        }
        if (j < n && j < i) {
            chars[index] = ')';
            dfs(chars, ans, index + 1, i, j + 1, n);
        }
    }

    //23、合并K个升序链表
    /*
    给你一个链表数组，每个链表都已经按升序排列。
请你将所有链表合并到一个升序链表中，返回合并后的链表。
     */
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> queue = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode t0, ListNode t1) {
                return t0.val - t1.val;
            }
        });
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) queue.add(lists[i]);
        }
        ListNode head = new ListNode(0);
        ListNode cur = head;
        while (!queue.isEmpty()) {
            cur.next = queue.poll();
            cur = cur.next;
            if (cur.next != null) queue.add(cur.next);
        }
        return head.next;
    }

    //
    public ListNode mergeKLists02(ListNode[] lists) {
        return merge(lists, 0, lists.length - 1);
    }

    public ListNode merge(ListNode[] lists, int l, int r) {
        if (l == r) {
            return lists[l];
        }
        if (l > r) {
            return null;
        }
        int mid = (l + r) >> 1;
        return mergeTwoLists(merge(lists, l, mid), merge(lists, mid + 1, r));
    }

    public ListNode mergeTwoLists(ListNode a, ListNode b) {
        if (a == null || b == null) {
            return a != null ? a : b;
        }
        ListNode head = new ListNode(0);
        ListNode tail = head, aPtr = a, bPtr = b;
        while (aPtr != null && bPtr != null) {
            if (aPtr.val < bPtr.val) {
                tail.next = aPtr;
                aPtr = aPtr.next;
            } else {
                tail.next = bPtr;
                bPtr = bPtr.next;
            }
            tail = tail.next;
        }
        tail.next = (aPtr != null ? aPtr : bPtr);
        return head.next;
    }

    //24. 两两交换链表中的节点
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode ans = head.next;
        ListNode pre = new ListNode(0);
        while (head != null && head.next != null) {
            ListNode next = head.next;
            pre.next = next;
            head.next = next.next;
            next.next = head;
            pre = head;
            head = head.next;
        }
        return ans;
    }

    //25、K 个一组翻转链表
    /*
    给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
k 是一个正整数，它的值小于或等于链表的长度。
如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode pre = new ListNode(0);
        ListNode ans = pre;
        ListNode tail = head;
        while (true) {
            int num = k;
            while (num > 0 && tail != null) {
                num--;
                tail = tail.next;
            }
            if (num != 0) {
                pre.next = head;
                return ans.next;
            }
            pre.next = reverse(head,tail);
            pre = head;
            head = tail;
        }

    }

    public ListNode reverse(ListNode head,ListNode tail){
        ListNode pre = null;
        ListNode next = head;
        while (next != tail) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

}
