package oop.ex4.data_structures;
import java.math.*;
import java.util.*;

import static java.lang.Math.abs;
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
            if (newValue > father.value){
                father.rightSon = newNode;
            }
            else{
                father.leftSon = newNode;
            }
            fixTree(newNode);
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
            node.height = 0;
        }
        else if (node.rightSon == null){
            node.height = (node.leftSon.height+1);
        }
        else if (node.leftSon == null){
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
                return father;
            }
            grandchild = child;
            child = father;
            father = father.father;

        }
        fixHeight(father);
        checkViolations(father, child, grandchild);
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
            return true;
        }
        return false;
    }

    /*
    Fixes an RR violation.
     */
    private void fixRRViolation(Node father, Node child){
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
            root = child;
        }
        father.rightSon = orphanNode;
        if (orphanNode != null) {
            orphanNode.father = father;
        }
    }

    /*
    Fixes an RL violation.
     */
    private void fixRLViolation(Node father, Node child, Node grandchild){
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
    }

    /*
    Fixes an LR violation.
     */
    private void fixLRViolation(Node father, Node child, Node grandchild){
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
        Node father = deleteNode.father;
        if (isLeaf(deleteNode)) {   //Checks if toDelete is a leaf.
            changeAncestorsChild(father, deleteNode, null);
            deleteNode = null;
            size--;
            return true;
        }

        Node singleChild = singleChilded(deleteNode);
        if (singleChild != null){   //Checks if toDelete has only one chlid.
            singleChild.father = father;
            changeAncestorsChild(father, deleteNode, singleChild);
            deleteNode = null;
            size--;
            return true;
        }
        Node successor = deleteNode.getSuccessor();
        swap(deleteNode, successor);
        father = deleteNode.father;
        changeAncestorsChild(father, deleteNode, null);
        deleteNode = null;
        fixHeight(father);
        Node curNode = fixTree(father); //Re-balancing the tree.
        while (curNode != null) {
            curNode = fixTree(curNode);
        }
        size--;
        return true;
    }


}