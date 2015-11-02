package edu.uba.filters;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import edu.uba.filters.Entropy;


public class EntropyTest{


    @Test
    public void testEntropy(){
        List<String> survived = Arrays.asList("1","0","1","1","0","1","0","0","0","1","0","1","0","0","1");
        Entropy entropy = new Entropy();
        Double result = entropy.entropy(survived);
        assertEquals(.996,result,.006);
    }

    @Test
    public void testConditionalProbability(){
        //male = 1
        //survived = 1

        List<String> survived = Arrays.asList("1","0","1","1","0","1","0","0","0","1","0","1","0","0","1");
        List<String> sex = Arrays.asList("0","1","0","1","1","0","0","1","1","0","1","0","0","1","1");
        Entropy entropy = new Entropy();
        double conditionalProb = entropy.conditionalProbability(survived,sex,"1","1");
        assertEquals(.250,conditionalProb,.005);
    }

    @Test
    public void testJointEntropy(){

        //male = 1
        //survived = 1

        List<String> survived = Arrays.asList("1","0","1","1","0","1","0","0","0","1","0","1","0","0","1");
        List<String> sex = Arrays.asList("0","1","0","1","1","0","0","1","1","0","1","0","0","1","1");
        Entropy entropy = new Entropy();
        double jointEntropy = entropy.jointEntropy(survived,sex);
        assertEquals(1.99,jointEntropy,.006);
    }

    @Test
    public void testSpecifiedConditionalEntropy(){

        //male = 1
        //survived = 1

        List<String> survived = Arrays.asList("1","0","1","1","0","1","0","0","0","1","0","1","0","0","1");
        List<String> sex = Arrays.asList("0","1","0","1","1","0","0","1","1","0","1","0","0","1","1");
        Entropy entropy = new Entropy();
        double specCondiEntropy = entropy.specifiedConditionalEntropy(survived,sex,"1","1");
        assertEquals(.5,specCondiEntropy,.005);

    }

    @Test
    public void testConditionalEntropy(){

        //male = 1
        //survived = 1

        List<String> survived = Arrays.asList("1","0","1","1","0","1","0","0","0","1","0","1","0","0","1");
        List<String> sex = Arrays.asList("0","1","0","1","1","0","0","1","1","0","1","0","0","1","1");
        Entropy entropy = new Entropy();
        Double result = entropy.conditionalEntropy(survived,sex);
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


}
