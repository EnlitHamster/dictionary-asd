package exercise4;

public class DirectedEdge<V, L extends Comparable<L>> extends Edge<V, L> {

  public DirectedEdge(V vertex1, V vertex2, L label) {
    super(vertex1, vertex2, label);
  }

  public boolean equals(Object edge) {
    if (edge != null && edge instanceof Edge) {
    // (v1, v2) == (v1', v2') <=> v1 == v1' && v2 == v2'
    return this.v1().equals(((Edge)edge).v1()) &&
      this.v2().equals(((Edge)edge).v2()) &&
      this.label().equals(((Edge)edge).label());
    }	else {
      return false;
    }
  }

}
