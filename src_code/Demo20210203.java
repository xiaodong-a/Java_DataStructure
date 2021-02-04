package com.xdwang;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

/**
 * @author xdwang
 * @create 2021-02-03 10:01
 */
public class Demo20210203 {
    //23
    public boolean VerifySquenceOfBST(int[] sequence) {
        if (sequence == null || sequence.length == 0) return false;
        return dfs(sequence, 0, sequence.length - 1);
    }

    public boolean dfs(int[] sequence, int start, int end) {
        if (start >= end) return true;
        int middle = sequence[end];
        int loc = start;
        int num;
        for (; loc < end; loc++) {
            if (sequence[loc] > middle) break;
        }
        num = loc;
        for (; loc < end; loc++) {
            if (sequence[loc] < middle) return false;
        }
        return dfs(sequence, start, loc - 1) && dfs(sequence, loc, end - 1);
    }

    //非递归方法
    public boolean VerifySquenceOfBST2(int[] sequence) {
        if (sequence == null || sequence.length == 0) return false;
        int max = Integer.MAX_VALUE;
        int min = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<>();
        stack.add(min);
        for (int i = sequence.length - 1; i >= 0; i--) {
            if (sequence[i] > max) return false;
            while (sequence[i] < stack.peek()) {
                max = stack.pop();
            }
            stack.add(sequence[i]);
        }
        return true;
    }

    public boolean VerifySquenceOfBST3(int[] sequence) {
        if (sequence == null || sequence.length == 0) return false;
        if (sequence.length == 1) return true;
        int i = 1;
        int min = Integer.MIN_VALUE;
        int max;
        while (i < sequence.length) {
            for (; i < sequence.length; i++) {
                if (sequence[i] < min) return false;
                if (sequence[i] < sequence[i - 1]) break;
            }
            max = sequence[i - 1];
            for (; i < sequence.length; i++) {
                if (sequence[i] < min) return false;
                if (sequence[i] > sequence[i - 1]) break;
            }
            min = max;
        }
        return true;
    }


    @Test
    public void test1() {
        for (int i = 0; i < 10000000; i++) {
            int[] input = generate();
            if (VerifySquenceOfBST(input) != VerifySquenceOfBST3(input)) {
                for (int j = 0; j < input.length; j++) {
                    System.out.print(input[j] + " ");
                }
                System.out.println();
            }
        }
    }

    public int[] generate() {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            set.add((int) (Math.random() * 100));
        }
        int[] res = new int[set.size()];
        int i = 0;
        for (Integer integer : set) {
            res[i++] = integer;
        }
        return res;
    }


    //24
    class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }
    }

    //递归方法一
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        if (root.left == null && root.right == null && target == root.val) {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(root.val);
            res.add(temp);
        }
        ArrayList<ArrayList<Integer>> left = new ArrayList<>();
        ArrayList<ArrayList<Integer>> right = new ArrayList<>();
        if (root.left != null) left = FindPath(root.left, target - root.val);
        if (root.right != null) right = FindPath(root.right, target - root.val);
        for (int i = 0; i < left.size(); i++) {
            ArrayList<Integer> temp = left.get(i);
            temp.add(0, root.val);
            res.add(temp);
        }
        for (int i = 0; i < right.size(); i++) {
            ArrayList<Integer> temp = right.get(i);
            temp.add(0, root.val);
            res.add(temp);
        }
        return res;
    }

    //递归方法二
    ArrayList<ArrayList<Integer>> res = new ArrayList<>();
    ArrayList<Integer> path = new ArrayList<>();

    public ArrayList<ArrayList<Integer>> FindPath2(TreeNode root, int target) {
        if (root == null) return res;
        path.add(root.val);
        if (root.left == null && root.right == null && target == root.val) {
            res.add(new ArrayList<Integer>(path));
        }
        if (root.left != null) FindPath2(root.left, target - root.val);
        if (root.right != null) FindPath2(root.right, target - root.val);
        path.remove(path.size() - 1);
        return res;
    }

    //非递归方法，用后序遍历吧
    public ArrayList<ArrayList<Integer>> FindPath3(TreeNode root, int target) {
        ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();
        if (root == null) return paths;
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        TreeNode pre = root;
        path.add(root.val);
        target -= root.val;
        while (!stack.isEmpty()) {
            root = stack.peek();
            if (root.left != null && pre != root.left && pre != root.right) {
                stack.add(root.left);
                path.add(root.left.val);
                target -= root.left.val;
            } else if (root.right != null && pre != root.right) {
                stack.add(root.right);
                path.add(root.right.val);
                target -= root.right.val;
            } else {
                if (root.left == null && root.right == null) {
                    if (target == 0) paths.add(new ArrayList<>(path));
                }
                pre = root;
                stack.pop();
                target += root.val;
                path.remove(path.size() - 1);
            }
        }
        return paths;
    }


    //25..
    class RandomListNode {
        int label;
        RandomListNode next = null;
        RandomListNode random = null;

        RandomListNode(int label) {
            this.label = label;
        }
    }

    public RandomListNode Clone(RandomListNode pHead) {
        if (pHead == null) return null;
        RandomListNode next;
        RandomListNode list1 = pHead;
        while (list1 != null) {
            next = list1.next;
            list1.next = new RandomListNode(list1.label);
            list1.next.next = next;
            list1 = next;
        }
        list1 = pHead;
        while (list1 != null) {
            list1.next.random = list1.random == null ? null : list1.random.next;
            list1 = list1.next.next;
        }
        RandomListNode res = pHead.next;
        list1 = pHead;
        while (list1 != null) {
            next = list1.next.next;
            list1.next.next = next == null ? null : next.next;
            list1.next = next;
            list1 = next;
        }
        return res;
    }


    public RandomListNode Clone2(RandomListNode pHead) {
        if (pHead == null) return null;
        RandomListNode ret = new RandomListNode(pHead.label);
        ret.random = pHead.random;
        RandomListNode q = ret;
        RandomListNode p = pHead;
        while (p.next != null) {
            q.next = new RandomListNode(p.next.label);
            q.random = p.random;
            q = q.next;
            p = p.next;
        }
        RandomListNode r = ret;
        while (r != null) {
            p = pHead;
            q = ret;
            while (p != r.random) {
                p = p.next;
                q = q.next;
            }
            r.random = q;
            r = r.next;
        }
        return ret;
    }

    public RandomListNode Clone3(RandomListNode head) {
        RandomListNode n1 = head;
        HashMap<RandomListNode, RandomListNode> hashMap = new HashMap<>();
        while (n1 != null) {
            hashMap.put(n1, new RandomListNode(n1.label));
            n1 = n1.next;
        }
        n1 = head;
        while (n1 != null) {
            hashMap.get(n1).next = hashMap.get(n1.next);
            hashMap.get(n1).random = hashMap.get(n1.random);
            n1 = n1.next;
        }
        return hashMap.get(head);
    }

    public RandomListNode Clone4(RandomListNode head) {
        if (head == null) return null;
        RandomListNode cur = head;
        RandomListNode n1;
        while (cur != null) {
            n1 = new RandomListNode(cur.label);
            n1.next = cur.next;
            cur.next = n1;
            cur = n1.next;
        }
        cur = head;
        while (cur != null) {
            cur.next.random = cur.random == null ? null : cur.random.next;
            cur = cur.next.next;
        }
        cur = head;
        n1 = head.next;
        RandomListNode n2 = n1;
        while (cur != null) {
            cur.next = n2.next;
            cur = cur.next;
            n2.next = cur == null ? null : cur.next;
            n2 = n2.next;
        }
        return n1;
    }

    //26
    public TreeNode Convert(TreeNode pRootOfTree) {
        TreeNode res = null;
        TreeNode pre = null;
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || pRootOfTree != null) {
            if (pRootOfTree != null) {
                stack.add(pRootOfTree);
                pRootOfTree = pRootOfTree.left;
            } else {
                pRootOfTree = stack.pop();
                res = res == null ? pRootOfTree : res;
                if (pre != null) {
                    pre.right = pRootOfTree;
                    pRootOfTree.left = pre;
                }
                pre = pRootOfTree;
                pRootOfTree = pRootOfTree.right;
            }
        }
        return res;
    }


    public TreeNode Convert3(TreeNode pRootOfTree) {
        TreeNode res = null;
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || pRootOfTree != null) {
            if (pRootOfTree != null) {
                stack.add(pRootOfTree);
                pRootOfTree = pRootOfTree.right;
            } else {
                pRootOfTree = stack.pop();
                if (res != null) {
                    res.left = pRootOfTree;
                    pRootOfTree.right = res;
                }
                res = pRootOfTree;
                pRootOfTree = pRootOfTree.left;
            }
        }
        return res;
    }

    //递归做法
    public TreeNode Convert2(TreeNode pRootOfTree) {
        if(pRootOfTree==null) return null;
        if(pRootOfTree.left == null && pRootOfTree.right==null){
            return pRootOfTree;
        }
        TreeNode ret ;
        if(pRootOfTree.left!=null){
            ret = Convert2(pRootOfTree.left);
            TreeNode p = ret;
            while(p.right!=null) p=p.right;
            p.right = pRootOfTree;
            pRootOfTree.left = p;
        }else{
            ret = pRootOfTree;
        }
        if(pRootOfTree.right!=null){
            TreeNode p = Convert2(pRootOfTree.right);
            pRootOfTree.right = p;
            p.left = pRootOfTree;
        }
        return ret;
    }
}