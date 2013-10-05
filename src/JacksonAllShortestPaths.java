/**
 * Created by Sobercheg on 10/3/13.
 */
public class JacksonAllShortestPaths {

    private final WeightedGraph graph;
    private final int[][] allShortestPaths;

    public JacksonAllShortestPaths(WeightedGraph graph) {
        this.graph = graph;
        this.allShortestPaths = new int[graph.getV()][graph.getV()];

        // Step 1. Add a new vertex source s and new edges with weight 0 from the source to every other vertex G
        WeightedGraph graphWithNewSource = addNewSource(graph);

        // Step 2. Run Bellman-Ford on G' with the new source vertex s
        BellmanFordShortestPath newSourceShortestPath = new BellmanFordShortestPath(graphWithNewSource, graphWithNewSource.getV() - 1);
        if (newSourceShortestPath.hasNegativeCycles()) {
            throw new IllegalStateException("There are negative cycles");
        }

        // Step 3. Re-weight G->G'': We' = We + Pu - Pv (where Pv is the shortest path from s to v in G')
        WeightedGraph reweightedGraph = reweightGraph(graphWithNewSource, newSourceShortestPath.distances());

        // Step 4. For each vertex in G'' run Dijkstra shortest path
        for (int v = 0; v < graph.getV(); v++) {
            DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(reweightedGraph, v);

            // Step 5. Calculate shortest path ("unweight"): d(v,w)=d'(v,w) - Pu + Pv
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

    private WeightedGraph reweightGraph(WeightedGraph graphWithNewSource, int[] distances) {
        WeightedGraph reweightedGraph = new WeightedGraph(graph.getV());
        for (Edge edge : graph.getAllEdges()) {
            reweightedGraph.addEdge(new Edge(
                    edge.getFrom(),
                    edge.getTo(),
                    edge.getWeight() + distances[edge.getFrom()] - distances[edge.getTo()]));
        }
        return reweightedGraph;
    }

}
