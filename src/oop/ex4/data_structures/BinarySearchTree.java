package oop.ex4.data_structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static java.lang.Math.abs;

/**
 * An abstract class representing a binray search tree.
 */
public abstract class BinarySearchTree implements Iterable<Integer> {


    /* ================================= BINARY NODE SUB-CLASS ================================= */

    /**
     * This class represents a node in an AVL tree.
     */
    class Node {
        /*The height and the numeric value of the node.*/
        int height, depth, value;
        /*The node's pointers to its father, left son and right son. */
        Node rightSon, leftSon, father;

        /**
         * An empty constructor of node
         * @param value the node's value.
         */
        Node(int value) {
            father = null;
            this.value = value;
            leftSon = null;
            rightSon = null;
            height = 0;
            depth = -2;  // Default value since depth is only set when value is searched.
        }

        /**
         * A constructor of Node.
         * @param father The node's father.
         * @param value The node's value.
         */
        Node(Node father, int value) {
            this.father = father;
            this.value = value;
            leftSon = null;
            rightSon = null;
            height = 0;
            depth = -2;  // Default value since depth is only set when value is searched.
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
         *
         * @return the height difference between the two children of a node.
         */
        int getHeightDiff() {
            if (leftSon == null && rightSon == null) {
                return 0;
            } else if (leftSon == null) {
                return rightSon.height + 1;
            } else if (rightSon == null) {
                return leftSon.height + 1;
            }
            return abs(rightSon.height - leftSon.height);
        }

        /**
         * Returns the node with the smallest value in the subtree of which
         * this node is the root. Note that it might be this node itself, hence null
         * should never be returned.
         *
         * @return The subtree minimal node.
         */
        Node getSubtreeMinNode() {
            Node minNode = this;
            while (minNode.leftSon != null) {
                minNode = minNode.leftSon;
            }
            return minNode;
        }

        /**
         * Returns the ancestor of this node with minimal value that is
         * bigger than that of this node. Note that this node cannot be returned.
         *
         * @return The ancestor that succeeds this node, or null if there is none.
         */
        private Node getAncestorSuccessor() {
            Node succ = this;
            while (succ.father != null) {
                Node succParent = succ.father;
                // If succ is the left son of the parent, the parent is the ancestor successor.
                if (succParent.leftSon != null && succParent.leftSon.equals(succ)) {
                    return succParent;
                } else {
                    succ = succParent;
                }
            }
            return null;
        }

        /**
         * Gets the node in the tree with the smallest value that is bigger
         * than that of the this node. Note that if this returns null than there
         * is no successor in the tree.
         *
         * @return The successor node.
         */
        Node getSuccessor() {
            // If it has a right son, return the minimal node in its subtree.
            if (this.rightSon != null) {
                return this.rightSon.getSubtreeMinNode();
            } else {
                return this.getAncestorSuccessor();
            }
        }
    }

    /* ================================= END SUB-CLASS ================================= */

    /* ======================== BINARY TREE ITERATOR SUB-CLASS ========================= */

    /**
     * Implements an iterator for an ordered binary tree.
     * The iterator iterates through integer values in ascending order.
     */
    class BinaryTreeIterator implements Iterator<Integer> {

        /**
         * The root of the tree that we iterate on.
         */
        Node treeRoot;

        /**
         * The node of which the value was returned by nex().
         */
        Node currNode;

        BinaryTreeIterator(Node treeRoot) {
            this.treeRoot = root;
            this.currNode = null;
        }

        public boolean hasNext() {
            // The next value exists unless we are on a node with no successor, or the tree is empty.
            return (!(currNode != null && currNode.getSuccessor() == null) && !(treeRoot == null));
        }

        public Integer next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            // Haven't begun iterating yet.
            else if (currNode == null) {
                currNode = treeRoot.getSubtreeMinNode();
            } else {
                currNode = currNode.getSuccessor();
            }
            return currNode.value;
        }

        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }

    /* ================================= END SUB-CLASS ================================= */

    /**
     * The number of nodes in the tree
     */
    int size;

    /**
     * The root node.
     */
    Node root;

    /**
     * returns the number of nodes in the tree.
     *
     * @return The nunmber of nodes in the tree.
     */
    public int size() {
        return size;
    }

    /**
     * Returns whether a value is in the tree.
     *
     * @param searchVal The value to be searched.
     * @return The depth of the node with that value, or -1 if it does not exist.
     */
    public int contains(int searchVal) {
        Node candidate = valueToNode(searchVal);
        if (candidate == null || candidate.value != searchVal) {
            return -1;
        } else {
            return candidate.depth;
        }
    }

    /**
     * Add a new node with the given key to the tree.
     *
     * @param newValue the value of the new node to add.
     * @return true if the value to add is not already in the tree and it was successfully added,
     * false otherwise.
     */
    public boolean add(int newValue){
        Node father = expectedParent(newValue);
        if (father != null) {
            Node newNode = new Node(father, newValue);
            if (newValue > father.value) {
                father.rightSon = newNode;
            } else {
                father.leftSon = newNode;
            }
            size++;
            return true;
        } else if (father == null && root == null) {     //The tree is empty!
            root = new Node(newValue);
            size++;
            return true;
        }
        return false; //newValue is contained in the tree.
    }


    /*
    A helper method to delete a node if it has a single child. Returns the deleted node's father.
     */
    Node singleChildDelete(Node deleteNode, Node singleChild, Node father){
        if (deleteNode == root){
            root = singleChild;
            root.father = null;
            size--;
            return null;
        }
        singleChild.father = father;
        changeAncestorsChild(father, deleteNode, singleChild);
        size--;
        return deleteNode.father;
    }

    /*
    A helper method to delete a node if it is a leaf. Returns the deleted node's father.
     */
    Node leafDelete(Node deleteNode, Node father){
        if (deleteNode == root){ //Tree only has a root
            root = null;
            size--;
            return null;
        }
        changeAncestorsChild(father, deleteNode, null);
        size--;
        return deleteNode.father;
    }

    /**
     * Removes the node with the given value from the tree, if it exists.
     * @param toDelete The value to be deleted.
     * @return True if was successfully deleted, false otherwise.
     */
    public boolean delete(int toDelete) {
        Node deleteNode = valueToNode(toDelete);
        if (deleteNode == null || deleteNode.value != toDelete) {
            return false; //value is not in the tree
        }
        Node father = deleteNode.father;
        if (isLeaf(deleteNode)) {   //Checks if toDelete is a leaf.
            leafDelete(deleteNode, father);
            return true;
        }
        Node singleChild = singleChilded(deleteNode);
        if (singleChild != null) {   //Checks if toDelete has only one child.
            singleChildDelete(deleteNode, singleChild, father);
            return true;
        }
        Node successor = deleteNode.getSuccessor();
        swap(deleteNode, successor);
        father = deleteNode.father;
        if (!isLeaf(deleteNode)){ //the original successor had a right son.
            singleChildDelete(deleteNode, deleteNode.rightSon, father);
            return true;
        }
        leafDelete(deleteNode, father);
        return true;
    }

    /**
     * Changes an ancestor's oldSon with a newSon
     *
     * @param father The ancestor Node
     * @param oldSon The old son Node
     * @param newSon The new son Node
     */
    void changeAncestorsChild(Node father, Node oldSon, Node newSon) {
        if (father.leftSon == oldSon) {
            father.leftSon = newSon;
        } else if (father.rightSon == oldSon) {
            father.rightSon = newSon;
        }
    }

    /**
     * Checks if a node is leaf.
     *
     * @param node the node to be checked.
     * @return true if leaf, false otherwise.
     */
    boolean isLeaf(Node node) {
        return node.rightSon == null && node.leftSon == null;
    }


    /**
     * Checks if a node is single-childed, if so returns the child.
     *
     * @param node The node to be checked.
     * @return The single child of the node if it has exactly one child, null otherwise.
     */
    Node singleChilded(Node node) {
        if (node.leftSon != null && node.rightSon == null) {
            return node.leftSon;
        }
        if (node.leftSon == null && node.rightSon != null) {
            return node.rightSon;
        }
        return null;
    }

    /*
    Checks if two nodes are part of the same nuclear family.
     */
    private boolean sameFamily(Node node1, Node node2) {
        return node1 == node2.father || node2 == node1.father;
    }


    /*
    Swaps two famliy members.
     */
    private void familySwap(Node father, Node child) {
        Node grandfather = father.father;
        Node rightGrandSon = child.rightSon;
        Node leftGrandSon = child.leftSon;
        child.father = grandfather;
        if (grandfather != null) {
            changeAncestorsChild(grandfather, father, child);
        }
        if (child == father.rightSon) {
            if (father.leftSon != null) {
                father.leftSon.father = child;
                child.leftSon = father.leftSon;
            }
            else{
                child.leftSon = null;
            }
            child.rightSon = father;
        } else {
            if (father.rightSon != null) {
                father.rightSon.father = child;
                child.rightSon = father.rightSon;
            }
            else{
                child.rightSon = null;
            }
            child.leftSon = father;
        }
        father.father = child;
        father.rightSon = rightGrandSon;
        father.leftSon = leftGrandSon;
        if(rightGrandSon != null) {
            rightGrandSon.father = father;
        }
        if(leftGrandSon != null) {
            leftGrandSon.father = father;
        }
        if (father == root){
            root = child;
        }
    }


    private void rootSwap(Node successor){
        successor.leftSon = root.leftSon;
        successor.rightSon = root.rightSon;
        root.leftSon.father = successor;
        root.rightSon.father = successor;
        root.father = successor.father;
        changeAncestorsChild(successor.father, successor, root);
        root.leftSon = null;
        root.rightSon = null;
        successor.father = null;
        root = successor;
    }

    /**
     * Returns the bigger son of a given father. Returns null if father has a leaf.
     * If both sons have the same height, returns the left son.
     * @param father The father node.
     * @return The bigger son, null if father is leaf.
     */
    Node getBiggerSon(Node father){
        if (isLeaf(father)){
            return null;
        }
        else if (father.rightSon == null){
            return father.leftSon;
        }
        else if (father.leftSon == null){
            return father.rightSon;
        }

        else if (father.rightSon.height > father.leftSon.height){
            return father.rightSon;
        }
        return father.leftSon;
    }

    /**
     * Swaps a node and its successor.
     * @param deleteNode a given node.
     * @param successor  its successor.
     */
    void swap(Node deleteNode, Node successor) {
        successor.height = deleteNode.height;
        if (sameFamily(deleteNode, successor)) {
            familySwap(deleteNode, successor);
            return;
        }
        if (deleteNode  == root){
            rootSwap(successor);
            return;
        }
        Node deleteFather = deleteNode.father;
        Node deleteRightSon = deleteNode.rightSon;
        Node deleteLeftSon = deleteNode.leftSon;
        Node successorFather = successor.father;
        Node successorRightSon = successor.rightSon;
        successor.father = deleteFather;
        changeAncestorsChild(deleteFather, deleteNode, successor);
        successor.leftSon = deleteLeftSon;
        successor.rightSon = deleteRightSon;
        deleteLeftSon.father = successor;
        deleteRightSon.father = successor;
        deleteNode.father = successorFather;
        changeAncestorsChild(successorFather, successor, deleteNode);
        deleteNode.leftSon = null;
        deleteNode.rightSon = successorRightSon;
        if (successorRightSon != null){
            successorRightSon.father = deleteNode;
        }

    }


    /**
     * @param searchVal The value to be searched.
     * @return The expected parent node of the value if it is not in the tree, and null otherwise.
     */
    Node expectedParent(int searchVal) {
        Node candidate = valueToNode(searchVal);
        if (candidate == null || searchVal == candidate.value) {
            return null;
        }
        return candidate;
    }

    /**
     * Returns a node with a given value if it exists, or the node that is expected to be its
     * parent if it was added to the tree (prior to rotations). The function also updates the node's
     * depth if it is found. This helper method is used by several API methods to prevent probing the
     * tree multiple times.
     *
     * @param searchVal The value to be searched.
     * @return The expected father node if searchValue is not in the tree, or the node with the same value.
     * Returns null only if the tree is empty.
     */
    Node valueToNode(int searchVal) {
        int depth = 0;
        Node currNode = root;  // The node we're comparing to, and might be returned.
        Node nextNode = root;  // The node we proceed to, or stop if it's null.
        if (root != null) {
            root.depth = 0;
        }
        while (nextNode != null) {
            currNode = nextNode;
            if (searchVal == currNode.value) {
                currNode.depth = depth;
                return currNode;
            } else {
                if (searchVal > currNode.value) {
                    nextNode = currNode.rightSon;
                } else {
                    nextNode = currNode.leftSon;
                }
                depth++;
            }
        }
        return currNode;
    }

    /**
     * Returns an iterator for the AVL tree.
     *
     * @return The iterator
     */
    public Iterator<Integer> iterator() {
        return new BinaryTreeIterator(this.root);
    }

}
