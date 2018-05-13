/**
 * This class represents a node in an AVL tree.
 */
public class Node {
    /*The height of the node.*/
    private int height;
    /*The node's pointers to its father, left son and right son. */
    private Node rightSon, leftSon, father;


    /**
     * An Empty constructor of Node.
     */
    Node(){
        this.father = null;
        leftSon = null;
        rightSon = null;
        height = 0;
    }

    /**
     * A constructor of Node.
     * @param father The node's father.
     */
    Node(Node father){
        this.father = father;
        leftSon = null;
        rightSon = null;
        this.height = 0;
    }

    /**
     * A getter function for the node's height.
     * @return the node's height.
     */
    public int getHeight(){
        return height;
    }

    /**
     * A getter function for the node's left son.
     * @return the node's left son.
     */
    public Node getLeftSon(){
        return leftSon;
    }

    /**
     * A getter function for the node's right son.
     * @return the node's right son.
     */
    public Node getRightSon(){
        return rightSon;
    }

    /**
     * A getter function for the node's father.
     * @return the node's father.
     */
    public Node getFather(){
        return father;
    }
}