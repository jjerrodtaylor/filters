package edu.uba.filters;

import com.google.common.collect.LinkedListMultimap;
import edu.uba.filters.Tools.FileHelper;
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

    @Test
    public void testLinearTransform(){
        Data data = new Data();
        double[] datos = new double[2];
        datos[0]=10.0;
        datos[1]=100.0;
        String[] transformed = data.linearTransform(datos,1,5);

        assertEquals("1",transformed[0]);
        assertEquals("5",transformed[1]);

    }

    @Test
    public void testDiscreteize(){
        FileHelper fileHelper = new FileHelper();
        List<String> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/birthwt.txt");
        LinkedList<String> headersToChange = new LinkedList<String>();
        headersToChange.add("age");
        headersToChange.add("lwt");
        assertNotNull(lines);
        assertEquals(186,lines.size());

        Data freshData = fileHelper.parseCSVData(lines);
        String untransformed = freshData.getData().get("age").get(0);
        assertEquals(untransformed,"19");
        Data discreteData = freshData.discretize(freshData.getData(),headersToChange,1,5);
        String transformed = discreteData.getData().get("age").get(0);
        assertEquals("3",transformed);
        assertEquals(184,discreteData.getData().get("age").size());
    }

    @Test
    public void testFCBS(){
        FileHelper fileHelper = new FileHelper();
        List<String> lines = fileHelper.readFileToMemory("/Users/jamaaltaylor/Documents/datos/birthwt.txt");
        LinkedList<String> headersToChange = new LinkedList<String>();
        headersToChange.add("age");
        headersToChange.add("lwt");

        Data freshData = fileHelper.parseCSVData(lines);
        Data discreteData = freshData.discretize(freshData.getData(),headersToChange,1,5);
        Entropy entropy = new Entropy();
        entropy.fcbf(discreteData,"lwt",.5);

    }
}
