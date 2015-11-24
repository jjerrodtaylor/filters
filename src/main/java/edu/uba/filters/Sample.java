package edu.uba.filters;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import edu.uba.filters.Tools.FileHelper;
import org.apache.commons.math3.stat.StatUtils;

import java.util.*;

/*
*
* http://stats.stackexchange.com/questions/133376/mean-of-the-bootstrap-sample-vs-statistic-of-the-sample
* */
public class Sample {

    private List<String[]> randomSample;
    private Data randomSampleData = new Data();



    public List<String[]> getRandomSample() {
        return randomSample;
    }


    public void randomSampleReplacement(List<String> data, int numOfSamplesToDraw){
        List<String[]> randomSample = new ArrayList<String[]>();
        FileHelper fileHelper = new FileHelper();

        //add the headers
        randomSample.add(data.get(0).split(","));
        for(int i=0;i<numOfSamplesToDraw;i++){
            String randomString = data.get(Util.generateRandomInt(0,data.size()-1));
            randomSample.add(randomString.split(","));
        }

        this.randomSample = randomSample;
        this.randomSampleData.setData(this.randomSample);
    }

    public Double bootstrapIG(int numInResample, int numTimesToResample, String interestedSet, List<String> reducingSet){
        double[] bootstrappedStat = new double[numTimesToResample];
        List<String> interestedSample = randomSampleData.dataColumn(interestedSet,DataOption.GET,true);
        List<String> intSetRe = new ArrayList<String>();
        List<String> redSetRe = new ArrayList<String>();
        Entropy entropy = new Entropy();
        Double resampledStat;

        for(int i=0;i<numTimesToResample;i++){
            for(int j=0;j<numInResample;j++){
                int intSetrandNum = Util.generateRandomInt(0,randomSample.size()-2);
                int redSetrandNum = Util.generateRandomInt(0,reducingSet.size()-2);

                intSetRe.add(interestedSample.get(intSetrandNum));
                redSetRe.add(reducingSet.get(redSetrandNum));
            }

            resampledStat = entropy.informationGain(intSetRe,redSetRe);
            bootstrappedStat[i] = resampledStat;
        }

        double meanInfoGain = StatUtils.mean(bootstrappedStat);

        return meanInfoGain;
    }

    public Double bootstrapIG(int numInResample, int numTimesToResample, String interestedSet, String reducingSet){

        double[] bootstrappedStat = new double[numTimesToResample];
        List<String> intSetSample = randomSampleData.dataColumn(interestedSet,DataOption.GET,true);
        List<String> redSetSample = randomSampleData.dataColumn(reducingSet,DataOption.GET,true);
        List<String> intSetRe = new ArrayList<String>();
        List<String> redSetRe = new ArrayList<String>();
        Entropy entropy = new Entropy();
        Double resampledStat;


        for(int i=0;i<numTimesToResample;i++){
            for(int j=0;j<numInResample;j++){
                int intSetrandNum = Util.generateRandomInt(0,randomSample.size()-2);
                int redSetrandNum = Util.generateRandomInt(0,randomSample.size()-2);

                intSetRe.add(intSetSample.get(intSetrandNum));
                redSetRe.add(redSetSample.get(redSetrandNum));
            }

            resampledStat = entropy.informationGain(intSetRe,redSetRe);
            bootstrappedStat[i] = resampledStat;

        }

        double meanInfoGain = StatUtils.mean(bootstrappedStat);

        return meanInfoGain;
    }

    /*public List<Double> bootstrapEntropy(int numInResample, int numTimesToResampe, String interestedSet){

        double[] bootstrappedStat = new double[numTimesToResampe];
        List<String>
        FileHelper fileHelper = new FileHelper();
        Entropy entropy = new Entropy();
        List<Integer> resample;
        Double resampledStat;
        Data data;

        for(int i=0;i<numTimesToResampe;i++){
            resample = randomSample(this.randomSample,numInResample);
            resampledStat = entropy.entropy(interSet);
            bootstrappedStat.add(resampledStat);
        }

        return bootstrappedStat;
    } */
}
