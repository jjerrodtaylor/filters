package edu.uba.filters;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.math3.stat.*;
import java.util.Iterator;
import java.util.List;


public class Data {

    private LinkedListMultimap<String,String> data = LinkedListMultimap.create();

    Data(){
        super();
    }

    Data(LinkedListMultimap<String, String> data){
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


    public LinkedListMultimap<String,String> discretize(ListMultimap<String,String> data, int firstGroup, int lastGroup){
        double useDoub;
        double[] doubList;
        String[] transformations;
        List<String> workingList;
        LinkedListMultimap<String, String> discreteData = LinkedListMultimap.create();

        Iterator<String> keySetIterator =  data.keys().iterator();
        String test;
        org.apache.commons.math3.stat.Frequency frequency = new org.apache.commons.math3.stat.Frequency();

        while(keySetIterator.hasNext()){
            test = data.get(keySetIterator.next()).get(0);

            if(isDouble(test) == true || isInteger(test) == true){
                workingList = data.get(keySetIterator.next());
                doubList = transformToDouble(data.get(keySetIterator.next()));
                transformations = linearTransform(doubList,firstGroup,lastGroup);

                for(int i = 0;i<workingList.size();i++){
                    discreteData.put(keySetIterator.next(),transformations[i]);
                }
            }
        }

        return discreteData;
    }
}
