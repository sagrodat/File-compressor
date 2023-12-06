package Tree;

import Utility.MinHeap;

import java.util.ArrayList;

import static Utility.Constants.MAX_UNIQUE;


public class HuffTree {




    Node root;
    Node [] nodes;
    int nodeIterator;
    int [] occurrences;
    MinHeap heap;
    ArrayList<Integer> binaryData;



    //CONSTRUCTORS
    public HuffTree(int [] occurrences)
    {
        this.occurrences = occurrences;
        this.nodeIterator = 0;
        this.nodes = new Node[MAX_UNIQUE * 2];
        createLeafNodes();
        this.heap = new MinHeap(this.nodes);
        createOtherNodes();
        createTreeBinaryData();
    }

    //PRIVATE CLASSES
    private class Buffer {
        int val;
        int bitsUsed;
        public Buffer()
        {
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
        private void saveValAndResetBuffer() {
            binaryData.add(val);
            bitsUsed = 0;
            val = 0;
        }
        public void saveLeftoverValue() {
            int unusedBits = 8 - bitsUsed;
            for(int i = 0; i < unusedBits ; i++)
            {
                val = val << 1;
            }
            saveValAndResetBuffer();
        }
    }

    //WORKERS
    public void createTreeBinaryData() {
        this.binaryData = new ArrayList<Integer>();
        Buffer buf = new Buffer();
        traverseTreeToCreateTreeBinaryData(this.root,buf);
        buf.saveLeftoverValue();
    }

    private void traverseTreeToCreateTreeBinaryData(Node node, Buffer buf) {
        if(node == null)
            return;
        if(node.getLetter() == -1)
        {
            buf.addZero();
        }
        else
        {
            buf.addOne();
            buf.addLetter(node.getLetter());
        }
        traverseTreeToCreateTreeBinaryData(node.getLeft(),buf);
        traverseTreeToCreateTreeBinaryData(node.getRight(),buf);
    }

    private void createLeafNodes() {
        for(int i =0 ; i < occurrences.length ;i++)
        {
            if(occurrences[i] != 0)
            {
                Node node = new Node(null,null,i,occurrences[i]);
                this.nodes[nodeIterator++] = node;
            }
        }
    }

    private void createOtherNodes()
    {
        while(heap.getHeapSize() > 1)
        {
            Node childA = heap.extractMin();
            Node childB = heap.extractMin();
            Node parent = new Node(childA,childB,-1,childA.getOccurrences() + childB.getOccurrences());
            heap.insertNode(parent);
        }
        this.root = heap.getRoot();
    }

    //PRINTERS
    public void printTreeNodes()
    {
        for(int i = 0; i < nodeIterator ; i++)
        {
            System.out.println((char)this.nodes[i].getLetter() + " -> " + this.nodes[i].getOccurrences());
        }
    }

    private void printTreeNodeInfo(Node node)
    {
        if(node == null)
            return;
        System.out.println((char)node.getLetter() + " -> " + node.getOccurrences());
        System.out.println("left :");
        printTreeNodeInfo(node.getLeft());
        System.out.println("right :");
        printTreeNodeInfo(node.getRight());
    }
    public void printTreeInfo()
    {
        printTreeNodeInfo(this.root);
    }

    //SETTERS AND GETTERS
    public ArrayList<Integer> getBinaryTreeData() { return this.binaryData; }
    public Node getRoot() { return this.root; }
    public Node[] getNodes() { return this.nodes; }
    public MinHeap getHeap() { return this.heap; }

}
