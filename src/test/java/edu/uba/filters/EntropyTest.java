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

    @Before
    public void SetUp()
    {
        List<String> survived = Arrays.asList("1","0","1","1","0","1","0","0","0","1","0","1","0","0","1");
        List<String> sex = Arrays.asList("0","1","0","1","1","0","0","1","1","0","1","0","0","1","1");
    }

    @Test
    public void testEntropy(){
        List<String> survived = Arrays.asList("1","0","1","1","0","1","0","0","0","1","0","1","0","0","1");
        Entropy entropy = new Entropy();
        Double result = entropy.entropy(survived);
        assertEquals(.996,result,.006);
    }

    @Test
    public void testConditionalEntropy(){

        //male = 1
        //survived = 1
        /*sur sex
        * 1    0
        * 0    1
        * 1    0
        * 1    1
        * 0    1
        * 1    0
        * 0    0
        * 0    1
        * 0    1
        * 1    0
        * 0    1
        * 1    0
        * 0    0
        * 0    1
        * 1    1
        * */
        List<String> survived = Arrays.asList("1","0","1","1","0","1","0","0","0","1","0","1","0","0","1");
        List<String> sex = Arrays.asList("0","1","0","1","1","0","0","1","1","0","1","0","0","1","1");
        Entropy entropy = new Entropy();
        Double result = entropy.conditionalEntropy(survived,sex,"survived","sex");
        assertEquals(.206,result,.006);
    }


}
