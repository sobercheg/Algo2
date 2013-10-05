import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sobercheg on 10/3/13.
 */
public class WeightedGraph {
    private final int V;
    private final List<Edge>[] edges;
    private final List<Edge>[] inboundEdges;

    public WeightedGraph(int V) {
        this.V = V;

        this.edges = (List<Edge>[]) new List[this.V];
        for (int i = 0; i < this.V; i++) {
            edges[i] = new ArrayList<Edge>();
        }

        this.inboundEdges = (List<Edge>[]) new List[this.V];
        for (int i = 0; i < this.V; i++) {
            inboundEdges[i] = new ArrayList<Edge>();
        }
    }

    public void addEdge(Edge edge) {
        edges[edge.getFrom()].add(edge);
        inboundEdges[edge.getTo()].add(edge);
    }

    public List<Edge> getEdges(int v) {
        return edges[v];
    }

    public List<Edge> getAllEdges() {
        List<Edge> edgeList = new ArrayList<Edge>();
        for (List<Edge> vertexEdges : edges) {
            edgeList.addAll(vertexEdges);
        }
        return edgeList;
    }

    public List<Edge> getInboundEdges(int v) {
        return inboundEdges[v];
    }

    public int getV() {
        return V;
    }
}
