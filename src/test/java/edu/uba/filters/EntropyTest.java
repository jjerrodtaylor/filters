package edu.uba.filters;

import edu.uba.filters.Primatives.EntropyCalculator;
import edu.uba.filters.Tools.FileHelper;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;

import edu.uba.filters.Primatives.Entropy;


public class EntropyTest{


    @Test
    public void testEntropyCalculator(){
        FileHelper fileHelper = new FileHelper();
        List<String[]> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/humidity.csv");
        Data freshData = new Data();
        freshData.setData(lines);//.parseCSVData(lines);

        EntropyCalculator calculator = new EntropyCalculator();

        assertEquals(0, calculator.getData().size());
        calculator.addData("1");
        assertEquals(1, calculator.getData().size());
        assertEquals(false,calculator.isRunning());
    }

    @Test
    public void testEntropy(){
        FileHelper fileHelper = new FileHelper();
        List<String[]> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/humidity.csv");
        Data freshData = new Data();
        freshData.setData(lines);

        Entropy entropy = new Entropy();
        Double result = entropy.entropy(freshData.dataColumn("PlayBall", DataOption.GET,true));
        assertEquals(.940,result,.006);
    }

    /*@Test
    public void testInformationGain(){

        FileHelper fileHelper = new FileHelper();
        List<String[]> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/humidity.csv");
        Data freshData = new Data();//fileHelper.parseCSVData(lines);
        freshData.setData(lines);

        Entropy entropy = new Entropy();
        Double result = entropy.informationGain(freshData.dataColumn("PlayBall", DataOption.GET,true), freshData.dataColumn("Wind", DataOption.GET,true));
        assertEquals(0.048,result,.005);
        result = entropy.informationGain(freshData.dataColumn("PlayBall",DataOption.GET,true),freshData.dataColumn("Outlook",DataOption.GET,true));
        assertEquals(0.246,result,.005);
    }


    @Test
    public void testConditionalEntropy(){

        FileHelper fileHelper = new FileHelper();
        List<String[]> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/humidity.csv");
        Data freshData = new Data();//fileHelper.parseCSVData(lines);
        freshData.setData(lines);

        Entropy entropy = new Entropy();
        Double result = entropy.conditionalEntropy(freshData.dataColumn("PlayBall",DataOption.GET,true), freshData.dataColumn("Wind",DataOption.GET,true));
        assertEquals(0.892,result,.006);
    }

    @Test
    public void testigRank(){

        FileHelper fileHelper = new FileHelper();
        List<String[]> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/humidity.csv");
        Data freshData = new Data();//fileHelper.parseCSVData(lines);
        freshData.setData(lines);
        Entropy entropy = new Entropy();
        entropy.igRank(freshData,"PlayBall");

    }

    @Test
    public void testSymmetricalUncertainty(){
        FileHelper fileHelper = new FileHelper();
        List<String[]> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/humidity.csv");
        Data freshData = new Data();//fileHelper.parseCSVData(lines);
        freshData.setData(lines);
        Entropy entropy = new Entropy();
        entropy.symmetricalUncertainty(freshData.dataColumn("PlayBall", DataOption.GET, true), freshData.dataColumn("Wind", DataOption.GET, true));
    }

    @Test
    public void testFCBF(){
        FileHelper fileHelper = new FileHelper();
        List<String[]> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/humidity.csv");
        Data freshData = new Data();//fileHelper.parseCSVData(lines);
        freshData.setData(lines);
        Entropy entropy = new Entropy();
        entropy.fcbf(freshData,"PlayBall",0.8);
    } */
}
