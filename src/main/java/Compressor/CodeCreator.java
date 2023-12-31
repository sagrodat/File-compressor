package Compressor;

import Tree.Node;

public class CodeCreator {
    private String [] codes;
    private Node root;
    public CodeCreator(Node root)
    {
        this.root = root;
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

        if(root.getLeft() == null && root.getRight() == null) //special case, tree consists of only one node
        {
            codes[root.getLetter()] = "0";
        }
        else
        {
            traverseTreeToCreateCodes(root, "");
        }
    }

    public String [] getCodes(){return this.codes;}

}
