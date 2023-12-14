package Tree;

public class MinHeap {

    private int heapSize;
    private Node [] nodes;

    //CONSTRUCTORS
    public MinHeap(Node [] nodes, int numberOfNodes)
    {
        this.nodes = nodes;
        this.heapSize = numberOfNodes;
        buildHeap();
    }

    //WORKERS
    private void buildHeap() {
        int n = heapSize;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyDown(i, n);
        }
    }

    private void heapifyDown(int parentId, int maxId) {
        int leftChildId = 2 * parentId + 1;
        int rightChildId = 2 * parentId + 2;
        int smallestValId = parentId;

        while(true)
        {
            if (leftChildId < maxId && nodes[leftChildId].getOccurrences() < nodes[smallestValId].getOccurrences()) {
                smallestValId = leftChildId;
            }

            if (rightChildId < maxId && nodes[rightChildId].getOccurrences() < nodes[smallestValId].getOccurrences()) {
                smallestValId = rightChildId;
            }

            if (smallestValId != parentId) {
                swap(parentId, smallestValId);
                leftChildId = 2 * smallestValId + 1;
                rightChildId = 2 * smallestValId + 2;
                parentId = smallestValId;
            }
            else
            {
                break;
            }
        }

    }
    private void heapifyUp(int childId)
    {
        int parentId = (childId -1)/2;
        while(nodes[parentId].getOccurrences() > nodes[childId].getOccurrences() && parentId >= 0)
        {
            swap(childId,parentId);
            childId = parentId;
            parentId = (parentId - 1)/2;
        }
    }

    public void insertNode(Node node)
    {
        heapSize++;
        this.nodes[heapSize-1] = node;
        heapifyUp(heapSize-1);
    }

    public Node extractMin()
    {
        if(heapSize < 1)
            return null;

        swap(0,heapSize-1);
        Node output = nodes[heapSize-1];

        nodes[heapSize-1] = null;

        heapSize--;
        heapifyDown(0,heapSize);
        return output;
    }


    private void swap( int firstId, int secondId) {

        if (firstId != secondId) {

            Node firstNode = nodes[firstId];
            nodes[firstId] = nodes[secondId];
            nodes[secondId] = firstNode;
        }
    }
    //SETTERS AND GETTERS
    public int getHeapSize() { return this.heapSize; }
    public Node getRoot() { return this.nodes[0]; }


    //PRINTERS
    private void printHeapNodeInfo(int index)
    {
        if(index >= heapSize)
            return;
        System.out.println((char)nodes[index].getLetter() + " -> " + nodes[index].getOccurrences());
        System.out.println("left :");
        printHeapNodeInfo(index * 2 + 1);
        System.out.println("right :");
        printHeapNodeInfo(index * 2 + 2);
    }
    public void printHeapInfo()
    {
        printHeapNodeInfo(0);
    }
}
