package edu.uba.filters;


import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import edu.uba.filters.Entropy;

public class FrequencyTest {

    @Test
    public void frequencyTest(){
        List<String> survived = Arrays.asList("1","0","1","1","0","1","0","0","0","1","0","1","0","0","1");
        Frequency<String> frequency = new Frequency<String>();

        for(String s:survived){
            frequency.addValue(s);
        }

        assertEquals(2,frequency.getUniqueCount());
        assertEquals(15,frequency.getSumFreq());
        assertEquals(.466,frequency.getPct("1"),.005);

    }
}
