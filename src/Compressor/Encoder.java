package Compressor;

import Tree.HuffTree;
import Utility.Constants;
import FileManagement.FileStreamsInitializer;
import Utility.SaveBuffer;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Encoder {

    private RandomAccessFile reader;
    private RandomAccessFile writer;
    private int [] occurrences;
    private HuffTree tree;
    private String [] codes;

    private SaveBuffer saveBuffer;


    //CONSTRUCTORS
    public Encoder(String inputPath, String outputPath)
    {
        FileStreamsInitializer fileStreamsInitializer = new FileStreamsInitializer(inputPath, outputPath);
        this.reader = fileStreamsInitializer.getFileInputStream();
        this.writer = fileStreamsInitializer.getFileOutputStream();
    }

    public void encodeContent()
    {
        countOccurrences();
        createHuffManTree();
        createCodes();
    }

    public void saveEncodedContentToFile() {
        tree.createTreeBinaryData();
        createContentBinaryData();

        for(Integer value : saveBuffer.getBinaryData())
        {
            try
            {
                writer.write(value);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private int countBitsToReadFromLastByte()
    {
        int totalCodeLen = 0;
        for(int i = 0 ; i < occurrences.length ; i++)
        {
            if(codes[i] == null)
                continue;

            totalCodeLen += occurrences[i] * codes[i].length();
        }
        return totalCodeLen%8 != 0 ? totalCodeLen%8 : 0;
    }

    private void createContentBinaryData()
    {
        this.saveBuffer = tree.getSaveBuffer();
        try {
            reader.seek(0);
        }
        catch(IOException e)
        {
            System.err.println("Error when accessing input file!");
        }

        saveBuffer.addLetter(countBitsToReadFromLastByte() - (8-saveBuffer.getBitsUsed()));

        int tmp;
        try {
            while ((tmp = this.reader.read()) != -1) {
                saveBuffer.addCode(codes[tmp]);
            }
        }
        catch (IOException e)
        {
            System.err.println("Failed to read from file!");
        }
        saveBuffer.saveLeftoverValue();

    }
    //WORKERS
    private void saveBinaryTreeDataToFile()
    {
        SaveBuffer saveBuffer = tree.getSaveBuffer();
        for(int i = 0; i < saveBuffer.getBinaryData().size() ; i++)
        {
            try
            {
                writer.write(saveBuffer.getBinaryData().get(i));
            }
            catch(IOException e)
            {
                System.err.println("Blad przy zapisie do pliku!");
            }
        }
    }


    private void createHuffManTree() {
        this.tree = new HuffTree(this.occurrences);
    }

    private void countOccurrences()
    {
        this.occurrences = new int[Constants.MAX_UNIQUE];
        int tmp;
        try {
            while ((tmp = this.reader.read()) != -1) {
                occurrences[tmp]++;
            }
        }
        catch (IOException e)
        {
            System.err.println("Failed to read from file!");
        }
    }

    public void createCodes()
    {
        CodeCreator codeCreator = new CodeCreator(tree.getRoot());
        codeCreator.createCodes();
        this.codes = codeCreator.getCodes();
    }

    //PRINTERS
    public void printOccurrences()
    {
        for(int i = 0; i < Constants.MAX_UNIQUE; i++){
            if(occurrences[i] != 0)
                System.out.println((char)i + " -> " + occurrences[i]);
        }
    }

    public void printCodes()
    {
        for(int i = 0 ; i < Constants.MAX_UNIQUE ;i++)
        {
            if(codes[i] != null)
                System.out.println((char)i + "(" + i + ") " + codes[i]);
        }
    }

    //SETTERS AND GETTERS
    public HuffTree getTree(){return this.tree;}

    public String [] getCodes(){return this.codes;}
}
