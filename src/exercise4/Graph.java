package exercise4;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

public abstract class Graph<V, L extends Comparable<L>> {
  protected Map<V, HashMap<V, L>> graph;

  /**
  * Create a new empty Graph
  */
  public Graph() {
    this.graph = new HashMap<V, HashMap<V, L>>();
  }

  /**
   * Add an edge on the array if both vertex1 and vertex2 are contained
   * If the vertex already exist, the original label is overitten with the new one
   *
   * @param vertex1 the start of the edge
   * @param vertex2 the end of the edge
   * @param label the label of the edge
   *
   * @return true on success, false otherwise
   */
  public abstract boolean addEdge(V vertex1, V vertex2, L label);

	/**
   * Function used to know if the Graph is Directed or not
   *
	 * @return true if the graph is Directed, false otherwise
	 */
  public abstract boolean isDirected();

	/**
   * Delete an edge from the graph if both vertex1 and vertex2 are contained
   *
	 * @param vertex1 the start of the edge
	 * @param vertex2 the end of the edge
   *
	 * @return true on success, false otherwise
	 */
	public abstract boolean deleteEdge(V vertex1, V vertex2);

	/**
   * Count the number of edge in the graph
   *
	 * @return the number of edge in the graph
	 */
	public abstract int countEdge();

	/**
   * The function return the set of edge in che graph
   *
	 * @return the set of edge
	 */
  public abstract ArrayList<Edge<V, L>> edgeSet();

	/**
   * Add a vertex if not already contained
   *
	 * @param vertex the vertex that must be added
   *
	 * @return true on success, false otherwise
	 */
	public boolean addVertex(V vertex) {
    if (!this.graph.containsKey(vertex)) {
      this.graph.put(vertex, new HashMap<V, L>());
      return true;
    } else {
      return false;
    }
  }

	/**
   * Check if a vertex is contained
   *
	 * @param vertex the vertex that must be find
   *
	 * @return true if the vertex is contained, false otherwise
	 */
	public boolean containsVertex(V vertex) {
		return this.graph.containsKey(vertex);
	}

	/**
   * Check if a vertex is contained
   *
	 * @param vertex the vertex that must be find
   *
	 * @return true if the vertex is contained, false otherwise
	 */
	public boolean containsEdge(V vertex1, V vertex2) {
		Map<V, L> neighbors = this.graph.get(vertex1);

		if (neighbors != null) {
			// if vertex1 exist and there is an edge between it and vertex2
			// then vertex2 must exist because an edge can't be added if on
			// of the two vertex does not exist
			return neighbors.containsKey(vertex2);
		} else {
			return false;
		}
	}

	/**
   * Delete a vertex from the graph if it is contained
   *
	 * @param vertex the vertex that must be deleted
   *
	 * @return true on success, false otherwise
	 */
	public boolean deleteVertex(V vertex) {
		if (this.graph.containsKey(vertex)) {
			Set<V> neighborSet = this.graph.keySet();
			// Delete vertex from all the adjacency list in which appear
			for (V neighbor : neighborSet) {
				if (!neighbor.equals(vertex)) {
					this.graph.get(neighbor).remove(vertex);
				}
			}
			// remove the vertex from the graph
			this.graph.remove(vertex);
			return true;
		} else {
			return false;
		}
	}

	/**
   * The function return the set of vertex of the graph
   *
	 * @return the set of vertex
	 */
	public ArrayList<V> vertexSet() {
    return new ArrayList<V>(this.graph.keySet());//create the arrayList from the set of vertex
  }

	/**
   * Find all the neighbor of a vertex
   *
	 * @param vertex the vertex of whom the neighbor must be found
   *
	 * @return the neighbor of the vertex
	 */
	public ArrayList<V> neighbors(V vertex) {
		if (this.graph.containsKey(vertex)) {
			return new ArrayList<V>(this.graph.get(vertex).keySet());//Crate the arrayList from the set of neighbor of vertex
		} else {
			return null;
		}
	}

	/**
   * Find the label of a edge between two vertex if the edge exist
   *
	 * @param vertex1 the start of the edge
	 * @param vertex2 the end of the edge
   *
	 * @return the label of the edge, null if the edge doesn't exist
	 */
	public L getLabel(V vertex1, V vertex2) {
		L label = null;
		Map<V, L> neighbors = this.graph.get(vertex1);
		if (neighbors != null) {
			// vertex1 exist
			label = neighbors.get(vertex2);
			// if label == null then there isn't an edge between vertex1 and vertex2 does not exist
		}
		return label;
	}

	/**
   * Find the number of vertex of the graph
   *
	 * @return the number of vertex
	 */
	public int countVertex() {
		return this.graph.size();
	}

	public void print() {
		for (V vertex: this.graph.keySet()) {
			System.out.print(vertex + "-> ");
			Map<V, L> neighbors = this.graph.get(vertex);
			for (V neighbor : neighbors.keySet()) {
				System.out.print(neighbor + "(" + neighbors.get(neighbor) + ") ");
			}
			System.out.println("");
		}
	}
}
