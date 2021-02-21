package com.vivi.tree.linked;

import lombok.Data;

/**
 * 定义节点类
 */
@Data
class DoubleNode<T> {

    DoubleNode last;

    DoubleNode next;

    T data;

    public DoubleNode() {
    }

    public DoubleNode(T data) {
        this.data = data;
    }
}

