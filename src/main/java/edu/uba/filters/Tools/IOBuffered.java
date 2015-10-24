package edu.uba.filters.Tools;

/*
* For the uninitiated:
* http://en.wikipedia.org/wiki/Factory_method_pattern
* */
public abstract class IOBuffered
{
    public IOBuffered()
    {
        super();
    }

    public IOBuffered(IOType type)
    {
        this.ioType = type;
    }

    private IOType ioType = null;

    /**
     *
     * @param filePath
     */
    protected abstract void construct(String filePath);


    /**
     *
     * @return
     */
    public IOType getIoType()
    {
        return ioType;
    }

    /**
     *
     * @param type
     */
    public void setIoType(IOType type)
    {
        this.ioType = type;
    }
}