package tests;

import java.util.ArrayList;
import exercise4.UndirectedGraph;
import exercise4.Kruskal;
import exercise4.Edge;

public class ItalianDistGraphMST {
	private static void usage() {
		System.out.println("Usage:");
		System.out.println("\t$ java ItalianDistGraphMST [CSV file path]");
	}

 	private static UndirectedGraph<String, Double> createGraph(String path) {
		UndirectedGraph<String, Double> g = new UndirectedGraph<String, Double>();
		Reader reader = new Reader(path);
		ArrayList<Record> arr = reader.scan();

		if (arr != null) {
			for (Record record : arr) {
				String s = record.getStart();
				String e = record.getEnd();
				double d = record.getDistance();

				g.addVertex(s);
				g.addVertex(e);
				g.addEdge(s, e, d);
			}
		} else {
			System.exit(1);
		}

		return g;
	}

	public static void main(String[] args) throws Exception {
    if (args.length > 1) {
			usage();
			System.exit(2);
		}

		UndirectedGraph<String, Double> g = null;
		if (args.length == 0) {
			g = createGraph("italian_dist_graph.csv");
		} else {
			g = createGraph(args[0]);
		}

		Kruskal<String, Double> k = new Kruskal<String, Double>(g);
		ArrayList<Edge<String, Double>> arr = k.mstKruskal();
		double sum = 0.;

		for (Edge<String, Double> e : arr) {
			sum += e.label();
		}

		System.out.println("[*] Number of vertexes:\t" + g.countVertex());
		System.out.println("[*] Number of edges:\t" +  arr.size());
		System.out.printf("[*] MST's weight:\t%.3f Km\n", sum / 1000);
	}
}
