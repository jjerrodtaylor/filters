package edu.uba.filters;

import edu.uba.filters.Primatives.Entropy;
import edu.uba.filters.Primatives.Probability;
import edu.uba.filters.Tools.FileHelper;
import org.apache.commons.math3.util.Pair;

import java.util.*;


public class Filters {

    public static void main(String[] args){
        FileHelper fileHelper = new FileHelper();
        Data data = new Data();
        Data original = new Data();
        Entropy entropy = new Entropy();
        Probability probability = new Probability();
        List<String[]> training = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/prudential_train.csv");
        data.setData(training);
        List<String> headersToChange = fileHelper.readLines("/Users/jamaaltaylor/Documents/datos/headers_to_change.csv");

        Set<String> changeThese = new HashSet<String>(headersToChange);
        List<Pair<String,Double>> scoredFeatures = new LinkedList<Pair<String, Double>>();
        String[] keys = data.getKeys();
        List<String> interestedSet = data.dataColumn("Response",DataOption.GET,true);


        for(int i=0;i<data.getNumOfKeys();i++){
            if(keys[i].equalsIgnoreCase(headersToChange.remove(0))){
                List<String> makeDiscrete = data.dataColumn(keys[i],DataOption.GET,true);
                double[] asDouble = data.transformToDouble(makeDiscrete);
                int numOfBins = data.numberOfBins(asDouble);
                data.discretize(makeDiscrete,1,numOfBins);
            }
           List<String> reducingSet = data.dataColumn(keys[i],DataOption.GET,true);

           double symUn = entropy.symmetricalUncertainty(interestedSet,reducingSet);

            Pair<String,Double> feature = new Pair<String, Double>(keys[i],symUn);
            scoredFeatures.add(feature);
        }


    }
}
