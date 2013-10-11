package tsp;

import util.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Sobercheg on 10/9/13.
 */
public class TravelingSalesmanDynamicProgrammingTest {

    private TravelingSalesmanDynamicProgramming tsp = new TravelingSalesmanDynamicProgramming(new ArrayList<Point>());

    public static void main(String[] args) {
        TravelingSalesmanDynamicProgrammingTest test = new TravelingSalesmanDynamicProgrammingTest();
        test.testIndex();
        test.testSubsets();
        test.testSolution();
    }

    private void testSolution() {
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(0, 1));
        points.add(new Point(1, 0));
        points.add(new Point(1, 1));
        TravelingSalesmanDynamicProgramming tsp = new TravelingSalesmanDynamicProgramming(points);
        System.out.println(tsp.minimumCost());
    }

    private void testIndex() {
        Assert.equals(tsp.getIndex(0), 1, "{0} -> 1");
        Assert.equals(tsp.getIndex(1), 2, "{1} -> 2");
        Assert.equals(tsp.getIndex(0, 1), 3, "{0,1} -> 3");
        Assert.equals(tsp.getIndex(7), 128, "{7} -> 128");
        Assert.equals(tsp.getIndex(7, 0), 129, "{7,0} -> 129");
    }

    private void testSubsets() {
        HashSet<Integer> source = new HashSet<Integer>();
        source.add(0);
        source.add(1);
        source.add(2);
        source.add(3);
        System.out.println(tsp.getSubsetsOfSize(source, 2));
    }

}
