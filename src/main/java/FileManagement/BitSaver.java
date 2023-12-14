package FileManagement;

public class BitSaver {

    private FileManager writer;
    private int val;
    private int bitsUsed;
    public BitSaver(FileManager writer)
    {
        this.writer = writer;
        this.val = 0;
        this.bitsUsed = 0;
    }
    public void addZero()
    {
        if(isBufferFull()) {saveValAndResetBuffer();}
        val = val << 1;
        bitsUsed++;
    }
    public void addOne()
    {
        if(isBufferFull()) {saveValAndResetBuffer();}
        val = val << 1;
        val+=1;
        bitsUsed++;
    }
    public void addLeastSignificantBits(int value, int numberOfBits)
    {
        int test = 0;
        for(int i = 0; i < numberOfBits; i++)
        {
            if(isBufferFull()){saveValAndResetBuffer();}
            val = val << 1;
            val += ( (value >> (numberOfBits - i - 1) ) & 1);

            test = test << 1;
            test += ( (value >> (numberOfBits - i - 1) ) & 1);
            bitsUsed++;
        }
    }
    public void addLetter(int letter)
    {
        for(int i = 0; i < 8 ; i++)
        {
            if(isBufferFull()) {saveValAndResetBuffer();}
            val = val << 1;
            val += ( (letter >> (7-i)) & 1 );
            bitsUsed++;
        }
    }
    private boolean isBufferFull() {   return bitsUsed == 8; }
    private void saveValAndResetBuffer()
    {
        writer.write(val);
        bitsUsed = 0;
        val = 0;
    }

    public void saveLeftoverValue()
    {
        saveValAndResetBuffer();
    }

    public void addCode(String code) {
        for(int i = 0; i < code.length() ; i++)
        {
            if(isBufferFull()) {saveValAndResetBuffer();}
            val = val << 1;
            val += code.charAt(i) - '0';
            bitsUsed++;
        }
    }

    public void shiftValueToMostSignificantBits()
    {
        for(int i = 0 ;i < getNumberOfUnusedBits() ; i++)
        {
            val = val << 1;
        }
    }

    public int getNumberOfUsedBits(){return this.bitsUsed;}
    public int getNumberOfUnusedBits(){return 8 - this.bitsUsed;}

    public int getVal(){return this.val;}
}
