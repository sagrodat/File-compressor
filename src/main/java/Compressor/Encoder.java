package Compressor;

import FileManagement.FileManager;
import Tree.HuffTree;
import Utility.Constants;
import Utility.SaveBuffer;

public class Encoder {

    private FileManager reader;
    private FileManager writer;
    private int [] occurrences;
    private HuffTree tree;
    private String [] codes;

    private SaveBuffer saveBuffer;


    //CONSTRUCTORS
    public Encoder(){}

    public void encodeContent(String inputPath)
    {
        this.reader = new FileManager(inputPath,"r");
        countOccurrences();
        createHuffManTree();
        createCodes();
    }

    public void saveEncodedContentToFile(String outputPath) {
        this.writer = new FileManager(outputPath,"rw");
        tree.createTreeBinaryData();
        createContentBinaryData();

        for(Integer value : saveBuffer.getBinaryData())
        {
            writer.write(value);
        }
        writer.close();
    }

    //change to private!!!
    public int countBitsToReadFromLastByte()
    {
        int totalCodeLen = 0;
        for(int i = 0 ; i < occurrences.length ; i++)
        {
            if(codes[i] == null)
                continue;

            totalCodeLen += occurrences[i] * codes[i].length();
        }
        int atBit = (totalCodeLen + this.saveBuffer.getBitsUsed())%8;
        return atBit;
    }

    private void createContentBinaryData()
    {
        this.saveBuffer = tree.getSaveBuffer();
        reader.seek(0);

        saveBuffer.addLetter(countBitsToReadFromLastByte());

        int tmp;

        while ((tmp = this.reader.read()) != -1)
            saveBuffer.addCode(codes[tmp]);

        saveBuffer.saveLeftoverValue();

    }
    //WORKERS
    private void saveBinaryTreeDataToFile()
    {
        SaveBuffer saveBuffer = tree.getSaveBuffer();
        for(int i = 0; i < saveBuffer.getBinaryData().size() ; i++)
            writer.write(saveBuffer.getBinaryData().get(i));
    }


    private void createHuffManTree() {
        this.tree = new HuffTree(this.occurrences);
    }

    private void countOccurrences()
    {
        this.occurrences = new int[Constants.MAX_UNIQUE];
        int tmp;

        while ((tmp = this.reader.read()) != -1)
            occurrences[tmp]++;
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
