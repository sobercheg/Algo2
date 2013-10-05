import java.util.Arrays;

/**
 * Created by Sobercheg on 10/5/13.
 */
public class JacksonAllShortestPathsTest {
    public static void main(String[] args) {
        JacksonAllShortestPathsTest test = new JacksonAllShortestPathsTest();
        test.basic();
    }

    private void basic() {
        WeightedGraph graph = new WeightedGraph(4);
        graph.addEdge(new Edge(0,1, -1));
        graph.addEdge(new Edge(0,2, -1));
        graph.addEdge(new Edge(1,3, -1));
        graph.addEdge(new Edge(2,3, -4));

        JacksonAllShortestPaths jacksonAllShortestPaths = new JacksonAllShortestPaths(graph);
        System.out.println(Arrays.deepToString(jacksonAllShortestPaths.getAllShortestPaths()));
    }
}
