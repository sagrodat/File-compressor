package Tree;

import FileManagement.BitSaver;

import static Utility.Constants.MAX_UNIQUE;


public class HuffTree {
    private Node root;
    private Node [] nodes;
    private int numberOfNodes;
    private int [] occurrences;
    private MinHeap heap;

    private BitSaver bitSaver;


    //CONSTRUCTORS
    public HuffTree(int [] occurrences)
    {
        this.occurrences = occurrences;
        this.nodes = new Node[MAX_UNIQUE];
        createLeafNodes();
        this.heap = new MinHeap(this.nodes, this.numberOfNodes);
        createOtherNodes();
    }



    //WORKERS
    public void createAndSaveTreeBinaryData(BitSaver bitSaver) {
        this.bitSaver = bitSaver;
        traverseTreeToCreateTreeBinaryData(this.root);
    }

    private void traverseTreeToCreateTreeBinaryData(Node node) {
        if(node == null)
            return;
        if(node.getLetter() == -1)
        {
            bitSaver.addZero();
        }
        else
        {
            bitSaver.addOne();
            bitSaver.addLetter(node.getLetter());
        }
        traverseTreeToCreateTreeBinaryData(node.getLeft());
        traverseTreeToCreateTreeBinaryData(node.getRight());
    }

    private void createLeafNodes() {
        for(int i =0 ; i < occurrences.length ;i++)
        {
            if(occurrences[i] != 0)
            {
                Node node = new Node(null,null,i,occurrences[i]);
                this.nodes[numberOfNodes++] = node;
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
            numberOfNodes++;
        }
        this.root = heap.getRoot();
    }

    //PRINTERS
    public void printTreeNodes()
    {
        for(int i = 0; i < numberOfNodes; i++)
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
    public Node getRoot() { return this.root; }
    public Node[] getNodes() { return this.nodes; }
    public MinHeap getHeap() { return this.heap; }
    public BitSaver getSaveBuffer() {return this.bitSaver;}

}
