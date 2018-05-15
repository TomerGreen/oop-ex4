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

    /**
     * Returns the maximum number of nodes in an AVL tree of a given height.
     * @param h An AVL tree height
     * @return The minimum number of nodes in a tree of that height.
     */
    public static int findMaxNodes(int h) {
        int sum = 0;
        int layer = 0;  // The layer number in the tree, 0 being root.
        int nodesInLayer = 1;  // The number of nodes in that layer.
        while (layer <= h) {
            sum += nodesInLayer;
            layer++;
            nodesInLayer *= 2;
        }
        return sum;
    }

    /**
     * Returns the minimum number of nodes in an AVL tree of a given height.
     * @param h An AVL tree height
     * @return The minimum number of nodes in a tree of that height.
     */
    public static int findMinNodes(int h) {
        return findMaxNodes(h-1) + 1;
    }

    public boolean add(int newValue){
        Node father = expectedParent(newValue);
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
        child.setHeight(1);
        if (father == null){
            return;
        }
        while (father.getFather() != null){
            father.setHeight(1);
            if (checkViolations(father, child, grandchild)){
                father.setHeight(-1);
                return;
            }
            grandchild = child;
            child = father;
            father = father.getFather();
        }
        father.setHeight(1);
        checkViolations(father, child, grandchild);
    }

    private boolean checkViolations(Node father, Node child, Node grandchild){
        if (father.getHeightDiff() > 1){
            if(child == father.getRightSon() &&  grandchild == child.getRightSon()){
                fixRRViolation(father, child);
            }
            else if (child == father.getRightSon() && grandchild == child.getLeftSon()){
                fixRLViolation(father, child, grandchild);
            }
            else if (child == father.getLeftSon() && grandchild == child.getRightSon()){
                fixLRViolation(father, child, grandchild);
            }
            else if (child == father.getLeftSon() && grandchild == child.getLeftSon()){
                fixLLViolation(father, child);
            }
            return true;
        }
        return false;
    }

    private void fixRRViolation(Node father, Node child){
        Node grandfather = father.getFather();
        Node orphanNode = child.getLeftSon();
        child.setLeftSon(father);
        father.setFather(child);
        if (grandfather != null) {
            makeGrandDadsBoy(grandfather, father, child);
            child.setFather(grandfather);
        }
        else{
            child.setFather(null);
            root = child;
        }
        father.setRightSon(orphanNode);
        orphanNode.setFather(father);
    }

    private void fixRLViolation(Node father, Node child, Node grandchild){
        Node orphanChild = grandchild.getRightSon();
        father.setRightSon(grandchild);
        grandchild.setFather(father);
        grandchild.setRightSon(child);
        child.setFather(grandchild);
        child.setLeftSon(orphanChild);
        orphanChild.setFather(child);
        fixRRViolation(father, grandchild);
    }

    private void fixLRViolation(Node father, Node child, Node grandchild){
        Node orphanChild = grandchild.getLeftSon();
        father.setLeftSon(grandchild);
        grandchild.setFather(father);
        grandchild.setLeftSon(child);
        child.setFather(grandchild);
        child.setRightSon(orphanChild);
        orphanChild.setFather(child);
        fixLLViolation(father, grandchild);
    }

    private void fixLLViolation(Node father, Node child){
        Node grandfather = father.getFather();
        Node orphanNode = child.getRightSon();
        child.setRightSon(father);
        father.setFather(child);
        if (grandfather != null){
            makeGrandDadsBoy(grandfather, father, child);
            child.setFather(grandfather);
        }
        else{
            child.setFather(null);
            root = child;
        }
        father.setLeftSon(orphanNode);
        orphanNode.setFather(father);
    }

    private void makeGrandDadsBoy(Node grandfather, Node father, Node child){
        if (grandfather.getRightSon() == father){
            grandfather.setRightSon(child);
        }
        else if (grandfather.getLeftSon() == father){
            grandfather.setLeftSon(child);
        }
    }

    /**
     * Returns whether a value is in the tree.
     * @param searchVal The value to be searched.
     * @return The depth of the node with that value, or -1 if it does not exist.
     */
    public int contains(int searchVal){
        Node candidate = valueToNode(searchVal);
        if (candidate == null || candidate.getValue() != searchVal) {
            return -1;
        }
        else {
            return candidate.getHeight();
        }
    }


    /**
     * @param searchVal The value to be searched.
     * @return The expected parent node of the value if it is not in the tree, and null otherwise.
     */
    private Node expectedParent(int searchVal) {
        Node candidate = valueToNode(searchVal);
        if (candidate == null || searchVal == candidate.getValue()) {
            return null;
        }
        return candidate;
    }

    /**
     * Returns a node with a given value if it exists, or the node that is expected to be its
     * parent if it was added to the tree (prior to rotations).
     * This helper method is used by several API methods to prevent probing the tree multiple times.
     * @param searchVal The value to be searched.
     * @return The expected father node if searchValue is not in the tree, or the node with the same value.
     * Returns null only if the tree is empty.
     */
    private Node valueToNode(int searchVal){
        Node currNode = root;  // The node we're comparing too, and might be returned.
        Node nextNode = root;  // The node we proceed to, or stop if it's null.
        while (nextNode != null) {
            currNode = nextNode;
            if (searchVal == currNode.getValue()) {
                return currNode;
            }
            else if (searchVal > currNode.getValue()) {
                nextNode = currNode.getRightSon();
            }
            else {
                nextNode = currNode.getLeftSon();
            }
        }
        return currNode;
    }

}