package exercise4;

public abstract class Edge<V, L extends Comparable<L>> implements Comparable<Edge<V, L>> {
  private V vertex1;
  private V vertex2;
  private L label;

  @Override
  public int compareTo(Edge<V, L>otherEdge) {
    return label.compareTo(otherEdge.label);
  }

  public Edge(V vertex1, V vertex2, L label) {
    this.vertex1 = vertex1;
    this.vertex2 = vertex2;
    this.label = label;
  }

  public V v1() {
    return vertex1;
  }

  public V v2() {
    return vertex2;
  }

  public L label() {
    return label;
  }

  public String toString() {
    return "(" + vertex1 + ", " + vertex2 + ", " + label + ")";
  }
}
