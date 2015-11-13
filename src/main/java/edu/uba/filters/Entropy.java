package edu.uba.filters;

import java.util.*;
import edu.uba.filters.Frequency;
import org.apache.commons.math3.util.Pair;

public class Entropy extends Probability {


    public Entropy(){
        super();
    }

    private double log(double num, int base){
       return Math.log(num)/Math.log(base);
    }

    public double entropy(List<String> data){

        double entropy = 0.0;
        double prob = 0.0;

        if(this.iFrequency.getKeys().length==0){
            this.setInterestedFrequency(data);
        }


        String[] keys = iFrequency.getKeys();

        for(int i=0;i<keys.length;i++){

            prob = iFrequency.getPct(keys[i]);
            entropy = entropy - prob * log(prob,2);
        }

        iFrequency.clear();
        return entropy;
    }

    private double reducedEntropy(List<Pair<String,String>> data){

        double entropy = 0.0;
        double prob = 0.0;
        Frequency<String> frequency = new Frequency<String>();

        for(Pair<String,String> p:data){
            frequency.addValue(p.getFirst());
        }

        String[] keys = frequency.getKeys();

        for(int i=0;i<keys.length;i++){
            prob = frequency.getPct(keys[i]);
            entropy = entropy - prob * log(prob,2);
        }

        return entropy;
    }

    private List<Pair<String,String>> reduceSet(List<String> interestedSet, List<String> reducingSet, String reducingClass){
         List<Pair<String,String>> reduced = new LinkedList<Pair<String, String>>();
        for(int i=0;i<reducingSet.size();i++){
            if(reducingClass.equalsIgnoreCase(reducingSet.get(i))){
                Pair<String,String> addToSet = new Pair<String, String>(interestedSet.get(i),reducingSet.get(i));
                reduced.add(addToSet);
            }
        }

        return reduced;
    }

    public double conditionalEntropy(List<String> interestedSet, List<String> reducingSet){

        String[] set2Keys;
        double entropy = 0.0;
        double reducedEntropy = 0.0;
        List<Pair<String,String>> pairs;

        if(this.iFrequency.getKeys().length==0){
            this.setInterestedFrequency(interestedSet);
        }
        if(this.rFrequency.getKeys().length==0){
            this.setReducingFrequency(reducingSet);
        }

        set2Keys = this.rFrequency.getKeys();

        for(int i=0;i<set2Keys.length;i++){
            String key = set2Keys[i];

            pairs = reduceSet(interestedSet,reducingSet,key);
            reducedEntropy = reducedEntropy(pairs);

            double prob = rFrequency.getPct(key);
            entropy = entropy - (prob* -reducedEntropy);
        }

        iFrequency.clear();
        rFrequency.clear();

        return entropy;
    }

    public double informationGain(List<String> interestedSet, List<String> reducingSet){
        double entropy = entropy(interestedSet);
        double conditionalEntropy = conditionalEntropy(interestedSet,reducingSet);
        double infoGain = entropy - conditionalEntropy;
        return infoGain;
    }

    public List<Pair<String,Double>> igRank(Data data, String targetClass){

        int numOfKeys = data.getNumOfKeys();
        List<Pair<String,Double>> scoredFeatures = new LinkedList<Pair<String, Double>>();
        String[] keys = data.getKeys();
        double infoGain = 0.0;

        //assign a score to the correlations
        for(int i=0;i<numOfKeys;i++){
            if(!targetClass.equalsIgnoreCase(keys[i])){
                List<String> interestedSet = data.dataColumn(keys[i],DataOption.GET,true);// new ArrayList(data.getData().get(keys[i]));
                List<String> targetSet = data.dataColumn(targetClass,DataOption.GET,true);// new ArrayList(data.getData().get(targetClass));
                infoGain = informationGain(targetSet, interestedSet);
                String feature = data.getKey(i);
                Pair<String,Double> featureIndex = new Pair<String,Double>(feature,infoGain);
                scoredFeatures.add(featureIndex);
            }
        }

        //sort the correlations in descending order
        Collections.sort(scoredFeatures, new Comparator<Pair<String, Double>>() {
            public int compare(Pair<String, Double> o1, Pair<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        return scoredFeatures;
    }

    /*public double specifiedConditionalEntropy(List<String> interestedSet,
                                              List<String> reducingSet,
                                              String interestedClass, String reducingClass){

        Double entropy = 0.0;
        Double conditionalProb = 0.0;

        //if the sets are empty then set it
        if(this.iFrequency.getKeys().length==0){
            this.setInterestedFrequency(interestedSet);
        }

        if(this.rFrequency.getKeys().length==0){
            this.setReducingFrequency(reducingSet);
        }

        conditionalProb = conditionalProbability(interestedSet,reducingSet,interestedClass,reducingClass);
        entropy = - conditionalProb * log(conditionalProb,2);

        return entropy;
    }*/

    /*public double symmetricalUncertainty(List<String> interestedSet, List<String> reducingSet){
        double infoGain = informationGain(interestedSet,reducingSet);
        double intSet = entropy(interestedSet);
        double redSet = entropy(reducingSet);
        double symUnc = 2 * ( infoGain/ (intSet+redSet) );
        return symUnc;
    }

    public List<Pair<String,Double>> fcbf(Data data,String targetClass, double threshold){

        int numOfKeys = data.getData().keySet().toArray().length;
        List<Pair<String,Double>> scoredFeatures = new LinkedList<Pair<String, Double>>();
        List<Pair<String,Double>> suAboveThreshold = new LinkedList<Pair<String, Double>>();
        List<Pair<String,Double>> predominantFeatures = new LinkedList<Pair<String, Double>>();
        String[] keys = Util.convertToStringArray(data.getData().keySet().toArray());

        double symUnc = 0.0;

        //assign a score to the correlations
        for(int i=0;i<numOfKeys;i++){
            symUnc = symmetricalUncertainty(data.getData().get(targetClass), data.getData().get(keys[i]));
            if(symUnc > threshold){
                Object feature = data.getData().keySet().toArray()[i];
                Pair<String,Double> featureIndex = new Pair<String,Double>(Util.convertToString(feature),symUnc);
                scoredFeatures.add(featureIndex);
            }
        }

        //sort the correlations in descending order
        Collections.sort(scoredFeatures,new Comparator<Pair<String,Double>>(){
            public int compare(Pair<String,Double> o1, Pair<String,Double> o2){
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        Pair<String,Double> p = suAboveThreshold.get(0);
        predominantFeatures.add(p);
        double pqComparison = 0.0;
        double qcComparison = 0.0;

        for(int i = 0;i<suAboveThreshold.size();i++){
            for(int j = 0;j<predominantFeatures.size();j++){
                Pair<String,Double> q = suAboveThreshold.get(i);
                pqComparison = symmetricalUncertainty(data.getData().get(p.getKey()),data.getData().get(q.getKey()));
                qcComparison = symmetricalUncertainty(data.getData().get(targetClass),data.getData().get(q.getKey()));

                if(pqComparison >= qcComparison){
                    suAboveThreshold.remove(i);
                }else{
                    predominantFeatures.add(suAboveThreshold.get(i));
                    suAboveThreshold.remove(i);
                }
            }

        }

        return predominantFeatures;
    }*/
}
