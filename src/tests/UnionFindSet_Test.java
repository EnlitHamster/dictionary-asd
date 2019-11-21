package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.junit.Before;
import exercise3.UnionFindSet;

public class UnionFindSet_Test {

	@Test
	public void testMakeSet() {
		String[] set = {"a", "b", "c", "d"};
		UnionFindSet<String> union = new UnionFindSet<>();
		assertTrue(union.makeSet(set));
		assertEquals("a", union.find("a"));
		assertEquals("b", union.find("b"));
		assertEquals("c", union.find("c"));
		assertEquals("d", union.find("d"));
	}

	@Test
	public void emptySet() {
		String[] set = {};
		UnionFindSet<String> union = new UnionFindSet<>();
		assertEquals(null, union.find("a"));
	}

	@Test
	public void testAddSet() {
		String[] set = {"a", "b", "c", "d"};
		String[] set1 = {"e", "f"};
		UnionFindSet<String> union = new UnionFindSet<>();
		union.makeSet(set1);
		assertTrue(union.makeSet(set));
		assertEquals("f", union.find("f"));
		assertEquals("e", union.find("e"));
		assertEquals("a", union.find("a"));
		assertEquals("b", union.find("b"));
		assertEquals("c", union.find("c"));
		assertEquals("d", union.find("d"));
	}

	@Test
	public void testAddElements() {
		String[] set = {"a", "b", "c", "d"};
		UnionFindSet<String> union = new UnionFindSet<>();
		union.makeSet(set);
		assertTrue(union.makeSet("e"));
		assertEquals("e", union.find("e"));
		assertEquals("a", union.find("a"));
		assertEquals("b", union.find("b"));
		assertEquals("c", union.find("c"));
		assertEquals("d", union.find("d"));
	}

	@Test
	public void testAddContainedElementsSet() {
		String[] set = {"a", "b", "c", "d"};
		String[] set1 = {"e", "b", "f", "a"};
		UnionFindSet<String> union = new UnionFindSet<>();
		union.makeSet(set);
		assertFalse(union.makeSet(set));
		assertEquals(null, union.find("e"));
		assertEquals(null, union.find("f"));
		assertEquals("a", union.find("a"));
		assertEquals("b", union.find("b"));
		assertEquals("c", union.find("c"));
		assertEquals("d", union.find("d"));
	}

	@Test
	public void testTwoElemUnion() {
		String[] set = {"a", "b", "c", "d"};
		UnionFindSet<String> union = new UnionFindSet<>(set);
		union.union("a", "b");
		assertEquals("b", union.find("a"));
		assertEquals("b", union.find("b"));
		assertEquals("c", union.find("c"));
		assertEquals("d", union.find("d"));
	}

	@Test
	public void testMultipleUnion() {
		String[] set = {"a", "b", "c", "d"};
		UnionFindSet<String> union = new UnionFindSet<>(set);
		union.union("a", "b");
		union.union("c", "d");
		union.union("a", "c");
		assertEquals("d", union.find("a"));
		assertEquals("d", union.find("b"));
		assertEquals("d", union.find("c"));
		assertEquals("d", union.find("d"));
	}

	@Test
	public void sameSetElementsUnion() {
		String[] set = {"a", "b", "c", "d"};
		UnionFindSet<String> union = new UnionFindSet<>(set);
		union.union("a", "b");
		union.union("b", "c");
		assertFalse(union.union("a", "c"));
	}

	@Test
	public void absentElementsUnion() {
		String[] set = {"a", "b"};
		UnionFindSet<String> union = new UnionFindSet<>(set);
		assertFalse(union.union("c", "a"));
		assertFalse(union.union("a", "c"));
		assertFalse(union.union("d", "c"));
	}

	@Test
	public void differentRankUnion() {
		String[] set = {"a", "b", "c", "d"};
		UnionFindSet<String> union = new UnionFindSet<>(set);
		union.union("a", "b");
		union.union("a", "c");
		union.union("d", "c");
		assertEquals("b", union.find("a"));
		assertEquals("b", union.find("b"));
		assertEquals("b", union.find("c"));
		assertEquals("b", union.find("d"));
	}

}
