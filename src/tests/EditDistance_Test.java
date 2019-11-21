package tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import exercise2.EditDistance;

public class EditDistance_Test {
	@Test
	public void testEmptyStringsEditDistance() {
		assertEquals(0, EditDistance.editDistance("", ""));
	}

	@Test
	public void testCommutativity() {
		assertEquals(EditDistance.editDistance("palla", "pallone"), EditDistance.editDistance("pallone", "palla"));
	}

	@Test
	public void testOnlyInsertionEditDistance() {
		assertEquals(5, EditDistance.editDistance("abcde", ""));
	}

	@Test
	public void testOnlyDeletionEditDistance() {
		assertEquals(5, EditDistance.editDistance("", "abcde"));
	}

	@Test
	public void testOnlyNoOpEditDistance() {
		assertEquals(0, EditDistance.editDistance("abcde","abcde"));
	}

	@Test
	public void testOnlyReplacementEditDistance() {
		assertEquals(5, EditDistance.editDistance("abcde", "fghil"));
	}

	@Test
	public void testOneSizeStringsEditDistance() {
		assertEquals(1, EditDistance.editDistance("a", "b"));
	}

	@Test
	public void testHeadReplacementEditDistance() {
		assertEquals(1, EditDistance.editDistance("ab", "bb"));
	}

	@Test
	public void testTailReplacementEditDistance() {
		assertEquals(1, EditDistance.editDistance("ba", "bb"));
	}

	@Test
	public void testHeadInsertionEditDistance() {
		assertEquals(1, EditDistance.editDistance("ab", "b"));
	}

	@Test
	public void testTailInsertionEditDistance() {
		assertEquals(1, EditDistance.editDistance("abc", "ab"));
	}

	@Test
	public void testHeadDeletionEditDistance() {
		assertEquals(1, EditDistance.editDistance("b", "ab"));
	}

	@Test
	public void testTailDeletionEditDistance() {
		assertEquals(1, EditDistance.editDistance("b", "ba"));
	}

	@Test
	public void testGenericEditDistance() {
		assertEquals(3, EditDistance.editDistance("tassa", "passato"));
	}
}
