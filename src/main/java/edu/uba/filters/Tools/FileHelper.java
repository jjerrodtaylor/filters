package edu.uba.filters.Tools;

import java.io.*;
import java.util.*;
import com.opencsv.CSVReader;
import edu.uba.filters.Data;
import org.apache.commons.io.FileUtils;
import com.google.common.collect.LinkedListMultimap;



public class FileHelper {

    private IOBufferedWriter  bw = null;
    private IOBufferedReader br = null;


    public FileHelper(){
        super();
    }

    public File turnToFile(String filePath)
    {
        File file = new File(filePath);
        return file;
    }

    public void writeLinesToFile(List<String> linesToWrite, String nameOfFile, boolean append){

        File myFile = turnToFile(nameOfFile);
        try{
            FileUtils.writeLines(myFile,linesToWrite,append);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<String[]> readFileToMemory(String filepath)
    {
        List<String[]> fileContents = new ArrayList<String[]>();
        try {
            CSVReader reader = new CSVReader(new FileReader(filepath));
            fileContents = reader.readAll();
        }catch (IOException e){
            e.printStackTrace();
        }

        return fileContents;
    }

    /*public Data parseCSVData(List<String> data){

        LinkedListMultimap<String,String> transformed = LinkedListMultimap.create();
        String[] headers = data.get(0).split(",");
        String[] temp;
        Data datos = new Data();
        datos.setHeaders(headers);


        for(int i = 1;i<data.size();i++){
            for(int j = 0;j<headers.length;j++){
                temp = data.get(i).split(",");
                transformed.put(headers[j],temp[j]);
            }
        }

        datos.setData(transformed);
        return datos;
    }*/
}
