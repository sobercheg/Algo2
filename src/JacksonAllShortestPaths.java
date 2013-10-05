import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Sobercheg on 10/3/13.
 */
public class JacksonAllShortestPaths {

    private final WeightedGraph graph;
    private final int[][] allShortestPaths;

    public JacksonAllShortestPaths(WeightedGraph graph) {
        this.graph = graph;
        this.allShortestPaths = new int[graph.getV()][graph.getV()];

        System.out.println("Step 1. Add a new vertex source s and new edges with weight 0 from the source to every other vertex G");
        WeightedGraph graphWithNewSource = addNewSource(graph);

        System.out.println("Step 2. Run Bellman-Ford on G' with the new source vertex s");
        BellmanFordShortestPath newSourceShortestPath = new BellmanFordShortestPath(graphWithNewSource, graphWithNewSource.getV() - 1);
        if (newSourceShortestPath.hasNegativeCycles()) {
            throw new IllegalStateException("There are negative cycles");
        }

        System.out.println("Step 3. Re-weight G->G'': We' = We + Pu - Pv (where Pv is the shortest path from s to v in G'");
        WeightedGraph reweightedGraph = reweightGraph(graph, newSourceShortestPath.distances());

        System.out.println("Step 4. For each vertex in G'' run Dijkstra shortest path");
        for (int v = 0; v < graph.getV(); v++) {
            DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(reweightedGraph, v);
            if (v % 100 == 0)
                System.out.println("Step 5. Calculate shortest path for v=[" + v + "](unweight): d(v,w)=d'(v,w) - Pu + Pv");
            for (int w = 0; w < graph.getV(); w++) {
                if (dijkstraShortestPath.hasPathTo(w))
                    allShortestPaths[v][w] = dijkstraShortestPath.distTo(w) - newSourceShortestPath.distTo(v) + newSourceShortestPath.distTo(w);
                else allShortestPaths[v][w] = Integer.MAX_VALUE;
            }
        }
    }

    public int[][] getAllShortestPaths() {
        return allShortestPaths;
    }

    private WeightedGraph addNewSource(WeightedGraph graph) {
        WeightedGraph newSourceGraph = new WeightedGraph(graph.getV() + 1);
        for (Edge edge : graph.getAllEdges()) {
            newSourceGraph.addEdge(edge);
        }

        int newSourceVertex = graph.getV();
        for (int v = 0; v < graph.getV(); v++) {
            newSourceGraph.addEdge(new Edge(newSourceVertex, v, 0));
        }

        return newSourceGraph;
    }

    private WeightedGraph reweightGraph(WeightedGraph graph, int[] distances) {
        WeightedGraph reweightedGraph = new WeightedGraph(graph.getV());
        for (Edge edge : graph.getAllEdges()) {
            int newWeight = edge.getWeight() + distances[edge.getFrom()] - distances[edge.getTo()];
            if (newWeight < 0)
                System.out.println("[WARNING] New weight is negative " + newWeight);
            reweightedGraph.addEdge(new Edge(
                    edge.getFrom(),
                    edge.getTo(),
                    newWeight));
        }
        return reweightedGraph;
    }

    public static void main(String[] args) throws FileNotFoundException {
        WeightedGraph graph = GraphReader.readFromFile("D:\\Dropbox\\Learning\\Coursera\\g3.txt");
        JacksonAllShortestPaths jacksonAllShortestPaths = new JacksonAllShortestPaths(graph);

        int[][] allPaths = jacksonAllShortestPaths.getAllShortestPaths();
        int minDist = Integer.MAX_VALUE;
        for (int i = 0; i < allPaths.length; i++) {
            for (int j = 0; j < allPaths[i].length; j++) {
                if (allPaths[i][j] < minDist) minDist = allPaths[i][j];
            }
        }

        System.out.println("Shortest shortest path: " + minDist);
    }
}
