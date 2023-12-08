package Compressor;

import FileManagement.FileInfoReader;
import FileManagement.FileManager;
import Tree.HuffTree;
import Utility.Constants;
import FileManagement.BitSaver;

public class Encoder {

    private FileManager reader;
    private FileManager writer;
    private int [] occurrences;
    private HuffTree tree;
    private String [] codes;
    private BitSaver bitSaver;


    //CONSTRUCTORS
    public Encoder(){}

    //WORKERS
    public void encodeContentAndSaveToFile(String inputPath, String outputPath) {
        createTreeAndCodes(inputPath);
        this.writer = new FileManager(outputPath,"rw");
        this.bitSaver = new BitSaver(writer);
        saveOriginalExtension(inputPath);
        tree.createAndSaveTreeBinaryData(this.bitSaver);
        createAndSaveContentBinaryData();
        writer.close();
        reader.close();
    }

    private void saveOriginalExtension(String originalFile)
    {
        FileInfoReader fileInfoReader = new FileInfoReader();
        String extension = fileInfoReader.getExtension(originalFile);
        for(int i = 0; i < extension.length(); i++)
        {
            writer.write(extension.charAt(i));
        }
        writer.write(Constants.EndOfExtension);
    }
    private void createTreeAndCodes(String inputPath)
    {
        this.reader = new FileManager(inputPath,"r");
        countOccurrences();
        createHuffManTree();
        createCodes();
    }

    private void createAndSaveContentBinaryData()
    {
        this.bitSaver = tree.getSaveBuffer();
        reader.seek(0);

        bitSaver.addLeastSignificantBits(countBitsToReadFromLastByte(),3);

        int tmp;

        while ((tmp = this.reader.read()) != -1)
            bitSaver.addCode(codes[tmp]);

        bitSaver.saveLeftoverValue();

    }



    private int countBitsToReadFromLastByte()
    {
        int totalCodeLen = 0;
        int bitsToReadFromLastByteInformationLength = 3; //bits (0-7)
        for(int i = 0 ; i < occurrences.length ; i++)
        {
            if(codes[i] == null)
                continue;

            totalCodeLen += occurrences[i] * codes[i].length();
        }
        int bitsToReadFromLastByte = (totalCodeLen + bitsToReadFromLastByteInformationLength + this.bitSaver.getBitsUsed())%8;
        return bitsToReadFromLastByte;
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
