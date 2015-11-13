package edu.uba.filters;

import com.google.common.collect.*;
import org.apache.commons.math3.stat.*;

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

    public Data discretize(List<String[]> data,List<String> headersToChange, int firstGroup, int lastGroup){
        double[] doubList;
        String[] transformations;
        List<String> workingList;
        Data datos = new Data();
        List<String[]> discreteData = new ArrayList<String[]>();

        String[] keys = data.get(0);//Util.convertToStringArray(data.keySet().toArray());
        for(int i=0;i<keys.length;i++){
            workingList = dataColumn(keys[i],DataOption.GET,true);

            if(headersToChange.size() > 0) {
                String compare = headersToChange.get(0);//getFirst();
                if(keys[i].equalsIgnoreCase(compare)){
                    headersToChange.remove(0);//removeFirst();
                    doubList = transformToDouble(dataColumn(keys[i],DataOption.GET,true));
                    transformations = linearTransform(doubList,firstGroup, lastGroup);

                    for(int j =0;j<workingList.size();j++){

                        discreteData.get(i)[j] = transformations[j];
                    }

                }else{
                    for(int j=0;j<workingList.size();j++) {
                        discreteData.get(i)[j] = workingList.get(j);
                    }
                }

            }else {


                for (int j = 0; j < workingList.size(); j++) {
                    discreteData.get(i)[j] = workingList.get(j);
                }
            }
        }

        datos.setData(discreteData);
        return datos;
    }

    public void removeColumn(String column){

    }


}
