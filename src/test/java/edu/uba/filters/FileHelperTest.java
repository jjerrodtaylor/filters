package edu.uba.filters;

import edu.uba.filters.Tools.FileHelper;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class FileHelperTest {

    @Test
    public void fileHelperTest(){
        FileHelper fileHelper = new FileHelper();
        List<String> results = fileHelper.readLines("/Users/jamaaltaylor/Desktop/race-results/newtext/1-1-15-9.txt");
    }
}
