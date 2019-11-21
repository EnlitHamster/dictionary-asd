package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;
import exercise4.Graph;
import exercise4.Edge;
import exercise4.DirectedEdge;
import exercise4.DirectedGraph;

public class DirectedGraph_Test {

	public static int graphSize;
	Graph<Integer, String> graph;

	@Before
	public void creategraph() {
		graphSize = 5;
		graph = new DirectedGraph<Integer, String>();
		for (int i = 0; i < graphSize; i++) {
			graph.addVertex(i);
		}
	}

	@Test
	public void vertexSetTest() {
		ArrayList<Integer> vertex = graph.vertexSet();
		for (int i = 0; i < graphSize; i++) {
			assertTrue(vertex.contains(i));
		}
	}

	@Test
	public void containsVertexTest() {
		for (int i = 0; i < graphSize; i++) {
			assertTrue(graph.containsVertex(i));
		}
	}

	@Test
	public void addVertexTest() {
		assertTrue(graph.addVertex(graphSize));
		assertTrue(graph.containsVertex(graphSize));
	}

	@Test
	public void addSameVertexTest() {
		assertFalse(graph.addVertex(0));
	}

	@Test
	public void addEdgeTest() {
		assertTrue(graph.addEdge(0, 1, "a"));
		assertTrue(graph.containsEdge(0, 1));
	}

	@Test
	public void addLoop() {
		assertTrue(graph.addEdge(0, 0, "a"));
		assertTrue(graph.containsEdge(0, 0));
	}

	@Test
	public void linkAbstentVertexsTest() {
		assertFalse(graph.addEdge(graphSize, 0, "a"));
		assertFalse(graph.addEdge(0, graphSize, "a"));
		assertFalse(graph.addEdge(-1, graphSize, "a"));
		assertFalse(graph.addEdge(graphSize, -1, "a"));
		assertFalse(graph.addEdge(graphSize, graphSize, "a"));
	}

	@Test
	public void changeLabelTest() {
		assertTrue(graph.addEdge(0, 1, "a"));
		assertTrue(graph.addEdge(0, 1, "b"));
		assertEquals("b", graph.getLabel(0, 1));
	}

	@Test
	public void deleteUnlikedVertexTest() {
		assertTrue(graph.deleteVertex(1));
		assertFalse(graph.containsVertex(1));
	}

	@Test
	public void deleteAbsentVertexTest() {
		assertFalse(graph.deleteVertex(graphSize));
	}

	@Test
	public void deleteLinkedVertexTest() {
		graph.addEdge(0, 1, "a");
		graph.addEdge(0, 0, "a");
		graph.addEdge(1, 0, "a");
		graph.addEdge(1, 2, "a");
		graph.addEdge(2, 0, "a");
		assertTrue(graph.deleteVertex(0));
		assertFalse(graph.containsVertex(0));
		assertFalse(graph.containsEdge(0, 0));
		assertFalse(graph.containsEdge(0, 1));
		assertFalse(graph.containsEdge(1, 0));
		assertFalse(graph.containsEdge(2, 0));
		assertTrue(graph.containsEdge(1, 2));
	}

	@Test
	public void deleteEdgeTest() {
		graph.addEdge(0, 1, "a");
		graph.addEdge(1, 0, "a");
		assertTrue(graph.deleteEdge(0, 1));
		assertFalse(graph.containsEdge(0, 1));
		assertTrue(graph.containsEdge(1, 0));
	}

	@Test
	public void deleteCycleTest() {
		graph.addEdge(0, 1, "a");
		graph.addEdge(1, 0, "a");
		assertTrue(graph.deleteEdge(0, 1));
		assertTrue(graph.deleteEdge(1, 0));
		assertFalse(graph.containsEdge(1, 0));
		assertFalse(graph.containsEdge(0, 1));
	}

	@Test
	public void deleteAbsentEdgeTest() {
		assertFalse(graph.deleteEdge(graphSize, 0));
		assertFalse(graph.deleteEdge(0, graphSize));
	}

	@Test
	public void countVertexTest() {
		assertEquals(graphSize, graph.countVertex());
	}

	@Test
	public void countVertexAfterAddTest() {
		graph.addVertex(graphSize);
		assertEquals(graphSize + 1, graph.countVertex());
	}

	@Test
	public void countVertexAfterDeleteTest() {
		graph.deleteVertex(0);
		assertEquals(graphSize - 1, graph.countVertex());
	}

	@Test
	public void countEdgeTest() {
		assertEquals(0, graph.countEdge());
	}

	@Test
	public void countEdgeAfterAddTest() {
		graph.addEdge(0, 1, "a");
		assertEquals(1, graph.countEdge());
	}

	@Test
	public void countEdgeAfterDeleteTest() {
		graph.addEdge(0, 1, "a");
		graph.deleteEdge(0, 1);
		assertEquals(0, graph.countEdge());
	}

	@Test
	public void edgeSetTest() {
		graph.addEdge(0, 0, "a");
		graph.addEdge(0, 1, "b");
		graph.addEdge(2, 1, "c");
		ArrayList<Edge<Integer, String>> edges = graph.edgeSet();
		assertTrue(edges.contains(new DirectedEdge<Integer, String>(0, 0, "a")));
		assertTrue(edges.contains(new DirectedEdge<Integer, String>(0, 1, "b")));
		assertTrue(edges.contains(new DirectedEdge<Integer, String>(2, 1, "c")));
	}

	@Test
	public void unlikedVertexNeighborsTest() {
		ArrayList<Integer> neighbors = graph.neighbors(0);
		assertTrue(neighbors.size() == 0);
	}

	@Test
	public void linkedVertexNeighborsTest() {
		graph.addEdge(0, 0, "a");
		graph.addEdge(0, 1, "b");
		graph.addEdge(0, 2, "b");
		ArrayList<Integer> neighbors = graph.neighbors(0);
		assertTrue(neighbors.contains(0));
		assertTrue(neighbors.contains(1));
		assertTrue(neighbors.contains(2));
		assertFalse(neighbors.contains(3));
	}

	@Test
	public void getLabelTest() {
		graph.addEdge(0, 1, "a");
		assertEquals("a", graph.getLabel(0, 1));
	}

	@Test
	public void getLabelAfterDeleteTest() {
		graph.addEdge(0, 1, "a");
		graph.deleteEdge(0, 1);
		assertEquals(null, graph.getLabel(0, 1));
	}
}
