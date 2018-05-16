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
        assertTrue("Wrong root", tree.getRoot().getValue() == 1);
        assertTrue("Failed to add non-existent value", tree.add(5));
        assertTrue("Failed to add non-existent value", tree.add(10));
        assertTrue("Shouldn't be any violations!", tree.isAVLOkay(tree.getRoot()));
        assertTrue("Wrong root", tree.getRoot().getValue() == 5);
        assertTrue("Wrong child", tree.getRoot().getRightSon().getValue() == 10);
        assertTrue("Wrong child", tree.getRoot().getLeftSon().getValue() == 1);
        assertEquals("Wrong height", tree.contains(10), 0);
        assertEquals("Wrong height", tree.contains(1), 0);
        assertEquals("Wrong height", tree.contains(5), 1);
        assertTrue("Failed to add non-existent value", tree.add(17));
        assertTrue("Failed to add non-existent value", tree.add(20));
        assertTrue("Shouldn't be any violations!", tree.isAVLOkay(tree.getRoot()));
        assertTrue("Wrong root", tree.getRoot().getValue() == 5);
        assertEquals("Wrong height", tree.contains(17), 1);
        assertEquals("Wrong height", tree.contains(5), 2);
        assertEquals("Wrong height", tree.contains(10), 0);
        assertEquals("Wrong height", tree.contains(20), 0);
        assertEquals("Wrong height", tree.contains(1), 0);
        //Adding an RL violation
        assertTrue("Failed to add non-existent value", tree.add(25));
        assertTrue("Failed to add non-existent value", tree.add(22));
        assertTrue("Shouldn't be any violations!", tree.isAVLOkay(tree.getRoot()));
        assertTrue("Wrong root", tree.getRoot().getValue() == 17);
        assertEquals("Wrong height", tree.contains(5), 1);
        assertEquals("Wrong height", tree.contains(1), 0);
        assertEquals("Wrong height", tree.contains(10), 0);
        assertEquals("Wrong height", tree.contains(20), 0);
        assertEquals("Wrong height", tree.contains(25), 0);
        assertEquals("Wrong height", tree.contains(22), 1);
        assertEquals("Wrong pointers", 22, tree.getRoot().getRightSon().getValue());
        assertEquals("Wrong pointers", 25, tree.getRoot().getRightSon().getRightSon().getValue());
        assertEquals("Wrong pointers", 20, tree.getRoot().getRightSon().getLeftSon().getValue());
        assertEquals("Wrong pointers", 5, tree.getRoot().getLeftSon().getValue());
        assertEquals("Wrong pointers", 10, tree.getRoot().getLeftSon().getRightSon().getValue());
        assertEquals("Wrong pointers", 1, tree.getRoot().getLeftSon().getLeftSon().getValue());




        System.out.println("\n Checking LL Violation");
        //checking LL Violation
        tree = new AvlTree();
        assertTrue("Failed to add non-existent value", tree.add(100));
        assertTrue("Failed to add non-existent value", tree.add(50));
        assertTrue("Failed to add non-existent value", tree.add(10));
        assertTrue("Shouldn't be any violations!", tree.isAVLOkay(tree.getRoot()));
        assertTrue("Wrong root", tree.getRoot().getValue() == 50);
        assertTrue("Wrong child", tree.getRoot().getRightSon().getValue() == 100);
        assertTrue("Wrong child", tree.getRoot().getLeftSon().getValue() == 10);
        assertEquals("Wrong height", tree.contains(100), 0);
        assertEquals("Wrong height", tree.contains(10), 0);
        assertEquals("Wrong height", tree.contains(50), 1);
        assertTrue("Failed to add non-existent value", tree.add(7));
        assertTrue("Failed to add non-existent value", tree.add(2));
        assertTrue("Shouldn't be any violations!", tree.isAVLOkay(tree.getRoot()));
        assertTrue("Wrong root", tree.getRoot().getValue() == 50);
        assertEquals("Wrong height", tree.contains(7), 1);
        assertEquals("Wrong height", tree.contains(50), 2);
        assertEquals("Wrong height", tree.contains(10), 0);
        assertEquals("Wrong height", tree.contains(100), 0);
        assertEquals("Wrong height", tree.contains(2), 0);


        System.out.println("\n Checking LR Violation");
        //checking LR Violation
        tree = new AvlTree();
        assertTrue("Failed to add non-existent value", tree.add(10));
        assertTrue("Failed to add non-existent value", tree.add(7));
        assertTrue("Failed to add non-existent value", tree.add(5));
        assertTrue("Shouldn't be any violations!", tree.isAVLOkay(tree.getRoot()));
        assertTrue("Wrong root", tree.getRoot().getValue() == 7);
        assertTrue("Wrong child", tree.getRoot().getRightSon().getValue() == 10);
        assertTrue("Wrong child", tree.getRoot().getLeftSon().getValue() == 5);
        assertEquals("Wrong height", tree.contains(7), 1);
        assertEquals("Wrong height", tree.contains(10), 0);
        assertEquals("Wrong height", tree.contains(5), 0);


        System.out.println("\n Checking RL Violation");
        //checking RL Violation
        tree = new AvlTree();
        assertTrue("Failed to add non-existent value", tree.add(10));
        assertTrue("Failed to add non-existent value", tree.add(17));
        assertTrue("Failed to add non-existent value", tree.add(15));
        assertTrue("Shouldn't be any violations!", tree.isAVLOkay(tree.getRoot()));
        assertTrue("Wrong root", tree.getRoot().getValue() == 15);
        assertTrue("Wrong child", tree.getRoot().getRightSon().getValue() == 17);
        assertTrue("Wrong child", tree.getRoot().getLeftSon().getValue() == 10);
        assertEquals("Wrong height", tree.contains(15), 1);
        assertEquals("Wrong height", tree.contains(10), 0);
        assertEquals("Wrong height", tree.contains(17), 0);
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