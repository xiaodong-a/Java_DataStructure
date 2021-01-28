/**
 * @author xdwang
 * @create 2020-12-10 16:02
 */
//递归遍历二叉树 前序中序后序
public class RecursiveTraversalBT {
    public static class BinaryTree{
        int value;
        BinaryTree left;
        BinaryTree right;
        public BinaryTree(int value){
            this.value = value;
        }
    }

    public static void pre(BinaryTree root){
        if (root == null) return;
        System.out.println(root.value);
        pre(root.left);
        pre(root.right);
    }
    public static void mid(BinaryTree root){
        if (root == null) return;
        mid(root.left);
        System.out.println(root.value);
        mid(root.right);
    }
    public static void pos(BinaryTree root){
        if (root == null) return;
        pos(root.left);
        pos(root.right);
        System.out.println(root.value);
    }

    public static void main(String[] args) {
        BinaryTree root = new BinaryTree(1);
        root.left = new BinaryTree(2);
        root.right = new BinaryTree(3);
        root.left.left = new BinaryTree(4);
        root.left.right = new BinaryTree(5);
        root.right.left = new BinaryTree(6);
        root.right.right = new BinaryTree(7);
        pre(root);
        System.out.println("=================");
        mid(root);
        System.out.println("=================");
        pos(root);
    }
}
