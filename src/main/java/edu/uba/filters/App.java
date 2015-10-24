package edu.uba.filters;

import edu.uba.filters.Tools.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        FileHelper fileHelper = new FileHelper();
        Entropy entropy = new Entropy();
        ArrayList<String> data = null;
        HashMap<String,ArrayList<String>> transformedData = new HashMap<String, ArrayList<String>>();
        double testEntropy;
        data = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/titanic/train.csv");

        transformedData = fileHelper.parseCSVData(data);
        testEntropy = entropy.entropy(transformedData.get("Survived"));
        System.out.print("Hello");
    }
}
