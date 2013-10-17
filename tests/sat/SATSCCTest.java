package sat;

import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sobercheg on 10/16/13.
 */
public class SATSCCTest {

    public static void main(String[] args) {
        SATSCCTest test = new SATSCCTest();
        test.testSAT();
    }

    private void testSAT() {
        List<Clause> clauses = new ArrayList<Clause>();
        clauses.add(new Clause(1, 2));
        clauses.add(new Clause(-1, -2));
        clauses.add(new Clause(-1, 2));
        SATSCC sat = new SATSCC(clauses);
        Assert.assertTrue(sat.isSatisfiable(), clauses + " should be satisfiable");
    }
}
