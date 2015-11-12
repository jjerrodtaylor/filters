package edu.uba.filters;

import java.util.Collection;
import java.util.List;

/**
 * Created by jamaaltaylor on 11/1/15.
 */
public class Util {

    public static String convertToString(Object o){
        return (String) o;
    }

    public static String[] convertToStringArray(Object[] o){
        String[] s = new String[o.length];
        for(int i=0;i<o.length;i++){
            s[i] = (String)o[i];
        }
        return s;
    }

    public static double[] convertToDoubleArray(Object[] o){
        double[] d = new double[o.length];
        for(int i=0;i<o.length;i++){
            d[i] = (Double) o[i];
        }

        return d;
    }

    public static List<String> convertToList(Collection<String> c){
        return (List)c;
    }

    public static int generateRandomInt(int min, int max)
    {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

}
