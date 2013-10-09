package shortestpath;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Sobercheg on 10/5/13.
 */
public class GraphReader {

    /**
     * The first line indicates the number of vertices and edges, respectively.
     * Each subsequent line describes an edge (the first two numbers are its tail and head, respectively)
     * and its length (the third number).
     * <p/>
     * 1000 47978
     * 1 14 6
     * 1 25 47
     * 1 70 22
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public static WeightedGraph readFromFile(String file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(file));
        int V = scanner.nextInt();
        int E = scanner.nextInt();
        WeightedGraph graph = new WeightedGraph(V);

        for (int i = 0; i < E; i++) {
            if (!scanner.hasNextInt())
                throw new IllegalArgumentException("There is data for [" + i + "] edges only. Expected # of edges [" + E + "]");
            graph.addEdge(new Edge(scanner.nextInt() - 1, scanner.nextInt() - 1, scanner.nextInt()));
        }
        return graph;
    }
}
