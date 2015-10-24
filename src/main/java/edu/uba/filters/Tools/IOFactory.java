package edu.uba.filters.Tools;

/*
* For the uninitiated:
* http://en.wikipedia.org/wiki/Factory_method_pattern
* */
public class IOFactory
{
    /**
     *
     * @param filePath
     * @return
     */
    public static IOBufferedWriter buildIOBufferedWriter(String filePath)
    {
        return new IOBufferedWriter(filePath);
    }

    public static IOBufferedWriter buildIOBufferedWriter(String filePath, boolean append)
    {
        return new IOBufferedWriter(filePath,append);
    }

    /**
     *
     * @param filePath
     * @return
     */
    public static IOBufferedReader buildIOBufferedReader(String filePath)
    {
        return new IOBufferedReader(filePath);
    }
}