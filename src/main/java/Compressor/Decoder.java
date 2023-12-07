package Compressor;

import FileManagement.FileManager;
import Tree.Node;
import Utility.Constants;
import Utility.Directions;

import java.util.ArrayList;

public class Decoder {
    Node root;
    FileManager reader;
    FileManager writer;
    String [] codes;
    private ReadBuffer readBuffer;
    private ArrayList<Integer> decodedContent;
    //CONSTRUCTORS
    public Decoder()
    {
    }
    public void decodeContent(String inputPath)
    {
        this.reader = new FileManager(inputPath,"r");
        this.readBuffer = new ReadBuffer();
        restoreTreeFromBinaryData();
        restoreContentFromBinaryData();
    }


    private void restoreContentFromBinaryData() {
        int bitsToReadFromLastByte = readBuffer.getNextByte();
        boolean skippedEmptyBits = false;

        this.decodedContent = new ArrayList<>();
        int bit;
        Node tmp = root;
        while((bit = readBuffer.getNextBit()) !=-1)
        {
            if(readBuffer.isReadingLastByte() && !skippedEmptyBits)
            {
                readBuffer.skipBits(7 - bitsToReadFromLastByte);
                skippedEmptyBits = true;
                continue;
            }

            if(bit == 0)
                tmp = tmp.getLeft();
            else if(bit == 1)
                tmp = tmp.getRight();

            if(tmp.getLetter() != -1)
            {
                decodedContent.add(tmp.getLetter());
                tmp = root;
            }
        }
    }

    //WORKERS
    private Node createNewNode(ReadBuffer buf)
    {
        int nodeType = buf.getNextBit();
        int letter = nodeType == 0 ? -1 : buf.getNextByte();
        return new Node(null,null,letter,0);
    }
    private Node linkNewNode(Node parentNode, Node childNode, int direction)
    {
        if(parentNode == null) // linking root (starting a tree)
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

    private void traverseToRebuildTree(Node node, int direction)
    {
        Node newNode = createNewNode(readBuffer);
        node = linkNewNode(node,newNode,direction);
        if(newNode.getLetter() != -1)
            return;

        traverseToRebuildTree(newNode,Directions.LEFT);
        traverseToRebuildTree(newNode,Directions.RIGHT);
    }
    private void restoreTreeFromBinaryData() {
        traverseToRebuildTree(null, Directions.ROOT);
    }

    public void createCodes()
    {
        CodeCreator codeCreator = new CodeCreator(root);
        codeCreator.createCodes();
        this.codes = codeCreator.getCodes();
    }

    public void saveDecodedContentToFile(String outputPath)
    {
        this.writer = new FileManager(outputPath,"rw");
        for(Integer value : decodedContent)
                writer.write(value);
    }

    // PRIVATE CLASSES
    private class ReadBuffer {
        int value;
        int bitsUsed;
        public ReadBuffer()
        {
            loadNextValue();
        }
        private boolean areAllBitsUsed(){return this.bitsUsed == 8;}
        private boolean endOfFileReached(){ return value == -1; }
        private void loadNextValue()
        {
            this.value = reader.read();
            bitsUsed = 0;
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
        public boolean isReadingLastByte()
        {
            return (reader.getFilePointerPosition() == reader.length());
        }

        public void skipBits(int toSkip) {
            for(int i = 0; i < toSkip ; i++)
                getNextBit();
        }
    }
    //PRINTERS

    public void printDecodedContent()
    {
        for(int i = 0 ; i < decodedContent.size(); i++)
        {
            System.out.print((char)(int)decodedContent.get(i));
        }
    }
    public void printInputFileAsBits()
    {
        ReadBuffer buf = new ReadBuffer();
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
        ReadBuffer buf = new ReadBuffer();
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
        if(codes == null)
            createCodes();
        for(int i = 0 ; i < Constants.MAX_UNIQUE ;i++)
        {
            if(codes[i] != null)
                System.out.println((char)i + "(" + i + ") " + codes[i]);
        }
    }

    //SETTERS AND GETTERS
    public String [] getCodes(){return this.codes;}
}
