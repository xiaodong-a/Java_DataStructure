package com.leetcode;

import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author xdwang
 * @create 2021-03-03 19:52
 */
public class LeetCode20210303 {
    //202. 快乐数
    public boolean isHappy(int n) {
        //  Set<Integer> set = new HashSet<Integer>();
        Set<Integer> set = new HashSet<>(Arrays.asList(4, 16, 37, 58, 89, 145, 42, 20));
        while (n != 1 && !set.contains(n)) {
            //        set.add(n);
            n = getNext(n);
        }
        return n == 1;
    }

    public int getNext(int n) {
        int ans = 0;
        while (n > 0) {
            int t = n % 10;
            n /= 10;
            ans += t * t;
        }
        return ans;
    }

    //203. 移除链表元素
    public ListNode removeElements(ListNode head, int val) {
        ListNode ans = new ListNode(0);
        ans.next = head;
        head = ans;
        while (head.next != null) {
            if (head.next.val == val) head.next = head.next.next;
            else head = head.next;
        }
        return ans.next;
    }

    //204. 计数质数
    public int countPrimes(int n) {
        if (n < 3) return 0;
        int ans = 1;
        for (int i = 3; i < n; i = i + 2) {
            if (isPrime(i)) ans++;
        }
        return ans;
    }

    public boolean isPrime(int n) {
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public int countPrimes2(int n) {
        int ans = 0;
        boolean[] flag = new boolean[n];
        for (int i = 2; i < n; i++) {
            if (!flag[i]) {
                ans++;
                if ((long) i * i < n) {
                    for (int j = i * i; j < n; j += i) {
                        flag[j] = true;
                    }
                }
            }
        }
        return ans;
    }

    public int countPrimes3(int n) {
        boolean[] flag = new boolean[n];
        ArrayList<Integer> primes = new ArrayList<>();
        for (int i = 2; i < n; i++) {
            if (!flag[i]) {
                primes.add(i);
            }
            for (int j = 0; j < primes.size() && i * primes.get(j) < n; j++) {
                flag[i * primes.get(j)] = true;
                if (i % primes.get(j) == 0) break;
            }
        }
        return primes.size();
    }
//    public int countPrimes(int n) {
//        int ans = 0;
//        for (int i = 2; i < n; ++i) {
//            ans += isPrime(i) ? 1 : 0;
//        }
//        return ans;
//    }
//
//    public boolean isPrime(int x) {
//        for (int i = 2; i * i <= x; ++i) {
//            if (x % i == 0) {
//                return false;
//            }
//        }
//        return true;
//    }

    //205. 同构字符串
    public boolean isIsomorphic(String s, String t) {
        HashMap<Character, Character> map = new HashMap<>();
        Set<Character> set = new HashSet<>();
        char[] schars = s.toCharArray();
        char[] tchars = t.toCharArray();
        for (int i = 0; i < schars.length; i++) {
            if (!map.containsKey(schars[i])) {
                if (set.contains(tchars[i])) return false;
                map.put(schars[i], tchars[i]);
                set.add(tchars[i]);
            } else {
                if (map.get(schars[i]) != tchars[i]) return false;
            }
        }
        return true;
    }

    //206. 反转链表
    public ListNode reverseList(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return head;
        ListNode temp = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return temp;
    }

    public ListNode reverseList2(ListNode head) {
        if (head == null) return null;
        ListNode pre = null;
        while (head!= null){
            ListNode next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

}
