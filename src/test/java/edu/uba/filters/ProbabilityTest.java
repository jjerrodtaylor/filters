package edu.uba.filters;

import edu.uba.filters.Tools.FileHelper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import edu.uba.filters.Entropy;

public class ProbabilityTest {

    @Test
    public void testNaiveBays(){
        FileHelper fileHelper = new FileHelper();
        List<String> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/humidity.csv");
        Data freshData = fileHelper.parseCSVData(lines);

        List<String> predictLines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/predict.csv");
        Data predictData = fileHelper.parseCSVData(predictLines);
        Entropy entropy = new Entropy();
        Probability probability = new Probability();
        //probability.naiveBayesTrain(freshData,freshData.getData().get("PlayBall"));
        predictData.removeColumn("Day");
        freshData.removeColumn("Day");
        probability.naiveBayes(freshData, new ArrayList(freshData.getData().get("PlayBall")), BayesOption.TRAIN);
        probability.naiveBayes(predictData,new ArrayList(predictData.getData().get("PlayBall")),BayesOption.PREDICT);
        assertEquals(2,probability.getPredictions().size());

    }
}
