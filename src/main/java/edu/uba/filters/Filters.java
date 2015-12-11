package edu.uba.filters;

import edu.uba.filters.Tools.FileHelper;
import org.apache.commons.math3.util.Pair;

import java.util.*;


public class Filters {

    public static void main(String[] args){
        FileHelper fileHelper = new FileHelper();
        Sample sample = new Sample();
        Data data = new Data();
        Data original = new Data();
        Entropy entropy = new Entropy();
        Probability probability = new Probability();
        List<String[]> training = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/prudential_train.csv");
        data.setData(training);
        List<String> targetSet = data.dataColumn("Product_Info_2",DataOption.GET,true);
        Double testEntropy = entropy.entropy(targetSet);
        probability.naiveBayes(data,targetSet,BayesOption.TRAIN,true);
        /*sample.randomSampleReplacement(lines,60000);
        data.setData(sample.getRandomSample());
        List<String> headersToChange = fileHelper.readLines("/Users/jamaaltaylor/Documents/datos/headers_to_change.csv");
        List<String> targetSet = data.dataColumn("target_purchase",DataOption.GET,true);
        List<Pair<String,Double>> ranked = new ArrayList<Pair<String, Double>>();

        for(int i=0;i<data.getNumOfKeys();i++){
            String header = headersToChange.get(0);
            Double infoGain;
            if(data.getKeys()[i].equalsIgnoreCase(header)){
                List<String> discrete;
                List<String> theList = data.dataColumn(header, DataOption.GET, true);
                discrete = data.discretize(theList,1,20);
                infoGain =  entropy.informationGain(targetSet,discrete);
                Pair<String,Double> dPair = new Pair(data.getKey(i),infoGain);
                ranked.add(dPair);
                headersToChange.remove(0);

            }else{
                List<String> reducingSet = data.dataColumn(data.getKey(i),DataOption.GET,true);
                infoGain = entropy.informationGain(targetSet,reducingSet);
                Pair<String,Double> dPair = new Pair(data.getKey(i),infoGain);
                ranked.add(dPair);
            }

        }

        Collections.sort(ranked, new Comparator<Pair<String, Double>>() {
            public int compare(Pair<String, Double> o1, Pair<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        List<Pair<String,Double>> shortList = data.getPercentage(ranked,.10);
        original.setData(sample.getRandomSample());
        original.createNewDataSet(original,shortList);*/
    }
}
