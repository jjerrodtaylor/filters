package edu.uba.filters;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.HashMultiset;

import java.util.Comparator;

public class Frequency<T extends Comparable<T>> {

    private Multiset event = HashMultiset.create();

    public void addValue(T data){
        event.add(data);
    }

    public void clear(){
        event.clear();
    }

    public double getPct(T data){
        int numberOfIndElements = event.count(data);
        int totalNumOfElements = event.size();
        return (double) numberOfIndElements/totalNumOfElements;
    }

    public int getSumFreq(){
        return event.size();
    }

    public int getUniqueCount(){
        return event.entrySet().size();
    }
}
