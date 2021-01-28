import java.util.Stack;

/**
 * @author xdwang
 * @create 2020-12-09 21:53
 */
public class GetMinStack {
    Stack<Integer> stackData = new Stack<>();
    Stack<Integer> stackMin = new Stack<>();
    public void push(int num){
        stackData.push(num);
        if (stackMin.empty()){
            stackMin.push(num);
        }else{
            if (stackMin.peek()>=num)stackMin.push(num);
        }
    }
    public int pop(){
        if (stackData.empty()){
            throw new RuntimeException("栈为空");
        }
        int ans = stackData.pop();
        if (ans == stackMin.peek()) stackMin.pop();
        return ans;
    }
    public int getMin(){
        if (stackMin.empty()){
            throw new RuntimeException("栈为空");
        }
        return stackMin.peek();
    }

    public static void main(String[] args) {
        int[] num = new int[20];
        for (int i = 0; i < num.length; i++) {
            num[i] =(int) (Math.random()*100);
            System.out.print(num[i]+",");
        }
        System.out.println();
        GetMinStack myStack = new GetMinStack();
        for (int i = 0; i < num.length; i++) {
            myStack.push(num[i]);
            System.out.println("add "+num[i]);
            System.out.println("getMin " + myStack.getMin());
            if (i%2==0) {
                System.out.println("删除 " + myStack.pop());
            }
        }
    }
}
