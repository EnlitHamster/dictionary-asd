package exercise3;

public class Pair<L, R> {
	private L left;
	private R right;

	public Pair(L l, R r) {
		left = l;
		right = r;
	}

	public L left() {
		return left;
	}

	public R right() {
		return right;
	}

	public void left(L l) {
		left = l;
	}

	public void right(R r) {
		right = r;
	}

	public String toString(){
		return "(" + left + ", " + right + ")";
	}

	public boolean equals(Object pair) {
		if(pair != null && pair instanceof Pair){
			return this.left.equals(((Pair)pair).left) && this.right.equals(((Pair)pair).right);
		}
		else{
			System.out.println("f");
			return false;
		}
	}
}
