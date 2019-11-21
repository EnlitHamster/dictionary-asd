package exercise3;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
public class UnionFindSet<T> {

	Map<T, Pair<T, Integer>> partition;

	/**
   * Create an HashMap and ArrayList with the key of the same type of T
	 * This must be O(n)
	 */
	public UnionFindSet() {
		partition = new HashMap<T, Pair<T, Integer>>();
	}

	/**
   * Create an HashMap and ArrayList with the key of the same type of T
	 * This must be O(n)
	 * @param U the set that is used to initialize the UnionFindSet
	 */
	public UnionFindSet(T[] U) {
		partition = new HashMap<T, Pair<T, Integer>>();
		makeSet(U);
	}

	/**
   * Create an HashMap and ArrayList with the key of the same type of T
	 * This must be O(n)
	 * @param U the set that is used to initialize the UnionFindSet
	 */
	public UnionFindSet(ArrayList<T> U) {
		partition = new HashMap<T, Pair<T, Integer>>();
		makeSet(U);
	}

	/**
   * Pre: U is a set
	 * Insert the elem in the U set if they are not already contained in the set
	 * This must be O(n)
	 * @param U the set that is used to insert elements the UnionFindSet
	 * @return true if the keys in U hava been inserteds; false otherwise
	 */
	public boolean makeSet(T[] U) {
		boolean contains = false;
		// check if one of the element in U is contained in the partition
		for (int i = 0; i < U.length && !contains; i++) {
			contains = partition.containsKey(U[i]);
		}

    if (!contains) {
			// the elements in U are not contained in the partition
			// All the elements in U are in the partition
			// The element are added as a pair (father, rank)
			// If the element does not have a father then the father = null
			for (int i = 0; i < U.length; i++) {
				Pair<T, Integer> pair = new Pair<T, Integer>(null, 0); // the element does not have a father, the rank of the element is 0
				partition.put(U[i], pair);
			}
			return true;
		} else {
			return false;
		}
	}

	/**
   * Pre: U is a set
	 * Insert the elem in the U set if they are not already contained in the set
	 * This must be O(n)
	 * @param U the set that is used to insert elements the UnionFindSet
	 * @return true if the keys in U hava been inserteds; false otherwise
	 */
	public boolean makeSet(ArrayList<T> U) {
		boolean contains = false;
		// check if one of the element in U is contained in the partition
		for (int i = 0; i < U.size() && !contains; i++) {
			contains = partition.containsKey(U.get(i));
		}

		if (!contains) {
			// the elements in U are not contained in the partition
			// All the elements in U are in the partition
			// The element are added as a pair (father, rank)
			// If the element does not have a father then the father = null
			for (int i = 0; i < U.size(); i++) {
				Pair<T, Integer> pair = new Pair<T, Integer>(null, 0);
				partition.put(U.get(i), pair);
			}
			return true;
		} else {
			return false;
		}
	}

	/**
   * Insert the elem x if itsn't already contained in the set
	 * This must be O(n)
	 * @param x the element tha must be inserted the UnionFindSet
	 * @return true if the x has been inserteds; false otherwise
	 */
	public boolean makeSet(T x) {
		if (partition.containsKey(x)) {
			return false;
		} else {
			// x is not contained in the partition
			Pair<T, Integer> pair = new Pair<T, Integer>(null, 0);
			partition.put(x, pair);
			return true;
		}
	}

	/**
   * Find the ancestor of elem and compress the path
	 * Return null if the elem does not exist
	 * @param elem the elem whose ancestor must be find
	 */
	public T find(T elem) {
		if (partition.containsKey(elem) == false) {
			return null;
		} else {
			// elem is contained in the partition
			T parent = partition.get(elem).left(); // get the father of the elem
			if (parent == null) {
				// Base case
				// the elem does not have a father so is the root of the three
				return elem;
			} else {
				// Inductive case
				T top = find(parent); // find the root of the three in which is contained the parent of elem
				partition.get(elem).left(top); // set the root of the three as the parent of elem
				return top;
			}
		}
	}

	/**
   * Make the union of the two subset containing x and y
	 * @param x element that is contained in a subset
	 * @param y element that is contained in a subset
	 */
	public boolean union(T x, T y) {
		boolean r = true;
		T xAncestor = find(x);
		T yAncestor = find(y);
		if (xAncestor == null || yAncestor == null) {
			// x or y are not contained in the partition
			r = false;
		} else if (xAncestor.equals(yAncestor)) {
			// x and y are in the same set
			r = false;
		} else {
			Integer xAncestorRank = partition.get(xAncestor).right();
			Integer yAncestorRank = partition.get(yAncestor).right();
			if (xAncestorRank < yAncestorRank) {
				// the three in which is contained x is merged into the one in which is contained y
				partition.get(xAncestor).left(yAncestor);
			} else if (yAncestorRank < xAncestorRank) {
				// the three in which is contained y is merged into the one in which is contained x
				partition.get(yAncestor).left(xAncestor);
			} else {
				// the three in which is contained y is merged into the one in which is contained x
				partition.get(xAncestor).left(yAncestor);
				partition.get(yAncestor).right(yAncestorRank + 1);//increase the rank of the ancestor of y by one
			}
		}
		return r;
	}

	public void print() {
		for (T key : partition.keySet())
			System.out.println("[" + key + "] " + partition.get(key));
	}

}
