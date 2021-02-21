package nodes;
import org.w3c.dom.NodeList;

public class SignleNode {

    static class Node{
        int val;
        Node next;
    }
    static class DoubleNode{
        int val;
        DoubleNode pre;
        DoubleNode next;
    }

    /**
     * 反转单链表
     * @param head
     * @return
     */
    public static Node resverseNode(Node head){
        Node first = null;
        Node next = null;
        if (head == null){
            return head;
        }
        while (head != null){
            next = head.next;
            head.next = first;
            first = head;
            head=next;
        }

        return first;
    }

    /**
     * 反转双链表
     * @param doubleNode
     * @return
     */
    public static DoubleNode DoubleReveraeNode(DoubleNode doubleNode){
        DoubleNode first = null;
        DoubleNode next = null;
        if (doubleNode == null){
            return null;
        }
        while (doubleNode !=null){
            next = doubleNode.next;
            doubleNode.pre = next;
            doubleNode.next = first;
            first = doubleNode;
            doubleNode = next;
        }
        return first;
    }

    /**
     * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
     * @param head
     * @param n
     * @return
     */
    public Node removeNthFromEnd(Node head, int n) {
        Node p = head;
        Node q = head;
        for (int i = 0;i< n;i++){
            p = p.next;
        }
        if (p == null){
            return head.next;
        }
        while (p.next != null){
            p = p.next;
            q = q.next;
        }
        q.next = q.next.next;

        return head;
    }

    public static void main(String[] args) {
        Node node = new Node();
        node.val=1;
        node.next = new Node();
        node.next.val = 2;
        node.next.next = new Node();
        node.next.next.val=3;
        node.next.next.next = new Node();
        node.next.next.next.val=4;
        resverseNode(node);

        int n=2;
        int pos=method(node,n);
        if(pos==n-1){
            node=node.next;
        }

    }

    public static Node removeNthFromEnd2(Node head, int n) {
        int pos=method(head,n);
        if(pos==n-1){
            head=null;
        }
        return head;
    }
        //1 2 3 4 5
    public static int method(Node node,int n){
        if(node.next==null){
            return 0;
        }

        int pos=method(node.next,n)+1;
        if(pos==n){
            node.next=node.next.next;
        }
        return pos;
    }
}
