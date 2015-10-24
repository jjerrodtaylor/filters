package edu.uba.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jamaaltaylor on 10/23/15.
 */
public class Entropy {

    private class Event{
        String interestedAttribute = null;
        String reducingAttribute = null;
        HashMap<String,Integer> classCounter = null;
        HashMap<String, Integer> conditionalCounter = null;
        Double probability = 0.0;

        Event(String interestedAttribute){
            this.interestedAttribute = interestedAttribute;
            this.conditionalCounter = new HashMap<String, Integer>();
        }

        Event(String interestedAttribute, String reducingAttribute){
            this.interestedAttribute = interestedAttribute;
            this.reducingAttribute = reducingAttribute;
            this.conditionalCounter = new HashMap<String, Integer>();
            conditionalCounter.put(interestedAttribute,0);
            conditionalCounter.put(reducingAttribute,0);
        }

        public void getClassCounter(HashMap<String,Integer> classCounter){
            this.classCounter = classCounter;
        }

        public void add(String attribute){
            int count = 0;

            if(!classCounter.containsKey(attribute)){
                classCounter.put(attribute,0);
            }else{
                count = classCounter.get(attribute);
                count = count+1;
                classCounter.put(attribute,count);
            }
        }

        public void add(String iAttribute, String rAttribute){
            int iCount = conditionalCounter.get(iAttribute);
            int rCount = conditionalCounter.get(rAttribute);
            iCount = iCount + 1;
            rCount = rCount + 1;

            conditionalCounter.put(iAttribute,iCount);
            conditionalCounter.put(rAttribute,rCount);
        }

        public double probability(String value){

            Iterator<String> keySet;
            double prob = 0.0;
            int total = 0;

            keySet = classCounter.keySet().iterator();
            for(String key:classCounter.keySet()){
                total = total + classCounter.get(key);
            }

            return (double) classCounter.get(value)/total;
        }

        public double conditionalProbability(String interestedValue, String conditioningValue){
            return 0.0;
        }
    }

    private double log(double num, int base){
       return Math.log(num)/Math.log(base);
    }


    public double entropy(List<String> data){
        HashMap<String, Integer> classCounter = new HashMap<String, Integer>();
        int count;
        double entropy = 0;
        Iterator<String> keySet;
        Integer total = data.size();
        double prob;

        //determine how many elements of each class are in your set
        for(int i = 0; i<data.size();i++){
            if(!classCounter.containsKey(data.get(i))){
                classCounter.put(data.get(i),0);
            }else {
                count = classCounter.get(data.get(i));
                count = count+1;
                classCounter.put(data.get(i),count);
            }
        }

        keySet = classCounter.keySet().iterator();
        for(String key:classCounter.keySet()){
            Integer keyCount = classCounter.get(key);
            prob = (double) keyCount.intValue()/total.intValue();
            entropy = entropy - prob * log(prob,2);
        }

        return entropy;
    }

    public double conditionalEntropy(List<String> interestedSet, List<String> reducingSet, String interestedClass, String reducingClass){

        int total;
        HashMap<String,Integer> unconditionalCounter = new HashMap<String, Integer>();
        HashMap<String,Integer> conditionalCounter = new HashMap<String, Integer>();
        double entropy = 0;

        //first find out how many observations meet the criteria of the reducing set
        Event conditionalProb = new Event(interestedClass,reducingClass);
        conditionalProb.getClassCounter(conditionalCounter);

        Event unconditionalProb = new Event(interestedClass);
        unconditionalProb.getClassCounter(unconditionalCounter);

        for(int i = 0;i<reducingSet.size();i++){
            unconditionalProb.add(reducingSet.get(i));

            if (reducingSet.get(i).equalsIgnoreCase(reducingClass)){
                conditionalProb.add(reducingSet.get(i));
            }
        }
        return 0.0;
    }
}
