package com.leetcode;

import org.junit.Test;

import java.util.*;

/**
 * @author xdwang
 * @create 2021-02-12 9:52
 */
public class LeetCode20210212 {
    //16、最接近的三数之和
    /*
    给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 
    中的三个整数，使得它们的和与 target 最接近。返回这三个数的和。
    假定每组输入只存在唯一答案。
    提示：
    3 <= nums.length <= 10^3
    -10^3 <= nums[i] <= 10^3
    -10^4 <= target <= 10^4
     */
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int min = Integer.MAX_VALUE;
        int sum = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] + nums[i + 1] + nums[i + 2] - target > min) break;
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int k = nums.length - 1;
            int j = i + 1;
            while (j < k) {
                int num = nums[i] + nums[j] + nums[k] - target;
                if (Math.abs(num) < min) {
                    min = Math.abs(num);
                    sum = num + target;
                }
                if (num > 0) {
                    while (j < k && nums[k] == nums[k - 1]) k--;
                    k--;
                } else if (num < 0) {
                    while (j < k && nums[j] == nums[j + 1]) j++;
                    j++;
                } else return target;
            }
        }
        return sum;
    }


    public int threeSumClosest02(int[] nums, int target) {
        Arrays.sort(nums);
        int min = Integer.MAX_VALUE;
        int sum = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            int k = nums.length - 1;
            int j = i + 1;
            int ss = nums[i] + nums[i + 1] + nums[i + 2];
            if (ss - target > min) break;
            if (ss >= target) {
                k = j + 1;
            } else if (nums[i] + nums[k] + nums[k - 1] <= target) {
                j = k - 1;
            }
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            while (j < k) {
                int num = nums[i] + nums[j] + nums[k] - target;
                if (Math.abs(num) < min) {
                    min = Math.abs(num);
                    sum = num + target;
                }
                if (num > 0) {
                    while (j < k && nums[k] == nums[k - 1]) k--;
                    k--;
                } else if (num < 0) {
                    while (j < k && nums[j] == nums[j + 1]) j++;
                    j++;
                } else return target;
            }
        }
        return sum;
    }


    //17. 电话号码的字母组合
    /*
    给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     */
    String[][] str = {{"a", "b", "c"}, {"d", "e", "f"}, {"g", "h", "i"},
            {"j", "k", "l"}, {"m", "n", "o"}, {"p", "q", "r", "s"},
            {"t", "u", "v"}, {"w", "x", "y", "z"}};

    public List<String> letterCombinations(String digits) {
        List<String> list = new ArrayList<>();
        if (digits.length() == 0) return list;
        if (digits.length() == 1) {
            int num = Integer.parseInt(digits);
            for (int i = 0; i < str[num - 2].length; i++) {
                list.add(str[num - 2][i]);
            }
            return list;
        } else {
            List<String> list2 = letterCombinations(digits.substring(0, digits.length() - 1));
            int num = Integer.parseInt(digits.charAt(digits.length() - 1) + "");
            for (String s : list2) {
                for (int i = 0; i < str[num - 2].length; i++) {
                    list.add(s + str[num - 2][i]);
                }
            }
            return list;
        }
    }

    @Test
    public void test16() {
        letterCombinations("7");
    }

    char[][] chars = {{'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'},
            {'j', 'k', 'l'}, {'m', 'n', 'o'}, {'p', 'q', 'r', 's'},
            {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}};

    public List<String> letterCombinations02(String digits) {
        if (digits.length() == 0) return new ArrayList<>();
        List<String> ans = new ArrayList<>();
        char[] carr = new char[digits.length()];
        process(carr, ans, 0, digits);
        return ans;
    }

    public void process(char[] carr, List<String> ans, int index, String digits) {
        if (index == digits.length()) {
            ans.add(String.valueOf(carr));
            return;
        }
        int num = digits.charAt(index) - '2';
        char[] cc = chars[num];
        for (char c : cc) {
            carr[index] = c;
            process(carr, ans, index + 1, digits);
        }
    }

    //18. 四数之和
    /*
    给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 
    中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 
    相等？找出所有满足条件且不重复的四元组。
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums.length < 4) return new ArrayList<>();
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < nums.length - 3; i++) {
            if (i != 0 && nums[i] == nums[i - 1]) continue;
            threeSum(nums, i, ans, target - nums[i]);
        }
        return ans;
    }

    public void threeSum(int[] nums, int start, List<List<Integer>> ans, int target) {

        for (int i = start + 1; i < nums.length - 2; i++) {
            int k = nums.length - 1;
            int j = i + 1;
            int ss = nums[i] + nums[i + 1] + nums[i + 2];
            if (ss > target) break;
            if (nums[i] + nums[k] + nums[k - 1] < target) continue;
            if (i > start + 1 && nums[i] == nums[i - 1]) continue;
            while (j < k) {
                if (nums[i] + nums[j] + nums[k] < target) {
                    j++;
                } else if (nums[i] + nums[j] + nums[k] > target) {
                    k--;
                } else {
                    List<Integer> line = new ArrayList<>();
                    line.add(nums[start]);
                    line.add(nums[i]);
                    line.add(nums[j]);
                    line.add(nums[k]);
                    ans.add(line);
                    while (j < k && nums[j] == nums[j + 1]) j++;
                    while (j < k && nums[k] == nums[k - 1]) k--;
                    j++;
                    k--;
                }
            }
        }
    }

    //19. 删除链表的倒数第 N 个结点
    /*
    给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
进阶：你能尝试使用一趟扫描实现吗？
     */

    public class ListNode {
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

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (n <=0 ) return head;
        ListNode after = head;
        while (after!=null && n > 0){
            after = after.next;
            n--;
        }
        if (after == null){
            if (n == 0) return head.next;
            return head;
        }
        after = after.next;
        ListNode pre = head;
        while (after!=null){
            after = after.next;
            pre = pre.next;
        }
        pre.next = pre.next.next;
        return head;
    }

    //20. 有效的括号
    /*
    给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
有效字符串需满足：
左括号必须用相同类型的右括号闭合。
左括号必须以正确的顺序闭合。
     */
    public boolean isValid(String s) {
        if (s.length()%2==1) return  false;
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if(c==')'){
                if (stack.isEmpty()||stack.pop()!='(') return false;
            }else if(c==']'){
                if (stack.isEmpty()||stack.pop()!='[') return false;
            }else if(c=='}'){
                if (stack.isEmpty()||stack.pop()!='{') return false;
            }else stack.add(c);
        }
        return stack.isEmpty();
    }
}
