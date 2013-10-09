package shortestpath;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Sobercheg on 10/4/13.
 */
public class AbstractShortestPath implements ShortestPath {
    protected final WeightedGraph graph;
    protected final int V;
    protected final int S;
    protected final int[] distances;
    protected final Edge[] edgeTo;

    protected AbstractShortestPath(WeightedGraph graph, int source) {
        this.graph = graph;
        this.V = graph.getV();
        this.S = source;
        this.distances = new int[V];
        for (int i = 0; i < V; i++) {
            if (i != S) distances[i] = Integer.MAX_VALUE;
        }
        this.edgeTo = new Edge[V];

    }

    @Override
    public int distTo(int v) {
        return distances[v];
    }

    @Override
    public boolean hasPathTo(int v) {
        return distances[v] < Integer.MAX_VALUE;
    }

    @Override
    public Iterable<Edge> pathTo(int v) {
        Deque<Edge> path = new ArrayDeque<Edge>();
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[e.getFrom()]) {
            path.push(e);
        }
        return path;
    }

    @Override
    public int[] distances() {
        return distances;
    }
}
