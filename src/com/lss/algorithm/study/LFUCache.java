package com.lss.algorithm.study;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * LFU : Least Frequently Used 最少使用频次淘汰策略
 * 频次相同时淘汰最老的数据
 *
 */
public class LFUCache {

    private HashMap<Integer,Integer> keyValueMap = new HashMap<>();
    private HashMap<Integer,Integer> keyFreqMap = new HashMap<>();
    private HashMap<Integer,LinkedHashSet<Integer>> freqKeyGroupMap = new HashMap<>();

    private int minFreq = Integer.MAX_VALUE;

    private int cap ;

    public LFUCache(int cap){
        this.cap = cap;
    }

    public void put(int key,int val){
        if(keyValueMap.containsKey(key)){
            increaseFreq(key);
            keyValueMap.put(key,val);
        } else {

            if(keyValueMap.size() == cap){
                removeMinFreqOldest();
            }
            keyValueMap.put(key,val);
            keyFreqMap.put(key,1);

            if(minFreq > 1){
                minFreq = 1;
            }

            LinkedHashSet<Integer> keys = freqKeyGroupMap.get(1);
            if(keys == null){
                keys = new LinkedHashSet<>();
            }
            keys.add(key);
            freqKeyGroupMap.put(1,keys);
        }
    }

    private void removeMinFreqOldest(){
        LinkedHashSet<Integer> keys = freqKeyGroupMap.get(minFreq);

        int minFreqOldestKey = keys.iterator().next();

        keys.remove(minFreqOldestKey);
        keyValueMap.remove(minFreqOldestKey);
        keyFreqMap.remove(minFreqOldestKey);
    }

    public int get(int key){
        if(keyValueMap.containsKey(key)){
            increaseFreq(key);
            return keyValueMap.get(key);
        }
        return -1;
    }

    private void increaseFreq(int key){
        int freq = keyFreqMap.get(key);
        //更新频率
        keyFreqMap.put(key, freq + 1);
        HashSet<Integer> freqKeys = freqKeyGroupMap.get(freq);
        //更新频率所在组
        freqKeys.remove(key);

        //如果最低频率所在组都删除了
        if(freq == minFreq && freqKeys.size() == 0){
            minFreq = freq + 1;
        }

        LinkedHashSet<Integer> newFreqKeys = freqKeyGroupMap.get(freq + 1);
        if(newFreqKeys == null){
            newFreqKeys = new LinkedHashSet<>();
        }
        newFreqKeys.add(key);
        freqKeyGroupMap.put(freq + 1,newFreqKeys);
    }

    private void traverse(){
        Iterator<Integer> iterator = keyValueMap.keySet().iterator();
        while (iterator.hasNext()){
            Integer key = iterator.next();
            System.out.println("[" + key +"] = " + keyValueMap.get(key) + ",freq = " + keyFreqMap.get(key));
        }
    }

    public static void main(String[] args) {
        LFUCache lfuCache = new LFUCache(2);

        lfuCache.put(1,10);
        lfuCache.put(2,20);

        lfuCache.traverse();

        System.out.println("add 3");
        lfuCache.put(3,30);
        lfuCache.traverse();

        System.out.println("update 3");
        lfuCache.put(3,33);
        lfuCache.traverse();

        System.out.println("add 4");
        lfuCache.put(4,40);
        lfuCache.traverse();
    }
}
