package edu.uba.filters;


import edu.uba.filters.Primatives.Frequency;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class FrequencyTest {

    @Test
    public void frequencyTest(){
        List<String> survived = Arrays.asList("1","0","1","1","0","1","0","0","0","1","0","1","0","0","1");
        Frequency<String> frequency = new Frequency<String>();

        for(String s:survived){
            frequency.addValue(s);
        }

        String[] keys = frequency.getKeys();

        assertEquals(2,frequency.getUniqueCount());
        assertEquals(15,frequency.getSumFreq());
        assertEquals(.466,frequency.getPct("1"),.005);
        assertEquals("0",keys[1]);

    }
}
