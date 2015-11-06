package edu.uba.filters;


import edu.uba.filters.Tools.FileHelper;
import org.junit.Test;

import java.util.List;

public class ProbabilityTest {

    @Test
    public void testNaiveBays(){
        FileHelper fileHelper = new FileHelper();
        List<String> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/humidity.csv");
        Data freshData = fileHelper.parseCSVData(lines);

        Entropy entropy = new Entropy();
        Probability probability = new Probability();
        probability.naiveBayesTrain(freshData,freshData.getData().get("PlayBall"));
    }
}
