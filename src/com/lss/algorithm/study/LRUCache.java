package com.lss.algorithm.study;

import java.util.HashMap;

/**
 * LRU : Least Recently Used 最近最少使用缓存淘汰策略
 *
 * 接收一个capacity参数作为缓存的最大容量
 * 实现两个api:
 *  1. put(key,val)方法更新值
 *  2. get(key)方法返回对应的val,如果key不存在的话，返回-1
 * 要求：
 *  1. 两个操作的时间复杂度为O(1)
 *  2. 满足LRU的规则：当容量超过capacity的时候，淘汰最近最少使用的数据
 * 例如：
 *  capacity= 2
 *  put(1,1)  LRU : [(1,1)]
 *  put(2,2)  LRU : [(1,1),(2,2)]
 *
 *  put(3,3)  LRU: [(2,2),(3,3)] //(2，2）比（1,1)操作的时间距离现在时间更短，所以淘汰（1，1）
 *  put(2,4)  LRU: [(3,3),(2,4)]
 *
 *  思路：
 *     1. 在put的过程中，需要淘汰最近最少使用的数据，那么需要便捷地访问最近最少使用的数据。需要在put的时候
 *        就已经保证了我们按照最近使用的规则排好序，临时排序肯定难以满足时间复杂度的需求。
 *     2. get()也需要快速访问。很容易想到map或者数组，但是，数组在我们淘汰数据重新按照规则排序的时候，需要
 *        移动索引，时间复杂度不满足需求。
 *  ==>
 *      快速查找，并且有序，可以使用LinkedHashMap
 *      先自己实现LinkedHashMap
 *
 */
public class LRUCache {

    class Node {
        public int key ,val;
        public Node next,prev;
        public Node(int k, int v){
            this.key = k;
            this.val = v;
        }
    }

    //双向列表
    class DoubleList {

        private Node head,tail;
        private int size ;

        public DoubleList(){
            head = new Node(0,0);
            tail = new Node(0,0);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        public void addLast(Node x){
            x.prev = tail.prev;
            x.next = tail;
            tail.prev.next = x;
            tail.prev = x;
            size ++;
        }

        //这里可以快速删除
        public void remove(Node x){
            x.prev.next = x.next;
            x.next.prev = x.prev;
            size --;
        }

        public Node removeFirst(){
            if(head.next == tail){
                return null;
            }
            Node first = head.next;
            remove(first);
            return first;
        }

        public int size(){
            return size;
        }

    }

    //快速找到之前的节点
    private HashMap<Integer,Node> map;
    private DoubleList cache;
    //最大容量
    private int cap;

    public LRUCache(int capacity){
        this.cap = capacity;
        map = new HashMap<>();
        cache = new DoubleList();
    }

    /**
     * 标记为最新:最后的是最新使用的
     * @param key
     */
    private void makeRecently(int key){
        Node x = map.get(key);
        cache.remove(x);
        cache.addLast(x);
    }

    private void addRecently(int key ,int val){
        Node x = new Node(key,val);
        cache.addLast(x);
        map.put(key,x);
    }

    private void deleteKey(int key){
        Node x = map.get(key);
        cache.remove(x);
        map.remove(key);
    }

    private void removeLeastRecently(){
        Node deletedNode = cache.removeFirst();
        int deletedKey = deletedNode.key;
        map.remove(deletedKey);
    }

    public void put(int key,int val){
        if(map.containsKey(key)){
            deleteKey(key);
            addRecently(key,val);
            return;
        }

        if(cap == cache.size()){
            removeLeastRecently();
        }

        addRecently(key,val);
    }

    public int get(int key){
        if(!map.containsKey(key)){
            return -1;
        }
        makeRecently(key);
        return map.get(key).val;
    }

    public void traverse(){
        Node cur = cache.head.next;
        while (cur != cache.tail){
            System.out.println(cur.key + " = " + cur.val);
            cur = cur.next;
        }
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(1,1);

        cache.traverse();

        System.out.println("add 2");
        cache.put(2,2);
        cache.traverse();

        System.out.println("add 3");
        cache.put(3,3);
        cache.traverse();

        System.out.println("update 2");
        cache.put(2,22);
        cache.traverse();

    }
}
