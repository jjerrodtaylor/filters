package edu.uba.filters;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import edu.uba.filters.Tools.FileHelper;

import java.util.*;

public class Sample {

    private List<String> randomSample;
    private List<Integer> randomSampleNumbers;
    private Data randomSampleData;
    private List<String> headersToChange;
    private Table<Integer,String,String> table;
    private int firstGroup;
    private int lastGroup;

    public void setHeadersToChange(List<String> headersToChange) {
        this.headersToChange = headersToChange;
    }

    public void setFirstGroup(int firstGroup) {
        this.firstGroup = firstGroup;
    }

    public void setLastGroup(int lastGroup) {
        this.lastGroup = lastGroup;
    }

    public List<String> getRandomSample() {
        return randomSample;
    }

    public Data getRandomSampleData() {
        return randomSampleData;
    }

    public void createSampleTable(Multimap<String,String> sample){
        Object[] keys = sample.keySet().toArray();
        table = HashBasedTable.create();

        for(int i=0;i<keys.length;i++){
            String columnName = Util.convertToString(keys[0]);
            int jsize = randomSampleData.getDataColumn(columnName).size();
            for(int j=0;j<jsize;j++){
                int row = j;
                String column = Util.convertToString(keys[i]);
                table.put(j,column,randomSampleData.getDataPoint(column,row));
            }
        }
    }

    public void randomSampleReplacement(List<String> data, int numOfSamplesToDraw){
        List<String> randomSample = new ArrayList<String>();
        FileHelper fileHelper = new FileHelper();

        //add the headers
        randomSample.add(data.get(0));
        for(int i=0;i<numOfSamplesToDraw;i++){
            String randomString = data.get(Util.generateRandomInt(0,data.size()-1));
            randomSample.add(randomString);
        }

        this.randomSample = randomSample;
        this.randomSampleData = fileHelper.parseCSVData(randomSample);
        randomSampleData = randomSampleData.discretize(randomSampleData.getData(),this.headersToChange,this.firstGroup,this.lastGroup);
        this.createSampleTable(randomSampleData.getData());
    }

    public List<Integer> randomSample(List<String> data, int numOfSamplesToDraw){
        List<Integer> randomSample = new ArrayList<Integer>();

        for(int i=0;i<numOfSamplesToDraw;i++){
            int random = Util.generateRandomInt(0,data.size()-1);
            randomSample.add(random);
        }

        return randomSample;
    }

    public List<Double> bootstrapEntropy(int numInResample, int numTimesToResampe, String interestedSet){

        List<Double> bootstrappedStat = new LinkedList<Double>();
        FileHelper fileHelper = new FileHelper();
        Entropy entropy = new Entropy();
        List<Integer> resample;
        Double resampledStat;
        Data data;

        for(int i=0;i<numTimesToResampe;i++){
            resample = randomSample(this.randomSample,numInResample);
            List<String> interSet = (List) table.column(interestedSet).values();// data.getDataColumn(interestedSet);//.convertToList(data.getData().get(interestedSet));
            resampledStat = entropy.entropy(interSet);
            bootstrappedStat.add(resampledStat);
        }

        return bootstrappedStat;
    }

    public List<Double> bootstrapIG(int numInResample, int numTimesToResample, String interestedSet, String reducingSet){

        List<Double> bootstrappedStat = new LinkedList<Double>();
        FileHelper fileHelper = new FileHelper();
        Entropy entropy = new Entropy();
        List<Integer> resample;
        Double resampledStat;
        Data data;

        for(int i=0;i<numTimesToResample;i++){
            resample = randomSample(this.randomSample,numInResample);
            List<String> interSet = (List) table.column(interestedSet).values();
            List<String> redSet = (List) table.column(reducingSet).values();

            resampledStat = entropy.informationGain(interSet,redSet);
            bootstrappedStat.add(resampledStat);
        }

        return bootstrappedStat;
    }
}
