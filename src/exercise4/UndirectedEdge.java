package exercise4;

import java.util.Objects;
public class UndirectedEdge<V, L extends Comparable<L>> extends Edge<V, L> {

	public UndirectedEdge(V vertex1, V vertex2, L label) {
		super(vertex1, vertex2, label);
	}

	// {v1, v2} == {v1', v2'} <=> v1 == v1' & v2 == v2' || v1 == v2' & v2 == v1'
	@Override
	public boolean equals(Object edge) {
		if(edge != null && edge instanceof Edge) {
			return ((this.v1().equals(((Edge)edge).v1()) && this.v2().equals(((Edge)edge).v2())) ||
						  (this.v1().equals(((Edge)edge).v2()) && this.v2().equals(((Edge)edge).v1()))) &&
						 this.label().equals(((Edge)edge).label());
		} else {
			return false;
		}
	}

	public int hashCode() {
		int hashFirst = v1().hashCode();
		int hashSecond = v2().hashCode();
		int maxHash = Math.max(hashFirst, hashSecond);
		int minHash = Math.min(hashFirst, hashSecond);
		return Objects.hash(minHash, maxHash, label().hashCode());
  }

}
