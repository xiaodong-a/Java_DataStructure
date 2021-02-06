package com.xdwang;

import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author xdwang
 * @create 2021-02-06 8:56
 */
public class Demo20210206 {
    //40
    public void FindNumsAppearOnce(int[] array, int num1[], int num2[]) {
        int n = 0;
        for (int i = 0; i < array.length; i++) {
            n ^= array[i];
        }
//        n = n ^ (n - 1);
//        n = (n >> 1) + 1;
        n &= (-n);
        num1[0] = 0;
        num2[0] = 0;
        for (int i = 0; i < array.length; i++) {
            if ((array[i] & n) == 0) {
                num1[0] ^= array[i];
            } else {
                num2[0] ^= array[i];
            }
        }
    }

    //哈希法
    public void FindNumsAppearOnce2(int[] array, int num1[], int num2[]) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < array.length; i++) {
            if (map.get(array[i]) == null) {
                map.put(array[i], 1);
            } else {
                map.put(array[i], 2);
            }
        }
        int i = 0;
        for (; i < array.length; i++) {
            if (map.get(array[i]) == 1) {
                num1[0] = array[i];
                break;
            }
        }
        i = i + 1;
        for (; i < array.length; i++) {
            if ((int) map.get(array[i]) == 1) {
                num2[0] = array[i];
                break;
            }
        }
    }

    @Test
    public void test1() {
        int[] array = new int[]{2, 4, 3, 6, 3, 2, 5, 5};
        int[] n1 = new int[1];
        int[] n2 = new int[1];
        FindNumsAppearOnce(array, n1, n2);
        System.out.println(n1[0]);
        System.out.println(n2[0]);
    }

    public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> ress = new ArrayList<>();
        ArrayList<Integer> res = new ArrayList<>();
        if (sum < 3) return ress;
        int target = 0;
        for (int i = 1, j = 1; i < (sum + 1) >> 1; ) {
            if (target <= sum) {
                if (target == sum) ress.add(new ArrayList<>(res));
                target += j;
                res.add(j++);
            } else if (target > sum) {
                target -= i++;
                res.remove(0);
            }
        }
        return ress;
    }

    //利用等差数列的求和公式
    public ArrayList<ArrayList<Integer>> FindContinuousSequence2(int sum) {
        ArrayList<ArrayList<Integer>> ress = new ArrayList<>();
        if (sum < 3) return ress;
        int num = (int) Math.sqrt(2 * sum);
        for (int i = num; i >= 2; i--) {
            int temp = (i - 1) * i / 2;
            if ((sum - temp) % i != 0) continue;
            int first = (sum - temp) / i;
            ArrayList<Integer> list = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                list.add(first + j);
            }
            ress.add(list);
        }
        return ress;
    }

    //42
    public ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        ArrayList<Integer> res = new ArrayList<>();
        if (array == null || array.length < 2) return res;
        for (int i = 0, j = array.length - 1; i < j; ) {
            int s = array[i] + array[j];
            if (s == sum) {
                res.add(array[i]);
                res.add(array[j]);
                break;
            } else if (s > sum) j--;
            else i++;
        }
        return res;
    }

    public ArrayList<Integer> FindNumbersWithSum2(int[] array, int sum) {
        ArrayList<Integer> res = new ArrayList<>();
        if (array == null || array.length < 2) return res;
        for (int i = 0, j = array.length - 1; i < j; ) {
            int s = array[i] + array[j];
            if (s == sum) {
                if (res.size() == 0) {
                    res.add(array[i]);
                    res.add(array[j]);
                } else {
                    res.set(0, array[i]);
                    res.set(1, array[j]);
                }
                i++;
                j--;
            } else if (s > sum) {
                j--;
            } else i++;
        }
        return res;
    }

    //43
    public String LeftRotateString(String str, int n) {
        if (str == null || str.length() == 0) return str;
        n = n % str.length();
        return str.substring(n, str.length()) + str.substring(0, n);
    }

    public String ReverseSentence(String str) {
        StringBuffer str1 = new StringBuffer();
        int j = str.length();
        for (int i = str.length() - 1; i >= 0; i--) {
            if (' ' == str.charAt(i)) {
                str1.append(str.substring(i + 1, j));
                str1.append(" ");
                j = i;
            }
        }
        str1.append(str.substring(0, j));
        return str1.toString();
    }


    //45
    public boolean IsContinuous(int[] numbers) {
        if (numbers == null || numbers.length != 5) return false;
        int count0 = 0;
        Arrays.sort(numbers);
        for (; count0 < numbers.length; count0++) {
            if (numbers[count0] != 0) break;
        }
        int loc = count0 + 1;
        for (; loc < numbers.length; loc++) {
            if (numbers[loc] == numbers[loc - 1]) return false;
            count0 = count0 - (numbers[loc] - numbers[loc - 1] - 1);
        }
        if (count0 < 0) return false;
        return true;
    }

    public boolean IsContinuous2(int[] numbers) {
        if (numbers == null || numbers.length < 5) return false;
        Arrays.sort(numbers);
        int i = 0;
        for (; i < numbers.length; i++) {
            if (numbers[i] > 0) break;
        }
        if (numbers[numbers.length - 1] - numbers[i] >= 5) return false;
        for (; i < numbers.length - 1; i++) {
            if (numbers[i] == numbers[i + 1]) return false;
        }
        return true;
    }


    @Test
    public void test2() {
        ArrayList<Integer> res = new ArrayList<>();
        res.add(0, 1);
    }

    //46
    public int LastRemaining_Solution(int n, int m) {
        if (n < 1 || m < 1) return -1;
        int res = 0;
        for (int i = 2; i <= n; i++) {
            res = (res + m) % i;
        }
        return res;
    }

    //47
    public int Sum_Solution(int n) {
        boolean x = (n > 1 && (n += Sum_Solution(n - 1)) != 0);
        return n;
    }

    //48
    public int Add(int num1, int num2) {
        while (num2 != 0) {
            int c = (num1 & num2) << 1;
            num1 = num1 ^ num2;
            num2 = c;
        }
        return num1;
    }

    //49
    public int StrToInt(String str) {
        if (str == null || str.length() == 0) return 0;
        int flag = 1;
        if (str.charAt(0) == '-') flag = -1;
        int begin = 0;
        if (str.charAt(0) == '-' || str.charAt(0) == '+') begin = 1;
        int res = 0;
        for (; begin < str.length(); begin++) {
            int n = str.charAt(begin) - '0';
            if (n < 0 || n > 9) return 0;
            res = res * 10 + n;
        }
        return res * flag;
    }

    //50
    public boolean duplicate(int numbers[], int length, int[] duplication) {
        if (length < 2) return false;
        int[] arr = new int[length];
        for (int i = 0; i < numbers.length; i++) {
            arr[numbers[i]]++;
            if (arr[numbers[i]] > 1) {
                duplication[0] = numbers[i];
                return true;
            }
        }
        return false;
    }

    //51
    public int[] multiply(int[] A) {
        int len = A.length;
        int[] left = new int[len];
        int[] right = new int[len];
        left[0] = 1;
        right[len - 1] = 1;
        for (int i = 1; i < len; i++) {
            left[i] = A[i - 1] * left[i - 1];
            right[len - 1 - i] = A[len - i] * right[len - i];
        }
        for (int i = 0; i < len; i++) {
            left[i] = left[i] * right[i];
        }
        return left;
    }

    //52
    public boolean match(char[] str, char[] pattern) {
        if (str == null || pattern == null) return false;
        return match2(str, 0, pattern, 0);
    }

    public boolean match2(char[] str, int ss, char[] pattern, int sp) {
        if (ss == str.length && sp == pattern.length) return true;
        if (sp < pattern.length - 1 && pattern[sp + 1] == '*') {
            if ((ss < str.length && str[ss] == pattern[sp]) || (ss < str.length && pattern[sp] == '.')) {
                return match2(str, ss + 1, pattern, sp + 2) || match2(str, ss, pattern, sp + 2) || match2(str, ss + 1, pattern, sp);
            }
            return match2(str, ss, pattern, sp + 2);
        } else {
            if (ss == str.length || sp == pattern.length) return false;
            if (str[ss] == pattern[sp] || pattern[sp] == '.') return match2(str, ss + 1, pattern, sp + 1);
            return false;
        }
    }

//    public boolean match(char[] str, char[] pattern) {
//        if (str == null || pattern == null) return false;
//        int strs = 0;
//        int pats = 0;
//        return match2(str, strs, pattern, pats);
//    }
//
//    public boolean match2(char[] str, int strs, char[] pattern, int pats) {
//        if (str.length == strs && pattern.length == pats) {
//            return true;
//        }
//        if(pats+1<pattern.length && pattern[pats+1]=='*'){
//            if(strs!=str.length && (pattern[pats]=='.' || str[strs]==pattern[pats])){
//                return match2(str,strs+1,pattern,pats) || match2(str,strs,pattern,pats+2) ||match2(str,strs+1,pattern,pats+2);
//            }else{
//                return match2(str,strs,pattern,pats+2);
//            }
//        }else{
//            if(strs!=str.length && pats!=pattern.length && (pattern[pats]==str[strs]||pattern[pats]=='.')){
//                return match2(str,strs+1,pattern,pats+1);
//            }else{
//                return false;
//            }
//        }
//    }

    //53
    public boolean isNumeric(char[] str) {
        if (str == null || str.length == 0) return false;
        boolean flage = false;    //e 是否出现过
        boolean flagpoint = false; // . 是否出现过
        boolean preNum = false; //是否出现过数字
        for (int i = 0; i < str.length; i++) {
            char c = str[i];
            if (c >= '0' && c <= '9') {
                preNum = true;
            } else if (c == '.' && !flagpoint) {
                flagpoint = true;
            } else if ((c == 'E' || c == 'e') && !flage && preNum && i != str.length - 1) {
                flage = true;
                flagpoint = true;
            } else if ((c == '+' || c == '-') && (i == 0 || str[i - 1] == 'e' || str[i - 1] == 'E') && i != str.length - 1) {
                continue;
            } else return false;
        }
        if (preNum) return true;
        return false;
    }

    @Test
    public void test4() {
        String a = "ad ";
    }


    HashMap<Character, Integer> hashMap = new HashMap<>();
    Queue<Character> queue = new LinkedList<>();

    //Insert one char from stringstream
    public void Insert(char ch) {
        if (hashMap.get(ch) == null) {
            hashMap.put(ch, 1);
            queue.add(ch);
        } else {
            hashMap.put(ch, 2);
        }
    }

    //return the first appearence once char in current stringstream
    public char FirstAppearingOnce() {
        while (!queue.isEmpty()) {
            char c = queue.peek();
            if (hashMap.get(c) == 1) return c;
            queue.poll();
        }
        return '#';
    }

    //55
    public ListNode EntryNodeOfLoop(ListNode pHead) {
        if (pHead == null || pHead.next == null) return null;
        ListNode fast = pHead.next;
        ListNode slow = pHead;
        while (fast != slow) {
            if (fast.next == null || fast.next.next == null) return null;
            fast = fast.next.next;
            slow = slow.next;
        }
        fast = pHead;
        slow = slow.next;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }

    //哈希法
    public ListNode EntryNodeOfLoop2(ListNode pHead) {
        ListNode res = null;
        HashMap<ListNode, Integer> map = new HashMap<ListNode, Integer>();
        while (pHead != null) {
            if (map.get(pHead) != null) return pHead;
            map.put(pHead, 1);
            pHead = pHead.next;
        }
        return res;
    }

    //56
    public ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null) return null;
        ListNode head = new ListNode(0);
        head.next = pHead;
        ListNode pre = head;
        while (pHead != null) {
            while (pHead.next != null && pHead.val == pHead.next.val) pHead = pHead.next;
            if (pre.next == pHead) {
                pre = pHead;
                pHead = pHead.next;
            } else {
                pHead = pHead.next;
                pre.next = pHead;
            }
        }
        return head.next;
    }

    //57、
    class TreeLinkNode {
        int val;
        TreeLinkNode left = null;
        TreeLinkNode right = null;
        TreeLinkNode next = null;

        TreeLinkNode(int val) {
            this.val = val;
        }
    }

    public TreeLinkNode GetNext(TreeLinkNode pNode) {
        TreeLinkNode res = null;
        if (pNode == null) return res;
        if (pNode.right != null) {
            res = pNode.right;
            while (res.left != null) res = res.left;
            return res;
        }
        res = pNode.next;
        while (res != null) {
            if (res.left == pNode) return res;
            pNode = res;
            res = res.next;
        }
        return null;
    }

    //好像这个更快点
    public TreeLinkNode GetNext2(TreeLinkNode pNode) {
        if (pNode == null) return null;
        if (pNode.right != null) {
            pNode = pNode.right;
            while (pNode.left != null) pNode = pNode.left;
            return pNode;
        }
        while (pNode.next != null && pNode.next.right == pNode) {
            pNode = pNode.next;
        }
        if (pNode.next == null) return null;
        return pNode.next;
    }

    //58 递归
    boolean isSymmetrical(TreeNode pRoot) {
        if (pRoot == null) return true;
        return isSymmertical2(pRoot.left, pRoot.right);
    }

    boolean isSymmertical2(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left == null || right == null || left.val != right.val) return false;
        return isSymmertical2(left.left, right.right) && isSymmertical2(left.right, right.left);
    }

    // 非递归，左子树逆先序， 右子树先序
    boolean isSymmetrical_2(TreeNode pRoot) {
        Stack<TreeNode> integerStack1 = new Stack<>();
        Stack<TreeNode> integerStack2 = new Stack<>();
        if (pRoot != null) {
            integerStack1.add(pRoot);
            integerStack2.add(pRoot);
        }
        while (!integerStack1.empty()) {
            TreeNode num1 = integerStack1.pop();
            TreeNode num2 = integerStack2.pop();
            if (num1.val != num2.val) return false;
            if (num1.left != null || num2.right != null) {
                if (num1.left == null || num2.right == null) return false;
                integerStack1.add(num1.left);
                integerStack2.add(num2.right);
            }
            if (num1.right != null || num2.left != null) {
                if (num1.right == null || num2.left == null) return false;
                integerStack1.add(num1.right);
                integerStack2.add(num2.left);
            }
        }
        return true;
    }

    //59
    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (pRoot == null) return result;
        Stack<TreeNode> stack1 = new Stack<TreeNode>();
        Stack<TreeNode> stack2 = new Stack<TreeNode>();
        stack1.add(pRoot);
        while (!stack1.empty()) {
            ArrayList<Integer> line = new ArrayList<Integer>();
            while (!stack1.empty()) {
                TreeNode treenode = stack1.pop();
                line.add(treenode.val);
                if (treenode.left != null) stack2.add(treenode.left);
                if (treenode.right != null) stack2.add(treenode.right);
            }
            if (line.size() == 0) return result;
            result.add(line);
            line = new ArrayList<Integer>();
            while (!stack2.empty()) {
                TreeNode treenode = stack2.pop();
                line.add(treenode.val);
                if (treenode.right != null) stack1.add(treenode.right);
                if (treenode.left != null) stack1.add(treenode.left);
            }
            if (line.size() == 0) return result;
            result.add(line);
        }
        return result;
    }

    //60
    public ArrayList<ArrayList<Integer>> Print60(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (pRoot == null) return res;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(pRoot);
        while (!queue.isEmpty()){
            ArrayList<Integer> list = new ArrayList<>();
            int size = queue.size();
            while (size-->0){
                pRoot = queue.poll();
                list.add(pRoot.val);
                if (pRoot.left != null) queue.add(pRoot.left);
                if (pRoot.right != null) queue.add(pRoot.right);
            }
            res.add(list);
        }
        return res;
    }
}
