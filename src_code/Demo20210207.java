package com.xdwang;

import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.util.*;

/**
 * @author xdwang
 * @create 2021-02-07 9:19
 */
public class Demo20210207 {
    //61  先序序列化,非递归序列化很简单，遍历来一遍就行
    String Serialize(TreeNode root) {
        if (root == null) return "#!";
        return root.val + "!" + Serialize(root.left) + Serialize(root.right);
    }

    TreeNode Deserialize(String str) {
        String[] split = str.split("!");//最后有个空字符串
        int[] begin = new int[]{-1};
        return Deserialize2(split, begin);
    }

    TreeNode Deserialize2(String[] str, int[] begin) {
        begin[0] += 1;
        if (str[begin[0]].equals("#")) return null;
        TreeNode root = new TreeNode(Integer.parseInt(str[begin[0]]));
        root.left = Deserialize2(str, begin);
        root.right = Deserialize2(str, begin);
        return root;
    }

    @Test
    public void test1() {
        String str = "2!3!4!";
        String[] split = str.split("!");//最后有个空字符串
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }
    }

    @Test
    public void test2() {
        String str = "2!#!#!";
        Deserialize(str);
    }


    //先序遍历2
    String Serialize3(TreeNode root) {
        if (root == null) {
            return "#!";
        }
        return root.val + "!" + Serialize3(root.left) + Serialize3(root.right);
    }

    TreeNode Deserialize3(String str) {
        String[] string = str.split("!");
        Queue<String> queue = new LinkedList<String>();
        for (int i = 0; i < string.length; i++) {
            queue.add(string[i]);
        }
        return preNode(queue);
    }

    TreeNode preNode(Queue<String> queue) {
        String str = queue.poll();
        if ("#".equals(str)) {
            return null;
        }
        TreeNode treenode = new TreeNode(Integer.parseInt(str));
        treenode.left = preNode(queue);
        treenode.right = preNode(queue);
        return treenode;
    }

    //中序序列化,反序列化不行,不是唯一确定的
    String Serialize_m(TreeNode root) {
        if (root == null) {
            return "#!";
        }
        return Serialize_m(root.left) + root.val + "!" + Serialize_m(root.right);
    }

    @Test
    public void test4() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(4);
        System.out.println(Serialize_m(root));
        TreeNode root2 = new TreeNode(4);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(1);
        root2.right.right = new TreeNode(3);
        System.out.println(Serialize_m(root2));
    }


    //后序的序列化
    String Serialize_post(TreeNode root) {
        if (root == null) {
            return "#!";
        }
        return Serialize_post(root.left) + Serialize_post(root.right) + root.val + "!";
    }

    //后序反序列化
    TreeNode Deserialize_post(String str) {
        String[] string = str.split("!");
        Queue<String> queue = new LinkedList<String>();
        for (int i = string.length - 1; i >= 0; i--) {
            queue.add(string[i]);
        }
        return postNode(queue);
    }

    //比较慢
    TreeNode postNode(Queue<String> queue) {
        String str = queue.poll();
        if ("#".equals(str)) {
            return null;
        }
        TreeNode treenode = new TreeNode(Integer.parseInt(str));
        treenode.right = postNode(queue);
        treenode.left = postNode(queue);
        return treenode;
    }

    //后序反序列化
    TreeNode Deserialize_post2(String str) {
        String[] string = str.split("!");
        return postNode2(string, new int[]{string.length});
    }

    //这个快点
    TreeNode postNode2(String[] str, int[] begin) {
        begin[0] -= 1;
        if ("#".equals(str[begin[0]])) return null;
        TreeNode treenode = new TreeNode(Integer.parseInt(str[begin[0]]));
        treenode.right = postNode2(str, begin);
        treenode.left = postNode2(str, begin);
        return treenode;
    }

    //层次序列化
    String SerializeLevel(TreeNode root) {
        if (root == null) return "#!";
        String res = root.val + "!";
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            root = queue.poll();
            if (root.left != null) {
                queue.add(root.left);
                res += root.left.val + "!";
            } else {
                res += "#!";
            }
            if (root.right != null) {
                queue.add(root.right);
                res += root.right.val + "!";
            } else {
                res += "#!";
            }
        }
        return res;
    }

    TreeNode DeSerializeLevel(String string) {
        String[] str = string.split("!");
        int index = 0;
        TreeNode root = generateNode(str[index++]);
        if (root == null) return root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            cur.left = generateNode(str[index++]);
            cur.right = generateNode(str[index++]);
            if (cur.left != null) queue.add(cur.left);
            if (cur.right != null) queue.add(cur.right);
        }
        return root;
    }

    TreeNode generateNode(String val) {
        if ("#".equals(val)) return null;
        return new TreeNode(Integer.parseInt(val));
    }

    //62
    //第一种 递归 无返回值，看的题解
    int count = 0;
    TreeNode ans = null;

    TreeNode KthNode1(TreeNode pRoot, int k) {
        count = k;
        kth1(pRoot);
        return ans;
    }

    void kth1(TreeNode pRoot) {
        if (pRoot == null || count < 1) return;
        kth1(pRoot.left);
        if (count == 1) ans = pRoot;
        if (count-- > 1) kth1(pRoot.right);
    }

    TreeNode KthNode(TreeNode pRoot, int k) {
        count = k;
        return kth(pRoot);
    }

    TreeNode kth(TreeNode pRoot) {
        TreeNode res = null;
        if (pRoot == null || count < 1) return null;
        res = kth(pRoot.left);
        if (count == 1) res = pRoot;
        if (count-- > 1) res = kth(pRoot.right);
        return res;
    }

    //非递归方法
    TreeNode KthNode3(TreeNode pRoot, int k) {
        if (pRoot == null || k < 1) return null;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode cur = pRoot;
        while (!stack.isEmpty() || cur != null) {
            if (cur != null) {
                stack.add(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                k--;
                if (k == 0) break;
                cur = cur.right;
            }
        }
        if (k == 0) return cur;
        return null;
    }

    //63
    PriorityQueue<Integer> right = new PriorityQueue<>();
    PriorityQueue<Integer> left = new PriorityQueue<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer t1, Integer t2) {
            return t2 - t1;
        }
    });

    public void Insert(Integer num) {
        left.add(num);
        right.add(left.poll());
        if (left.size() < right.size() - 1) left.add(right.poll());
    }

    public Double GetMedian() {
        if (left.size() == right.size()) return (left.peek() + right.peek()) / 2.0;
        return new Double(right.peek());
    }

    //64
    public ArrayList<Integer> maxInWindows(int[] num, int size) {
        ArrayList<Integer> res = new ArrayList<>();
        if (num == null || num.length == 0 || size < 1 || num.length < size) return res;
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < num.length; i++) {
            while (!queue.isEmpty() && num[i] > queue.peekLast()) queue.pollLast();
            queue.add(num[i]);
            if (i + 1 >= size) {
                res.add(queue.peek());
                if (queue.peek() == num[i + 1 - size]) queue.poll();
            }
        }
        return res;
    }

    //65
    public boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        if (matrix == null || rows < 1 || cols < 1) return false;
        if (str == null || str.length == 0) return true;
        boolean[][] flag = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (hasP(flag, matrix, i, j, str, 0)) return true;
            }
        }
        return false;
    }

    public boolean hasP(boolean[][] flag, char[] matrix, int i, int j, char[] str, int s) {
        int rows = flag.length;
        int cols = flag[0].length;
        if (s == str.length) return true;
        if (i < 0 || i >= rows || j < 0 || j >= cols || flag[i][j] || matrix[i * cols + j] != str[s]) return false;
        flag[i][j] = true;
        boolean ans = hasP(flag, matrix, i + 1, j, str, s + 1) || hasP(flag, matrix, i - 1, j, str, s + 1)
                || hasP(flag, matrix, i, j + 1, str, s + 1) || hasP(flag, matrix, i, j - 1, str, s + 1);
        flag[i][j] = false;
        return ans;
    }

    //非递归
    class Direction {
        boolean left = true;
        boolean right = true;
        boolean up = true;
        boolean down = true;
        int val;

        public Direction(int val) {
            this.val = val;
        }
    }

    public boolean hasPath3(char[] matrix, int rows, int cols, char[] str) {
        if (matrix == null || matrix.length == 0 || rows < 0 || cols < 0 || matrix.length != rows * cols || str == null || str.length == 0)
            return false;
        boolean[] flag = new boolean[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i] == str[0]) {
                int strrest = 1;
                flag[i] = true;
                Stack<Direction> integerStack = new Stack<Direction>();
                integerStack.add(new Direction(i));
                while (!integerStack.empty()) {
                    Direction dir = integerStack.peek();
                    int cur = dir.val;
                    int row = cur / cols;
                    int col = cur % cols;
                    if (strrest == str.length) return true;
                    if (col < cols - 1 && !flag[cols * row + col + 1] && str[strrest] == matrix[cols * row + col + 1] && dir.right) { //right
                        dir.right = false;
                        col++;
                    } else if (row > 0 && !flag[cols * (row - 1) + col] && str[strrest] == matrix[cols * (row - 1) + col] && dir.up) {//up
                        dir.up = false;
                        row--;
                    } else if (row < rows - 1 && !flag[cols * (row + 1) + col] && str[strrest] == matrix[cols * (row + 1) + col] & dir.down) {//down
                        dir.down = false;
                        row++;
                    } else if (col > 0 && !flag[cols * row + col - 1] && str[strrest] == matrix[cols * row + col - 1] & dir.left) {//left
                        dir.left = false;
                        col--;
                    } else {
                        integerStack.pop();
                        flag[cols * row + col] = false;
                        strrest--;
                        continue;
                    }
                    strrest++;
                    flag[cols * row + col] = true;
                    integerStack.add(new Direction(cols * row + col));
                }
            }
        }
        return false;
    }

    //66
    public int movingCount(int threshold, int rows, int cols) {
        if (threshold < 0) return 0;
        boolean[][] flag = new boolean[rows][cols];
        return dfs(flag, threshold, 0, 0, rows, cols);
    }

    public int dfs(boolean[][] flag, int threshold, int i, int j, int rows, int cols) {
        if (i < 0 || i >= rows || j < 0 || j >= cols || flag[i][j] || getSum(i) + getSum(j) > threshold) return 0;
        flag[i][j] = true;
        return 1 + dfs(flag, threshold, i + 1, j, rows, cols) + dfs(flag, threshold, i, j + 1, rows, cols);
        //   return 1 + dfs(flag, threshold, i + 1, j, rows, cols) + dfs(flag, threshold, i, j + 1, rows, cols) + dfs(flag, threshold, i - 1, j, rows, cols) + dfs(flag, threshold, i, j - 1, rows, cols);
    }

    public int getSum(int n) {
        int sum = 0;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }

    @Test
    public void test5() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                System.out.print(getSum(i) + getSum(j) + "\t");
            }
            System.out.println();
        }
    }

    //广度遍历，非递归
    class Node {
        int x;
        int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public int movingCount2(int threshold, int rows, int cols) {
        if (threshold < 0 || rows < 1 || cols < 1) return 0;
        int res = 1;
        int[] dir = new int[]{1, 0, -1, 0, 1};
        boolean[][] flag = new boolean[rows][cols];
        flag[0][0] = true;
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(new Node(0, 0));
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (int i = 0; i < 4; i++) {
                int x = dir[i] + cur.x;
                int y = dir[i + 1] + cur.y;
                if (x >= 0 && x < rows && y >= 0 && y < cols && !flag[x][y] && getSum(x) + getSum(y) <= threshold) {
                    flag[x][y] = true;
                    res++;
                    queue.add(new Node(x, y));
                }
            }
        }
        return res;
    }

    @Test
    public void test6() {
        System.out.println(movingCount2(5, 3, 3));
    }

    public int cutRope(int target) {
        if (target < 2) return -1;
        if (target == 2) return 1;
        if (target == 3) return 2;
        int num = target % 3;
        if (num == 0) return (int) Math.pow(3, target / 3);
        else if (num == 1) return (int) Math.pow(3, target / 3 - 1) * 4;
        else return (int) Math.pow(3, target / 3) * 2;
    }

    public int cutRope2(int target) {
        if (target < 2) return -1;
        if (target == 2) return 1;
        if (target == 3) return 2;
        if (target == 4) return 4;
        int pre1 = 2;
        int pre2 = 3;
        int pre3 = 4;
        for (int i = 5; i <= target; i++) {
            int cur = pre1 * 3 > pre2 * 2 ? 3 * pre1 : 2 * pre2;
            pre1 = pre2;
            pre2 = pre3;
            pre3 = cur;
        }
        return pre3;
    }
}

