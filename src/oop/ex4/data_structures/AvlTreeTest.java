package oop.ex4.data_structures;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class AvlTreeTest {
    final static int[] INITIAL_ITEMS = {1,3,5, 7, 9, 10, 4, 6};

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
        assertEquals("Wrong size", 3, tree.size());
        assertTrue("Failed to add non-existent value", tree.add(17));
        assertTrue("Failed to add non-existent value", tree.add(20));
        assertTrue("Shouldn't be any violations!", tree.isAVLOkay(tree.getRoot()));
        assertTrue("Wrong root", tree.getRoot().getValue() == 5);
        assertEquals("Wrong height", tree.contains(17), 1);
        assertEquals("Wrong height", tree.contains(5), 2);
        assertEquals("Wrong height", tree.contains(10), 0);
        assertEquals("Wrong height", tree.contains(20), 0);
        assertEquals("Wrong height", tree.contains(1), 0);
        assertEquals("Wrong size", 5, tree.size());
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
        assertEquals("Wrong size", 7, tree.size());
        //Son pointers
        assertEquals("Wrong pointers", 22, tree.getRoot().getRightSon().getValue());
        assertEquals("Wrong pointers", 25, tree.getRoot().getRightSon().getRightSon().getValue());
        assertEquals("Wrong pointers", 20, tree.getRoot().getRightSon().getLeftSon().getValue());
        assertEquals("Wrong pointers", 5, tree.getRoot().getLeftSon().getValue());
        assertEquals("Wrong pointers", 10, tree.getRoot().getLeftSon().getRightSon().getValue());
        assertEquals("Wrong pointers", 1, tree.getRoot().getLeftSon().getLeftSon().getValue());
        assertEquals("Wrong pointers", null, tree.getRoot().getLeftSon().getLeftSon().getLeftSon());
        assertEquals("Wrong pointers", null, tree.getRoot().getLeftSon().getLeftSon().getRightSon());
        assertEquals("Wrong pointers", null, tree.getRoot().getLeftSon().getRightSon().getLeftSon());
        assertEquals("Wrong pointers", null, tree.getRoot().getLeftSon().getRightSon().getRightSon());
        assertEquals("Wrong pointers", null, tree.getRoot().getRightSon().getLeftSon().getLeftSon());
        assertEquals("Wrong pointers", null, tree.getRoot().getRightSon().getLeftSon().getRightSon());
        assertEquals("Wrong pointers", null, tree.getRoot().getRightSon().getRightSon().getLeftSon());
        assertEquals("Wrong pointers", null, tree.getRoot().getRightSon().getRightSon().getRightSon());

        //Father pointers
        assertEquals("Wrong pointers", null, tree.getRoot().getFather());
        assertEquals("Wrong pointers", 17, tree.getRoot().getRightSon().getFather().getValue());
        assertEquals("Wrong pointers", 17, tree.getRoot().getLeftSon().getFather().getValue());
        assertEquals("Wrong pointers", 22, tree.getRoot().getRightSon().getRightSon().getFather().getValue());
        assertEquals("Wrong pointers", 22, tree.getRoot().getRightSon().getLeftSon().getFather().getValue());
        assertEquals("Wrong pointers", 5, tree.getRoot().getLeftSon().getRightSon().getFather().getValue());
        assertEquals("Wrong pointers", 5, tree.getRoot().getLeftSon().getLeftSon().getFather().getValue());



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
    public void testDelete(){
        AvlTree tree = new AvlTree(INITIAL_ITEMS);
        assertEquals("Wrong size", 8, tree.size());
        assertEquals("Wrong height", 0, tree.contains(1) );
        assertEquals("Wrong height", 1, tree.contains(5) );
        assertEquals("Wrong height", 0, tree.contains(10) );
        assertEquals("Wrong height", 2 , tree.contains(3));
        assertEquals("Wrong height", 1, tree.contains(9));
        assertEquals("Wrong height", 3, tree.contains(7));
        assertEquals("Wrong root", 7, tree.getRoot().getValue());
        assertFalse("Deleted non-existent item", tree.delete(20));
        assertTrue("Unable to delete existent item", tree.delete(1));
        assertEquals("Wrong size", 7, tree.size());
        assertFalse("Deleted non-existent item", tree.delete(1));
        assertEquals("Contains non-existent item", -1, tree.contains(1));
        assertTrue("Unable to insert item", tree.add(1));
        assertEquals("Wrong place", 1, tree.getRoot().getLeftSon().getLeftSon().getValue());
        assertTrue("Unable to delete existent item", tree.delete(9));
        assertEquals("Wrong size", 7, tree.size());
        assertEquals("Wrong root", 7, tree.getRoot().getValue());
        assertEquals("Wrong place", 10, tree.getRoot().getRightSon().getValue());
        assertTrue("Unable to insert item", tree.add(9));
        assertEquals("Wrong size", 8, tree.size());
        assertEquals("Wrong place", 9, tree.getRoot().getRightSon().getLeftSon().getValue());
        assertTrue("Unable to delete existent item", tree.delete(3));
        assertEquals("Wrong size", 7, tree.size());
        assertEquals("Wrong place", 4, tree.getRoot().getLeftSon().getValue());
        assertEquals("Wrong place", 1, tree.getRoot().getLeftSon().getLeftSon().getValue());
        assertEquals("Wrong place", 5, tree.getRoot().getLeftSon().getRightSon().getValue());
        assertEquals("Wrong place", 6, tree.getRoot().getLeftSon().getRightSon().getRightSon().getValue());
        assertEquals("Wrong place", null, tree.getRoot().getLeftSon().getRightSon().getLeftSon());
    }



    @Test
    public void testFindMinAndMaxNodes() {
        System.out.println(AvlTree.findMinNodes(0));
        assertEquals(1, AvlTree.findMinNodes(0));
        assertEquals(2, AvlTree.findMinNodes(1));
        assertEquals(4, AvlTree.findMinNodes(2));
        assertEquals(7, AvlTree.findMinNodes(3));
        assertEquals(54, AvlTree.findMinNodes(7));
        assertEquals(4180, AvlTree.findMinNodes(16));
        assertEquals(317810, AvlTree.findMinNodes(25));
        assertEquals(433494436, AvlTree.findMinNodes(40));
        assertEquals(1, AvlTree.findMaxNodes(0));
        assertEquals(3, AvlTree.findMaxNodes(1));
        assertEquals(7, AvlTree.findMaxNodes(2));
        assertEquals(15, AvlTree.findMaxNodes(3));
        assertEquals(255, AvlTree.findMaxNodes(7));
        assertEquals(131071, AvlTree.findMaxNodes(16));
        assertEquals(67108863, AvlTree.findMaxNodes(25));
    }

    private void compareToRandomSet(int size) {
        TreeSet<Integer> intSet = new TreeSet<>();
        Random random = new Random();
        for (int i=0; i < size; i++) {
            intSet.add(random.nextInt());
        }
        int[] intArray = new int[intSet.size()];
        int index = 0;
        for (int value : intSet) {
            intArray[index] = value;
            index++;
        }
        AvlTree tree = new AvlTree(intArray);
        Iterator<Integer> treeIterator = tree.iterator();
        Iterator<Integer> setIterator = intSet.iterator();
        while (treeIterator.hasNext() || setIterator.hasNext()) {
            assertEquals(setIterator.next(), treeIterator.next());
        }
    }

    @Test
    public void testIterator() {
        int SMALL_SET_SIZE = 5;
        int LARGE_SET_SIZE = 100;
        compareToRandomSet(SMALL_SET_SIZE);
        compareToRandomSet(LARGE_SET_SIZE);
    }
}