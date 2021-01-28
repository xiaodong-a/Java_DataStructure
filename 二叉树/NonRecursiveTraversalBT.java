//方法不够简洁，理解也不容易。又从网上找到了比较简单的做法
//见NonRecursiveTraversalBT2
import java.util.Stack;

/**
 * @author xdwang
 * @create 2020-12-10 16:09
 */
//非递归  遍历二叉树  前序中序后序
public class NonRecursiveTraversalBT {
    public static class BinaryTree {
        int value;
        BinaryTree left;
        BinaryTree right;
        boolean lflag; //pos2方法使用
        boolean rflag; //pos2 pos3方法使用

        public BinaryTree(int value) {
            this.value = value;
        }
    }

    public static void pre(BinaryTree root) {
        Stack<BinaryTree> binaryTrees = new Stack<>();
        BinaryTree cur = root;
        while (cur != null || !binaryTrees.empty()) {
            if (cur != null) {
                System.out.println(cur.value);
                binaryTrees.push(cur);
                cur = cur.left;
            } else {
                cur = binaryTrees.pop();
                cur = cur.right;
            }
        }
    }

    public static void mid(BinaryTree root) {
        Stack<BinaryTree> binaryTrees = new Stack<>();
        BinaryTree cur = root;
        while (cur != null || !binaryTrees.empty()) {
            if (cur != null) {
                binaryTrees.push(cur);
                cur = cur.left;
            } else {
                cur = binaryTrees.pop();
                System.out.println(cur.value);
                cur = cur.right;
            }
        }
    }

    //后序用两个栈不就行了
    public static void pos(BinaryTree root) {
        Stack<BinaryTree> binaryTrees = new Stack<>();
        Stack<BinaryTree> binaryTrees2 = new Stack<>();
        BinaryTree cur = root;
        while (cur != null || !binaryTrees.empty()) {
            if (cur != null) {
                binaryTrees.push(cur);
                binaryTrees2.push(cur);
                cur = cur.right;
            } else {
                cur = binaryTrees.pop();
                cur = cur.left;
            }
        }
        while (!binaryTrees2.empty()) {
            System.out.println(binaryTrees2.pop().value);
        }
    }

    //用一个栈，不过要补数据
    public static void pos2(BinaryTree root) {
        Stack<BinaryTree> binaryTrees = new Stack<>();
        BinaryTree cur = root;
        while (cur != null || !binaryTrees.empty()) {
            if (cur != null && cur.lflag == false) {
                binaryTrees.push(cur);
                cur.lflag = true;
                cur = cur.left;
            } else {
                if (cur == null) {
                    cur = binaryTrees.peek();
                } else {
                    if (!cur.rflag) { //没进入right
                        cur.rflag = true;
                        cur = cur.right;
                    } else {
                        cur = binaryTrees.pop();
                        System.out.println(cur.value);
                        if (!binaryTrees.empty()) {
                            cur = binaryTrees.peek();
                        } else {
                            cur = null;
                        }
                    }
                }
            }
        }
    }

    //反正写着写着就出来个这个，挺有意思，这个是对的，虽然不好看出来
    public static void pos3(BinaryTree root) {
        Stack<BinaryTree> binaryTrees = new Stack<>();
        BinaryTree cur = root;
        while (cur != null || !binaryTrees.empty()) {
            if (cur != null && cur.rflag == false) {
                binaryTrees.push(cur);
                cur = cur.left;
            } else {
               if (cur == null){
                   cur = binaryTrees.peek();
               }
               if (!cur.rflag){
                   cur.rflag = true;
                   cur = cur.right;
               }else {
                   cur = binaryTrees.pop();
                   System.out.println(cur.value);
                   if (binaryTrees.empty()){
                       cur = null;
                   }else {
                       cur = binaryTrees.peek();
                       cur.rflag = true;
                       cur = cur.right;
                   }
               }
            }
        }
    }

    public static void main(String[] args) {
        BinaryTree root = new BinaryTree(1);
        root.left = new BinaryTree(2);
        root.right = new BinaryTree(3);

        root.left.left = new BinaryTree(4);
        root.left.left.left = new BinaryTree(8);
        root.left.left.right = new BinaryTree(9);

        root.left.right = new BinaryTree(5);
        root.left.right.left = new BinaryTree(10);
        root.left.right.right = new BinaryTree(11);


        root.right.left = new BinaryTree(6);
        root.right.left.left = new BinaryTree(12);
        root.right.left.right = new BinaryTree(13);


        root.right.right = new BinaryTree(7);
        root.right.right.left = new BinaryTree(14);
        root.right.right.right = new BinaryTree(15);



        pre(root);
        System.out.println("=================");
    //    mid(root);
        System.out.println("=================");
    //    pos3(root);
    }

}
