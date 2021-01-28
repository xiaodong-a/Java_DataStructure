/**
 * @author xdwang
 * @create 2020-12-08 20:38
 */
public class SlowFastLinkList {
    public static class Node{
        int value ;
        Node next;
        public Node(int value){
            this.value = value;
        }
    }

    //输入链表头节点，奇数长度返回中点，偶数长度返回上中点
    public static Node midOrUpmidNode(Node head){
        if (head == null || head.next == null||head.next.next == null) return head;
        Node slow = head.next;
        Node fast = head.next.next;
        while (fast.next != null && fast.next.next !=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    //输入链表头节点，奇数长度返回中点，偶数长度返回下中点
    public static Node midOrDownmidNode(Node head){
        if (head == null || head.next == null) return head;
        Node slow = head.next;
        Node fast = head.next;
        while (fast.next != null && fast.next.next !=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    //输入链表头节点，奇数长度返回中点前一个node，偶数长度返回上中点前一个node
    public static Node midOrUpmidPreNode(Node head){
        if (head == null || head.next == null|| head.next.next == null) return null;
        Node slow = head;
        Node fast = head.next.next;
        while (fast.next != null && fast.next.next !=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    //输入链表头节点，奇数长度返回中点前一个node，偶数长度返回下中点前一个node
    public static Node midOrDownmidPreNode(Node head){
        if (head == null || head.next == null) return null;
        Node slow = head;
        Node fast = head.next;
        while (fast.next != null && fast.next.next !=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    //输入链表头节点，判断是否为回文 12321  123321
    public static boolean isPalindrome(Node head){
        if (head == null || head.next == null) return true;
        Node n1 = head;  //慢指针
        Node n2 = head;  //快指针
        Node n3 = null;
        while (n2.next != null && n2.next.next != null){
            n1 = n1.next;
            n2 = n2.next.next;
        }
        n2 = n1.next;
        n1.next = null;
        while (n2 != null){
            n3 = n2.next;
            n2.next = n1;
            n1 = n2;
            n2 = n3;
        }
        n3 = n1;
        n2 = head;
        boolean flag = true;
        while (n2 != null && n3 != null){
            if (n2.value != n3.value) {
                flag = false;
                break;
            }
        }
        n2 = n1.next;
        n1.next = null;
        while (n2 != null){
            n3 = n2.next;
            n2.next = n1;
            n1 = n2;
            n2 = n3;
        }
        return flag;
    }

    //将单向链表按某值划分成左边小，中间相等，右边大的形式
    public static Node listPartition(Node head,int num){
        if (head == null || head.next == null) return head;
        Node sh = null;
        Node st = null;
        Node eh = null;
        Node et = null;
        Node lh = null;
        Node lt = null;
        while (head != null){
            if (head.value < num){
                if (sh == null){ //这步的判断不好，不如初始让sh,eh,lh为new Node(0)(当然是不同地址对象)，
                    sh = head;   //后面再通过sh = sh.next 换回来,见listPartition2方法
                    st = head;
                }else {
                    st.next = head;
                    st = head;
                }
            }else if(head.value == num){
                if (eh == null){
                    eh = head;
                    et = head;
                }else {
                    et.next = head;
                    et = head;
                }
            }else {
                if (lh == null){
                    lh = head;
                    lt = head;
                }else {
                    lt.next = head;
                    lt = head;
                }
            }
            head = head.next;
        }
        if (st != null){
            st.next = eh;
            et = et==null? st:et;
        }
        if (et != null){
            et.next = lh;
            lt = lt==null? et:lt;
        }
        if (lt != null) lt.next = null;
        return sh !=null? sh:(eh != null? eh:lh);
    }

    public static Node listPartition2(Node head,int num){
        if (head == null || head.next == null) return head;
        Node sh = new Node(0);
        Node st = sh;
        Node eh = new Node(0);
        Node et = eh;
        Node lh = new Node(0);
        Node lt = lh;
        while (head != null){
            if (head.value < num){
                    st.next = head;
                    st = head;
            }else if(head.value == num){
                    et.next = head;
                    et = head;
            }else {
                    lt.next = head;
                    lt = head;
            }
            head = head.next;
        }
        if (st != sh){
            st.next = eh.next;
            et = et==eh? st:et;
        }
        if (et != eh){
            et.next = lh.next;
            lt = lt==lh? et:lt;
        }
        if (lt != lh) lt.next = null;
        sh = sh.next;
        eh = eh.next;
        lh = lh.next;
        return sh !=null? sh:(eh != null? eh:lh);
    }
    public static void main(String[] args) {
        Node head = new Node(12);
        Node next = head;
        next.next = new Node(45);
        next = next.next;
        next.next = new Node(9);
        next = next.next;
        next.next = new Node(3);
        next = next.next;
        next.next = new Node(1231);
        next = next.next;
        next.next = new Node(36);
        next = next.next;
        next.next = new Node(45);
        next = next.next;
        next.next = new Node(5);
        next = next.next;
        Node ans = listPartition2(head,1);
        while (ans!=null){
            System.out.print(ans.value+",");
            ans = ans.next;
        }
    }
}
