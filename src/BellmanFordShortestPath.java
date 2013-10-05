import java.util.*;

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

    private void relax(Edge edge) {
        int v = edge.getFrom();
        int w = edge.getTo();
        if (distances[w] > distances[v] + edge.getWeight()) {
            distances[w] = distances[v] + edge.getWeight();
            edgeTo[w] = edge;
        }
    }

}
