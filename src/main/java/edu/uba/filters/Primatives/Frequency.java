package edu.uba.filters.Primatives;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.HashMultiset;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

public class Frequency<T extends Comparable<T>> {

    private Multiset event = HashMultiset.create();
    private Multimap event2 = LinkedListMultimap.create();

    public void addValue(T data){
        if(event2.containsKey(data) == false){
            event2.put(data,data);
        }

        event.add(data);
    }

    public void clear(){

        this.event = null;
        this.event2 = null;

        this.event = HashMultiset.create();
        this.event2 = LinkedListMultimap.create();
    }

    public double getPct(T data){
        int numberOfIndElements = event.count(data);
        int totalNumOfElements = event.size();
        return (double) numberOfIndElements/totalNumOfElements;
    }

    public int getNum(T data){
        int numberOfIndElements = event.count(data);
        return numberOfIndElements;
    }

    public int getSumFreq(){
        return event.size();
    }

    public int getUniqueCount(){
        return event.entrySet().size();
    }

    public String[] getKeys(){
        Set<String> test = event2.keySet();
        Object[] keys = test.toArray();
        String[] keysAsStrings = new String[keys.length];

        for(int i=0;i<keys.length;i++){
            keysAsStrings[i] = (String) keys[i];
        }

        return keysAsStrings;
    }
}
