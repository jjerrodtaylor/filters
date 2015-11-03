package edu.uba.filters;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.math3.stat.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Data {

    private LinkedListMultimap<String,String> data = LinkedListMultimap.create();

    public Data(){
        super();
    }

    public Data(LinkedListMultimap<String, String> data){
        this.data = data;
    }

    public LinkedListMultimap<String, String> getData() {
        return data;
    }

    public void setData(LinkedListMultimap<String, String> data) {
        this.data = data;
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
        double transformedNumber;
        int truncated;
        String[] transformations = new String[data.length];

        for(int i = 0;i<data.length;i++){
            transformedNumber = ((data[i] - (double) firstGroup)/(doubMax - doubMin) *
                    ((double)lastGroup - (double)firstGroup)) +
                    firstGroup;
            truncated = (int) transformedNumber;
            transformations[i] = Integer.toString(truncated);
        }

        return transformations;
    }



    public Data discretize(ListMultimap<String,String> data,LinkedList<String> headersToChange, int firstGroup, int lastGroup){
        double[] doubList;
        String[] transformations;
        List<String> workingList;
        Data datos = new Data();
        LinkedListMultimap<String, String> discreteData = LinkedListMultimap.create();

        String[] keys = Util.convertToStringArray(data.keySet().toArray());
        for(int i=0;i<keys.length;i++){
            workingList = data.get(keys[i]);

            if(headersToChange.size() > 0) {
                String compare = headersToChange.getFirst();
                if(keys[i].equalsIgnoreCase(compare)){
                    headersToChange.removeFirst();
                    doubList = transformToDouble(data.get(keys[i]));
                    transformations = linearTransform(doubList,firstGroup, lastGroup);

                    for(int j =0;j<workingList.size();j++){
                        discreteData.put(keys[i],transformations[j]);
                    }

                }

            }

            String stopper;
                for(int j=0;j<workingList.size();j++) {
                    discreteData.put(keys[i],workingList.get(j));
                }

        }

        datos.setData(discreteData);
        return datos;
    }
}
