package tests;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import exercise4.UndirectedGraph;
import exercise4.Kruskal;
import exercise4.Edge;

public class Kruskal_Test {

	UndirectedGraph<Character, Integer> g;
	Kruskal<Character, Integer> k;
	ArrayList<Edge<Character, Integer>> a;

	public int weight(ArrayList<Edge<Character, Integer>> a) {
		int weight = 0;
		for (Edge<Character, Integer> e : a) {
			weight += e.label();
		}
		return weight;
	}

	@Before
	public void init() throws Exception {
		g = new UndirectedGraph<Character, Integer>();
	}

	@Test
	public void emptyGraphTest() throws Exception {
		Kruskal<Character, Integer> k = new Kruskal<Character, Integer>(g);
		a = k.mstKruskal();
		assertEquals(0, a.size());
	}

	@Test
	public void oneVertexGraphTest() throws Exception {
		g.addVertex('A');
		Kruskal<Character, Integer> k = new Kruskal<Character, Integer>(g);
		a = k.mstKruskal();
		assertEquals(0, a.size());
	}

	@Test
	public void twoLinkedVertexGraphTest() throws Exception {
		g.addVertex('A');
		g.addVertex('B');
		g.addEdge('A', 'B', 1);
		Kruskal<Character, Integer> k = new Kruskal<Character, Integer>(g);
		a = k.mstKruskal();
		assertEquals(1, weight(a));
	}

	@Test
	public void cyclicGraphTest() throws Exception {
		g.addVertex('A');
		g.addVertex('B');
		g.addVertex('C');
		g.addEdge('A', 'B', 1);
		g.addEdge('B', 'C', 5);
		g.addEdge('C', 'A', 1);
		Kruskal<Character, Integer> k = new Kruskal<Character, Integer>(g);
		a = k.mstKruskal();
		assertEquals(2, weight(a));
	}

	@Test
	public void denseGraphTest() throws Exception {
		g.addVertex('A');
		g.addVertex('B');
		g.addVertex('C');
		g.addVertex('D');
		g.addEdge('A', 'B', 5);
		g.addEdge('A', 'D', 3);
		g.addEdge('A', 'C', 1);
		g.addEdge('B', 'C', 0);
		g.addEdge('B', 'D', -1);
		g.addEdge('C', 'D', 2);
		Kruskal<Character, Integer> k = new Kruskal<Character, Integer>(g);
		a = k.mstKruskal();
		assertEquals(0, weight(a));
	}

	@Test
	public void genericGraphTest() throws Exception {
		for (char c = 'a'; c <= 'i'; c++) {
			g.addVertex(c);
		}
		g.addEdge('a', 'b', 4);
		g.addEdge('a', 'h', 8);
		g.addEdge('b', 'c', 8);
		g.addEdge('b', 'h', 11);
		g.addEdge('h', 'i', 7);
		g.addEdge('h', 'g', 1);
		g.addEdge('i', 'c', 2);
		g.addEdge('i', 'g', 6);
		g.addEdge('c', 'f', 4);
		g.addEdge('c', 'd', 7);
		g.addEdge('d', 'f', 14);
		g.addEdge('d', 'e', 9);
		g.addEdge('f', 'e', 10);
		g.addEdge('f', 'g', 2);
		Kruskal<Character, Integer> k = new Kruskal<Character, Integer>(g);
    ArrayList<Edge<Character, Integer>> a = k.mstKruskal();
		assertEquals(37, weight(a));
	}

}
