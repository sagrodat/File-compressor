package Tree;

public class Node{
    private Node left;
    private Node right;
    private int letter;
    private int occurrences;
    //CONSTRUCTORS
    public Node(Node left, Node right, int letter, int occurrences)
    {
        this.left = left;
        this.right = right;
        this.letter = letter;
        this.occurrences = occurrences;
    }

    //GETTERS
    public Node getLeft() { return left; } public void setLeft(Node left) { this.left = left; }
    public Node getRight() { return right; } public void setRight(Node right) { this.right = right; }
    public int getLetter() { return letter; } public void setLetter(int letter) { this.letter = letter; }
    public int getOccurrences() { return occurrences; } public void setOccurrences(int occurrences) { this.occurrences = occurrences; }

    //OTHER
    public boolean isLeaf()
    {
        return letter != -1;
    }


}