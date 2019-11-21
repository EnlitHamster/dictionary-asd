package exercise4;

import java.util.ArrayList;
import exercise3.UnionFindSet;

public class Kruskal<V, L extends Comparable<L>> {
	private Graph<V, L> graph;

	public Kruskal(UndirectedGraph<V, L> graph) {
		this.graph = graph;
	}

	/**
   * Compute the MST of the Graph using the Kruskal algorithm
	 */
	public ArrayList<Edge<V, L>> mstKruskal() {
		ArrayList<Edge<V, L>> a = new ArrayList<Edge<V, L>>(); // The set of edges in the mst
		UnionFindSet<V> partition = new UnionFindSet<V>(graph.vertexSet()); // The auxiliary union-find-set used in the algorithm
		ArrayList<Edge<V, L>> edgeSet = graph.edgeSet();
		edgeSet.sort(null); // Sort the edges in a non-decreasig order
		for(Edge<V, L> edge : edgeSet) {
			if(!partition.find(edge.v1()).equals(partition.find(edge.v2()))) {
				// The two vertex aren't in the same set, so they aren't in the same three
				a.add(edge);
				partition.union(edge.v1(), edge.v2()); // Merge the set in which the two vertex are included, so link the three
			}
		}
		return a;
	}
}
