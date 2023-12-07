package Utility;

import java.util.ArrayList;

public class SaveBuffer {

    private ArrayList<Integer> binaryData;
    int val;
    private int bitsUsed;
    public SaveBuffer()
    {
        this.binaryData = new ArrayList<>();
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
        binaryData.add(val);
        bitsUsed = 0;
        val = 0;
    }

    public void saveLeftoverValue()
    {
        saveValAndResetBuffer();
    }

    public ArrayList<Integer> getBinaryData(){return this.binaryData;}

    public void addCode(String code) {
        for(int i = 0; i < code.length() ; i++)
        {
            if(isBufferFull()) {saveValAndResetBuffer();}
            val = val << 1;
            val += code.charAt(i) - '0';
            bitsUsed++;
        }
    }
    public int getBitsUsed(){return this.bitsUsed;}

}
