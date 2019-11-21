package tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import exercise2.EditDistance;

public class EditDistanceDyn_Test {
	@Test
	public void testEmptyStringsEditDistance() {
		assertEquals(0, EditDistance.editDistanceDyn("", ""));
	}

	@Test
	public void testCommutativity() {
		assertEquals(EditDistance.editDistanceDyn("palla", "pallone"), EditDistance.editDistanceDyn("pallone", "palla"));
	}

	@Test
	public void testOnlyInsertionEditDistance() {
		assertEquals(5, EditDistance.editDistanceDyn("abcde", ""));
	}

	@Test
	public void testOnlyDeletionEditDistance() {
		assertEquals(5, EditDistance.editDistanceDyn("", "abcde"));
	}

	@Test
	public void testOnlyNoOpEditDistance() {
		assertEquals(0, EditDistance.editDistanceDyn("abcde","abcde"));
	}

	@Test
	public void testOnlyReplacementEditDistance() {
		assertEquals(5, EditDistance.editDistanceDyn("abcde", "fghil"));
	}

	@Test
	public void testOneSizeStringsEditDistance() {
		assertEquals(1, EditDistance.editDistanceDyn("a", "b"));
	}

	@Test
	public void testHeadReplacementEditDistance() {
		assertEquals(1, EditDistance.editDistanceDyn("ab", "bb"));
	}

	@Test
	public void testTailReplacementEditDistance() {
		assertEquals(1, EditDistance.editDistanceDyn("ba", "bb"));
	}

	@Test
	public void testHeadInsertionEditDistance() {
		assertEquals(1, EditDistance.editDistanceDyn("ab", "b"));
	}

	@Test
	public void testTailInsertionEditDistance() {
		assertEquals(1, EditDistance.editDistanceDyn("abc", "ab"));
	}

	@Test
	public void testHeadDeletionEditDistance() {
		assertEquals(1, EditDistance.editDistanceDyn("b", "ab"));
	}

	@Test
	public void testTailDeletionEditDistance() {
		assertEquals(1, EditDistance.editDistanceDyn("b", "ba"));
	}

	@Test
	public void testGenericEditDistance() {
		assertEquals(3, EditDistance.editDistanceDyn("tassa", "passato"));
	}
}
