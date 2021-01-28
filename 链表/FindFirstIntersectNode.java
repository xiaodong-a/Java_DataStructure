/**
 * @author xdwang
 * @create 2020-12-10 12:07
 */
//给定两个可能有环也可能无环的单链表，头节点head1和head2。
//请实现一个函数，如果两个链表相交，请返回相交的第一个节点。
//如果不相交，返回null。
public class FindFirstIntersectNode {
    public static class Node{
        int value;
        Node next;
        Node rand;
        Node(int val){
            value = val;
        }
    }
    //返回入环节点，如果无环返回null
    public static Node getLoopNode(Node head){
            if (head == null || head.next == null || head.next.next == null) return null;
            Node slow = head.next;
            Node fast = head.next.next;
            while (slow != fast){//有点意思这个while和if
                if (fast.next == null || fast.next.next == null){
                    return null;
                }
                fast = fast.next.next;
                slow = slow.next;
            }
            fast = head;
            while (fast != slow){
                slow = slow.next;
                fast = fast.next;
            }
            return fast;
    }

    //如果两个链表都无环，返回第一个相交节点，如果不相交返回null
    public static Node noLoop(Node head1,Node head2){
        if (head1 == null || head2 == null) return null;
        int n = 0;
        Node n1 = head1;
        Node n2 = head2;
        while (n1.next != null){
            n++;
            n1 = n1.next;
        }
        while (n2.next != null){
            n--;
            n2 = n2.next;
        }
        if (n1 != n2) return null;
        n1 = n > 0 ? head1:head2;
        n2 = n > 0 ? head2:head1;
        n = Math.abs(n);
        while (n != 0){
            n--;
            n1 = n1.next;
        }
        while (n1 != n2){
            n1 = n1.next;
            n2 = n2.next;
        }
        return n1;
    }
    //两个有环链表，返回第一个相交节点，不相交返回null
    //如果是环上相交，就麻烦怎么算第一个相交点呢？ 就返回loop1吧
    public static Node bothLoop(Node head1,Node head2,Node loop1,Node loop2){
        if (loop1 == loop2){
            int n = 0;
            Node n1 = head1;
            Node n2 = head2;
            while (n1.next != loop1){
                n++;
                n1 = n1.next;
            }
            while (n2.next != loop2){
                n--;
                n2 = n2.next;
            }
            n1 = n > 0 ? head1:head2;
            n2 = n > 0 ? head2:head1;
            n = Math.abs(n);
            while (n != 0){
                n--;
                n1 = n1.next;
            }
            while (n1 != n2){
                n1 = n1.next;
                n2 = n2.next;
            }
            return n1;
        }else{
            Node n2 = loop2.next;
            while (n2 != loop2){
                if (n2 == loop1) return loop1;
                n2 = n2.next;
            }
            return null;
        }
    }
    public static Node getIntersectNode(Node head1,Node head2){
        if (head1 == null || head2 == null) return null; //这判断可有可无，noLoop函数内部有判断
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        if (loop1 == null && loop2 == null) return noLoop(head1,head2);
        if (loop1 != null && loop2 != null) return bothLoop(head1,head2,loop1,loop2);
        return null;
    }
}
