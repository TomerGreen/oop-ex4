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
        Node father = getLocation(newValue);
        if(father != null){
            Node newNode = new Node(father, newValue);
            if (newValue > father.getValue()){
                father.setRightSon(newNode);
            }
            else{
                father.setLeftSon(newNode);
            }
            fixTree(newNode);
            return true;
        }
        else if (father == null && root == null){   //The tree is empty!
            root = new Node(newValue);
            return true;
        }
        return false; //newValue is contained in the tree.
    }

    private void fixTree(Node newNode){
        Node grandchild = newNode;
        Node child = newNode.getFather();
        Node father= child.getFather();
        child.setHeight();
        if (father == null){
            return;
        }
        while (father.getFather() != null){
            father.setHeight();
            if (checkViolations(father, child, grandchild)){
                return;
            }
            grandchild = child;
            child = father;
            father = father.getFather();
        }
        father.setHeight();
        checkViolations(father, child, grandchild);
    }

    private boolean checkViolations(Node father, Node child, Node grandchild){
        if (father.getHeightDiff() > 1){
            if(child == father.getRightSon() &&  grandchild == child.getRightSon()){
                fixRRViolation(father, child, grandchild);
            }
            else if (child == father.getRightSon() && grandchild == child.getLeftSon()){
                fixRLViolation(father, child, grandchild);
            }
            else if (child == father.getLeftSon() && grandchild == child.getRightSon()){
                fixLRViolation(father, child, grandchild);
            }
            else if (child == father.getLeftSon() && grandchild == child.getLeftSon()){
                fixLLViolation(father, child, grandchild);
            }
            return true;
        }
        return false;
    }

    private void fixRRViolation(Node father, Node child, Node grandchild){
        Node orphanNode = child.getLeftSon();
        child.setLeftSon(father);
        father.setRightSon(orphanNode);
    }

    private void fixRLViolation(Node father, Node child, Node grandchild){

    }

    private void fixLRViolation(Node father, Node child, Node grandchild){

    }

    private void fixLLViolation(Node father, Node child, Node grandchild){
        Node orphanNode = child.getRightSon();
        child.setRightSon(father);
        father.setLeftSon(orphanNode);

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