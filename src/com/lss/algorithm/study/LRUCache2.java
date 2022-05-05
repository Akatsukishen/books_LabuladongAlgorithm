package com.lss.algorithm.study;

import java.util.LinkedHashMap;

/**
 * 使用LinkedHashMap实现LRUCache
 */
public class LRUCache2 {

    private int cap;
    private LinkedHashMap<Integer,Integer> cache = new LinkedHashMap<>();

    public LRUCache2(int capacity){
        this.cap = capacity;
    }

    public int get(int key){
        if(!cache.containsKey(key)){
            return -1;
        }
        makeRecently(key);
        return cache.get(key);
    }

    public void put(int key,int val){
        if(cache.containsKey(key)){
            cache.put(key,val);
            makeRecently(key);
            return;
        }
        if(cache.size() == cap){
            int oldestKey = cache.keySet().iterator().next();
            cache.remove(oldestKey);
        }
        cache.put(key,val);
    }

    private void makeRecently(int key){
        int val = cache.get(key);
        cache.remove(key);
        cache.put(key,val);
    }

}
