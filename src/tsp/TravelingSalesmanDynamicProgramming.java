package tsp;

import shortestpath.Edge;
import shortestpath.WeightedGraph;

import java.io.File;
import java.io.FileNotFoundException;
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
//                graph.addEdge(new Edge(j, i, distance));
            }
        }
        System.out.println("Initialized graph successfully");
    }

    public double minimumCost() {
        int subsetNum = (int) Math.pow(2, graph.getV());
        System.out.println("Initializing solution matrix of size[" + subsetNum + "][" + graph.getV() + "]...");
        double A[][] = new double[subsetNum][graph.getV()];
        // Step 1. Init the solution matrix
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                A[i][j] = Double.POSITIVE_INFINITY;
            }
        }

        A[getIndex(0)][0] = 0;
        System.out.println("Initialized solution matrix successfully");

        Set<Integer> initialSet = new HashSet<Integer>();
        for (int i = 0; i < graph.getV(); i++) {
            initialSet.add(i);
        }

        for (int m = 2; m <= graph.getV(); m++) {
            System.out.println("Subset size=" + m);
            for (Set<Integer> subset : getSubsetsOfSize(initialSet, m)) {
                if (!subset.contains(0)) continue;
                for (int j : subset) {
                    if (j == 0) continue;
                    Set<Integer> subsetMinusJ = new HashSet<Integer>(subset);
                    subsetMinusJ.remove(j);
                    double bestSolution = Double.POSITIVE_INFINITY;
                    for (int k : subset) {
                        if (k == j) continue;
                        Edge kjEdge = graph.getEdge(k, j);
                        double edgeCost = kjEdge == null ? Double.POSITIVE_INFINITY : kjEdge.getWeight();
                        double previousSolution = A[getIndex(subsetMinusJ)][k] + edgeCost;
                        if (previousSolution < bestSolution) {
                            bestSolution = previousSolution;
                            A[getIndex(subset)][j] = bestSolution;
                        }
                    }
                }
            }
        }

        double minSolution = Double.POSITIVE_INFINITY;
        for (int i = 1; i < graph.getV(); i++) {
            double solution = A[getIndex(initialSet)][i] + graph.getEdge(i, 0).getWeight();
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

    int getIndex(Set<Integer> vertices) {
        return getIndex(vertices.toArray(new Integer[0]));
    }

    Set<Set<Integer>> getSubsetsOfSize(Set<Integer> source, int size) {
        return RecursivePowerKSet.computeKPowerSet(source, size);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("D:\\Dropbox\\Learning\\Coursera\\tsp.txt"));
        int V = scanner.nextInt();
        List<Point> points = new ArrayList<Point>(V);
        for (int i = 0; i < V; i++) {
            points.add(new Point(scanner.nextDouble(), scanner.nextDouble()));
        }
        TravelingSalesmanDynamicProgramming tsp = new TravelingSalesmanDynamicProgramming(points);
        System.out.println(tsp.minimumCost());
    }
}
