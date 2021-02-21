package com.vivi.queue;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制。它应该支持以下操作： 获取数据 get 和 写入数据 put
 */
public class LRUCache {
    Map<Integer,Integer> map;
    Stack stack;
    int size;

    public LRUCache(int capacity) {
        stack = new Stack<>();
        map = new HashMap<>(capacity);
        size = capacity;
    }

    /**
     * 获取数据
     * @param key
     * @return
     */
    public int get(int key) {
        if (!stack.contains(key)){
            return -1;
        }
        boolean flag = stack.remove(Integer.valueOf(key));
        stack.push(key);
        return map.get(key);
    }

    public void put(int key, int value) {
        if (stack.contains(key)){
            stack.remove(Integer.valueOf(key));
        }else {
            if (stack.size() == size){
                int count = (int) stack.remove(0);
                map.remove(count);
            }
        }
        stack.push(key);
        map.put(key,value);
    }
}

