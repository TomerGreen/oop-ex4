package oop.ex4.data_structures;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class BinarySearchTree implements Iterable<Integer> {

    /** The number of nodes in the tree */
    protected int size;

    /** The root node. */
    protected Node root;

    /**
     * returns the number of nodes in the tree.
     * @return The nunmber of nodes in the tree.
     */
    public int size(){
        return size;
    }

    /**
     * Returns whether a value is in the tree.
     * @param searchVal The value to be searched.
     * @return The depth of the node with that value, or -1 if it does not exist.
     */
    public abstract int contains (int searchVal);

    /**
     * Add a new node with the given key to the tree.
     * @param newValue the value of the new node to add.
     * @return true if the value to add is not already in the tree and it was successfully added,
     * false otherwise.
     */
    public abstract boolean add(int newValue);

    /**
     * Removes the node with the given value from the tree, if it exists.
     * @param toDelete The value to be deleted.
     * @return True if was successfully deleted, false otherwise.
     */
    public abstract boolean delete(int toDelete);

    /**
     * @param searchVal The value to be searched.
     * @return The expected parent node of the value if it is not in the tree, and null otherwise.
     */
    protected Node expectedParent(int searchVal) {
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
    protected Node valueToNode(int searchVal){
        Node currNode = root;  // The node we're comparing to, and might be returned.
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

    /**
     * Returns an ordered list of the values in the tree.
     * @return An ascending order integer list of values in the tree.
     */
    private LinkedList<Integer> getTreeList() {
        LinkedList<Integer> list = new LinkedList<>();
        Node currNode = root.getSubtreeMinNode();
        while (currNode != null) {
            list.add(currNode.getValue());
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
