package Compressor;

import FileManagement.FileInfoReader;
import FileManagement.FileManager;
import Tree.Node;
import Utility.Constants;
import Utility.Directions;
import FileManagement.BitReader;

public class Decoder {

    private Node root;
    private FileManager reader;
    private FileManager writer;
    private String [] codes;
    private BitReader bitReader;
    private String originalFileExtension;

    //CONSTRUCTORS
    public Decoder(){}
    public void decodeContentAndSaveToFile(String inputPath, String outputPath)
    {
        this.reader = new FileManager(inputPath,"r");
        this.bitReader = new BitReader(reader);
        restoreOriginalExtension();
        restoreOriginalTree();
        restoreAndSaveContent(addOriginalExtensionIfNotSpecified(outputPath));
    }

    private String addOriginalExtensionIfNotSpecified(String path)
    {
        //if given extension matches the one of the original file dont do anything
        // if no extension or given doesnt match then add extnesion
        FileInfoReader fileInfoReader = new FileInfoReader();
        if(fileInfoReader.getExtension(path) == null || !fileInfoReader.getExtension(path).equals(originalFileExtension))
            return path + "." + originalFileExtension;
        else
            return path;
    }

    private void restoreOriginalExtension()
    {
        String extension = new String();
        int tmp;
        while((tmp = reader.read()) != Constants.EndOfExtension)
        {
            extension +=(char)tmp;
        }
        this.originalFileExtension = extension;
    }


    private int getBitsToReadFromLastByte()
    {
        int val = bitReader.getNextBits(3);
        if(val == 0)
            return 8;
        else
            return val;
    }


    private void restoreAndSaveContent(String outputPath) {
        this.writer = new FileManager(outputPath,"rw");
        int bitsToReadFromLastByte = getBitsToReadFromLastByte();
        //System.out.println("bitsTORead decodeing : " + bitsToReadFromLastByte);
        boolean skippedEmptyBits = false;


        int bit;
        Node tmp = root;
        while((bit = bitReader.getNextBit()) !=-1)
        {
            if(bitReader.isReadingLastByte() && !skippedEmptyBits && bitsToReadFromLastByte != 8)
            {
                //System.out.println("Entered skipping bits");
                bitReader.skipBits(7 - bitsToReadFromLastByte);
                skippedEmptyBits = true;
                continue;
            }

            if(bit == 0)
                tmp = tmp.getLeft();
            else if(bit == 1)
                tmp = tmp.getRight();

            if(tmp.getLetter() != -1)
            {
                //bitReader.getDecodedContent().add(tmp.getLetter());
                writer.write(tmp.getLetter());
                tmp = root;
            }
        }
        reader.close();
        writer.close();
    }

    //WORKERS
    private Node createNewNode(BitReader buf)
    {
        int nodeType = buf.getNextBit();
        int letter = nodeType == 0 ? -1 : buf.getNextByte();
        return new Node(null,null,letter,0);
    }
    private Node linkChildToParent(Node parentNode, Node childNode, int direction)
    {
        if(parentNode == null) // no parent means creating a new tree, passed childNode is root node
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

    private void traverseToRebuildTree(Node parentNode, int direction)
    {
        Node childNode = createNewNode(bitReader);
        parentNode = linkChildToParent(parentNode,childNode,direction);
        if(childNode.getLetter() != -1)
            return;

        traverseToRebuildTree(childNode,Directions.LEFT);
        traverseToRebuildTree(childNode,Directions.RIGHT);
    }
    private void restoreOriginalTree() {
        traverseToRebuildTree(null, Directions.ROOT);
    }

    public void createCodes()
    {
        CodeCreator codeCreator = new CodeCreator(root);
        codeCreator.createCodes();
        this.codes = codeCreator.getCodes();
    }



    //PRINTERS

    public void printInputFileAsBits()
    {
        BitReader buf = new BitReader(this.reader);
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
        BitReader buf = new BitReader(this.reader);
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

    public String getOriginalFileExtension() { return originalFileExtension;  }


}
