package edu.uba.filters.Tools;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

    public void writeFile(String stringToWrite, String nameOfFile)
    {
        this.bw = IOFactory.buildIOBufferedWriter(nameOfFile);
        try
        {
            this.bw.getBufferedWriter().write(stringToWrite);
            this.bw.getBufferedWriter().newLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                this.bw.getBufferedWriter().flush();
                this.bw.getBufferedWriter().close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    /**
     * Function takes an Arraylist of Strings as well as the path for where the file should be written to.
     * @param linesToWrite
     * @param nameOfFile
     */
    public void writeFile(List<String> linesToWrite, String nameOfFile)
    {
        this.bw = IOFactory.buildIOBufferedWriter(nameOfFile);

        for(String s: linesToWrite)
        {
            try
            {
                this.bw.getBufferedWriter().write(s);
                this.bw.getBufferedWriter().newLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            this.bw.getBufferedWriter().flush();
            this.bw.getBufferedWriter().close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<String> readFileToMemory(String filepath)
    {
        this.br = IOFactory.buildIOBufferedReader(filepath);
        String currentLine = null;
        ArrayList<String> fileContents = new ArrayList<String>();

        try
        {
            while((currentLine = this.br.getBufferedReader().readLine()) != null)
            {
                fileContents.add(currentLine);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                this.br.getBufferedReader().close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        return fileContents;
    }

    public boolean isDouble(String check){

        try{
            Double.parseDouble(check);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public boolean isInteger(String check){

        try{
            Integer.parseInt(check);
            return true;
        }catch(NumberFormatException e){
            return false;
        }

    }

    public LinkedListMultimap<String,String> parseCSVData(ArrayList<String> data){

        LinkedListMultimap<String,String> transformed = LinkedListMultimap.create();
        HashMap<String, ArrayList<String>> transformedData = new HashMap<String, ArrayList<String>>();
        String[] headers = data.get(0).split(",");
        String[] temp;

        for(int i = 1;i<data.size()-1;i++){
            for(int j = 0;j<headers.length;j++){
                temp = data.get(i).split(",");
                transformed.put(headers[j],temp[j]);
            }
        }

        return transformed;
    }

    public LinkedListMultimap<String,String> transformData(ListMultimap<String,String> data, int buckets){

        Iterator<String> keySetIterator =  data.keys().iterator();
        String test;
        Frequency frequency = new Frequency();
        int useInt;
        double useDoub;

        while(keySetIterator.hasNext()){
            test = data.get(keySetIterator.next()).get(0);
            if(isDouble(test) == true){
                for(String s: data.get(keySetIterator.next())){
                    frequency.addValue(Double.parseDouble(s));
                }

            }


        }

    }

}
