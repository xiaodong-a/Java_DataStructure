import java.util.Stack;

/**
 * @author xdwang
 * @create 2020-12-11 15:58
 */
public class NonRecursiveTraversalBT2 {
    public static class BinaryTree {
        int value;
        BinaryTree left;
        BinaryTree right;
        public BinaryTree(int value) {
            this.value = value;
        }
    }
    public static void pre(BinaryTree root){
        if (root != null){
            Stack<BinaryTree> binaryTrees = new Stack<BinaryTree>();
            binaryTrees.push(root);
            while (!binaryTrees.empty()){
                root = binaryTrees.pop();
                System.out.println(root.value);
                if (root.right != null) binaryTrees.push(root.right);
                if (root.left  != null) binaryTrees.push(root.left);
            }
        }
    }

    public static void mid(BinaryTree root){
        Stack<BinaryTree> binaryTrees = new Stack<BinaryTree>();
        while (root !=null || !binaryTrees.empty()){
            if (root != null){
                binaryTrees.push(root);
                root = root.left;
            }else{
                root = binaryTrees.pop();
                System.out.println(root.value);
                root = root.right;
            }
        }
    }
    public static void pos1(BinaryTree root){
        if (root != null){
            Stack<BinaryTree> binaryTrees = new Stack<BinaryTree>();
            Stack<Integer> integerStack = new Stack<Integer>();
            binaryTrees.push(root);
            while (!binaryTrees.empty()){
                root = binaryTrees.pop();
                integerStack.push(root.value);
                if (root.left != null) binaryTrees.push(root.left);
                if (root.right  != null) binaryTrees.push(root.right);
            }
            while (!integerStack.empty()) System.out.println(integerStack.pop());
        }
    }
    public static void pos2(BinaryTree root){
        if (root != null){
            Stack<BinaryTree> binaryTrees = new Stack<BinaryTree>();
            BinaryTree flag = null;
            binaryTrees.push(root);
            while (!binaryTrees.empty()){
                root = binaryTrees.peek();
                if (root.left != null && flag != root.left && flag != root.right){
                    binaryTrees.push(root.left);
                }else if(root.right != null && flag != root.right ){
                    binaryTrees.push(root.right);
                }else {
                    root = binaryTrees.pop();
                    System.out.println(root.value);
                    flag = root;
                }
            }
        }
    }

    public static void main(String[] args) {
        BinaryTree root = new BinaryTree(1);
        root.left = new BinaryTree(2);
        root.right = new BinaryTree(3);

        root.left.left = new BinaryTree(4);
//        root.left.left.left = new BinaryTree(8);
//        root.left.left.right = new BinaryTree(9);

        root.left.right = new BinaryTree(5);
//        root.left.right.left = new BinaryTree(10);
//        root.left.right.right = new BinaryTree(11);


        root.right.left = new BinaryTree(6);
//        root.right.left.left = new BinaryTree(12);
//        root.right.left.right = new BinaryTree(13);


        root.right.right = new BinaryTree(7);
//        root.right.right.left = new BinaryTree(14);
//        root.right.right.right = new BinaryTree(15);



      //  pre(root);
        System.out.println("=================");
       // mid(root);
        System.out.println("=================");
        pos2(root);
    }

}
