package shortestpath;

import util.Assert;

/**
 * Created by Sobercheg on 10/4/13.
 */
public class DijkstraShortestPathTest {

    public static void main(String[] args) {
        DijkstraShortestPathTest test = new DijkstraShortestPathTest();
        test.basic();
    }

    private void basic() {
        WeightedGraph wg = new WeightedGraph(5);
        wg.addEdge(new Edge(0, 1, 2));
        wg.addEdge(new Edge(0, 2, 1));
        wg.addEdge(new Edge(2, 3, 1));
        wg.addEdge(new Edge(3, 4, 1));
        wg.addEdge(new Edge(1, 4, 3));
        wg.addEdge(new Edge(4, 2, 1)); // cycle

        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(wg, 0);
        Assert.equals(dijkstraShortestPath.distTo(4), 3, "Path 0->4 must be 3");
        Assert.equals(dijkstraShortestPath.distTo(2), 1, "Path 0->2 must be 1");
        Assert.equals(dijkstraShortestPath.distTo(3), 2, "Path 0->3 must be 2");
    }
}
