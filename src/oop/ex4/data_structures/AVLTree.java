package oop.ex4.data_structures;


/**
 * A class representing an AVL tree.
 */
public class AVLTree {

    /** The root node. */
    protected Node root;

    public boolean add(int newValue){
        if(contains(newValue) > 0){
            Node father = getLocation(newValue);


        }
        return false; //newValue is contained in the tree.
    }


    private Node getLocation(int newValue){
        return new Node(1);
    }

    private int contains(int searchVal){
        return 1;
    }

}
