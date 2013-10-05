/**
 * Created by Sobercheg on 10/4/13.
 */
public class BellmanFordShortestPathTest {

    public static void main(String[] args) {
        BellmanFordShortestPathTest test = new BellmanFordShortestPathTest();
        test.basic();
        test.negative();
    }

    private void basic() {
        WeightedGraph wg = new WeightedGraph(5);
        wg.addEdge(new Edge(0, 1, 2));
        wg.addEdge(new Edge(0, 2, 1));
        wg.addEdge(new Edge(2, 3, 1));
        wg.addEdge(new Edge(3, 4, 1));
        wg.addEdge(new Edge(1, 4, 3));
        wg.addEdge(new Edge(4, 2, 1)); // cycle

        BellmanFordShortestPath dijkstraShortestPath = new BellmanFordShortestPath(wg, 0);
        assert dijkstraShortestPath.distTo(4) == 3 : "Path 0->4 must be 3";
        assert dijkstraShortestPath.distTo(2) == 1 : "Path 0->2 must be 1";
        assert dijkstraShortestPath.distTo(3) == 2 : "Path 0->3 must be 2";
    }

    private void negative() {
        WeightedGraph wg = new WeightedGraph(5);
        wg.addEdge(new Edge(0, 1, -2));
        wg.addEdge(new Edge(0, 2, -1));
        wg.addEdge(new Edge(2, 3, 1));
        wg.addEdge(new Edge(1, 3, -1));

        BellmanFordShortestPath dijkstraShortestPath = new BellmanFordShortestPath(wg, 0);
        assert dijkstraShortestPath.distTo(3) == -3 : "Path 0->3 must be -3";
        assert dijkstraShortestPath.distTo(1) == -2 : "Path 0->1 must be -2";
    }
}
