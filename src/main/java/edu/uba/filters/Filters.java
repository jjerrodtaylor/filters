package edu.uba.filters;

import edu.uba.filters.Tools.FileHelper;

import java.util.LinkedList;
import java.util.List;


public class Filters {

    public static void main(String[] args){
        FileHelper fileHelper = new FileHelper();
        Sample sample = new Sample();
        Data data = new Data();
        List<String[]> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/InnoCentive_data.csv");
        data.setData(lines);
        List<String[]> headersToChange = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/headers_to_change.csv");
        Entropy entropy = new Entropy();
        List<String> c3 = data.dataColumn("C3",DataOption.GET,true);
        Double testEntropy = entropy.entropy(c3);
    }
}
