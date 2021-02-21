package com.vivi.tree.linked;

/**
 * 定义链表类
 */
public class MyDoubleNodeList<T> {

    private DoubleNode node =new DoubleNode(); //空节点

    private DoubleNode next; //下一个节点

    private DoubleNode last;//表示最后一个节点

    private int size;

    /**
     * 向单向链表添加节点
     * @param t
     */
    public void add(T t){
        next = new DoubleNode(t);
        if (size == 0){  //如果是第一个节点
            node.setNext(next); //添加到空节点
            last = next; //更新最后一个节点
            size ++;
        }else {
            last.setNext(next); //添加到最后一个节点后面
            last = next; //更新最后一个节点
            size++;
        }
    }

    /**
     * 添加双向链表
     * @param t
     */
    public void doulAdd(T t){
        next = new DoubleNode(t);
        if (size == 0){
            node.setNext(next);
            last = next;//更新最后一个节点
            size++;
        }else {
            last.setNext(next); //添加到最后一个节点后面
            next.setLast(last); //next指向前一个节点
            last = next; //更新最后一个节点
            size++;
        }
    }

    /**
     * get节点元素的方法
     * @param index
     * @return
     */
    public Object get(int index){
        DoubleNode nodeList = node.getNext();
        for (int i = 0;i<index;i++){
            nodeList = nodeList.getNext();
        }
        return nodeList.getData();
    }

    public static void main(String[] args) {
        MyDoubleNodeList<Integer> nodeList = new MyDoubleNodeList<>();
        for (int i = 0;i<5;i++){
            nodeList.doulAdd(i);
            System.out.print(" "+nodeList.get(i));
        }
    }
}
