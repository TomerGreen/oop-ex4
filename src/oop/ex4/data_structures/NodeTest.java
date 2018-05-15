package oop.ex4.data_structures;


import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class NodeTest {

    @Test
    public void createNode(){
        Node root = new Node();
        Node leaf = new Node(root);
        assertEquals("leaf's father is not right", leaf.getFather(), root);
        assertEquals("Root has a father", root.getFather(), null);
        assertTrue("Leaf has children", leaf.getLeftSon() == null && leaf.getRightSon() == null);
        assertEquals("Leaf's height isn't zero", leaf.getHeight(), 0);

    }

}