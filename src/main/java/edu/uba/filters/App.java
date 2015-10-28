package edu.uba.filters;

import com.google.common.collect.LinkedListMultimap;
import edu.uba.filters.Tools.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        List<String> data = null;
        LinkedListMultimap<String,String> transformedData = LinkedListMultimap.create();
        double testEntropy;
        data = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/titanic/train.csv");
        transformedData = fileHelper.parseCSVData(data);
        testEntropy = entropy.entropy(transformedData.get("Survived"));
        System.out.print("Hello");
    }
}
