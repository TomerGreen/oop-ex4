package oop.ex4.data_structures;
import java.math.*;
import java.util.*;

import static java.lang.Math.max;

/**
 * A class representing an AVL tree.
 */
public class AvlTree extends BinarySearchTree {

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
            if (newValue > father.getValue()){
                father.setRightSon(newNode);
            }
            else{
                father.setLeftSon(newNode);
            }
//            System.out.println("Added node with value " + newValue + " before fixTree, father is " + newNode.getFather().getValue());
            fixTree(newNode);
//            System.out.println("Added node with value " + newValue + " after, father is " + newNode.getFather().getValue());
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
        if (node.getRightSon() == null){
            node.setHeight(node.getLeftSon().getHeight()+1);
        }
        else if (node.getLeftSon() == null){
            node.setHeight(node.getRightSon().getHeight()+1);
        }

        else {
            node.setHeight(max(node.getLeftSon().getHeight(), node.getRightSon().getHeight()) + 1);
        }

    }

    /*
    Climbs up from the leaf to the root and checks for violations.
    Returns the node at which the violation happened.
     */
    private Node fixTree(Node newNode){
        Node grandchild = newNode;
        Node child = newNode.getFather();
        Node father= child.getFather();
        fixHeight(child);
        if (father == null){
            return null;
        }
        while (father.getFather() != null){
            fixHeight(father);
            if (checkViolations(father, child, grandchild)){
//                System.out.println("exiting check violations..");
                return father;
            }
            grandchild = child;
            child = father;
            father = father.getFather();

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
//        System.out.println("checking node with value " + node.getValue());
        if (node.getLeftSon() != null || node.getRightSon() != null){
            if (node.getHeightDiff() > 1){
                return false;
            }
           return isAVLOkay(node.getLeftSon()) && isAVLOkay(node.getRightSon());

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
//            System.out.println("For the node " + father.getValue() + " a violation was found.");
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
        Node grandfather = father.getFather();
        Node orphanNode = child.getLeftSon();
        child.setLeftSon(father);
        father.setFather(child);
        father.setHeight(father.getHeight()-2);
        if (grandfather != null) {
            changeAncestorsChild(grandfather, father, child);
            child.setFather(grandfather);
        }
        else{
            child.setFather(null);
//            System.out.println("Setting new root as " + child.getValue());
            root = child;
        }
        father.setRightSon(orphanNode);
//        System.out.println("father has right son");
        if (orphanNode != null) {
//            System.out.println("Orphan isn't null");
            orphanNode.setFather(father);
        }
//        System.out.println("leaving RR");
    }

    /*
    Fixes an RL violation.
     */
    private void fixRLViolation(Node father, Node child, Node grandchild){
//        System.out.println("fixing RL");
        Node orphanChild = grandchild.getRightSon();
        father.setRightSon(grandchild);
        grandchild.setFather(father);
        grandchild.setRightSon(child);
        child.setFather(grandchild);
        child.setLeftSon(orphanChild);
        if (orphanChild != null) {
            orphanChild.setFather(child);
        }
        child.setHeight(child.getHeight()-1);
        grandchild.setHeight(grandchild.getHeight()+1);
        fixRRViolation(father, grandchild);
//        System.out.println("Leaving RL");
    }

    /*
    Fixes an LR violation.
     */
    private void fixLRViolation(Node father, Node child, Node grandchild){
//        System.out.println("fixing LR");
        Node orphanChild = grandchild.getLeftSon();
        father.setLeftSon(grandchild);
        grandchild.setFather(father);
        grandchild.setLeftSon(child);
        child.setFather(grandchild);
        child.setRightSon(orphanChild);
        if (orphanChild != null) {
            orphanChild.setFather(child);
        }
        child.setHeight(child.getHeight()-1);
        grandchild.setHeight(grandchild.getHeight()+1);
        fixLLViolation(father, grandchild);
    }

    /*
    Fixes an LL violation.
     */
    private void fixLLViolation(Node father, Node child){
//        System.out.println("fixing LL");
        Node grandfather = father.getFather();
        Node orphanNode = child.getRightSon();
        child.setRightSon(father);
        father.setFather(child);
        father.setHeight(father.getHeight()-2);
        if (grandfather != null){
            changeAncestorsChild(grandfather, father, child);
            child.setFather(grandfather);
        }
        else{
            child.setFather(null);
//            System.out.println("Setting new root as " + child.getValue());
            root = child;
        }
        father.setLeftSon(orphanNode);
        if (orphanNode != null) {
            orphanNode.setFather(father);
        }
    }

    public boolean delete(int toDelete){
        Node deleteNode = valueToNode(toDelete);
        if (deleteNode.getValue() != toDelete){
            return false; //value is not in the tree
        }
        Node father = deleteNode.getFather();
        if (isLeaf(deleteNode)) {
            changeAncestorsChild(father, deleteNode, null);
            deleteNode = null;
            return true;
        }

        Node child = singleChilded(deleteNode);
        if (child != null){
            child.setFather(father);
            changeAncestorsChild(father, deleteNode, child);
            deleteNode = null;
            return true;
        }

        Node successor = deleteNode.getSuccessor();
        swap(deleteNode, successor);
        father = deleteNode.getFather();
        changeAncestorsChild(father, deleteNode, null);
        fixHeight(father);
        Node curNode = fixTree(father);
        while (curNode != null) {
            curNode = fixTree(curNode);
        }
        return true;
    }

    /*
    Swaps two nodes and adjusts their pointers.
     */
    private void swap(Node node1, Node node2){
        Node node1Father = node1.getFather();
        Node node1RightSon = node1.getRightSon();
        Node node1LeftSon = node1.getLeftSon();
        Node node2Father = node2.getFather();
        Node node2RightSon = node2.getRightSon();
        Node node2LeftSon = node2.getLeftSon();

        node2.setFather(node1Father);
        changeAncestorsChild(node1Father, node1, node2);
        node2.setLeftSon(node1LeftSon);
        node1LeftSon.setFather(node2);
        node2.setRightSon(node1RightSon);
        node1RightSon.setFather(node2);

        node1.setFather(node2Father);
        changeAncestorsChild(node2Father, node2, node1);
        node1.setLeftSon(node2LeftSon);
        node2LeftSon.setFather(node1);
        node1.setRightSon(node2RightSon);
        node2RightSon.setFather(node1);


    }

    /*
    Changes the old son of an ancestor with a new son
     */
    private void changeAncestorsChild(Node father, Node oldSon, Node newSon){
        if (father.getLeftSon() == oldSon){
            father.setLeftSon(newSon);
        }
        else if (father.getRightSon() == oldSon){
            father.setRightSon(newSon);
        }
    }

    /*
    Checks if a node is a leaf.
     */
    private boolean isLeaf(Node node){
        return node.getHeight() == 0;
    }

    /*
    Checks if a node only has one son.
    Returns the single child.
     */
    private Node singleChilded(Node node){
        if(node.getLeftSon() != null && node.getRightSon() == null){
            return node.getLeftSon();
        }
        if (node.getLeftSon() == null && node.getRightSon() != null){
            return node.getRightSon();
        }
        return null;
    }

    public int contains(int searchVal){
        Node candidate = valueToNode(searchVal);
        if (candidate == null || candidate.getValue() != searchVal) {
            return -1;
        }
        else {
            return candidate.getHeight();
        }
    }

}