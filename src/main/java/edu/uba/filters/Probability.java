package edu.uba.filters;

import java.util.*;

import org.apache.commons.math3.util.Pair;


public class Probability {

    protected Frequency<String> iFrequency = new Frequency<String>();
    protected Frequency<String> rFrequency = new Frequency<String>();
    private String[] targetClassKeys;
    private HashMap<String,Double> priors = new HashMap<String, Double>();
    private LinkedList<Pair<String,Double>> predictions = new LinkedList<Pair<String,Double>>();


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

    public LinkedList<Pair<String,Double>> getPredictions(){
        return this.predictions;
    }
    /*
   * return conditional probability of P(interestedClass|reducingClass)
   * */
    public double conditionalProbability(List<String> interestedSet,
                                         List<String> reducingSet,
                                         String interestedClass,
                                         String reducingClass){
        List<Integer> conditionalData = new LinkedList<Integer>();
        double returnProb = 0;
        iFrequency.clear();
        rFrequency.clear();

        this.setInterestedFrequency(interestedSet);
        this.setReducingFrequency(reducingSet);


        for(int i = 0;i<reducingSet.size();i++){
            if(reducingSet.get(i).equalsIgnoreCase(reducingClass)){
                if(interestedSet.get(i).equalsIgnoreCase(interestedClass)){
                    conditionalData.add(i);
                }
            }
        }

        int numerator = conditionalData.size();
        int denominator = this.rFrequency.getNum(reducingClass);

        if(denominator !=0){
            returnProb = (double)numerator/denominator;
        }

        iFrequency.clear();
        rFrequency.clear();
        return returnProb;
    }

    public void naiveBayes(Data data,List<String> targetClass, BayesOption bayesOption){
        //intialize variables
        int numOfClasses = data.getHeaders().size();
        Object[] keyNames = data.getHeaders().toArray();
        double conditionalProb = 1.0;
        double prob = 1.0;
        String[] rClass;
        String priorName;


        iFrequency.clear();
        rFrequency.clear();

        if(bayesOption.compareTo(BayesOption.TRAIN) == 0){
            this.setInterestedFrequency(targetClass);
            this.targetClassKeys = Util.convertToStringArray(iFrequency.getKeys());

            for(int i=0;i<this.targetClassKeys.length;i++){
                priors.put(this.targetClassKeys[i], iFrequency.getPct(this.targetClassKeys[i]));
            }
        }


        //for each classification in the target class
        for(int i=0;i<this.targetClassKeys.length;i++){

            //get all of the different classes for that variable
            for(int j=0;j<numOfClasses;j++){

                String reducingKey = Util.convertToString(keyNames[j]);
                List<String> reducingClass = data.getData().get(reducingKey);
                this.setReducingFrequency(reducingClass);
                Object[] reducingClassKeys = rFrequency.getKeys();
                rClass = Util.convertToStringArray(reducingClassKeys);


                for(int k=0;k<reducingClassKeys.length;k++){

                    if(bayesOption.compareTo(BayesOption.TRAIN) == 0){
                        conditionalProb = conditionalProbability(targetClass, reducingClass, this.targetClassKeys[i], rClass[k]);
                        priorName = this.targetClassKeys[i]+"|"+rClass[k];
                        priors.put(priorName,conditionalProb);
                    }

                    if(bayesOption.compareTo(BayesOption.PREDICT) == 0){
                        priorName = this.targetClassKeys[i]+"|"+rClass[k];
                        prob = prob * priors.get(priorName);

                    }
                }
                rFrequency.clear();

            }

            if(bayesOption.compareTo(BayesOption.PREDICT) == 0){
                prob = prob * priors.get(this.targetClassKeys[i]);
                Pair<String,Double> pred = new Pair<String, Double>(this.targetClassKeys[i],prob);
                this.predictions.add(pred);
            }

        }

        this.iFrequency.clear();
        this.rFrequency.clear();

    }

    public String getNaiveBayesPrediction(){
        Collections.sort(predictions, new Comparator<Pair<String, Double>>() {
            public int compare(Pair<String, Double> o1, Pair<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return this.predictions.get(0).getKey();
    }

}