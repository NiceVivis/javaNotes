package com.vivi.tree.linked;

import com.vivi.tree.linked.ListNode;

/**
 * 反转一个单链表。
 * 输入: 1->2->3->4->5
 * 输出: 5->4->3->2->1
 */
public class ReverseList_Solution {

    public static void main(String[] args) {
        ListNode node = new ListNode();
        node.data = 1;
        node.next = new ListNode();
        node.next.data = 2;
        node.next.next = new ListNode();
        node.next.next.data = 3;
        node.next.next.next = new ListNode();
        node.next.next.next.data = 4;
        System.out.println("反转前"+node);
        ListNode listNode = reverseList(node);
        System.out.println("反转后"+listNode);

    }
    public static ListNode reverseList(ListNode head) {
        ListNode first = null;
        ListNode last =null;
        if (head == null){
            return head;
        }

        while (head!=null){
            last = head.next;
            head.next = first;
            first = head;
            head = last;
        }
        return first;
    }
}
