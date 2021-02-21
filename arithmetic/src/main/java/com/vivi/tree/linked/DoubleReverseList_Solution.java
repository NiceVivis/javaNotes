package com.vivi.tree.linked;

import com.vivi.tree.linked.DoubleNode;

public class DoubleReverseList_Solution {
    public static void main(String[] args) {
        DoubleNode node = new DoubleNode();
        node.data = 1;
        node.next = new DoubleNode();
        node.next.data = 2;
        node.next.last = node.next;
        node.next.next = new DoubleNode();
        node.next.next.data = 3;
        node.next.next.last = node.next.next;
        node.next.next.next = new DoubleNode();
        node.next.next.next.last = node.next.next.next;
        node.next.next.next.data = 4;
        System.out.println("反转前"+node);
        DoubleNode listNode = reverseList(node);
        System.out.println("反转后"+listNode);
    }
    public static DoubleNode reverseList(DoubleNode head) {
        if (head == null){
            return head;
        }

        // * 输入: 1->2->3->4->5
        // * 输出: 5->4->3->2->1
        DoubleNode first = null;
        DoubleNode last = null;
        while (head !=null){
            last = head.next;
            head.next = first;
            first.last = last;
            first = head;
            head = last;
        }
        return first;
    }
}
