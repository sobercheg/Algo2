/**
 * Created by Sobercheg on 10/4/13.
 */
public class BellmanFordShortestPathTest {

    public static void main(String[] args) {
        BellmanFordShortestPathTest test = new BellmanFordShortestPathTest();
        test.basic();
        test.negativeWeight();
        test.negativeCycle();
    }

    private void basic() {
        WeightedGraph wg = new WeightedGraph(5);
        wg.addEdge(new Edge(0, 1, 2));
        wg.addEdge(new Edge(0, 2, 1));
        wg.addEdge(new Edge(2, 3, 1));
        wg.addEdge(new Edge(3, 4, 1));
        wg.addEdge(new Edge(1, 4, 3));
        wg.addEdge(new Edge(4, 2, 1)); // cycle

        BellmanFordShortestPath bellmanFordShortestPath = new BellmanFordShortestPath(wg, 0);
        assert bellmanFordShortestPath.distTo(4) == 3 : "Path 0->4 must be 3";
        assert bellmanFordShortestPath.distTo(2) == 1 : "Path 0->2 must be 1";
        assert bellmanFordShortestPath.distTo(3) == 2 : "Path 0->3 must be 2";

        assert !bellmanFordShortestPath.hasNegativeCycles() : "Should not report negative cycles";
    }

    private void negativeWeight() {
        WeightedGraph wg = new WeightedGraph(5);
        wg.addEdge(new Edge(0, 1, -2));
        wg.addEdge(new Edge(0, 2, -1));
        wg.addEdge(new Edge(2, 3, 1));
        wg.addEdge(new Edge(1, 3, -1));

        BellmanFordShortestPath bellmanFordShortestPath = new BellmanFordShortestPath(wg, 0);
        assert bellmanFordShortestPath.distTo(3) == -3 : "Path 0->3 must be -3";
        assert bellmanFordShortestPath.distTo(1) == -2 : "Path 0->1 must be -2";

        assert !bellmanFordShortestPath.hasNegativeCycles() : "Should not report negative cycles";

    }

    private void negativeCycle() {
        WeightedGraph wg = new WeightedGraph(5);
        wg.addEdge(new Edge(0, 1, -1));
        wg.addEdge(new Edge(0, 2, -1));
        wg.addEdge(new Edge(1, 2, -1));
        wg.addEdge(new Edge(3, 1, -1));
        wg.addEdge(new Edge(2, 3, -1));

        BellmanFordShortestPath bellmanFordShortestPath = new BellmanFordShortestPath(wg, 0);
        assert bellmanFordShortestPath.hasNegativeCycles() : "Should report a negative cycle";

    }
}
