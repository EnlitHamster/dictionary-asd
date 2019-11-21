package exercise4;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

public class UndirectedGraph<V, L extends Comparable<L>> extends Graph<V, L> {
	/**
   * Create a new Undirected Graph
	 */
	public UndirectedGraph() {
    super();
  }

  public boolean addEdge(V vertex1, V vertex2, L label) {
    Map<V, L> vertex1Neighbors = this.graph.get(vertex1);
    Map<V, L> vertex2Neighbors = this.graph.get(vertex2);

    if (vertex1Neighbors != null && vertex2Neighbors != null && !vertex1.equals(vertex2)) {
			// both the vertex exist and they are different
			vertex1Neighbors.put(vertex2, label);
			vertex2Neighbors.put(vertex1, label);
			// the edge is added in both the adjacency list
      return true;
    }
    else {
      return false;
    }
  }

  public boolean isDirected() {
    return false;
  }

	public boolean deleteEdge(V vertex1, V vertex2) {
    boolean r = false;
		Map<V, L> vertex1Neighbors = this.graph.get(vertex1);
		Map<V, L> vertex2Neighbors = this.graph.get(vertex2);
    if (vertex1Neighbors != null && vertex2Neighbors != null) {
			// both the vertex exist
      if (vertex1Neighbors.remove(vertex2) != null && vertex2Neighbors.remove(vertex1) != null) { //edge is removed from both the adjacency lists
        // there is an edge between vertex1 and vertex2
        r = true;
      }
    }
    return r;
  }

	public int countEdge() {
    int sum = 0;
		Set<V> vertexSet = this.graph.keySet();
    for (V vertex : vertexSet) {
      sum += this.graph.get(vertex).size();
    }
    return sum/2; // sum/2 because every edge has been counted twice
  }

	public ArrayList<Edge<V, L>> edgeSet() {
    HashSet<Edge<V, L>> addedEdge = new HashSet<Edge<V, L>>(); // an HashSet that is used to check in O(1) if an edge as been already added in r
		ArrayList<Edge<V, L>> r = new ArrayList<Edge<V, L>>();
		Set<V> vertexSet = this.graph.keySet();
    for (V vertex : vertexSet) {
			Map<V, L> labels = this.graph.get(vertex); // the labels of the edges (vertex, neighbor)
			Set<V> neighborsSet = labels.keySet(); // all the neighbor of vertex
      for(V neighbor : neighborsSet){
				Edge<V, L> currEdge = new UndirectedEdge<V, L>(vertex, neighbor, labels.get(neighbor)); // the current edge
				if(!addedEdge.contains(currEdge)){
					// the edge hasn't been already added to the result array
					r.add(currEdge); // add to the result array the edge (vertex, neighbor, label)
					addedEdge.add(currEdge); // save the edge in the set of already added edge
				}
			}
			// The cost of this method is O(|V| +  |E|):
			// The the total iterations over the inner cycle are O(|E|), that's because there is an iteration for each edge
			// The total iteration over the extern cycle are O(|V|), that's because there is an iteration for each vertex
			// Note that the number of iteration of the inner cycle does not dipend from the extern one.
    }
		return r;
  }
}
