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
        List<String[]> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/humidity.csv");
        Data freshData = new Data();
        freshData.setData(lines);

        List<String[]> predictLines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/predict.csv");
        Data predictData = new Data();
        predictData.setData(predictLines);

        Entropy entropy = new Entropy();
        Probability probability = new Probability();
        List<String> targetClass = freshData.dataColumn("PlayBall",DataOption.GET,true);
        probability.naiveBayes(freshData, targetClass, BayesOption.TRAIN,true);
        probability.naiveBayes(predictData, predictData.dataColumn("PlayBall",DataOption.GET,true),BayesOption.PREDICT,true);
        assertEquals(2,probability.getPredictions().size());

    }
}
