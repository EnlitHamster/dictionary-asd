package exercise2;

public class EditDistance {
	private static final int INFINITE = Integer.MAX_VALUE;
	private static final int NIL = -1;

	/**
	 * objective and source strings for both edit distances algorithms
	 */
	private static String o, s;

	/**
	 * Memoization matrix used by the dynamic version of the edit distance
	 * algorithm
	 */
	private static int[][] c;

	/**
	 * Computes the minimum of variable number of integers
	 *
	 * @param N -> list of numbers
	 * @return minimum in N
	 */
	private static int min(int ... N) {
		int m = INFINITE;
		for (int n : N)
			m = n < m ? n : m;
		return m;
	}

	/**
	 * Fills in the 2D matrix with a given value
	 *
	 * @param m -> matrix to fill
	 * @param value -> number to fill with
	 */
	private static void fillMatrix(int[][] m, int value) {
		for(int i = 0; i < m.length; ++i)
			for(int j = 0; j < m[i].length; ++j)
				m[i][j] = value;
	}

	/**
	 * Computes the minimum Levenshtein's distance to transform src in obj.
	 * Uses the normal DI recursive programming. O(2^n)
	 *
	 * @param i -> position reached in obj
	 * @param j -> position reached in src
	 * @return minimum Levenshtein's distance between obj & src
	 */
	private static int editDistance(int i, int j) {
		if (i == o.length()) return s.length() - j;
		else if (j == s.length()) return o.length() - i;
		else {
			// Let L(o,s) be the Levenshtein distance function and r_o and r_s
			// the substrings of o and s without their leading character. Then:
			int
				// Replace distance = L(r_o,r_s) + 1 * d		w/ d = o_0 == s_0
				dRepl = editDistance(i + 1, j + 1) + (o.charAt(i) == s.charAt(j) ? 0 : 1),
				// Cancel distance = L(o,r_s) + 1
				dCanc = 1 + editDistance(i, j + 1),
				// Insertion distance = L(r_o,s) + 1
				dInsr = 1 + editDistance(i + 1, j);

			// ============================================================
			// the No Operation distance has been included in the Replace
			// distance, because the former is a special case of the latter
			//
			// NoOp = Repl - 1 => Repl = L(r_o,r_s) + 1 * d (d = 0 if NoOp)
			// ============================================================

			return min(dCanc, dInsr, dRepl);
		}
	}

	/**
	 * Computes the minimum Levenshtein's distance to transform src in obj.
	 * Uses the normal recursive dynamic programming. O(n^2)
	 *
	 * @param i -> position reached in obj
	 * @param j -> position reached in src
	 * @return minimum Levenshtein's distance between obj & src
	 */
	private static int editDistanceDyn(int i, int j) {
		if(i == o.length()) return s.length() - j;
		else if(j == s.length()) return o.length() - i;
		else if (c[i][j] == NIL) {
			//Then the edit distance hasn't been computed yet

			// Let L(o,s) be the Levenshtein distance function and r_o and r_s
			// the substrings of o and s without their leading character. Then:
			int
				// Replace distance = L(r_o,r_s) + 1 * d		w/ d = o_0 == s_0
				dRepl = editDistanceDyn(i + 1, j + 1) + (o.charAt(i) == s.charAt(j) ? 0 : 1),
				// Cancel distance = L(o,r_s) + 1
				dCanc = 1 + editDistanceDyn(i, j + 1),
				// Insertion distance = L(r_o,s) + 1
				dInsr = 1 + editDistanceDyn(i + 1, j);

			// ============================================================
			// the No Operation distance has been included in the Replace
			// distance, because the former is a special case of the latter
			//
			// NoOp = Repl - 1 => Repl = L(r_o,r_s) + 1 * d (d = 0 if NoOp)
			// ============================================================

			c[i][j] = min(dCanc, dInsr, dRepl);//Saving the edit distance between Oi, Si
		}

		return c[i][j];
	}

	/**
	 * Wrapper method that sets up the variables for the execution of the
	 * recursive version of the Levenshtein distance's algorithm
	 *
	 * @param obj -> objective sequence
	 * @param src -> source sequence
	 * @return minimum Levenshtein's distance between obj & src
	 */
	public static int editDistance(String obj, String src) {
		if (obj.equals(src)) return 0;

		o = obj;
		s = src;

		return editDistance(0, 0);
	 }

 	/**
 	 * Wrapper method that sets up the variables for the execution of the
 	 * dynamic programming version of the Levenshtein distance's algorithm
 	 *
 	 * @param obj -> objective string
 	 * @param src -> source string
 	 * @return minimum Levenshtein's distance between obj & src
 	 */
 	public static int editDistanceDyn(String obj, String src) {
 		if (obj.equals(src)) return 0;

 		c = new int[obj.length()][src.length()];
 		fillMatrix(c, NIL);
 		o = obj;
 		s = src;

 		return editDistanceDyn(0, 0);
 	}
}
