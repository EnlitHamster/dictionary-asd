package exercise4;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

public class DirectedGraph<V, L extends Comparable<L>> extends Graph<V, L> {

	/**
	 * Create a new Directed Graph
	 */
	public DirectedGraph() {
		super();
	}

  public boolean addEdge(V vertex1, V vertex2, L label) {
    Map<V, L> neighbors = this.graph.get(vertex1);

    if (neighbors != null && this.graph.containsKey(vertex2)) {
      // The edge is already contained, so it must nust change the label
      neighbors.put(vertex2, label);
      return true;
    } else {
      return false;
    }
  }

  public boolean isDirected() {
    return true;
  }

  public boolean deleteEdge(V vertex1, V vertex2) {
    boolean r = false;

    Map<V, L> neighbors = this.graph.get(vertex1);
    if (neighbors != null) {
      // vertex1 exist
      if (neighbors.remove(vertex2) != null) {
        // the edge (vertex1, vertex2) exist
        r = true;
      }
    }

    return r;
  }

  public int countEdge() {
    int sum = 0;
    Set<V> vertexSet = this.graph.keySet();
    // Sum all the size of the adjacency lists
    for (V vertex : vertexSet) {
      sum += this.graph.get(vertex).size();
    }
    return sum;
  }

  public ArrayList<Edge<V, L>> edgeSet() {
    ArrayList<Edge<V, L>> r = new ArrayList<Edge<V, L>>();
    Set<V> vertexSet = this.graph.keySet();
    for (V vertex : vertexSet) {
      Map<V, L> labels = this.graph.get(vertex); // the labels of the edges (vertex, neighbor)
      Set<V> neighborsSet = labels.keySet(); // all the neighbor of vertex
      for (V neighbor : neighborsSet) {
        r.add(new DirectedEdge<V, L>(vertex, neighbor, labels.get(neighbor)));// add to the result array the edge (vertex, neighbor, label)
      }
      // The cost of this method is O(|V| +  |E|):
      // The the total iterations over the inner cycle are O(|E|), that's because there is an iteration for each edge
      // The total iteration over the extern cycle are O(|V|), that's because there is an iteration for each vertex
      // Note that the number of iteration of the inner cycle does not dipend from the extern one.
    }
    return r;
  }

}
