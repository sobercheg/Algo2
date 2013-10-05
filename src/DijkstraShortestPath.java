import java.util.*;

/**
 * Created by Sobercheg on 10/3/13.
 */
public class DijkstraShortestPath extends AbstractShortestPath {

    private final Map<Integer, VertexMarker> markerMap;
    private final Queue<VertexMarker> queue;

    public DijkstraShortestPath(WeightedGraph graph, int source) {
        super(graph, source);
        this.markerMap = new HashMap<Integer, VertexMarker>(V);
        for (int i = 0; i < V; i++) {
            markerMap.put(i, new VertexMarker(i, distances[i]));
        }
        this.queue = new PriorityQueue<VertexMarker>(V);

        queue.add(markerMap.get(source));
        while (!queue.isEmpty()) {
            relax(queue.poll());
        }
    }

    private void relax(VertexMarker vertex) {
        int v = vertex.getVertex();
        for (Edge e : graph.getEdges(v)) {
            int w = e.getTo();
            if (distances[w] > distances[v] + e.getWeight()) {
                distances[w] = distances[v] + e.getWeight();
                edgeTo[w] = e;
                VertexMarker adjacentVertexMarker = markerMap.get(w);
                if (queue.contains(adjacentVertexMarker)) {
                    queue.remove(adjacentVertexMarker);
                }
                VertexMarker relaxedVertexMarker = new VertexMarker(w, distances[w]);
                queue.offer(relaxedVertexMarker);
                markerMap.put(w, relaxedVertexMarker);
            }
        }
    }

}
