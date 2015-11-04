package edu.uba.filters;

import edu.uba.filters.Tools.FileHelper;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import edu.uba.filters.Entropy;


public class EntropyTest{


    @Test
    public void testEntropy(){
        FileHelper fileHelper = new FileHelper();
        List<String> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/birthwt.txt");
        Data freshData = fileHelper.parseCSVData(lines);

        LinkedList<String> headersToChange = new LinkedList<String>();
        headersToChange.add("lwt");

        Data discreteData = freshData.discretize(freshData.getData(),headersToChange,1,10);

        Entropy entropy = new Entropy();
        Double result = entropy.entropy(discreteData.getData().get("lwt"));
        assertEquals(2.48,result,.006);
    }

    @Test
    public void testConditionalProbability(){

        FileHelper fileHelper = new FileHelper();
        List<String> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/birthwt.txt");
        Data freshData = fileHelper.parseCSVData(lines);

        LinkedList<String> headersToChange = new LinkedList<String>();
        headersToChange.add("age");
        headersToChange.add("lwt");


        Data discreteData = freshData.discretize(freshData.getData(), headersToChange, 1, 10);

        Entropy entropy = new Entropy();
        double conditionalProb = entropy.conditionalProbability(discreteData.getData().get("lwt"),discreteData.getData().get("age"),"4","6");
        assertEquals(.1,conditionalProb,.005);
    }

    @Test
    public void testJointEntropy(){


        FileHelper fileHelper = new FileHelper();
        List<String> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/birthwt.txt");
        Data freshData = fileHelper.parseCSVData(lines);

        LinkedList<String> headersToChange = new LinkedList<String>();
        headersToChange.add("age");
        headersToChange.add("lwt");

        Data discreteData = freshData.discretize(freshData.getData(), headersToChange, 1, 10);

        Entropy entropy = new Entropy();
        double jointEntropy = entropy.jointEntropy(discreteData.getData().get("lwt"),discreteData.getData().get("age"));
        assertEquals(5.05,jointEntropy,.006);
    }

    @Test
    public void testSpecifiedConditionalEntropy(){

        FileHelper fileHelper = new FileHelper();
        List<String> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/birthwt.txt");
        Data freshData = fileHelper.parseCSVData(lines);

        LinkedList<String> headersToChange = new LinkedList<String>();
        headersToChange.add("age");
        headersToChange.add("lwt");

        Data discreteData = freshData.discretize(freshData.getData(), headersToChange, 1, 10);

        Entropy entropy = new Entropy();
        double specCondiEntropy = entropy.specifiedConditionalEntropy(discreteData.getData().get("lwt"),discreteData.getData().get("age"),"4","6");
        assertEquals(.332,specCondiEntropy,.005);

    }

    @Test
    public void testConditionalEntropy(){

        FileHelper fileHelper = new FileHelper();
        List<String> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/birthwt.txt");
        Data freshData = fileHelper.parseCSVData(lines);

        LinkedList<String> headersToChange = new LinkedList<String>();
        headersToChange.add("age");
        headersToChange.add("lwt");

        Data discreteData = freshData.discretize(freshData.getData(), headersToChange, 1, 10);

        Entropy entropy = new Entropy();
        Double result = entropy.conditionalEntropy(discreteData.getData().get("lwt"),discreteData.getData().get("age"));
        assertEquals(.996,result,.006);
    }

    @Test
    public void testInformationGain(){

        //male = 1
        //survived = 1

        List<String> survived = Arrays.asList("1","0","1","1","0","1","0","0","0","1","0","1","0","0","1");
        List<String> sex = Arrays.asList("0","1","0","1","1","0","0","1","1","0","1","0","0","1","1");
        Entropy entropy = new Entropy();
        Double result = entropy.informationGain(survived,sex);
        assertEquals(0,result,.005);
    }

    @Test
    public void testSymmetricalUncertainty(){

        //male = 1
        //survived = 1

        List<String> survived = Arrays.asList("1","0","1","1","0","1","0","0","0","1","0","1","0","0","1");
        List<String> sex = Arrays.asList("0","1","0","1","1","0","0","1","1","0","1","0","0","1","1");
        Entropy entropy = new Entropy();
        Double result = entropy.symmetricalUncertainty(survived,sex);
        assertEquals(0,result,.005);
    }

    @Test
    public void testFCBF(){

    }


}
