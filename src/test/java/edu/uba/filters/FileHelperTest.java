package edu.uba.filters;

import edu.uba.filters.Tools.FileHelper;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import edu.uba.filters.Entropy;

public class FileHelperTest {

    @Test
    public void fileHelperTest(){
        FileHelper fileHelper = new FileHelper();
        List<String> results = fileHelper.readLines("/Users/jamaaltaylor/Desktop/race-results/newtext/1-1-15-9.txt");
        fileHelper.parseTextData(results);
    }
}
