package edu.uba.filters;

import java.util.*;
import edu.uba.filters.Frequency;
import org.apache.commons.math3.util.Pair;

public class Entropy {

    private Frequency<String> iFrequency = new Frequency<String>();
    private Frequency<String> rFrequency = new Frequency<String>();
    private HashMap<String,Double> priors = new HashMap<String, Double>();

    Entropy(){
        super();
    }

    public void setInterestedFrequency(List<String> interestedFrequency){
        for(String s: interestedFrequency){
            this.iFrequency.addValue(s);
        }
    }

    public void setReducingFrequency(List<String> reducingFrequency){
        for(String s:reducingFrequency){
            this.rFrequency.addValue(s);
        }
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

    /*
    * return conditional probability of P(interestedClass|reducingClass)
    * */
    public double conditionalProbability(List<String> interestedSet,
                                         List<String> reducingSet,
                                         String interestedClass,
                                         String reducingClass){
        List<Integer> conditionalData = new LinkedList<Integer>();

        if(iFrequency.getKeys().length==0){
            this.setInterestedFrequency(interestedSet);
        }

        if(rFrequency.getKeys().length==0){
            this.setReducingFrequency(reducingSet);
        }

        for(int i = 0;i<reducingSet.size();i++){
            if(reducingSet.get(i).equalsIgnoreCase(reducingClass)){
                if(interestedSet.get(i).equalsIgnoreCase(interestedClass)){
                    conditionalData.add(i);
                }
            }
        }

        int numerator = conditionalData.size();
        int denominator = this.rFrequency.getNum(reducingClass);

        return (double)numerator/denominator;
    }

    public double specifiedConditionalEntropy(List<String> interestedSet,
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
    }

    public double jointEntropy(List<String> set1, List<String> set2){

        String[] set1Keys;
        String[] set2Keys;
        Double prob1;
        Double prob2;
        Double entropy = 0.0;

        if(this.iFrequency.getKeys().length==0){
            this.setInterestedFrequency(set1);
        }

        if(this.rFrequency.getKeys().length==0){
            this.setReducingFrequency(set2);
        }

        set1Keys = this.iFrequency.getKeys();
        set2Keys = this.rFrequency.getKeys();

        for(int i=0;i<set1Keys.length;i++){
            for(int j=0;j<set2Keys.length;j++){
                prob1 = iFrequency.getPct(set1Keys[i]);
                prob2 = rFrequency.getPct(set2Keys[j]);

                entropy = entropy - (prob1*prob2)*log((prob1*prob2),2);
            }
        }

        return entropy;
    }

    public double conditionalEntropy(List<String> interestedSet, List<String> reducingSet){

        double jointEntropy = jointEntropy(interestedSet,reducingSet);
        double reducingEntropyX = entropy(reducingSet);
        double conEntYgivenX = jointEntropy - reducingEntropyX;

        return conEntYgivenX;
    }

    public double informationGain(List<String> interestedSet, List<String> reducingSet){
        double entropy = entropy(interestedSet);
        double conditionalEntropy = conditionalEntropy(interestedSet,reducingSet);
        double infoGain = entropy - conditionalEntropy;
        return infoGain;
    }

    public double symmetricalUncertainty(List<String> interestedSet, List<String> reducingSet){
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
    }

    public void naiveBayesTrain(Data data,List<String> targetClass){

        int numOfClasses = data.getData().keySet().toArray().length;
        Object[] keyNames = data.getData().keySet().toArray();
        Frequency<String> probs = new Frequency<String>();
        double conditionalProb = 0.0;
        String priorName;
        String probName;

        for(int i=0;i<targetClass.size();i++){
            probName = targetClass.get(i)+"|"+targetClass.get(i);
            probs.addValue(targetClass.get(i));
            priors.put(probName,probs.getPct(targetClass.get(i)));
        }

        for(int i=1;i<numOfClasses;i++){
            conditionalProb = conditionalProbability(data.getData().get(targetClass.get(i)),
                                   data.getData().get(Util.convertToString(keyNames[i])),
                                   targetClass.get(i),
                                   Util.convertToString(keyNames[i]));
            priorName = targetClass.get(i)+"|"+Util.convertToString(keyNames[i]);
            priors.put(priorName,conditionalProb);
        }
    }

    public void naiveBayesPredict(){

    }
}
