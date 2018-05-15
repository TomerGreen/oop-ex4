package oop.ex4.data_structures;


/**
 * A class representing an AVL tree.
 */
public class AvlTree {

    /** The root node. */
    protected Node root;

    /**
     * Creates an empty tree.
     */
    public AvlTree() {
        root = null;
    }

    /**
     * Creates a new tree by adding values from an array, one by one.
     * @param data The array of values that
     */
    public AvlTree(int[] data) {
        for (int value : data) {
            this.add(value);
        }
    }

    /**
     * A copy constructor that creates a new tree from an existing one.
     * @param tree The copied tree.
     */
    public AvlTree(AvlTree tree) {
        this.root = tree.root;
    }

    public boolean add(int newValue){
        if(contains(newValue) > 0){
            Node father = getLocation(newValue);

        }
        return false; //newValue is contained in the tree.
    }


    /**
     * Given a new value to be added, returns the expected father node, before any rotation.
     * @param newValue The value to be added.
     * @return The expected father node if newValue is not in the tree, null otherwise.
     */
    private Node getLocation(int newValue){
        Node currNode = root;  // The node we're comparing too, and might be returned.
        Node nextNode = root;  // The node we proceed to, or stop if it's null.
        while (nextNode != null) {
            currNode = nextNode;
            if (newValue == currNode.getValue()) {
                return null;
            }
            else if (newValue > currNode.getValue()) {
                nextNode = currNode.getRightSon();
            }
            else {
                nextNode = currNode.getLeftSon();
            }
        }
        return currNode;
    }

    private int contains(int searchVal){
        return 1;
    }

}
