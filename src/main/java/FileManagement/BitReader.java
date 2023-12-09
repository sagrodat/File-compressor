package FileManagement;

public class BitReader {
    private int value;
    private int bitsUsed;
    private FileManager reader;
    //CONSTRUCTORS
    public BitReader(FileManager reader)
    {
        this.reader = reader;
        this.bitsUsed = 8; // to not load a value when instantiating but to load a value when adequate method called
    }
    //WORKERS
    private boolean areAllBitsUsed(){return this.bitsUsed == 8;}
    private boolean isEndOfFileReached(){ return value == -1; }
    private void loadNextValue()
    {
        this.value = this.reader.read();
        bitsUsed = 0;
    }
    public int getNextBit()
    {
        if (areAllBitsUsed()) {
            loadNextValue();
        }

        if(isEndOfFileReached())
            return -1;

        int bitValue = (this.value >> (7 - bitsUsed))&1;
        bitsUsed++;
        return bitValue;
    }
    public int getNextBits(int numberOfBits)
    {
        if(isEndOfFileReached())
            return -1;

        int bitsValue = 0;
        for(int i = 0 ; i < numberOfBits ; i++)
        {
            bitsValue = bitsValue<<1;
            bitsValue += getNextBit();
        }
        return bitsValue;
    }
    public int getNextByte()
    {
        if(isEndOfFileReached())
            return -1;

        int byteValue = 0;
        for(int i = 0 ;i < 8 ; i++)
        {
            byteValue = byteValue << 1;
            byteValue += getNextBit();
        }
        return byteValue;
    }
    public boolean  isReadingLastByte()
    {
        return (reader.getFilePointerPosition() == reader.length());
    }

    public void skipBits(int toSkip) {
        for(int i = 0; i < toSkip ; i++)
            getNextBit();
    }

    public void closeReader(){
        reader.close();}
}
