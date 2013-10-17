package sat;

import graph.Edge;
import graph.KosarajuSCC;
import graph.SCC;
import graph.WeightedGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Sobercheg on 10/16/13.
 * <p/>
 * Suppose 2-SAT formula has n variables
 * 1. create a graph G containing 2n vertices, labeling them 1,2,...,n,-1,-2,...,-n
 * 2. for each clause (x_i V x_j)
 * - add edges (-i,j) and (-j,i) to G
 * 3. compute all SCCs in G (by applying DFS in 2 rounds)
 * 4. for each SCC G'
 * - create a hash set S, and add all the labels of vertices in G' to S
 * - for each vertex v in G'
 * - check if -v.label is in S
 * - if yes, terminate and report UNSATISFIABLE
 * 5. report SATISFIABLE
 * <p/>
 * 4'. for each vertex u in G
 * - get vertex v such that u.label == -v.label
 * - check if u and v share the same leader
 * - if yes, terminate and report UNSATISFIABLE
 */
public class SATSCC implements SAT {

    private List<Clause> clauses;
    private Set<Integer> implicationLabels;
    private WeightedGraph implicationGraph;
    private int maxLabel;

    public SATSCC(List<Clause> clauses) {
        this.clauses = clauses;
        implicationLabels = new HashSet<Integer>();
        maxLabel = 0;
        for (Clause clause : clauses) {
            implicationLabels.add(clause.x);
            implicationLabels.add(clause.y);
            maxLabel = Math.max(maxLabel, Math.abs(clause.x));
            maxLabel = Math.max(maxLabel, Math.abs(clause.y));
        }

        implicationGraph = getImplicationGraph(maxLabel * 2 + 1);
    }

    public boolean isSatisfiable() {

        SCC scc = new KosarajuSCC(implicationGraph);
        for (int label : implicationLabels) {
            if (implicationLabels.contains(-label) && scc.stronglyConnected(vertex(label), vertex(-label))) {
                return false;
            }
        }

        return true;
    }

    WeightedGraph getImplicationGraph(int size) {
        WeightedGraph graph = new WeightedGraph(size);
        for (Clause clause : clauses) {
            graph.addEdge(new Edge(vertex(-clause.x), vertex(clause.y), 1));
            graph.addEdge(new Edge(vertex(-clause.y), vertex(clause.x), 1));
        }

        return graph;
    }

    private int vertex(int label) {
        return label + maxLabel;
    }

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length < 1) throw new IllegalArgumentException("File path(s) expected");
        for (String arg : args) {
            Scanner scanner = new Scanner(new File(arg));
            int num = scanner.nextInt();
            List<Clause> clauses = new ArrayList<Clause>();
            for (int i = 0; i < num; i++) {
                clauses.add(new Clause(scanner.nextInt(), scanner.nextInt()));
            }
            SAT sat = new SATSCC(clauses);
            System.out.println(arg + " is satisfiable: " + sat.isSatisfiable());
        }
    }

}
