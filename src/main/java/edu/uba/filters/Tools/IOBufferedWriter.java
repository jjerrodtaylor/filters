package edu.uba.filters.Tools;

import java.io.*;


public class IOBufferedWriter extends IOBuffered
{
    private BufferedWriter bw = null;

    public IOBufferedWriter(String filePath)
    {
        super(IOType.BUFFERED_WRITTER);
        construct(filePath);
    }

    public IOBufferedWriter(String filePath, boolean append)
    {
        super(IOType.BUFFERED_WRITTER);
        construct(filePath, append);
    }

    @Override
    protected void construct(String filePath)
    {
        try
        {
            this.bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath),"UTF8"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    protected void construct (String filePath, boolean append)
    {
        try
        {
            this.bw = new BufferedWriter(new FileWriter(filePath, true));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public BufferedWriter getBufferedWriter()
    {
        return this.bw;
    }
}
