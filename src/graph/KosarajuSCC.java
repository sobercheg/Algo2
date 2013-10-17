package graph;

/**
 * Created by Sobercheg on 10/16/13.
 */
public class KosarajuSCC implements SCC {

    private WeightedGraph graph;
    private boolean[] marked;
    private int[] id;
    private int count;

    public KosarajuSCC(WeightedGraph graph) {
        this.graph = graph;
        this.marked = new boolean[graph.getV()];
        this.id = new int[graph.getV()];
        DepthFirstOrder depthFirstOrder = new DepthFirstOrder(graph.reverse());
        for (int s : depthFirstOrder.reversePostorder()) {
            if (!marked[s]) {
                dfs(s);
                count++;
            }
        }
    }

    private void dfs(int source) {
        marked[source] = true;
        id[source] = count;
        for (Edge edge : graph.getEdges(source)) {
            int w = edge.getTo();
            if (!marked[w])
                dfs(w);
        }

    }

    @Override
    public boolean stronglyConnected(int u, int v) {
        return id[u] == id[v];
    }

    @Override
    public int id(int v) {
        return id[v];
    }

    @Override
    public int count() {
        return count;
    }
}
