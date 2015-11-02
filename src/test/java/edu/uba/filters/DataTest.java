package edu.uba.filters;

import com.google.common.collect.LinkedListMultimap;
import org.junit.Test;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;


public class DataTest {

    @Test
    public void testIsDouble(){
        LinkedListMultimap<String,String> datos = LinkedListMultimap.create();
        Data data = new Data(datos);
        boolean testFalse = data.isDouble("s");
        boolean testTrue = data.isDouble("1.0");

        assertEquals(false,testFalse);
        assertEquals(true,testTrue);
    }

    @Test
    public void testIsInt(){
        LinkedListMultimap<String,String> datos = LinkedListMultimap.create();
        Data data = new Data(datos);
        boolean testFalse = data.isInteger("s");
        boolean testTrue = data.isInteger("1");

        assertEquals(false,testFalse);
        assertEquals(true,testTrue);
    }

    @Test
    public void testTransformToDouble(){
        List<String> stringList = new LinkedList<String>();
        stringList.add("1");
        Data data = new Data();
        double[] newDoubles = data.transformToDouble(stringList);

        assertEquals(1,newDoubles.length);
        assertEquals(1.0,newDoubles[0],0.0);
    }


}
