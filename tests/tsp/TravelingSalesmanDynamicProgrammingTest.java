package tsp;

import com.google.common.collect.Sets;
import util.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Sobercheg on 10/9/13.
 */
public class TravelingSalesmanDynamicProgrammingTest {

    private TravelingSalesmanDynamicProgramming tsp = new TravelingSalesmanDynamicProgramming(new ArrayList<Point>());

    public static void main(String[] args) {
        TravelingSalesmanDynamicProgrammingTest test = new TravelingSalesmanDynamicProgrammingTest();
        test.testIndex();
//        test.testSubsets();
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
        Set<Integer> source = new HashSet<Integer>();
        int number = 25;
        List<Set<Integer>>[] sets = (List<Set<Integer>>[]) new List[number + 1];
        for (int i = 0; i < number; i++) {
            source.add(i);
            sets[i] = new ArrayList<Set<Integer>>();
        }
        sets[number] = new ArrayList<Set<Integer>>();

        Set<Set<Integer>> powerSet = Sets.powerSet(source);
        System.out.println("Adding [" + powerSet.size() + "] sets to the array...");
        int counter = 0;
        for (Set<Integer> set : powerSet) {
            if (counter % 10000 == 0) System.out.println("[" + counter + "]");
            sets[set.size()].add(set);
            counter++;
        }

        for (int i = 0; i < number; i++)
            System.out.println("# of subsets of size [" + i + "] is [" + sets[i].size() + "]");

    }

}
