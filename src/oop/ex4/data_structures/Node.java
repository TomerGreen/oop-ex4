package oop.ex4.data_structures;
import java.math.*;

import static java.lang.Math.abs;


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
     * A getter method for the node's height.
     * @return the node's height.
     */
    public int getHeight(){
        return height;
    }

    public void setHeight(int newHeight){

        height = newHeight;
    }
    /**
     * A getter method for the node's left son.
     * @return the node's left son.
     */
    public Node getLeftSon(){
        return leftSon;
    }

    /**
     * A setter mthod for the node's left son.
     * @param newValue the new son's value.
     */
    public Node setLeftSon(int newValue){
        leftSon = new Node(this, newValue);
        return leftSon;
    }

    /**
     * A setter method for the node's left son.
     * @param node the ndoe to be the new left son.
     */
    public Node setLeftSon(Node node){
        leftSon = node;
        return leftSon;
    }

    /**
     * A getter method for the node's right son.
     * @return the node's right son.
     */
    public Node getRightSon(){
        return rightSon;
    }

    /**
     * A setter method for the node's right son.
     * @param newValue the new son's value.
     */
    public Node setRightSon(int newValue){
        rightSon = new Node(this, newValue);
        return rightSon;
    }

    /**
     * A setter method for the node's right son.
     * @param node the ndoe to be the new right son.
     */
    public Node setRightSon(Node node){
        rightSon = node;
        return rightSon;
    }


    /**
     * A getter method for the node's father.
     * @return the node's father.
     */
    public Node getFather(){
        return father;
    }

    /**
     * A setter method from the node's father.
     * @param father The new father to be assigned.
     */
    public void setFather(Node father){
        this.father = father;

    }

    /**
     * A getter method for the node's numeric value.
     * @return the node's numeric value.
     */
    public int getValue(){
        return value;
    }

    /**
     * Returns the height difference between the two children of a node.
     * @return the height difference between the two children of a node.
     */
    public int getHeightDiff(){

        if(leftSon == null && rightSon == null){
//            System.out.println("Checking height diff for node " + this.getValue()  + "height diff is 0" );
            return 0;
        }
        else if(leftSon == null){
//            System.out.println("Checking height diff for node " + this.getValue()  + " height diff is " +rightSon.getHeight()+1 );
            return rightSon.getHeight()+1;
        }
        else if(rightSon == null){
//            System.out.println("Checking height diff for node " + this.getValue()  + " height diff is " + -(leftSon.getHeight()+1) );
            return leftSon.getHeight()+1;
        }
//        System.out.println("Checking height diff for node " + this.getValue()  + " height diff is " +abs(rightSon.height- leftSon.height)  );
        return abs(rightSon.height- leftSon.height);
    }
}