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
public class Sample extends Thread{

    private List<String> data;
    private Thread thread;


    public void run(){

    }


    public void getData(List<String> data){
        this.data = data;
    }

    public void breakUp(int numOfGroups){

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


    }






}
