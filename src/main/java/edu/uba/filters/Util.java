package edu.uba.filters;

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

}
