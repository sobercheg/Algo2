package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sobercheg on 10/16/13.
 */
public class DepthFirstOrder {

    private WeightedGraph graph;
    private List<Integer> preorder;
    private List<Integer> postorder;
    private boolean marked[];

    public DepthFirstOrder(WeightedGraph graph) {
        this.graph = graph;
        this.preorder = new ArrayList<Integer>();
        this.postorder = new ArrayList<Integer>();
        this.marked = new boolean[graph.getV()];
        for (int v = 0; v < graph.getV(); v++) {
            if (!marked[v])
                dfs(v);
        }
    }

    private void dfs(int source) {
        marked[source] = true;
        preorder.add(source);
        for (Edge e : graph.getEdges(source)) {
            int w = e.getTo();
            if (!marked[w])
                dfs(w);
        }

        postorder.add(source);
    }

    public Iterable<Integer> preorder() {
        return preorder;
    }

    public Iterable<Integer> postorder() {
        return postorder;
    }

    public Iterable<Integer> reversePostorder() {
        List<Integer> reverse = new ArrayList<Integer>(postorder);
        Collections.reverse(reverse);
        return reverse;
    }
}
