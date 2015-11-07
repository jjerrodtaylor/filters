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

    public void naiveBayesTrain(Data data,List<String> targetClass){

        //initialize the variables
        int numOfClasses = data.getData().keySet().toArray().length;
        Object[] keyNames = data.getData().keySet().toArray();
        double conditionalProb = 0.0;
        String[] rClass;
        String priorName;

        //clear the frequency distributions then
        // set the distribution for the target class, get the target class keys
        // and set the priors
        iFrequency.clear();
        rFrequency.clear();
        this.setInterestedFrequency(targetClass);
        Object[] targetClassKeys = iFrequency.getKeys();
        String[] tClass = Util.convertToStringArray(targetClassKeys);

        for(int i=0;i<tClass.length;i++){
            priors.put(tClass[i],iFrequency.getPct(tClass[i]));
        }

        //for each explanatory variable
        for(int i=1;i<numOfClasses;i++){

            //calculate the probability of some target value
            for(int j =0;j<targetClassKeys.length;j++){

                //code to get the keys for the different reducing events
                String reducingKey = Util.convertToString(keyNames[i]);
                List<String> reducingClass = data.getData().get(reducingKey);
                this.setReducingFrequency(reducingClass);
                Object[] reducingClassKeys = rFrequency.getKeys();
                rClass = Util.convertToStringArray(reducingClassKeys);

                //given that some reducing event has occured
                for(int k=0;k<reducingClassKeys.length;k++){

                    //calculate the conditional probability now that you have the various target and reducing states
                    conditionalProb = conditionalProbability(targetClass, reducingClass, tClass[j], rClass[k]);
                    priorName = tClass[j]+"|"+rClass[k];
                    priors.put(priorName,conditionalProb);
                }
                rFrequency.clear();
            }
        }

        iFrequency.clear();
        rFrequency.clear();
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