package edu.uba.filters;

import java.util.*;

import org.apache.commons.math3.util.Pair;


public class Probability {

    protected Frequency<String> iFrequency = new Frequency<String>();
    protected Frequency<String> rFrequency = new Frequency<String>();
    private HashMap<String,Double> priors = new HashMap<String, Double>();


    public Probability(){
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

    public String naiveBayesPredict(Data data,List<String> targetClasses){

        int numOfKeys = data.getData().keySet().toArray().length;
        Object[] keyNames = data.getData().keySet().toArray();
        List<Pair<String,Double>> predictions = new LinkedList<Pair<String, Double>>();
        double prob = 0;
        String name = null;

        for(int j=0;j<targetClasses.size();j++){
            prob = priors.get(targetClasses.get(j));
            for(int i=0;i<numOfKeys;i++){
                name = targetClasses.get(j)+"|"+Util.convertToString(keyNames[i]);
                prob = prob * priors.get(name);
            }
            Pair<String,Double> pred = new Pair<String, Double>(targetClasses.get(j),prob);
        }

        Collections.sort(predictions, new Comparator<Pair<String, Double>>() {
            public int compare(Pair<String, Double> o1, Pair<String, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        return predictions.get(0).getKey();

    }

}