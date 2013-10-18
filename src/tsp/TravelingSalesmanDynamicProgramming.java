package tsp;

import com.google.common.collect.Sets;
import graph.Edge;
import graph.WeightedGraph;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.*;


/**
 * Created by Sobercheg on 10/9/13.
 */
public class TravelingSalesmanDynamicProgramming {
    private static final int BUCKETS = 31;
    private static final int MAX_WEIGHT_HEURISTIC = 3500;
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
        Map<Integer, Double>[][] A = (Map<Integer, Double>[][]) new Map[number][BUCKETS];
        Map<Integer, Double>[][] Aprev = (Map<Integer, Double>[][]) new Map[number][BUCKETS];

        // Step 1. Init the solution matrix
        for (int b = 0; b < BUCKETS; b++) {
            for (int i = 0; i < A.length; i++) {
                Aprev[i][b] = new HashMap<Integer, Double>();
                A[i][b] = new HashMap<Integer, Double>();
            }
        }
        put(Aprev[0], getIndex(0), 0D);

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
            sets[set.size()].add(set);
            counter++;
        }

        double weights[][] = new double[number][number];
        for (int i = 0; i < number; i++) {
            for (int j = 0; j < number; j++) {
                Edge kjEdge = graph.getEdge(i, j);
                weights[i][j] = kjEdge == null ? Double.POSITIVE_INFINITY : kjEdge.getWeight();
            }
        }

        int heuristicSkipped = 0;
        int heuristicHit = 0;
        for (int m = 2; m <= graph.getV(); m++) {
            System.out.println("Subset size=" + m);
            sets[m - 1] = null; // GC optimization
            A = (Map<Integer, Double>[][]) new Map[number][BUCKETS];
            for (int b = 0; b < BUCKETS; b++) {

                for (int i = 0; i < A.length; i++) {
                    A[i][b] = new HashMap<Integer, Double>();
                }
            }
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
                        // heuristic
                        if (weights[k][j] > MAX_WEIGHT_HEURISTIC) {
                            heuristicSkipped++;
                            if (heuristicSkipped % 1000000 == 0)
                                System.out.println("Heuristic skipped " + heuristicSkipped);
                            continue;
                        }
                        heuristicHit++;
                        if (heuristicHit % 1000000 == 0) System.out.println("Heuristic hit " + heuristicHit);

                        Double value = get(Aprev[k], subsetMinusJIndex);
                        double previousSolution = (value != null) ?
                                (value + weights[k][j]) : Double.POSITIVE_INFINITY;
                        if (previousSolution <= bestSolution) {
                            bestSolution = previousSolution;
                        }
                    }
                    put(A[j], subsetIndex, bestSolution);
                }
            }
            System.out.println("m=" + m + ": ");
            for (int b = 0; b < BUCKETS; b++)
                System.out.println("Map [" + b + "] size=" + A[1][b].size() + "; " + (A[1][b].size() * number * 12 / 1024 / 1024) + " MB");
            Aprev = A;

        }

        int initialSetIndex = getIndex(initialSet);
        double minSolution = Double.POSITIVE_INFINITY;
        for (int i = 1; i < number; i++) {
            double solution = get(A[i], initialSetIndex) + graph.getEdge(i, 0).getWeight();
            if (solution < minSolution) {
                minSolution = solution;
            }
        }

        return minSolution;
    }

    private void put(Map<Integer, Double>[] maps, int key, double value) {
        maps[key % BUCKETS].put(key, value);
    }

    private Double get(Map<Integer, Double>[] maps, int key) {
        return maps[key % BUCKETS].get(key);
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
        Scanner scanner = new Scanner(new StringReader("22\n" +
//                "20833.3333 17100.0000\n" +
//                "20900.0000 17066.6667\n" +
//                "21300.0000 13016.6667\n" +
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

  /*      Scanner scanner = new Scanner(new StringReader("24\n" +
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
                "27126.1111 13151.9444\n" +
                "23126.1111 12151.9444\n" +
                "27233.3333 10450.0000\n"));
*/

        int V = scanner.nextInt();
        List<Point> points = new ArrayList<Point>(V);
        for (int i = 0; i < V; i++) {
            points.add(new Point(scanner.nextDouble(), scanner.nextDouble()));
        }
        TravelingSalesmanDynamicProgramming tsp = new TravelingSalesmanDynamicProgramming(points);
        System.out.println(tsp.minimumCost());
    }
}
