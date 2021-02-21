package nodes;


/**
 * 链表
 */
 class ListNode {
    int val;
    ListNode next;
}
public class LinkNode {

    /**
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     * (2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 7 -> 0 -> 8
     * 342 + 465 = 807
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        while (l1 !=null){

        }

        return null;
    }

    /**
     * 链表的冒泡排序
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next ==null){
            return head;
        }
        ListNode cur = null,tail = null;
        cur = head;
        while (cur.next !=tail){
            while (cur.next != tail) {
                if (cur.val > cur.next.val) {
                    int temp = cur.val;
                    cur.val = cur.next.val;
                    cur.next.val = temp;
                }
                cur = cur.next;
            }
            tail = cur;
            cur = head;
        }
        return head;
    }

    /**
     * 链表的快排
     * @param begin
     * @param end
     * @return
     */
    public ListNode sortListF(ListNode begin,ListNode end){
        if (begin == null || begin ==end || begin == end){
            return begin;
        }

        ListNode first = begin;
        ListNode second = begin.next;

        return null;
    }



    /**
     * 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
     * O(NlogN)
     * @param
     * @param
     * @return
     */
       public ListNode mergeKLists(ListNode[] lists) {
           int len = lists.length;
           if (len == 0){
               return null;
           }
           return mergeKLists(lists,0,len-1);
       }

    public ListNode mergeKLists(ListNode[] lists, int l, int r) {
           if (l==r){
               return lists[l];
           }
           int mid = (r-l)/2+l;
           ListNode l1 = mergeKLists(lists,l,mid);
           ListNode l2 = mergeKLists(lists,mid+1,r);
           return mergeTwoSortedListNode(l1,l2);
    }

    public ListNode mergeTwoSortedListNode(ListNode l1, ListNode l2) {
           if (l1 == null){
               return l2;
           }
           if (l2 == null){
               return l1;
           }
           if (l1.val < l2.val){
               l1.next = mergeTwoSortedListNode(l1.next,l2);
               return l1;
           }
           l2.next = mergeTwoSortedListNode(l1,l2.next);
           return l2;
    }

    /**
     * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
     * 输入：1->2->4, 1->3->4
     * 输出：1->1->2->3->4->4
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode head = null;
        if (l1.val <= l2.val){
            head = l1;
            head.next = mergeTwoLists(l1.next,l2);
        }else {
            head = l2;
            head.next = mergeTwoLists(l1,l2.next);
        }
        return head;
    }
}
