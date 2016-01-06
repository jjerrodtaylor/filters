package edu.uba.filters.Tools;

import java.io.*;
import java.util.*;
import com.opencsv.CSVReader;
import org.apache.commons.io.FileUtils;
import java.util.regex.*;


public class FileHelper {

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

    public List<String> readLines(String filepath){

        List<String> fileContents = new LinkedList<String>();
        File file = new File(filepath);

        try{
            fileContents = FileUtils.readLines(file,"UTF-8");
        }catch (IOException e){
            e.printStackTrace();
        }

        return fileContents;

    }

    public List<String> readDirectory(String path){
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        List<String> returnNames = new ArrayList<String>();

        for(File f: listOfFiles){
            if(f.isFile()){
                returnNames.add(f.getName());
            }
        }

        return returnNames;
    }
}
