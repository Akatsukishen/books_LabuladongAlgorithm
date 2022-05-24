package com.lss.algorithm.study;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class NestedInteger {

    private int value;
    private List<NestedInteger> list;

    public NestedInteger(int value){
        this.value = value;
    }

    public NestedInteger(List<NestedInteger> list){
        this.list = list;
    }

    public boolean isInteger() {
        return list == null;
    }

    public Integer getInteger() {
        return value;
    }

    public List<NestedInteger> getList(){
        return list;
    }

    public static class NestedIterator implements Iterator<Integer>{

        private List<Integer> values = new ArrayList<>();
        private int currentIndex = 0;

        public NestedIterator(List<NestedInteger> nestedList){
            for(NestedInteger nestedInteger : nestedList){
                iteratorAdd(nestedInteger);
            }
        }

        private void iteratorAdd(NestedInteger nestedInteger){
            if(nestedInteger.isInteger()){
                values.add(nestedInteger.getInteger());
            } else {
                for(NestedInteger nestedList : nestedInteger.getList()){
                    iteratorAdd(nestedList);
                }
            }
        }

        @Override
        public boolean hasNext() {
            return currentIndex < values.size();
        }

        @Override
        public Integer next() {
            return values.get(currentIndex++);
        }
    }

    public static class LazyIterator implements  Iterator<Integer> {

        private LinkedList<NestedInteger> list;

        public LazyIterator(List<NestedInteger> nestedList) {
            list = new LinkedList<>(nestedList);
        }

        @Override
        public boolean hasNext() {
            while (!list.isEmpty() && !list.get(0).isInteger()){
                List<NestedInteger> first = list.remove(0).list;
                for(int i = first.size() - 1; i >=0 ; i--){
                    list.addFirst(first.get(i));
                }
            }
            return !list.isEmpty();
        }

        @Override
        public Integer next() {
            return list.remove(0).getInteger();
        }
    }

    public static void main(String[] args) {
        NestedInteger nested1 = new NestedInteger(1);
        NestedInteger nested21 = new NestedInteger(21);
        NestedInteger nested22 = new NestedInteger(22);
        NestedInteger nested23 = new NestedInteger(23);
        List<NestedInteger> list2 = new ArrayList<>();
        list2.add(nested21);
        list2.add(nested22);
        list2.add(nested23);
        NestedInteger nested2 = new NestedInteger(list2);

        NestedInteger nested31 = new NestedInteger(31);

        List<NestedInteger> listed32 = new ArrayList<>();
        listed32.add(new NestedInteger(322));
        listed32.add(new NestedInteger(3222));
        NestedInteger nested32 = new NestedInteger(listed32);


        List<NestedInteger> list3 = new ArrayList<>();
        list3.add(nested31);
        list3.add(nested32);

        NestedInteger nested3 = new NestedInteger(list3);

        List<NestedInteger> list = new ArrayList<>();
        list.add(nested1);
        list.add(nested2);
        list.add(nested3);

        LazyIterator iterator = new LazyIterator(list);
        while (iterator.hasNext()){
            System.out.print(iterator.next() + "\t");
        }

    }

}
