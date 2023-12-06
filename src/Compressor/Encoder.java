package Compressor;

import Tree.HuffTree;
import Tree.Node;
import Utility.Constants;
import FileManagement.FileStreamsInitializer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Encoder {

    FileInputStream reader;
    FileOutputStream writer;
    int [] occurrences;
    HuffTree tree;
    String [] codes;


    //CONSTRUCTORS
    public Encoder(String inputPath, String outputPath)
    {
        FileStreamsInitializer fsi = new FileStreamsInitializer(inputPath, outputPath);
        this.reader = fsi.getFileInputStream();
        this.writer = fsi.getFileOutputStream();
        countOccurrences();
        createHuffManTree();
    }




    //WORKERS

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

    private void traverseTreeToCreateCodes(Node node, String currentCode)
    {
        if(node == null)
            return;
        if(node.getLetter() != -1)
        {
            this.codes[node.getLetter()] = currentCode;
        }
        traverseTreeToCreateCodes(node.getLeft(), currentCode + "0");
        traverseTreeToCreateCodes(node.getRight(), currentCode + "1");
    }

    public void createCodes()
    {
        this.codes = new String[256];
        traverseTreeToCreateCodes(tree.getRoot(), "");
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
