package oop.ex4.data_structures;
import java.math.*;
import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.max;

/**
 * A class representing an AVL tree.
 */
public class AvlTree extends BinarySearchTree {

    /* ================================= SUB-CLASSES ================================= */

    /**
     * This class represents a node in an AVL tree.
     */
    public class Node {
        /*The height and the numeric value of the node.*/
        private int height, value;
        /*The node's pointers to its father, left son and right son. */
        private Node rightSon, leftSon, father;


        /**
         * An Empty constructor of Node.
         */
        Node(int value){
            this.father = null;
            this.value = value;
            leftSon = null;
            rightSon = null;
            height = 0;
        }

        /**
         * A constructor of Node.
         * @param father The node's father.
         */
        Node(Node father, int value){
            this.father = father;
            this.value = value;
            leftSon = null;
            rightSon = null;
            height = 0;
        }

        /**
         * @param other Another node.
         * @return Whether the values of this and the other node are equal.
         */
        private boolean equals(Node other) {
            return this.value == other.value;
        }

        /**
         * Returns the height difference between the two children of a node.
         * @return the height difference between the two children of a node.
         */
        private int getHeightDiff(){

            if(leftSon == null && rightSon == null){
//            System.out.println("Checking height diff for node " + this.value  + "height diff is 0" );
                return 0;
            }
            else if(leftSon == null){
//            System.out.println("Checking height diff for node " + this.value  + " height diff is " +rightSon.height+1 );
                return rightSon.height+1;
            }
            else if(rightSon == null){
//            System.out.println("Checking height diff for node " + this.value  + " height diff is " + -(leftSon.height+1) );
                return leftSon.height+1;
            }
//        System.out.println("Checking height diff for node " + this.value  + " height diff is " +abs(rightSon.height- leftSon.height)  );
            return abs(rightSon.height- leftSon.height);
        }

        /**
         * Returns the node with the smallest value in the subtree of which
         * this node is the root. Note that it might be this node itself, hence null
         * should never be returned.
         * @return The subtree minimal node.
         */
        private Node getSubtreeMinNode() {
            Node minNode = this;
            while (minNode.leftSon != null) {
                minNode = minNode.leftSon;
            }
            return minNode;
        }

        /**
         * Returns the ancestor of this node with minimal value that is
         * bigger than that of this node. Note that this node cannot be returned.
         * @return The ancestor that succeeds this node, or null if there is none.
         */
        private Node getAncestorSuccessor() {
            Node succ = this;
            while (succ.father != null) {
                Node succParent = succ.father;
                // If succ is the left son of the parent, the parent is the ancestor successor.
                if (succParent.leftSon != null && succParent.leftSon.equals(succ)) {
                    return succParent;
                }
                else {
                    succ = succParent;
                }
            }
            return null;
        }

        /**
         * Gets the node in the tree with the smallest value that is bigger
         * than that of the this node. Note that if this returns null than there
         * is no successor in the tree.
         * @return The successor node.
         */
        private Node getSuccessor() {
            // If it has a right son, return the minimal node in its subtree.
            if (this.rightSon != null) {
                return this.rightSon.getSubtreeMinNode();
            }
            else {
                return this.getAncestorSuccessor();
            }
        }

    }

    /* ================================= END SUB-CLASSES ================================= */


    /**
     * Creates an empty tree.
     */
    public AvlTree() {
        root = null;
        size = 0;
    }

    /**
     * Creates a new tree by adding values from an array, one by one.
     * @param data The array of values that
     */
    public AvlTree(int[] data) {
        size = 0;
        for (int value : data) {
            this.add(value);
        }
    }

    /**
     * A copy constructor that creates a new tree from an existing one.
     * @param tree The copied tree.
     */
    public AvlTree(AvlTree tree) {
        for (int value : tree) {
            this.add(value);
        }
        size = tree.size();
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
     * A minimal tree of height h has a root, a subtree of height h-1 and a subtree of height h-2.
     * @param h An AVL tree height
     * @return The minimum number of nodes in a tree of that height.
     */
    public static int findMinNodes(int h) {
        if (h < 0) {
            return 0;
        }
        if (h == 0) {
            return 1;
        }
        return 1 + findMinNodes(h-1) + findMinNodes(h-2);
    }

    public boolean add(int newValue){
        Node father = expectedParent(newValue);
        if(father != null){
            Node newNode = new Node(father, newValue);
            if (newValue > father.value){
                father.rightSon = newNode;
            }
            else{
                father.leftSon = newNode;
            }
//            System.out.println("Added node with value " + newValue + " before fixTree, father is " + newNode.father.value);
            fixTree(newNode);
//            System.out.println("Added node with value " + newValue + " after, father is " + newNode.father.value);
            size++;
            return true;
        }
        else if (father == null && root == null){   //The tree is empty!
            root = new Node(newValue);
            size++;
            return true;
        }
        return false; //newValue is contained in the tree.
    }

    /*
    Updates the heights of a node according to its children's heights.
     */
    private void fixHeight(Node node){
        if (isLeaf(node)){
            node.setHeight(0);
        }
        else if (node.getRightSon() == null){
            node.setHeight(node.getLeftSon().getHeight()+1);
        }
        else if (node.leftSon != null){
            node.height = node.rightSon.height+1;
        }
        else {
            node.height = max(node.leftSon.height, node.rightSon.height) + 1;
        }

    }

    /*
    Climbs up from the leaf to the root and checks for violations.
    Returns the node at which the violation happened.
     */
    private Node fixTree(Node newNode){
        Node grandchild = newNode;
        Node child = newNode.father;
        Node father= child.father;
        fixHeight(child);
        if (father == null){
            return null;
        }
        while (father.father != null){
            fixHeight(father);
            if (checkViolations(father, child, grandchild)){
//                System.out.println("exiting check violations..");
                return father;
            }
            grandchild = child;
            child = father;
            father = father.father;

        }
        fixHeight(father);
//        System.out.println("Entering check violations");
        checkViolations(father, child, grandchild);
//        System.out.println("Leaving fixTree..");
        return null;
    }

    /**
     * DEBUG METHOD - DELETE WHEN SUBMITTING
     * @return
     */
    public boolean isAVLOkay(Node node){
        if (node == null){
            return true;
        }
//        System.out.println("checking node with value " + node.value);
        if (node.leftSon != null || node.rightSon != null){
            if (node.getHeightDiff() > 1){
                return false;
            }
           return isAVLOkay(node.leftSon) && isAVLOkay(node.rightSon);

        }
        return true;
    }

    /**
     * DEBUG METHOD - DELETE WHEN SUBMITTING
     * @return
     */
    public Node getRoot(){
        return root;
    }

    /*
     * This method checks if the AVL property is violated in a specific node.
     */
    private boolean checkViolations(Node father, Node child, Node grandchild){
        if (father.getHeightDiff() > 1){
//            System.out.println("For the node " + father.value + " a violation was found.");
            if(child == father.rightSon &&  grandchild == child.rightSon){
                fixRRViolation(father, child);
            }
            else if (child == father.rightSon && grandchild == child.leftSon){
                fixRLViolation(father, child, grandchild);
            }
            else if (child == father.leftSon && grandchild == child.rightSon){
                fixLRViolation(father, child, grandchild);
            }
            else if (child == father.leftSon && grandchild == child.leftSon){
                fixLLViolation(father, child);
            }
//            System.out.println("Leaving checkViolations");
            return true;
        }
        return false;
    }

    /*
    Fixes an RR violation.
     */
    private void fixRRViolation(Node father, Node child){
//        System.out.println("fixing RR");
        Node grandfather = father.father;
        Node orphanNode = child.leftSon;
        child.leftSon = father;
        father.father = child;
        father.height = father.height-2;
        if (grandfather != null) {
            changeAncestorsChild(grandfather, father, child);
            child.father = grandfather;
        }
        else{
            child.father = null;
//            System.out.println("Setting new root as " + child.value);
            root = child;
        }
        father.rightSon = orphanNode;
//        System.out.println("father has right son");
        if (orphanNode != null) {
//            System.out.println("Orphan isn't null");
            orphanNode.father = father;
        }
//        System.out.println("leaving RR");
    }

    /*
    Fixes an RL violation.
     */
    private void fixRLViolation(Node father, Node child, Node grandchild){
//        System.out.println("fixing RL");
        Node orphanChild = grandchild.rightSon;
        father.rightSon = grandchild;
        grandchild.father = father;
        grandchild.rightSon = child;
        child.father = grandchild;
        child.leftSon = orphanChild;
        if (orphanChild != null) {
            orphanChild.father = child;
        }
        child.height = child.height-1;
        grandchild.height = grandchild.height+1;
        fixRRViolation(father, grandchild);
//        System.out.println("Leaving RL");
    }

    /*
    Fixes an LR violation.
     */
    private void fixLRViolation(Node father, Node child, Node grandchild){
//        System.out.println("fixing LR");
        Node orphanChild = grandchild.leftSon;
        father.leftSon = grandchild;
        grandchild.father = father;
        grandchild.leftSon = child;
        child.father = grandchild;
        child.rightSon = orphanChild;
        if (orphanChild != null) {
            orphanChild.father = child;
        }
        child.height = child.height-1;
        grandchild.height = grandchild.height+1;
        fixLLViolation(father, grandchild);
    }

    /*
    Fixes an LL violation.
     */
    private void fixLLViolation(Node father, Node child){
//        System.out.println("fixing LL");
        Node grandfather = father.father;
        Node orphanNode = child.rightSon;
        child.rightSon = father;
        father.father = child;
        father.height = father.height-2;
        if (grandfather != null){
            changeAncestorsChild(grandfather, father, child);
            child.father = grandfather;
        }
        else{
            child.father = null;
//            System.out.println("Setting new root as " + child.value);
            root = child;
        }
        father.leftSon = orphanNode;
        if (orphanNode != null) {
            orphanNode.father = father;
        }
    }

    public boolean delete(int toDelete){
        Node deleteNode = valueToNode(toDelete);
        if (deleteNode.value != toDelete){
            return false; //value is not in the tree
        }
        Node father = deleteNode.getFather();
        if (isLeaf(deleteNode)) {   //Checks if toDelete is a leaf.
            changeAncestorsChild(father, deleteNode, null);
            deleteNode = null;
            return true;
        }

        Node singleChild = singleChilded(deleteNode);
        if (singleChild != null){   //Checks if toDelete has only one chlid.
            singleChild.setFather(father);
            changeAncestorsChild(father, deleteNode, singleChild);
            deleteNode = null;
            return true;
        }

        Node successor = deleteNode.getSuccessor();
//        System.out.println("My successor is " + successor.getValue());
//        System.out.println("His father is " + successor.getFather().getValue());
//        System.out.println("Right child is " + successor.getRightSon());
//        System.out.println("Left child is " + successor.getLeftSon());
        swap(deleteNode, successor);

//        System.out.println("Now toDelete is " + deleteNode.getValue());
//        System.out.println("His father is  " + deleteNode.getFather().getValue());
//        System.out.println("Now toDelete is " + deleteNode.getValue());
        father = deleteNode.getFather();
        changeAncestorsChild(father, deleteNode, null);
        deleteNode = null;
        fixHeight(father);
        Node curNode = fixTree(father); //Re-balancing the tree.
        while (curNode != null) {
            curNode = fixTree(curNode);
        }
        return true;
    }


    /*
    Checks if two nodes are part of the same nuclear family.
     */
    private boolean sameFamily(Node node1, Node node2){
        return node1 == node2.getFather() || node2 == node1.getFather();
    }

    /*
    Swaps two nodes and adjusts their pointers.
     */
    private void swap(Node deleteNode, Node successor){
        Node deleteFather = deleteNode.getFather();
        Node deleteRightSon = deleteNode.getRightSon();
        Node deleteLeftSon = deleteNode.getLeftSon();
        Node successorFather = successor.getFather();
        if (sameFamily(deleteNode, successor)){
//            System.out.println("Same Family!");
            Node father = deleteNode;
            Node grandfather =father.getFather();
            Node child = successor;
//            System.out.println("father is " +father.getValue());
//            System.out.println("child is " +child.getValue());
            child.setFather(father.getFather());
            changeAncestorsChild(grandfather, father, child);
            if (child == father.getRightSon()) {
                father.getLeftSon().setFather(child);
                child.setLeftSon(father.getLeftSon());
                child.setRightSon(father);
            }
            else {
                father.getRightSon().setFather(child);
                child.setRightSon(father.getRightSon());
                child.setLeftSon(father);
            }
            father.setFather(child);
            father.setRightSon(child.getRightSon());
            father.setLeftSon(child.getLeftSon());
            return;
            }
        successor.setFather(deleteFather);
        changeAncestorsChild(deleteFather, deleteNode, successor);
        successor.setLeftSon(deleteLeftSon);
        successor.setRightSon(deleteRightSon);
        deleteLeftSon.setFather(successor);
        deleteRightSon.setFather(successor);
        deleteNode.setFather(successorFather);
        changeAncestorsChild(successorFather, successor, deleteNode);
        deleteNode.setLeftSon(null);
        deleteNode.setRightSon(null);
    }




    /*
    Changes the old son of an ancestor with a new son.
     */
    private void changeAncestorsChild(Node father, Node oldSon, Node newSon){
        if (father.leftSon == oldSon){
            father.leftSon = newSon;
        }
        else if (father.rightSon == oldSon){
            father.rightSon = newSon;
        }
    }

    /*
    Checks if a node is a leaf.
     */
    private boolean isLeaf(Node node){
        return node.getRightSon() == null && node.getLeftSon() == null;
    }

    /*
    Checks if a node only has one son.
    Returns the single child.
     */
    private Node singleChilded(Node node){
        if(node.leftSon != null && node.rightSon == null){
            return node.leftSon;
        }
        if (node.leftSon == null && node.rightSon != null){
            return node.rightSon;
        }
        return null;
    }

    public int contains(int searchVal){
        Node candidate = valueToNode(searchVal);
        if (candidate == null || candidate.value != searchVal) {
            return -1;
        }
        else {
            return candidate.height;
        }
    }



    /**
     * @param searchVal The value to be searched.
     * @return The expected parent node of the value if it is not in the tree, and null otherwise.
     */
    private Node expectedParent(int searchVal) {
        Node candidate = valueToNode(searchVal);
        if (candidate == null || searchVal == candidate.value) {
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
        Node currNode = root;  // The node we're comparing to, and might be returned.
        Node nextNode = root;  // The node we proceed to, or stop if it's null.
        while (nextNode != null) {
            currNode = nextNode;
            if (searchVal == currNode.value) {
                return currNode;
            }
            else if (searchVal > currNode.value) {
                nextNode = currNode.rightSon;
            }
            else {
                nextNode = currNode.leftSon;
            }
        }
        return currNode;
    }

    /**
     * Returns an ordered list of the values in the tree.
     * @return An ascending order integer list of values in the tree.
     */
    private LinkedList<Integer> getTreeList() {
        LinkedList<Integer> list = new LinkedList<>();
        Node currNode = root.getSubtreeMinNode();
        while (currNode != null) {
            list.add(currNode.value);
            currNode = currNode.getSuccessor();
        }
        return list;
    }

    /**
     * Returns an iterator for the AVL tree. The iterator
     * @return
     */
    public Iterator<Integer> iterator() {
        LinkedList<Integer> treeList = getTreeList();
        return treeList.iterator();
    }

}