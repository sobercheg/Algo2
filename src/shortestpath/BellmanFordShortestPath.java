package shortestpath;

/**
 * Created by Sobercheg on 10/4/13.
 */
public class BellmanFordShortestPath extends AbstractShortestPath {

    public BellmanFordShortestPath(WeightedGraph graph, int source) {
        super(graph, source);
        for (int i = 0; i < V; i++) {
            for (Edge edge : graph.getAllEdges()) {
                relax(edge);
            }
        }
    }

    public boolean hasNegativeCycles() {
        for (Edge edge: graph.getAllEdges()) {
            if (distances[edge.getTo()] > distances[edge.getFrom()] + edge.getWeight()) return true;
        }
        return false;
    }

    private void relax(Edge edge) {
        int v = edge.getFrom();
        int w = edge.getTo();
        if (distances[v] == Integer.MAX_VALUE) return;
        if (distances[w] > distances[v] + (int)edge.getWeight()) {
            distances[w] = distances[v] + (int)edge.getWeight();
            edgeTo[w] = edge;
        }
    }

}
