import java.util.HashMap;

/**
 * @author xdwang
 * @create 2020-12-09 12:25
 */
/*
一种特殊的单链表节点类描述如下
class Node{
    int value;
    Node next;
    Node rand;
    Node(int val){value = val;}
}
rand指向链表中任意一个节点，也可能指向null
复制链表
*/
class Node{
    int value;
    Node next;
    Node rand;
    Node(int val){
        value = val;
    }
}
public class CopyList {
    //1.使用HashMap
    public static Node copyListWithRand1(Node head){
        Node n1 = head;
        HashMap<Node,Node> hashMap = new HashMap<>();
        while (n1 != null){
            hashMap.put(n1,new Node(n1.value));
            n1 = n1.next;
        }
        n1 = head;
        while (n1 != null){
            hashMap.get(n1).next = hashMap.get(n1.next);
            hashMap.get(n1).rand = hashMap.get(n1.rand);
            n1 = n1.next;
        }
        return hashMap.get(head);
    }

    //2.在每个链表后接复制的链表
    public static Node copyListWithRand2(Node head){
        if(head == null ) return null;
        Node cur = head;
        Node n1;
        while (cur != null){
            n1 = new Node(cur.value);
            n1.next = cur.next;
            cur.next = n1;
            cur = n1.next;
        }
        cur = head;
        while (cur != null){
            cur.next.rand = cur.rand == null ? null:cur.rand.next;
            cur = cur.next.next;
        }
        cur = head;
        n1 = head.next;
        Node n2 = n1;
        while (cur != null){
            cur.next = n2.next;
            cur = cur.next;
            n2.next = cur == null? null:cur.next;
            n2 = n2.next;
        }
        return n1;
    }

}
