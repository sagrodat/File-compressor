package Compressor;

import FileManagement.FileStreamsInitializer;
import Tree.HuffTree;
import Tree.Node;
import Utility.Constants;
import Utility.Directions;
import org.w3c.dom.ls.LSOutput;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decoder {
    Node root;
    FileInputStream reader;
    FileOutputStream writer;
    String [] codes;
    //CONSTRUCTORS
    public Decoder(String inputPath, String outputPath)
    {
        FileStreamsInitializer fsi = new FileStreamsInitializer(inputPath,  outputPath);
        this.reader = fsi.getFileInputStream();
        this.writer = fsi.getFileOutputStream();

        restoreTreeFromBinaryData();
    }
    //WORKERS
    private Node createNewNode( Buffer buf)
    {
        int nodeType = buf.getNextBit();
        int letter = nodeType == 0 ? -1 : buf.getNextByte();
        return new Node(null,null,letter,0);
    }
    private Node linkNewNode(Node parentNode, Node childNode, int direction)
    {
        if(parentNode == null) // root node
        {
            parentNode = childNode;
            this.root = parentNode;
            return parentNode;
        }

        if(direction == Directions.LEFT)
            parentNode.setLeft(childNode);
        else if(direction == Directions.RIGHT)
            parentNode.setRight(childNode);

        return parentNode;
    }

    private void traverseToRebuildTree(Node node, int direction, Buffer buf)
    {
        Node newNode = null;
        newNode = createNewNode(buf);
        node = linkNewNode(node,newNode,direction);
        if(newNode.getLetter() != -1)
            return;

        traverseToRebuildTree(newNode,Directions.LEFT,buf);
        traverseToRebuildTree(newNode,Directions.RIGHT,buf);
    }
    private void restoreTreeFromBinaryData() {
        Buffer buf = new Buffer();
        traverseToRebuildTree(null, Directions.NONE, buf);
    }


    private class Buffer{
        int value;
        int bitsUsed;
        public Buffer()
        {
            loadNextValue();
        }
        private boolean areAllBitsUsed(){return this.bitsUsed == 8;}
        private boolean endOfFileReached(){ return value == -1; }
        private void loadNextValue()
        {
            try
            {
                this.value = reader.read();
                bitsUsed = 0;
            }
            catch (IOException e)
            {
                System.err.println("Failed to read from file!");
            }
        }
        public int getNextBit()
        {
            if (areAllBitsUsed()) {
                loadNextValue();
            }

            if(endOfFileReached())
                return -1;


            int bitValue = (this.value >> (7 - bitsUsed))&1;
            bitsUsed++;
            return bitValue;
        }
        public int getNextByte()
        {
            if(endOfFileReached())
                return -1;

            int byteValue = 0;
            for(int i = 0 ;i < 8 ; i++)
            {
                byteValue = byteValue << 1;
                byteValue += getNextBit();
            }
            return byteValue;
        }
    }


    private void traverseTreeToCreateCodes(Node node, String currentCode)
    {
        if(node == null)
            return;
        if(node.getLetter() != -1)
        {
            codes[node.getLetter()] = currentCode;
        }
        traverseTreeToCreateCodes(node.getLeft(), currentCode + "0");
        traverseTreeToCreateCodes(node.getRight(), currentCode + "1");
    }

    public void createCodes()
    {
        this.codes = new String[256];
        traverseTreeToCreateCodes(root, "");
    }

    //PRINTERS

    public void printInputFileAsBits()
    {
        Buffer buf = new Buffer();
        int tmp;
        int cnt = 0;
        while((tmp = buf.getNextBit()) != -1)
        {
            System.out.print(tmp);
            cnt++;
            if(cnt%8 == 0)
                System.out.print(" ");

        }
    }

    public void printInputFileAsTreeInfo()
    {
        Buffer buf = new Buffer();
        int tmp;
        while((tmp = buf.getNextBit()) != -1)
        {
            System.out.print(tmp);
            if(tmp == 1)
                System.out.print((char)buf.getNextByte());

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
    public String [] getCodes(){return this.codes;}
}
