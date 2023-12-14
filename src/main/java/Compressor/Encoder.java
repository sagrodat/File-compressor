package Compressor;

import FileManagement.FileInfoReader;
import FileManagement.FileManager;
import Tree.HuffTree;
import GlobalVariables.Constants;
import FileManagement.BitSaver;

import java.io.File;

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
        this.reader = new FileManager(inputPath,"r");
        validateFile();
        createTreeAndCodes();
        this.writer = new FileManager( createOutputFileName(inputPath, outputPath) ,"rw");
        this.bitSaver = new BitSaver(writer);

        saveCompressionTag();
        saveOriginalExtension(inputPath);
        tree.createAndSaveTreeBinaryData(this.bitSaver);
        createAndSaveContentBinaryData();

        writer.close();
        reader.close();
    }

    private void validateFile()
    {
        if(reader.length() == 0)
        {
            System.err.println("Can't compress, file is empty!");
            System.exit(-1);
        }
    }

    private void saveCompressionTag()
    {
        for(int i = 0; i < Constants.compressionTag.length() ; i++)
            writer.write(Constants.compressionTag.charAt(i));
    }

    private String createOutputFileName(String inputFilePath, String outputFilePath)
    {
        FileInfoReader fileInfoReader = new FileInfoReader();

        File outputSpot = new File(outputFilePath);
        if(outputSpot.isDirectory())
            outputFilePath = outputFilePath + "\\" + addCompressionExtension(fileInfoReader.getFileNameWithoutExtension(inputFilePath));
        else
            outputFilePath = addCompressionExtension(outputFilePath);
        return outputFilePath;
    }

    private String addCompressionExtension(String path)
    {
        path += Constants.customExtension;
        return path;
    }

    private void saveOriginalExtension(String originalFile)
    {
        FileInfoReader fileInfoReader = new FileInfoReader();
        String extension = fileInfoReader.getExtension(originalFile);
        writer.write(extension);
        writer.write(Constants.EndOfExtension);
    }
    private void createTreeAndCodes()
    {
        countOccurrences();
        createHuffManTree();
        createCodes();
    }
    private void coverOneNodeTreeEdgeCase()
    {
        for(int i = 0 ; i < reader.length() ; i++)
        {
            bitSaver.addZero();
        }
        bitSaver.addOne();
        bitSaver.shiftValueToMostSignificantBits();
        bitSaver.saveLeftoverValue();
    }

    private void createAndSaveContentBinaryData()
    {
        reader.seek(0);

        if(tree.getRoot().isLeaf())
        {
           coverOneNodeTreeEdgeCase();
           return;
        }

        bitSaver.addLeastSignificantBits(countBitsToReadFromLastByte(),3);

        int tmp;
        while ((tmp = this.reader.read()) != -1)
            bitSaver.addCode(codes[tmp]);

        bitSaver.saveLeftoverValue();
    }

    private int countBitsToReadFromLastByte()
    {
        int bitsToReadFromLastByteInformationLength = 3; //(bits)
        return (calculateTreeCost() + bitsToReadFromLastByteInformationLength + this.bitSaver.getNumberOfUsedBits())%8;
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

    public int calculateTreeCost()
    {
        int treeCost = 0;
        for(int i = 0 ; i < Constants.MAX_UNIQUE ; i++)
        {
            if(codes[i] == null)
                continue;

            treeCost += occurrences[i] * codes[i].length();
        }
        return treeCost;
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
