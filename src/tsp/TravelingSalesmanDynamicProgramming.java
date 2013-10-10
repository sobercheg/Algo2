package tsp;

import shortestpath.Edge;
import shortestpath.WeightedGraph;

import java.awt.*;
import java.util.List;


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
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.size(); j++) {
                Point from = points.get(i);
                Point to = points.get(j);
                if (from.equals(to)) continue;
                double distance = Math.sqrt((from.x - to.x) * (from.x - to.x) + (from.y - to.y) * (from.y - to.y));
                graph.addEdge(new Edge(i, j, distance));
                graph.addEdge(new Edge(j, i, distance));
            }
        }
    }

    public double minimumCost() {
        double A[][] = new double[(int) Math.pow(2, graph.getV())][graph.getV()];

        // Step 1. Init the solution matrix
        for (int i = 0; i < A.length; i++) {

        }


        for (int m = 2; m <= graph.getV(); m++) {

        }


        return 0;
    }

    int getIndex(int... vertices) {
        int index = 0;
        for (int vertex : vertices) {
            int value = 1 << vertex;
            index += value;
        }
        return index;
    }
}
