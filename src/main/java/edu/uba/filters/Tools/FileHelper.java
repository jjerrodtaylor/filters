package edu.uba.filters.Tools;

import java.io.*;
import java.util.*;

import edu.uba.filters.Data;
import org.apache.commons.io.FileUtils;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.LinkedListMultimap;
import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.StatUtils;



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

    public List<String> readFileToMemory(String filepath)
    {
        List<String> fileContents = new ArrayList<String>();
        File file = new File(filepath);

        try{
            fileContents = FileUtils.readLines(file,"UTF-8");
        }catch (IOException e){
            e.printStackTrace();
        }

        return fileContents;
    }

    public Data parseCSVData(List<String> data){

        LinkedListMultimap<String,String> transformed = LinkedListMultimap.create();
        HashMap<String, ArrayList<String>> transformedData = new HashMap<String, ArrayList<String>>();
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
    }
}
