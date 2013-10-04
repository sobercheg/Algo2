import java.util.*;

/**
 * Created by Sobercheg on 10/3/13.
 */
public class DijkstraShortestPath implements ShortestPath {
    private final WeightedGraph graph;
    private final int V;
    private final int S;
    private final int[] distances;
    private final int[] pathTo;
    private final boolean[] marked;

    private final Map<Integer, VertexMarker> markerMap;

    private final Queue<VertexMarker> queue;

    public DijkstraShortestPath(WeightedGraph graph, int source) {
        this.graph = graph;
        this.V = graph.getV();
        this.S = source;
        this.distances = new int[V];
        this.markerMap = new HashMap<Integer, VertexMarker>(V);
        for (int i = 0; i < V; i++) {
            if (i != S) distances[i] = Integer.MAX_VALUE;
            markerMap.put(i, new VertexMarker(i, distances[i]));
        }
        this.pathTo = new int[V];
        this.marked = new boolean[V];
        this.queue = new PriorityQueue<VertexMarker>(V);

        queue.add(markerMap.get(source));
        while (!queue.isEmpty()) {
            relax(queue.poll());
        }
    }

    private void relax(VertexMarker vertex) {
        for (Edge e : graph.getEdges(vertex.vertex)) {
            int w = e.getTo();
            if (distances[w] > distances[vertex.vertex] + e.getWeight()) {
                distances[w] = distances[vertex.vertex] + e.getWeight();
                VertexMarker adjacentVertexMarker = markerMap.get(w);
                if (queue.contains(adjacentVertexMarker)) {
                    queue.remove(adjacentVertexMarker);
                }
                VertexMarker relaxedVertexMarker = new VertexMarker(w, distances[w]);
                queue.offer(relaxedVertexMarker);
                markerMap.put(w, relaxedVertexMarker);
                pathTo[vertex.vertex] = w;
            }
        }
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
        return null;
    }

    private final static class VertexMarker implements Comparable<VertexMarker> {
        private final int vertex;
        private final int distance;

        private VertexMarker(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(VertexMarker o) {
            return Integer.compare(distance, o.distance);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            VertexMarker that = (VertexMarker) o;

            if (distance != that.distance) return false;
            if (vertex != that.vertex) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = vertex;
            result = 31 * result + distance;
            return result;
        }

        public int getVertex() {
            return vertex;
        }


        public int getDistance() {
            return distance;
        }
    }
}
