package edu.uba.filters.Primatives;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jamaaltaylor on 1/3/16.
 */
public class EntropyCalculator extends Probability implements Runnable{

    private double result = 0;
    private List<String> data = new LinkedList<>();
    private boolean running = false;

    public void run(){
        running = true;
        double entropy = 0.0;
        double prob = 0.0;

        if(this.iFrequency.getKeys().length == 0){
            this.setInterestedFrequency(data);
        }

        String[] keys = iFrequency.getKeys();

        for(int i=0;i<keys.length;i++){
            prob = iFrequency.getPct(keys[i]);
            entropy = entropy - prob * log(prob,2);
        }

        iFrequency.clear();
        result = entropy;
        running = false;
    }

    public List<String> getData() {
        return data;
    }

    public double getResult(){
        return result;
    }

    public void addData(String data){
        this.data.add(data);
    }

    public boolean isRunning(){
        return running;
    }

}


