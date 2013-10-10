package tsp;

import util.Assert;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Sobercheg on 10/9/13.
 */
public class TravelingSalesmanDynamicProgrammingTest {

    public static void main(String[] args) {
        TravelingSalesmanDynamicProgrammingTest test = new TravelingSalesmanDynamicProgrammingTest();
        test.testIndex();
    }

    private void testIndex() {
        TravelingSalesmanDynamicProgramming tsp = new TravelingSalesmanDynamicProgramming(new ArrayList<Point>());
        Assert.equals(tsp.getIndex(0),1, "{0} -> 1");
        Assert.equals(tsp.getIndex(1),2, "{1} -> 2");
        Assert.equals(tsp.getIndex(0,1),3, "{0,1} -> 3");
        Assert.equals(tsp.getIndex(7),128, "{7} -> 128");
        Assert.equals(tsp.getIndex(7,0),129, "{7,0} -> 129");
    }

}
