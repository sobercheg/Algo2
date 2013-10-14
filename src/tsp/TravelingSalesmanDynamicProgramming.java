package tsp;

import com.google.common.collect.Sets;
import shortestpath.Edge;
import shortestpath.WeightedGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.*;


/**
 * Created by Sobercheg on 10/9/13.
 */
public class TravelingSalesmanDynamicProgramming {

    private WeightedGraph graph;

    public TravelingSalesmanDynamicProgramming(WeightedGraph graph) {
        this.graph = graph;
    }

    // Coordinates
    public TravelingSalesmanDynamicProgramming(List<Point> points) {
        graph = new WeightedGraph(points.size());
        System.out.println("Initializing graph from [" + points.size() + "] points...");
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.size(); j++) {
                if (i == j) continue;
                Point from = points.get(i);
                Point to = points.get(j);
                if (from.equals(to)) continue;
                double distance = Math.sqrt((from.x - to.x) * (from.x - to.x) + (from.y - to.y) * (from.y - to.y));
                graph.addEdge(new Edge(i, j, distance));
            }
        }
        System.out.println("Initialized graph successfully");
    }

    public double minimumCost() {
        int number = graph.getV();
        Map<Integer, Double>[] A = (Map<Integer, Double>[]) new Map[number];
        Map<Integer, Double>[] Aprev = (Map<Integer, Double>[]) new Map[number];
        // Step 1. Init the solution matrix
        for (int i = 0; i < A.length; i++) {
            Aprev[i] = new HashMap<Integer, Double>();
        }

        Aprev[0].put(getIndex(0), 0D);

        List<Set<Integer>>[] sets = (List<Set<Integer>>[]) new List[number + 1];
        Set<Integer> initialSet = new HashSet<Integer>();
        for (int i = 0; i < number; i++) {
            initialSet.add(i);
            sets[i] = new ArrayList<Set<Integer>>();
        }
        sets[number] = new ArrayList<Set<Integer>>();

        Set<Set<Integer>> powerSet = Sets.powerSet(initialSet);
        System.out.println("Adding [" + powerSet.size() + "] sets to the array...");
        int counter = 0;
        for (Set<Integer> set : powerSet) {
            if (counter % 10000 == 0) System.out.println("[" + counter + "]");
            if (set == null) continue;
            if (set.size() >= sets.length)
                System.out.println("set.size() >= sets.length " + set.size() + " >= " + sets.length);
            sets[set.size()].add(set);
            counter++;
        }
        powerSet = null;

        double weights[][] = new double[number][number];
        for (int i = 0; i < number; i++) {
            for (int j = 0; j < number; j++) {
                Edge kjEdge = graph.getEdge(i, j);
                weights[i][j] = kjEdge == null ? Double.POSITIVE_INFINITY : kjEdge.getWeight();
            }
        }

        for (int m = 2; m <= graph.getV(); m++) {
            System.out.println("Subset size=" + m);
            sets[m - 1] = null; // GC optimization
            for (int i = 0; i < A.length; i++) {
                A[i] = new HashMap<Integer, Double>();
            }
            System.gc();
            for (Set<Integer> subset : sets[m]) {
                if (subset == null) continue;
                if (!subset.contains(0)) continue;
                int subsetIndex = getIndex(subset);
                for (int j : subset) {
                    if (j == 0) continue;
                    int subsetMinusJIndex = getIndexButOne(subset, j);
                    double bestSolution = Double.POSITIVE_INFINITY;
                    for (int k : subset) {
                        if (k == j) continue;
                        double previousSolution = ((Aprev[k] != null && Aprev[k].containsKey(subsetMinusJIndex)) ?
                                Aprev[k].get(subsetMinusJIndex) : Double.POSITIVE_INFINITY) + weights[k][j];
                        if (previousSolution <= bestSolution) {
                            bestSolution = previousSolution;
                            A[j].put(subsetIndex, bestSolution);
                        }
                    }
                }
            }
            System.arraycopy(A, 0, Aprev, 0, A.length);
        }

        int initialSetIndex = getIndex(initialSet);
        double minSolution = Double.POSITIVE_INFINITY;
        for (int i = 1; i < number; i++) {
            double solution = A[i].get(initialSetIndex) + graph.getEdge(i, 0).getWeight();
            if (solution < minSolution) {
                minSolution = solution;
            }
        }

        return minSolution;
    }

    int getIndex(Integer... vertices) {
        int index = 0;
        for (int vertex : vertices) {
            int value = 1 << vertex;
            index += value;
        }
        return index;
    }

    int getIndexButOne(int exception, Integer... vertices) {
        int index = 0;
        for (int vertex : vertices) {
            if (vertex == exception) continue;
            int value = 1 << vertex;
            index += value;
        }
        return index;
    }


    int getIndex(Collection<Integer> vertices) {
        return getIndex(vertices.toArray(new Integer[0]));
    }

    int getIndexButOne(Collection<Integer> vertices, Integer exception) {
        return getIndexButOne(exception, vertices.toArray(new Integer[0]));
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new StringReader("25\n" +
                "20833.3333 17100.0000\n" +
                "20900.0000 17066.6667\n" +
                "21300.0000 13016.6667\n" +
                "21600.0000 14150.0000\n" +
                "21600.0000 14966.6667\n" +
                "21600.0000 16500.0000\n" +
                "22183.3333 13133.3333\n" +
                "22583.3333 14300.0000\n" +
                "22683.3333 12716.6667\n" +
                "23616.6667 15866.6667\n" +
                "23700.0000 15933.3333\n" +
                "23883.3333 14533.3333\n" +
                "24166.6667 13250.0000\n" +
                "25149.1667 12365.8333\n" +
                "26133.3333 14500.0000\n" +
                "26150.0000 10550.0000\n" +
                "26283.3333 12766.6667\n" +
                "26433.3333 13433.3333\n" +
                "26550.0000 13850.0000\n" +
                "26733.3333 11683.3333\n" +
                "27026.1111 13051.9444\n" +
                "27096.1111 13415.8333\n" +
                "27153.6111 13203.3333\n" +
                "27166.6667 9833.3333\n" +
                "27233.3333 10450.0000\n"));
        int V = scanner.nextInt();
        List<Point> points = new ArrayList<Point>(V);
        for (int i = 0; i < V; i++) {
            points.add(new Point(scanner.nextDouble(), scanner.nextDouble()));
        }
        TravelingSalesmanDynamicProgramming tsp = new TravelingSalesmanDynamicProgramming(points);
        System.out.println(tsp.minimumCost());
    }
}
