package edu.uba.filters.Tools;

import java.io.*;
import java.util.*;
import com.opencsv.CSVReader;
import org.apache.commons.io.FileUtils;
import java.util.regex.*;


public class FileHelper {

    Pattern lastRaced = Pattern.compile("/([\\d|-].*?)\\s\\s+/");
    Pattern pgm = Pattern.compile("/[\\d|-].*?\\s\\s+(\\d+)\\s\\s+/");
    Pattern horseName = Pattern.compile("/[\\d|-].*?\\s\\s+\\d+\\s\\s+(.*?)\\d\\d/");
    Pattern wgt = Pattern.compile("/[\\d|-].*?\\s\\s+\\d+\\s\\s+.*?(\\d\\d.*?)\\s\\s+/");
    Pattern me = Pattern.compile("/[\\d|-].*?\\s\\s+\\d+\\s\\s+.*?(\\d\\d.*?)\\s\\s+([A-Z|-].*?)\\s\\s+/");
    Pattern pp = Pattern.compile("/[\\d|-].*?\\s\\s+\\d+\\s\\s+.*?\\d\\d.*?\\s\\s+[A-Z|-].*?\\s\\s+(\\d+)\\s\\s+/");
    Pattern odds = Pattern.compile("/.*\\s\\s+(\\d*\\.?\\d*\\*?)\\s\\s+.*$/");


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

    public void  parseTextData(List<String> data){

        for(String s:data){

            Matcher lastRacedmatcher = lastRaced.matcher(s);
            Matcher pgmMatcher = pgm.matcher(s);
            Matcher horseNameMatcher = horseName.matcher(s);
            Matcher wgtMatcher = wgt.matcher(s);
            Matcher meMatcher = me.matcher(s);
            Matcher ppMatcher = pp.matcher(s);
            Matcher oddsMatcher = odds.matcher(s);


            if(lastRacedmatcher.find()){
                String test = lastRacedmatcher.group();
            }

            if(pgmMatcher.find()){

            }

            if(horseNameMatcher.find()){

            }

            if(wgtMatcher.find()){

            }

            if(meMatcher.find()){

            }

            if(ppMatcher.find()){

            }

            if(oddsMatcher.find()){

            }


        }
    }
}
