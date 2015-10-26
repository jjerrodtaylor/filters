package edu.uba.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math3.stat.Frequency;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.Map;
/**
 * Created by jamaaltaylor on 10/23/15.
 */
public class Entropy {

    private Frequency frequency = new Frequency();

    Entropy(){
        super();
    }

    Entropy(List<String> data){
        for(String s:data){
            frequency.addValue(s);
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
        Table<String,String,Integer> conditionalData = HashBasedTable.create();
        frequency.clear();

        for(int i=0;i<reducingSet.size();i++){
            frequency.addValue(reducingSet.get(i));
            conditionalData.put(interestedSet.get(i),reducingSet.get(i),i);
        }

        Integer num = conditionalData.row(interestedClass).size();
        Integer den = conditionalData.column(reducingClass).size();

        return (double) num/den;
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
