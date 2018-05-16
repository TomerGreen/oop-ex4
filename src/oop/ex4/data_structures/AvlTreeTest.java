package oop.ex4.data_structures;

import org.junit.Test;

import static org.junit.Assert.*;

public class AvlTreeTest {

    @Test
    public void testSimpleOperations() {
        AvlTree tree = new AvlTree();
        assertTrue("Contains found non-existent value", tree.contains(0) == -1);
        assertTrue("Failed to add value to an empty tree", tree.add(0));
        assertTrue("Contains failed to return 0 for root height", tree.contains(0) == 0);
        assertTrue("Contains found non-existent value", tree.contains(1) == -1);
        assertFalse("Add() returned true for existent value", tree.add(0));
        assertTrue("Failed to add non-existent value", tree.add(-1));
        assertTrue("Contains failed to return 0 for leaf height", tree.contains(-1) == 0);
        assertTrue("Contains failed to return 1 for leaf height", tree.contains(0) == 1);
        assertTrue("Failed to add non-existent value", tree.add(1));
        assertTrue("Contains failed to return 0 for leaf height", tree.contains(1) == 0);
        assertTrue("Contains failed to return 1 for root height", tree.contains(0) == 1);
        assertTrue("Failed to add non-existent value", tree.add(100));
        assertTrue("Contains found non-existent value", tree.contains(2) == -1);
        assertTrue("Contains did not return 2 for existent value depth", tree.contains(100) == 0);
        assertTrue("Contains did not return 2 for existent value depth", tree.contains(1) == 1);
        assertTrue("Contains did not return 2 for existent value depth", tree.contains(-1) == 0);
        assertTrue("Contains did not return 2 for existent value depth", tree.contains(0) == 2);

    }

    @Test
    public void testViolations(){
        AvlTree tree = new AvlTree();
        System.out.println("\n Checking RR Violation");
        //checking RR Violation
        assertTrue("Failed to add non-existent value", tree.add(1));
        assertTrue("Failed to add non-existent value", tree.add(5));
        assertTrue("Failed to add non-existent value", tree.add(10));
        assertTrue("Shouldn't be any violations!", tree.isAVLOkay(tree.getRoot()));

        System.out.println("\n Checking LL Violation");
        //checking LL Violation
        tree = new AvlTree();
        assertTrue("Failed to add non-existent value", tree.add(10));
        assertTrue("Failed to add non-existent value", tree.add(5));
        assertTrue("Failed to add non-existent value", tree.add(1));
        assertTrue("Shouldn't be any violations!", tree.isAVLOkay(tree.getRoot()));


        System.out.println("\n Checking LR Violation");
        //checking LR Violation
        tree = new AvlTree();
        assertTrue("Failed to add non-existent value", tree.add(10));
        assertTrue("Failed to add non-existent value", tree.add(7));
        assertTrue("Failed to add non-existent value", tree.add(5));
        assertTrue("Shouldn't be any violations!", tree.isAVLOkay(tree.getRoot()));

        System.out.println("\n Checking RL Violation");
        //checking RL Violation
        tree = new AvlTree();
        assertTrue("Failed to add non-existent value", tree.add(10));
        assertTrue("Failed to add non-existent value", tree.add(17));
        assertTrue("Failed to add non-existent value", tree.add(15));
        assertTrue("Shouldn't be any violations!", tree.isAVLOkay(tree.getRoot()));

    }

    @Test
    public void testFindMinAndMaxNodes() {
        System.out.println(AvlTree.findMinNodes(0));
        assertEquals(1, AvlTree.findMinNodes(0));
        assertEquals(1, AvlTree.findMaxNodes(0));
        assertEquals(2, AvlTree.findMinNodes(1));
        assertEquals(3, AvlTree.findMaxNodes(1));
        assertEquals(4, AvlTree.findMinNodes(2));
        assertEquals(7, AvlTree.findMaxNodes(2));
    }
}