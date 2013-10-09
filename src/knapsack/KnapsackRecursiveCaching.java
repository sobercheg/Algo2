package knapsack;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sobercheg on 9/27/13.
 */
public class KnapsackRecursiveCaching extends KnapsackNaive {

    private final Map<Integer, Integer>[] solutionCache;

    /**
     * [knapsack_size][number_of_items]
     * [value_1] [weight_1]
     * [value_2] [weight_2]
     */
    public KnapsackRecursiveCaching(String file) throws FileNotFoundException {
        super(file);
        solutionCache = (HashMap<Integer, Integer>[]) new HashMap[numberOfItems + 1];
    }

    @Override
    public int getOptimalSolution() {
        return getOptimalSolution(numberOfItems, knapsackSize);
    }

    private int getOptimalSolution(int item, int size) {
        if (item == 0) return 0;
        if (solutionCache[item] != null && solutionCache[item].containsKey(size)) return solutionCache[item].get(size);

        int previous = solutionCache[item - 1] != null && solutionCache[item - 1].get(size) != null ? solutionCache[item - 1].get(size) :
                getOptimalSolution(item - 1, size);
        if (size < weights[item - 1]) return previous;

        int lessSize = size - weights[item - 1];
        int withItem = solutionCache[item - 1] != null && solutionCache[item - 1].containsKey(lessSize) ? solutionCache[item - 1].get(lessSize) :
                getOptimalSolution(item - 1, size - weights[item - 1]);
        int max = previous > withItem + values[item - 1] ? previous : withItem + values[item - 1];
        if (solutionCache[item] == null) solutionCache[item] = new HashMap<Integer, Integer>();
        solutionCache[item].put(size, max);
        return max;

    }

    public static void main(String[] args) throws FileNotFoundException {
        KnapsackRecursiveCaching knapsack = new KnapsackRecursiveCaching("D:\\Dropbox\\Learning\\Coursera\\knapsack_big.txt");
        System.out.println(knapsack.getOptimalSolution());
    }
}

