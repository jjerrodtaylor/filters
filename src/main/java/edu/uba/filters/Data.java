package edu.uba.filters;

import com.google.common.collect.*;
import org.apache.commons.math3.stat.*;
import org.apache.commons.math3.util.Pair;

import java.util.*;


public class Data {

    public List<String[]> getData() {
        return data;
    }

    public void setData(List<String[]> data) {
        this.data = data;
    }

    private List<String[]> data;

    public Data(){
        super();
    }

    public Data(List<String[]> data){
        this.data = data;
    }

    public List<String> dataColumn(String name, DataOption dataOption, Boolean header){
        List<String> column = new ArrayList<String>();
        List<String[]> removed = new ArrayList<String[]>();
        Integer length = data.get(0).length;
        String[] keySet = data.get(0);
        String[] newList = null;
        Integer columnGet = null;

        for(int i=0;i<length;i++){
            if(name.equalsIgnoreCase(keySet[i])){
                columnGet = i;
                break;
            }
        }

        if(columnGet != null){
            if(DataOption.GET.compareTo(dataOption) == 0){
                if(header == true){
                    for(int i=1;i<data.size();i++){
                        column.add(data.get(i)[columnGet]);
                    }
                }else{
                    for(int i=0;i<data.size();i++){
                        column.add(data.get(i)[columnGet]);
                    }
                }
            }
        }


        return column;
    }

    public boolean isDouble(String check){

        try{
            Double.parseDouble(check);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public boolean isInteger(String check){

        try{
            Integer.parseInt(check);
            return true;
        }catch(NumberFormatException e){
            return false;
        }

    }

    public Integer getNumOfKeys(){
        return data.get(0).length;
    }

    public String[] getKeys(){
        String[] keys = null;
        if(data != null){
            keys = data.get(0);
        }

        return keys;
    }

    public String getKey(Integer index){
        return data.get(0)[index];
    }

    public double[] transformToDouble(List<String> data){
        double[] newData = new double[data.size()];
        double insert;

        if(isDouble(data.get(0)) == true){
            for(int i=0;i<data.size();i++){
                insert = Double.parseDouble(data.get(i));
                newData[i] = insert;
            }
        }else{
            for(int i=0;i<data.size();i++){
                insert = Integer.parseInt(data.get(i));
                newData[i] = (double) insert;
            }
        }

        return newData;
    }

    /*
    * https://en.wikipedia.org/wiki/Freedman%E2%80%93Diaconis_rule
    * */
    public double calculateBinSize(double[] data){
        double firstQuartile = StatUtils.percentile(data,.25);
        double thirdQuartile = StatUtils.percentile(data,.75);
        double iqr = thirdQuartile - firstQuartile;
        double binSize = 2*iqr*(Math.pow(data.length,.33));

        return binSize;
    }

    public String[] linearTransform(double[] data, int firstGroup, int lastGroup){
        double doubMin = StatUtils.min(data);
        double doubMax = StatUtils.max(data);
        double firstPartNum;
        double firstPartDen;
        double firstPart;
        double secondPart;
        double transformedNumber;
        int truncated;
        String[] transformations = new String[data.length];

        for(int i = 0;i<data.length;i++){
            firstPartNum =  data[i] - doubMin;
            firstPartDen = doubMax - doubMin;
            firstPart = firstPartNum/firstPartDen;

            secondPart = ( (double)lastGroup - (double)firstGroup );

            transformedNumber = firstPart * secondPart +firstGroup;

            truncated = (int) transformedNumber;
            transformations[i] = Integer.toString(truncated);
        }

        return transformations;
    }

    public List<String> discretize(List<String> data, int firstGroup, int lastGroup){

        double[] toDouble = transformToDouble(data);
        String[] transformations = linearTransform(toDouble,firstGroup,lastGroup);
        List<String> transformedData = new ArrayList<String>();

        for(int i=0;i<transformations.length;i++){
            String[] insert = new String[1];
            transformedData.add(transformations[i]);
        }

        return transformedData;
    }

    public List<Pair<String,Double>> getPercentage(List<Pair<String,Double>> ranked,double percentageToGet){

        if(percentageToGet > 1.0){
            percentageToGet = percentageToGet/100.0;
        }

        double percentage = ranked.size() * percentageToGet;
        int finalPercentage = (int) percentage + 1;
        List<Pair<String,Double>> shortList = new ArrayList<Pair<String, Double>>();
        for(int i=0;i<finalPercentage;i++){
            shortList.add(ranked.remove(i));
        }

        return shortList;
    }

    public List<String[]> createNewDataSet(Data data, List<Pair<String,Double>> rankedData){
        List<String[]> originalDataSet = data.getData();
        List<String[]> newDataSet = new ArrayList<String[]>();

        for(int i=0;i<rankedData.size();i++){
            String nameToGet = rankedData.get(i).getFirst();
            for(int j=0;j<originalDataSet.size()-1;j++){
                List<String> addThis = data.dataColumn(nameToGet,DataOption.GET,true);
                String[] dataList = new String[rankedData.size()];
                dataList[i] = addThis.get(j);
                newDataSet.add(dataList);
            }
            int m=0;

        }

        return newDataSet;
    }




}
