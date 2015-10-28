package edu.uba.filters;

import java.util.*;

import edu.uba.filters.Frequency;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class Entropy {

    Entropy(){
        super();
    }


    private double log(double num, int base){
       return Math.log(num)/Math.log(base);
    }

    public double entropy(List<String> data){

        double entropy = 0.0;
        double prob = 0.0;
        Frequency<String> frequency = new Frequency<String>();

        for(String s:data){
            frequency.addValue(s);
        }

        String[] keys = frequency.getKeys();

        for(int i=0;i<keys.length;i++){

            prob = frequency.getPct(keys[i]);
            entropy = entropy - prob * log(prob,2);
        }

        return entropy;
    }

    public double conditionalEntropy(List<String> interestedSet, List<String> reducingSet, String interestedClass, String reducingClass){

        int total;
        int counter = 0;
        double probXY = 0.0;
        double probY = 0.0;
        double entropy = 0.0;
        Frequency<String> interestedFrequency = new Frequency<String>();
        Frequency<String> reducingFrequency = new Frequency<String>();

        List<Integer> conditionalData = new LinkedList<Integer>();

        for(int i=0;i<reducingSet.size();i++){
            interestedFrequency.addValue(reducingSet.get(i));
            reducingFrequency.addValue(interestedSet.get(i));
            if(reducingSet.get(i).equalsIgnoreCase(reducingClass)){
                if(interestedSet.get(i).equalsIgnoreCase(interestedClass)){
                    conditionalData.add(i);
                }
            }
        }

        String[] keys = reducingFrequency.getKeys();
        for(int i =0;i<reducingFrequency.getKeys().length;i++){
            probXY = (double) conditionalData.size()/reducingSet.size();
            probY = (double) interestedFrequency.getPct(keys[i]);
            entropy = entropy - probXY*log(probY,2);
        }

        return entropy;
    }

    public double informationGain(List<String> interestedSet, List<String> reducingSet, String interestedClass, String reducingClass){

        double infoGain = entropy(interestedSet) - conditionalEntropy(interestedSet,reducingSet,interestedClass,reducingClass);
        return infoGain;
    }

    public double symmetricalUncertainty(List<String> interestedSet, List<String> reducingSet, String interestedClass, String reducingClass){
        double symUnc = 2 * (informationGain(interestedSet, reducingSet, interestedClass, reducingClass)/(entropy(interestedSet)+entropy(reducingSet)));
        return symUnc;
    }
}
