package edu.uba.filters;

import edu.uba.filters.Tools.FileHelper;

import java.util.LinkedList;
import java.util.List;


public class Filters {

    public static void main(String[] args){
        FileHelper fileHelper = new FileHelper();
        Sample sample = new Sample();
        List<String> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/InnoCentive_data.csv");
        List<String> headersToChange = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/headers_to_change.csv");
        sample.setFirstGroup(1);
        sample.setLastGroup(20);
        sample.setHeadersToChange(headersToChange);
        sample.randomSampleReplacement(lines,1000);

        //Data transformedData = parseCSVData.discretize(parseCSVData.getData(),headersToChange,1,20);

        sample.bootstrapEntropy(50,1000,"C3");
    }
}
