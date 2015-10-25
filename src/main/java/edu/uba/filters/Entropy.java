package edu.uba.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math3.stat.Frequency;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
/**
 * Created by jamaaltaylor on 10/23/15.
 */
public class Entropy {

    private Frequency frequency = new Frequency();

    Entropy(List<String> data){
        for(String s:data){
            frequency.addValue(s);
        }
    }

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

        public void setClassCounter(HashMap<String,Integer> classCounter){
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

        //I might have to come back to this.
        public double conditionalProbability(String interestedValue, String conditioningValue){

            int numerator;
            int denominator;

            numerator = classCounter.get(interestedValue);
            denominator = conditionalCounter.get(conditioningValue);
            return (double) numerator/denominator;
        }
    }

    private double log(double num, int base){
       return Math.log(num)/Math.log(base);
    }

    public double entropy(List<String> data){

        double entropy = 0;
        double prob;
        //for each unique element
        for(int i = 0; i<frequency.getUniqueCount();i++){
            prob = frequency.getPct(frequency.valuesIterator().next());
            entropy = entropy - (prob) * log(prob,2);
        }

        return entropy;
    }

    public double conditionalEntropy(List<String> interestedSet, List<String> reducingSet, String interestedClass, String reducingClass){

        int total;
        int counter = 0;
        Table<Integer,String,String> conditionalData = HashBasedTable.create();
        frequency.clear();

        for(int i=0;i<reducingSet.size();i++){
            frequency.addValue(reducingSet.get(i));
            conditionalData.put(i,interestedSet.get(i),reducingSet.get(i));
        }


        /*HashMap<String,Integer> unconditionalCounter = new HashMap<String, Integer>();
        HashMap<String,Integer> conditionalCounter = new HashMap<String, Integer>();
        Iterator<String> unconditionalKeySet;
        Iterator<String> conditionalKeySet;

        double entropy = 0;

        //first find out how many observations meet the criteria of the reducing set
        Event conditionalProb = new Event(interestedClass,reducingClass);
        conditionalProb.setClassCounter(conditionalCounter);

        Event unconditionalProb = new Event(interestedClass);
        unconditionalProb.setClassCounter(unconditionalCounter);

        for(int i = 0;i<reducingSet.size();i++){
            unconditionalProb.add(reducingSet.get(i));

            if (reducingSet.get(i).equalsIgnoreCase(reducingClass)){
                conditionalProb.add(reducingSet.get(i));
            }
        }

        conditionalKeySet = conditionalCounter.keySet().iterator();
        unconditionalKeySet = unconditionalCounter.keySet().iterator();

        for(String key:conditionalCounter.keySet()){
            entropy = entropy - unconditionalProb.probability(unconditionalKeySet.next()) *
                                conditionalProb.conditionalProbability(interestedClass,reducingClass) *
                                log(conditionalProb.conditionalProbability(interestedClass,reducingClass),2);
        }   */
        return entropy;
    }

    public double informationGain(){
        return 0.0;
    }
}
