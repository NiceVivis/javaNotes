package com.vivi.tree.linked;

/**
 * 链表每K个节点间逆序
 * 给定一个单链表的头节点head，实现一个调整单链表的函数，使得每K个节点之间逆序，如果最后不够K个节点一组，则不调整最后几个节点
 * 如：
 * 1->2->3->4->5->6->7->8->null,k = 3
 * 3->2->1->6->5->4->7->8->null
 * 因为 k == 3,所以每三个节点之间逆序，但其中的7，8不调整，因为只要两个节点不够一组
 */
public class Inverse_Solution {

    public static ListNode inverse(ListNode head,int k){
        ListNode resultHead = null;
        ListNode lastGroupTail = null;
        ListNode nextGroupHead = null;
        ListNode curr = head;
        int i = 0;
        //如果链表长度为k的整数倍，那么要依赖这个条件退出
        while (curr != null){
            for (;i<k-1 && curr.next != null;++i){
                curr = curr.next;
            }
            //够k个
            if (i == k-1){
                //此时curr为新的head，head为tail
                i = 0;
                nextGroupHead = curr.next;
                inverse(head,curr);
            }else {
                //当前组元素个数不足k，退出
                if (lastGroupTail == null){
                    resultHead = head;
                }else {
                    lastGroupTail.next = head;
                }
                break;
            }
            if (resultHead == null){
                resultHead = curr;
            }
            if (lastGroupTail != null){
                lastGroupTail.next = curr;
            }
            lastGroupTail = head;
            head = nextGroupHead;
            curr = head;
        }
        return resultHead;
    }

    /**
     * 链表反转
     * @param head
     * @param tail
     */
    private static void inverse(ListNode head,ListNode tail){
        ListNode pre = null,curr = head,post = head.next;
        while (curr != tail){
            curr.next = pre;
            pre = curr;
            curr = post;
            post = post.next;
        }
        curr.next = pre;
    }

}
