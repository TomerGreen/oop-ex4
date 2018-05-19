package oop.ex4.data_structures; /**
 * Tester for ex4.
 * Created by:Evyatar
 * version 1.2
 * <p>
 * You are more then welcome to improve the tester - add tests, fix bugs, improve the current ones or make
 * the tester more accessible. If you do so, update the changelog below.
 * <p>
 * Changelog:
 * 1.0 - 17/5/18 - Evyatar - Created.
 * 1.1                     - Removed test 8. add some sub-tests.
 * 1.2                     - Added test 8 (spacial cases).
 */

// we have 8 tests:

//test1:
//check that the tree acts like a set (add, delete, contains, size... uses constant ints. also check
// AvlTree(AvlTree tree) constructor, and check if the iterator works correct.
// you can change the number of iterations.

//test2:
//like test 1 but with random numbers

//test3-5:
//each of them, does a groupe of sub-tests. is each sub-test, we generate an AvlTree, add a some ints,
// delete some ints, add again and delete again. after that, we check that every node is in the correct depth.

//test6-7:
//findMinNodes and findMaxNodes (you should use simple formula for both of them)

//test8:
//end cases that might or should throw exceptions.


import oop.ex4.data_structures.AvlTree;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
//package oop.ex4.data_structures;


public class Tester_oop_ex4 {

	@Test
	public void test_1_tree_acts_as_sorted_set() {
		final int size = 30000;
		AvlTree tree = new AvlTree();
		AvlTree secondTree;
		SortedSet<Integer> set = new TreeSet<>();
		for (int i = 0; i <= size / 2; i++) {
			// multiply by a prime and than modulo to shuffled the parameters.
			int x = (i * 7919) % size;
			assertEquals("problem with addition of " + x, set.add(x), tree.add(x));
		}
		secondTree = new AvlTree(tree);

		for (int i = 0; i <= size / 2; i++) {
			//this time a different prime
			int x = (i * 6607) % size;
			assertEquals("problem with depth of " + x, set.contains(x), tree.contains(x) >= 0);
			assertEquals("problem with depth of " + x, set.contains(x), secondTree.contains(x) >= 0);
		}

		Iterator<Integer> treeIter = tree.iterator();
		Iterator<Integer> secondTreeIter = secondTree.iterator();
		Iterator<Integer> setIter = set.iterator();


		while (treeIter.hasNext() || setIter.hasNext() || secondTreeIter.hasNext()) {
			Integer next = setIter.next();
			assertEquals("The iterator is not sorted", next, treeIter.next());
			assertEquals("The iterator is not sorted", next, secondTreeIter.next());
		}

		for (int i = 0; i <= size / 2; i++) {
			int x = (i * 1153) % size;
			boolean b = set.remove(x);
			assertEquals("problem with deletion of " + x, b, tree.delete(x));
			assertEquals("problem with deletion of " + x, b, secondTree.delete(x));
		}
		assertEquals(set.size(), tree.size());
		assertEquals(set.size(), secondTree.size());
	}

	@Test
	public void test_2_tree_acts_as_sorted_set_random() {
		final int size = 30000;
		AvlTree tree = new AvlTree();
		AvlTree secondTree;
		SortedSet<Integer> set = new TreeSet<>();
		Random random = new Random();
		for (int i = 0; i <= size; i++) {
			int x = random.nextInt(size);
			assertEquals("problem with addition of " + x, set.add(x), tree.add(x));
		}

		secondTree = new AvlTree(tree);

		for (int i = 0; i <= size; i++) {
			int x = random.nextInt(size);
			assertEquals("problem with depth of " + x, set.contains(x), tree.contains(x) >= 0);
			assertEquals("problem with depth of " + x, set.contains(x), secondTree.contains(x) >= 0);

		}

		Iterator<Integer> treeIter = tree.iterator();
		Iterator<Integer> secondTreeIter = secondTree.iterator();
		Iterator<Integer> setIter = set.iterator();

		while (treeIter.hasNext() || setIter.hasNext() || secondTreeIter.hasNext()) {
			Integer next = setIter.next();
			assertEquals("The iterator is not sorted", next, treeIter.next());
			assertEquals("The iterator is not sorted", next, secondTreeIter.next());
		}

		for (int i = 0; i <= size; i++) {
			int x = random.nextInt(size);
			boolean b = set.remove(x);
			System.out.println("Trying deletion " + i + "/" + size);
			assertEquals("problem with deletion of " + x, b, tree.delete(x));
			assertEquals("problem with deletion of " + x, b, secondTree.delete(x));

		}
		assertEquals(set.size(), tree.size());
		assertEquals(set.size(), secondTree.size());
	}

	@Test
	public void test_3_just_add() {
		for (int i = 1; i <= 10; i++) {
			doSubTest(getDataToTest(i));
			doSubTestAddInConstructor(getDataToTest(i));
		}

	}

	@Test
	public void test_4_add_and_delete() {
		for (int i = 11; i <= 19; i++) {
			doSubTest(getDataToTest(i));
			doSubTestAddInConstructor(getDataToTest(i));
		}

	}

	@Test
	public void test_5_very_hard() {
		for (int i = 20; i <= 22; i++) {
			doSubTest(getDataToTest(i));
			doSubTestAddInConstructor(getDataToTest(i));
		}

	}


	@Test
	public void test_6_findMinNodes() {
		assertEquals(1, AvlTree.findMinNodes(0));
		assertEquals(2, AvlTree.findMinNodes(1));
		assertEquals(4, AvlTree.findMinNodes(2));
		assertEquals(7, AvlTree.findMinNodes(3));
		assertEquals(54, AvlTree.findMinNodes(7));
		assertEquals(4180, AvlTree.findMinNodes(16));
		assertEquals(317810, AvlTree.findMinNodes(25));
		assertEquals(433494436, AvlTree.findMinNodes(40));
	}

	@Test
	public void test_7_findMaxNodes() {

		assertEquals(1, AvlTree.findMaxNodes(0));
		assertEquals(3, AvlTree.findMaxNodes(1));
		assertEquals(7, AvlTree.findMaxNodes(2));
		assertEquals(15, AvlTree.findMaxNodes(3));
		assertEquals(255, AvlTree.findMaxNodes(7));
		assertEquals(131071, AvlTree.findMaxNodes(16));
		assertEquals(67108863, AvlTree.findMaxNodes(25));
	}

	@Test
	public void test_8_spacial_cases() {
		// empty iterator
		AvlTree tree = new AvlTree();
		for (Iterator<Integer> it = tree.iterator(); it.hasNext(); ) {
			fail("iterator should be empty");
		}
		for (int ignored : tree) {
			fail("iterator should be empty");
		}

		// iter exceptions
		Iterator<Integer> iterator = tree.iterator();
		try {
			iterator.next();
			fail("an exceptions should has been thrown");
		}
		catch (java.util.NoSuchElementException e){
			// good job! you should throw this exception
		}
		catch (Exception e){
			fail("wrong exception, the exception type should be 'NoSuchElementException");
		}
		try {
			iterator.remove();
			fail("an exceptions should has been thrown");
		}
		catch (java.lang.UnsupportedOperationException e){
			// good job! you should throw this exception
		}
		catch (Exception e){
			fail("wrong exception, the exception type should be 'UnsupportedOperationException");
		}

		// the following lines should work without exceptions (exceptions would change the API)

		// null array
		int[] emptyArr = {};
		AvlTree tree0 = new AvlTree(emptyArr);

		// null array
		int[] thisIsNull = null;
		AvlTree tree1 = new AvlTree(thisIsNull);

		// null AvlTree
		AvlTree treeNull = null;
		AvlTree tree2 = new AvlTree(treeNull);

		// empty tree in constructor
		AvlTree tree3 = new AvlTree(tree);
	}


	// --------------------------------------helper functions------------------------------------
	/*
	insert an index and returns that test data for that index (1-21)
	 */
	private TestData getDataToTest(int i) {
		TestData testData = new TestData(i);
		int[] add;
		int[] depth;
		int[] delete;
		switch (i) {
			case 1:
				add = new int[]{};
				depth = new int[]{};
				delete = new int[]{};
				testData.insertAddMoves(add, depth);
				testData.insertDeleteMoves(delete);
				break;
			case 2:
				add = new int[]{5};
				depth = new int[]{0};
				testData.insertAddMoves(add, depth);
				break;
			case 3:
				add = new int[]{1, 2};
				depth = new int[]{0, 1};
				testData.insertAddMoves(add, depth);
				break;
			case 4:
				add = new int[]{5, 3, 2};
				depth = new int[]{1, 0, 1};
				testData.insertAddMoves(add, depth);
				break;

			case 5:
				add = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
				depth = new int[]{2, 1, 2, 0, 2, 1, 3, 2, 3};
				testData.insertAddMoves(add, depth);
				break;

			case 6:
				add = new int[]{10, 20, 30, 40, 35};
				depth = new int[]{1, 0, 2, 2, 1};
				testData.insertAddMoves(add, depth);
				break;

			case 7:
				add = new int[]{10, 20, 30, 40, 35, 9, 5, 7, 6};
				depth = new int[]{2, 0, 2, 2, 1, 1, 3, 3, 2};
				testData.insertAddMoves(add, depth);
				break;

			case 8:
				add = new int[]{10, 20, 30, 40, 35, 9, 5, 7, 6, 13, 14, 4};
				depth = new int[]{3, 1, 3, 3, 2, 0, 2, 2, 1, 2, 3, 3};
				testData.insertAddMoves(add, depth);
				break;

			// false add
			case 9:
				add = new int[]{5, 3, 2, 5, 3, 2};
				depth = new int[]{1, 0, 1, 1, 0, 1};
				testData.insertAddMoves(add, depth);
				break;
			case 10:
				add = new int[]{10, 20, 30, 40, 35, 9, 5, 10, 30, 7, 6, 13, 14, 4};
				depth = new int[]{3, 1, 3, 3, 2, 0, 2, 3, 3, 2, 1, 2, 3, 3};
				testData.insertAddMoves(add, depth);
				break;

			// delete
			case 11:
				delete = new int[]{10, 20, 30, 40, 35, 9, 5, 7, 6, 13, 14, 4};
				testData.insertDeleteMoves(delete);
				break;

			case 12:
				add = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
				depth = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};
				testData.insertAddMoves(add, depth);
				delete = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
				testData.insertDeleteMoves(delete);
				break;
			case 13:
				add = new int[]{1, 2, 3, 4, 5, 6};
				depth = new int[]{2, 1, -1, 0, 1, 2};
				testData.insertAddMoves(add, depth);
				delete = new int[]{3};
				testData.insertDeleteMoves(delete);
				break;
			case 14:
				add = new int[]{1, 2, 3, 4, 5, 6, 0};
				depth = new int[]{1, 0, 2, 1, 2, -1, 2};
				testData.insertAddMoves(add, depth);
				delete = new int[]{6};
				testData.insertDeleteMoves(delete);
				break;
			case 15:
				add = new int[]{1, 2, 3, 4, 5, 6, 0};
				depth = new int[]{1, 0, 2, 1, -1, 2, 2};
				testData.insertAddMoves(add, depth);
				delete = new int[]{5};
				testData.insertDeleteMoves(delete);
				break;
			case 16:
				add = new int[]{1, 2, 3, 4, 5, 6, 0};
				depth = new int[]{1, -1, 2, 0, 1, 2, 2};
				testData.insertAddMoves(add, depth);
				delete = new int[]{2};
				testData.insertDeleteMoves(delete);
				break;
			case 17:
				add = new int[]{1, 2, 3, 4, 5, 6, 0, 0, 1, 2, 3, 4, 5, 6};
				depth = new int[]{1, -1, 2, 0, 1, 2, 2, 2, 1, -1, 2, 0, 1, 2};
				testData.insertAddMoves(add, depth);
				delete = new int[]{2};
				testData.insertDeleteMoves(delete);
				break;
			case 18:
				add = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
				depth = new int[]{2, 1, 2, 0, 2, -1, 1, -1, -1, -1, 2, -1};
				testData.insertAddMoves(add, depth);
				delete = new int[]{8, 9, 6, 10, 12, 6, 10, 6};
				testData.insertDeleteMoves(delete);
				break;
			case 19:
				add = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
				depth = new int[]{1, 2, -1, 0, 3, -1, 2, 1, 3, -1, 2, -1};
				testData.insertAddMoves(add, depth);
				delete = new int[]{8, 9, 6, 10, 12, 6, 10, 6};
				testData.insertDeleteMoves(delete);
				add = new int[]{100, 0, 8, 9};
				depth = new int[]{3, 2, 1, 3};
				testData.insertAddMoves(add, depth);
				delete = new int[]{3};
				testData.insertDeleteMoves(delete);
				break;
			case 20:
				add = new int[]{256, 566, 798, 934, 409, 804, 570, 892};
				depth = new int[]{1, 0, -1, 2, -1, 1, 2, 3};
				testData.insertAddMoves(add, depth);
				delete = new int[]{798};
				testData.insertDeleteMoves(delete);
				add = new int[]{657, 290, 32};
				depth = new int[]{3, 2, 2};
				testData.insertAddMoves(add, depth);
				delete = new int[]{409};
				testData.insertDeleteMoves(delete);
				break;

			case 21:
				add = new int[]{10, 20, 30, 40, 50, 60, 70, 80, 45, 44};
				depth = new int[]{2, 1, 2, 0, 3, 1, 2, 3, 2, 3};
				testData.insertAddMoves(add, depth);
				break;

			case 22:
				add = new int[]{256, 566, 798, 934, 409, 804, 570, 892, 5, 43, 339, 445, 133, 532, 168, 720,
						894, 817, 538, 442, 343, 201, 249, 91, 348, 169, 139, 766, 186, 637};
				depth = new int[]{0, 1, -1, -1, 2, 3, 4, -1, 3, 2, 5, -1, 4, -1, -1, 2, -1, -1, 4, -1, 3,
						3, 4, 3, 4, 1, -1, 4, 4, -1};
				testData.insertAddMoves(add, depth);
				delete = new int[]{894, 593, 892, 483, 445, 653, 532, 416, 445, 471, 817, 316, 934, 765};
				testData.insertDeleteMoves(delete);
				add = new int[]{932, 73, 428, 572, 17, 577, 61, 526, 176, 749, 170, 1, 791, 946, 739, 306,
						978, 173, 345, 569, 956, 415, 850, 926, 286, 180, 648, 25, 280, 790};
				depth = new int[]{4, 4, 4, 3, 4, 5, 5, 3, 2, 5, 3, 4, 5, 6, 6, 4, 6, 4, 5, 5, 5, 5, 5, 6, 5, 5, 4, 5, 6, 6};
				testData.insertAddMoves(add, depth);
				delete = new int[]{798, 639, 892, 742, 168, 536, 442, 738, 442, 654, 139, 282, 637, 704};
				testData.insertDeleteMoves(delete);
				break;

		}
		return testData;
	}

	/*
	 * test the Avltree with the given testData, as explained at top of document.
	 * @param testData the testData
	 */
	private void doSubTest(TestData testData) {
		String errMsgStart = "\nproblem with testData " + testData.id + ".\n";
		String errMsg;
		AvlTree tree = new AvlTree();
		HashSet<Integer> hashSet = new HashSet<>();
		HashSet<Integer> hashSet2 = new HashSet<>();

		errMsg = errMsgStart + "the size of the tree should be " + hashSet.size() + "...";
		assertEquals(errMsg, hashSet.size(), tree.size());
		for (Move move : testData) {
			switch (move.type) {
				case Move.ADD:
					errMsg = errMsgStart + "trying to add " + move.data + "...";
					assertEquals(errMsg, hashSet.add(move.data), tree.add(move.data));
					break;
				case Move.DELETE:
					errMsg = errMsgStart + "trying to delete " + move.data + "...";
					assertEquals(errMsg, hashSet.remove(move.data), tree.delete(move.data));
					break;
			}
			errMsg = errMsgStart + "the size of the tree should be " + hashSet.size() + "...";
			assertEquals(errMsg, hashSet.size(), tree.size());
		}
		for (Move move : testData) {
			if (hashSet2.contains(move.data)) continue;
			errMsg = errMsgStart + "the depth of " + move.data + " should be " + move.depth + "...";
			assertEquals(errMsg, move.depth, tree.contains(move.data));

			errMsg = errMsgStart + "the size of the tree should be " + hashSet.size() + "...";
			assertEquals(errMsg, hashSet.size(), tree.size());
			hashSet2.add(move.data);
		}

	}

	/*
	 * test the Avltree with the given testData, as explained at top of document. add some of the testData in
	 * the constrictor.
	 * @param testData the testData
	 */
	private void doSubTestAddInConstructor(TestData testData) {
		String errMsgStart = "\nproblem with testData " + testData.id + ".\n";
		String errMsg;
		AvlTree tree = new AvlTree(testData.addArr);
		HashSet<Integer> hashSet = new HashSet<Integer>();
		HashSet<Integer> all_nums_that_have_been_checked = new HashSet<>();

		if (testData.addArr != null) {
			for (int num : testData.addArr) {
				hashSet.add(num);
			}
		}
		for (Move move : testData) {
			switch (move.type) {
				case Move.ADD:
					errMsg = errMsgStart + "trying to add " + move.data + "...";
					assertEquals(errMsg, hashSet.add(move.data), tree.add(move.data));
					break;
				case Move.DELETE:
					errMsg = errMsgStart + "trying to delete " + move.data + "...";
					assertEquals(errMsg, hashSet.remove(move.data), tree.delete(move.data));
					break;
			}
			errMsg = errMsgStart + "the size of the tree should be " + hashSet.size() + "...";
			assertEquals(errMsg, hashSet.size(), tree.size());
		}
		for (Move move : testData) {
			if (all_nums_that_have_been_checked.contains(move.data)) continue;
			errMsg = errMsgStart + "the depth of " + move.data + " should be " + move.depth + "...";
			assertEquals(errMsg, move.depth, tree.contains(move.data));

			errMsg = errMsgStart + "the size of the tree should be " + hashSet.size() + "...";
			assertEquals(errMsg, hashSet.size(), tree.size());
			all_nums_that_have_been_checked.add(move.data);
		}

	}


	// --------------------------------------helper classes------------------------------------

	/*
	represent one move (delete an int or delete an int) and hold the depth that should be for that node.
	 */
	private class Move {
		static final int ADD = 0, DELETE = 1;
		int type;
		int data;
		int depth;

		void setAdd(int data, int depth) {
			this.type = ADD;
			this.data = data;
			this.depth = depth;
		}

		void setDelete(int data) {
			this.type = DELETE;
			this.data = data;
			this.depth = -1;
		}

		void setSize(int data) {
			this.data = data;
		}
	}

	/*
	holds a list of moves to be done in a test.
	 */
	private class TestData implements Iterable<Move> {
		LinkedList<Move> moves;
		int[] addArr;

		int id;

		TestData(int id) {
			moves = new LinkedList<>();
			this.id = id;
			addArr = null;
		}

		void insertAddMoves(int[] add, int[] depth) {
			if (add.length != depth.length) throw new IllegalArgumentException();
			for (int i = 0; i < add.length; i++) {
				Move move = new Move();
				move.setAdd(add[i], depth[i]);
				moves.add(move);
			}
			if (addArr != null) {
				addArr = add;
			}
		}

		void insertDeleteMoves(int[] delete) {
			for (int i = 0; i < delete.length; i++) {
				Move move = new Move();
				move.setDelete(delete[i]);
				moves.add(move);
			}
		}

		@Override
		public Iterator<Move> iterator() {
			return moves.iterator();
		}
	}
}
