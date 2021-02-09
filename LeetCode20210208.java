package com.leetcode;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author xdwang
 * @create 2021-02-08 9:26
 */
public class LeetCode20210208 {
    /*
    1、两数之和
    给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出
   和为目标值 的那 两个 整数，并返回它们的数组下标。你可以假设每种输入
    只会对应一个答案。但是，数组中同一个元素不能使用两遍。你可以按任意
    顺序返回答案。
   */
    //第一种 暴力法
    public int[] twoSum01(int[] nums, int target) {
        int[] ans = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    ans[0] = i;
                    ans[1] = j;
                    return ans;
                }
            }
        }
        return ans;
    }

    //第二种 哈希法
    //hashMap.containsKey(target - nums[i]) 比 hashMap.get(target - nums[i])!=null好点
    public int[] twoSum02(int[] nums, int target) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashMap.containsKey(target - nums[i])) {
                return new int[]{hashMap.get(target - nums[i]), i};
            }
            hashMap.put(nums[i], i);
        }
        return new int[0];
    }

    //2、两数相加
    /*
   给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 
    逆序 的方式存储的，并且每个节点只能存储 一位 数字。
   请你将两个数相加，并以相同形式返回一个表示和的链表。
    你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     */
    //leetcode有点意思，不需要考虑特殊的情况，主要放在代码主要逻辑上
    //不为了代码的简单，为了 较好的执行，较少的判断
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

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode ans = new ListNode(0);
        ListNode cur = ans;
        int pre = 0;
        while (l1 != null && l2 != null) {
            int sum = l1.val + l2.val + pre;
            cur.next = new ListNode(sum % 10);
            pre = sum / 10;
            cur = cur.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        ListNode res = l1 == null ? l2 : l1;
        while (res != null) {
            int sum = res.val + pre;
            cur.next = new ListNode(sum % 10);
            pre = sum / 10;
            cur = cur.next;
            res = res.next;
        }
        if (pre != 0) cur.next = new ListNode(pre);
        return ans.next;
    }

    //3、无重复最长子串
    /*
    给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
    */
    //第一种 HashMap法
    public int lengthOfLongestSubstring01(String s) {
        int l = 0;
        int m = 0;
        char[] c = s.toCharArray();
        HashMap<Character, Integer> map = new HashMap<>();
        for (int r = 0; r < c.length; r++) {
            if (map.containsKey(c[r]) && map.get(c[r]) >= l) {
                m = Math.max(r - l, m);
                l = map.get(c[r]) + 1;
            }
            map.put(c[r], r);
        }
        return Math.max(c.length - l, m);
    }

    public int lengthOfLongestSubstring01_1(String s) {
        int l = 0;
        int m = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int r = 0; r < s.length(); r++) {
            if (map.containsKey(s.charAt(r)) && map.get(s.charAt(r)) >= l) {
                m = Math.max(r - l, m);
                l = map.get(s.charAt(r)) + 1;
            }
            map.put(s.charAt(r), r);
        }
        return Math.max(s.length() - l, m);
    }

    //第二种  看了时间最短的提交，为啥要用hashmap呢？ 不需要
    public int lengthOfLongestSubstring02(String s) {
        int left = 0;
        int max = 0;
        char[] chars = s.toCharArray();
        outer:
        for (int right = 0; right < chars.length; right++) {
            for (int j = left; j < right; j++) {
                if (chars[j] == chars[right]) {
                    left = j + 1;
                    continue outer;
                }
            }
            max = Math.max(right - left + 1, max);
        }
        return max;
    }

    public int lengthOfLongestSubstring02_2(String s) {
        int l = 0;
        int max = 0;
        outer:
        for (int r = 0; r < s.length(); r++) {
            for (int j = l; j < r; j++) {
                if (s.charAt(j) == s.charAt(r)) {
                    l = j + 1;
                    continue outer;
                }
            }
            max = Math.max(r - l + 1, max);
        }
        return max;
    }

    @Test
    public void test03() {
        lengthOfLongestSubstring01("abcabcbb");
    }

    //4、寻找两个正序数组的中位数
    /*
    给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。
    请你找出并返回这两个正序数组的中位数
     */
    //我的解法 3ms 39.5MB
    public double findMedianSortedArrays01(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int len = m + n;
        int[] me = new int[len];
        int loc = 0;
        for (int i = 0, j = 0; i < m || j < n; ) {
            if (j == n || (i < m && nums1[i] < nums2[j])) {
                me[loc++] = nums1[i++];
            } else {
                me[loc++] = nums2[j++];
            }
        }
        if (len % 2 == 0) return (me[len / 2] + me[len / 2 - 1]) / 2.0;
        return me[len / 2];
    }

    //看了题解，改,时间上改 还是3ms 39.5MB
    public double findMedianSortedArrays01_2(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int len = m + n;
        int[] me = new int[len];
        int loc = 0;
        for (int i = 0, j = 0; i < m || j < n; ) {
            if (j == n || (i < m && nums1[i] < nums2[j])) {
                me[loc++] = nums1[i++];
            } else {
                me[loc++] = nums2[j++];
            }
            if (loc > len / 2) break;
        }
        if (len % 2 == 0) return (me[len / 2] + me[len / 2 - 1]) / 2.0;
        return me[len / 2];
    }

    //时间空间上都改 2ms 39.6MB
    public double findMedianSortedArrays01_3(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int len = m + n;
        int num1 = 0;
        int num2 = 0;
        int flag = len % 2;
        int loc = 0;
        for (int i = 0, j = 0; i < m || j < n; ) {
            int num = 0;
            if (j == n || (i < m && nums1[i] < nums2[j])) {
                num = nums1[i++];
            } else {
                num = nums2[j++];
            }
            if (loc == len / 2 - 1 + flag) num1 = num;
            if (loc == len / 2) {
                num2 = num;
                break;
            }
            loc++;
        }
        return (num1 + num2) / 2.0;
    }

    //看题解，再次优化
    public double findMedianSortedArrays01_4(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int len = m + n;
        int num1 = 0, num2 = 0;
        int flag = len % 2;
        int i = 0, j = 0;
        for (int loc = 0; loc <= len / 2; loc++) {
            if (j == n || (i < m && nums1[i] < nums2[j])) {
                if (loc == len / 2 - 1 + flag) num1 = nums1[i];
                if (loc == len / 2) {
                    num2 = nums1[i];
                    break;
                }
                i++;
            } else {
                if (loc == len / 2 - 1 + flag) num1 = nums2[j];
                if (loc == len / 2) {
                    num2 = nums2[j];
                    break;
                }
                j++;
            }
        }
        return (num1 + num2) / 2.0;
    }

    //O(log(n+m)) 解法
    public double findMedianSortedArrays02(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int len = n + m;
        int flag = len % 2;
        int k = len / 2 + flag;
        int i = 0, j = 0;
        while (k != 1) {
            if (i == n || j == m) break;
            int n1 = Math.min(i + k / 2 - 1, n - 1);
            int n2 = Math.min(j + k / 2 - 1, m - 1);
            if (nums1[n1] < nums2[n2]) {
                k = k - (n1 - i + 1);
                i = n1 + 1;
            } else {
                k = k - (n2 - j + 1);
                j = n2 + 1;
            }
        }
        if (i == n) return (nums2[j + k - 1] + nums2[j + k - flag]) / 2.0;
        if (j == m) return (nums1[i + k - 1] + nums1[i + k - flag]) / 2.0;
        int n1 = 0, n2 = 0;
        if (nums1[i] < nums2[j]) {
            n1 = nums1[i];
            i++;
        } else {
            n1 = nums2[j];
            j++;
        }
        if (flag == 1) return n1;
        if (i == n) n2 = nums2[j];
        else if (j == m) n2 = nums1[i];
        else n2 = Math.min(nums2[j], nums1[i]);
        return (n1 + n2) / 2.0;
    }

    //递归，还有这样竟然比我快，。。改我的非递归
    public double findMedianSortedArrays02_2(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int left = (n + m + 1) / 2;
        int right = (n + m + 2) / 2;
        //将偶数和奇数的情况合并，如果是奇数，会求两次同样的 k 。
        return (getKth(nums1, 0, n - 1, nums2, 0, m - 1, left) + getKth(nums1, 0, n - 1, nums2, 0, m - 1, right)) * 0.5;
    }

    private int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;
        //让 len1 的长度小于 len2，这样就能保证如果有数组空了，一定是 len1
        // if (len1 > len2) return getKth(nums2, start2, end2, nums1, start1, end1, k);
        if (len1 == 0) return nums2[start2 + k - 1];
        if (len2 == 0) return nums1[start1 + k - 1];
        if (k == 1) return Math.min(nums1[start1], nums2[start2]);

        int i = start1 + Math.min(len1, k / 2) - 1;
        int j = start2 + Math.min(len2, k / 2) - 1;

        if (nums1[i] > nums2[j]) {
            return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        } else {
            return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }
    }


    //垃圾时间测试
    public double findMedianSortedArrays02_03(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int len = n + m;
        int left = (len + 1) / 2;
        int right = (len + 2) / 2;
        //将偶数和奇数的情况合并，如果是奇数，会求两次同样的 k 。
        if (len % 2 == 0) return (getKth(nums1, n, nums2, m, left) + getKth(nums1, n, nums2, m, right)) * 0.5;
        return getKth(nums1, n, nums2, m, left);
    }

    public int getKth(int[] nums1, int n, int[] nums2, int m, int k) {
        int len = n + m;
        int i = 0, j = 0;
        while (true) {
            if (i == n) return nums2[j + k - 1];
            if (j == m) return nums1[i + k - 1];
            if (k == 1) return Math.min(nums1[i], nums2[j]);
            int n1 = Math.min(i + k / 2 - 1, n - 1);
            int n2 = Math.min(j + k / 2 - 1, m - 1);
            if (nums1[n1] < nums2[n2]) {
                k = k - (n1 - i + 1);
                i = n1 + 1;
            } else {
                k = k - (n2 - j + 1);
                j = n2 + 1;
            }
        }
    }

    //Olog(min(n,m))算法
    public double findMedianSortedArrays03(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) return findMedianSortedArrays03(nums2, nums1);
        int n = nums1.length;
        int m = nums2.length;
        int left = 0, right = n;
        while (left < right) {
            int i = (left + right) / 2;
            int j = (n + m) / 2 - i - 1;
            int nums_im1 = nums1[i];
            int nums_j = nums2[j];
            if (nums_im1 <= nums_j) {
                left = i + 1;
            } else {
                right = i;
            }
        }
        right = (n + m) / 2 - left;
        int nums_im1 = left == 0 ? Integer.MIN_VALUE : nums1[left - 1];
        int nums_i = left == n ? Integer.MAX_VALUE : nums1[left];
        int nums_jm1 = right == 0 ? Integer.MIN_VALUE : nums2[right - 1];
        int nums_j = right == m ? Integer.MAX_VALUE : nums2[right];
        int median1 = Math.max(nums_im1, nums_jm1);
        int median2 = Math.min(nums_i, nums_j);
        return (m + n) % 2 == 0 ? (median1 + median2) / 2.0 : median2;
    }

    @Test
    public void test04() {
        System.out.println(findMedianSortedArrays03(new int[]{1,3}, new int[]{2,7}));
    }
}
