package com.lss.algorithm.study;

import java.util.*;

public class SlideWindow {

    /**
     * 滑动窗口左开右闭区间 【left，right）
     * 1. right 自增，窗口向右移，直至不能移动。
     * 2. 在窗口右移的过程中，查看是否有解，如果窗口内有解，那么暂停向右扩大窗口。
     * 3. 在窗口有解的情况下，向左压缩窗口，在压缩的过程中看没有可能找到局部最优解。
     * 4. 压缩后，如果窗口里面已经没有解，则right自增，重复2-4过程。
     * 
     * @param s  输入字符串
     * @param t  目标字符串
     * @return   找到在s中包含t的最小子串
     */
    public static String minWindow(String s,String t){
        int left = 0;
        int right = 0;
        int valid = 0;
        int start = 0;
        int len = Integer.MAX_VALUE;

        Map<Character,Integer> needs = new HashMap<>();
        for(Character c : t.toCharArray()){
            needs.put(c, needs.getOrDefault(c,0) + 1);
        }

        Map<Character,Integer> window = new HashMap<>();

        while (right < t.length()){
            char c = t.charAt(right);
            right ++;

            if(needs.containsKey(c)){
                window.put(c,window.getOrDefault(c,0) + 1);
                if(Objects.equals(window.get(c), needs.get(c))){
                    valid ++;
                }
            }

            while (valid == needs.size()){
                if(right - left < len){
                    len = right - left;
                    start = left;
                }
                char d = s.charAt(left);
                left ++;
                if(needs.containsKey(d)){
                    if(Objects.equals(window.get(c), needs.get(c))){
                        valid --;
                    }
                    window.put(c, window.get(c) - 1);
                }
            }
        }

        return len == Integer.MAX_VALUE ? "" : s.substring(start,len);
    }

    /**
     * 给定一个S和一个非空字符串t,找到S中所有是T的字母异位词的字串，返回这些字串的起始索引
     * 字母异位词 其实就是全排列
     * 例如 "cbaebabacd" 和 "abc" [0,6]
     * @param s 字符串S
     * @param t 字符串t
     * @return
     */
    public static List<Integer> findAnagrams(String s, String t) {
        List<Integer> list = new LinkedList<>();
        int left = 0 ;
        int right = 0;
        Map<Character,Integer> needs = new HashMap<>();
        for(Character c : t.toCharArray()){
            needs.put(c, needs.getOrDefault(c,0) + 1);
        }

        int valid = 0;
        Map<Character,Integer> window = new HashMap<>();

        while (right < s.length()){
            char c = s.charAt(right);
            right ++ ;

            if(needs.containsKey(c)){
                window.put(c, window.getOrDefault(c,0) + 1);

                if(Objects.equals(window.get(c), needs.get(c))){
                    valid  ++;
                }
            }

            while (right - left >= t.length()){
                if(valid == needs.size()){
                    list.add(left);
                }
                char d = s.charAt(left);
                left ++;
                if(needs.containsKey(d)){
                    if(Objects.equals(window.get(d), needs.get(d))){
                        valid --;
                    }
                    window.put(d,window.getOrDefault(d,0) - 1);
                }
            }
        }
        return list;
    }

    public static int getLongestSubString(String s) {

        int left = 0 ;
        int right = 0;
        Map<Character,Integer> windowMap = new HashMap<>();
        int len = -1;
        while (right < s.length()){
            Character c = s.charAt(right);
            right ++;

            if(!windowMap.containsKey(c)){
                windowMap.put(c,right - 1);
                if(len < right - left){
                    len = right - left;
                }
            } else {
                left = windowMap.get(c) + 1;
                windowMap.put(c,right - 1);
            }
        }
        return len;
    }

    public static void main(String[] args) {
        String s = "ADBECFEBACG";
        String t = "ABC";
        System.out.println(minWindow(s,t));

        s = "cbaebabacd";
        t = "abc";
        System.out.println(findAnagrams(s,t));

        System.out.println(getLongestSubString("aabab"));
    }
}
