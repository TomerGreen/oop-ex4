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
}