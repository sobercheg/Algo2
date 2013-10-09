package knapsack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Sobercheg on 9/27/13.
 * In this programming problem and the next you'll code up the knapsack algorithm from lecture. Let's start with a warm-up. Download the text file here. This file describes a knapsack instance, and it has the following format:
 * [knapsack_size][number_of_items]
 * [value_1] [weight_1]
 * [value_2] [weight_2]
 * ...
 * For example, the third line of the file is "50074 659", indicating that the second item has value 50074 and size 659, respectively.
 * <p/>
 * You can assume that all numbers are positive. You should assume that item weights and the knapsack capacity are integers.
 * <p/>
 * In the box below, type in the value of the optimal solution.
 * <p/>
 * ADVICE: If you're not getting the correct answer, try debugging your algorithm using some small test cases. And then post them to the discussion forum!
 */
public class KnapsackNaive {

    protected final int knapsackSize;
    protected final int numberOfItems;
    protected final int[] values;
    protected final int[] weights;

    /**
     * [knapsack_size][number_of_items]
     * [value_1] [weight_1]
     * [value_2] [weight_2]
     */
    public KnapsackNaive(String file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(file));
        knapsackSize = scanner.nextInt();
        numberOfItems = scanner.nextInt();
        values = new int[numberOfItems];
        weights = new int[numberOfItems];
        for (int i = 0; i < numberOfItems; i++) {
            if (!scanner.hasNextInt())
                throw new IllegalArgumentException("No more input values on i=" + i + ", total=" + numberOfItems);
            values[i] = scanner.nextInt();
            weights[i] = scanner.nextInt();
        }
    }

    public int getOptimalSolution() {
        int[][] A = new int[numberOfItems + 1][knapsackSize + 1];
        for (int i = 0; i < numberOfItems; i++) {
            for (int x = 0; x <= knapsackSize; x++) {
                if (x < weights[i]) A[i + 1][x] = A[i][x];
                else
                    A[i + 1][x] = Math.max(A[i][x], A[i][x - weights[i]] + values[i]);
            }
        }

        return A[numberOfItems][knapsackSize];
    }

    public static void main(String[] args) throws FileNotFoundException {
        KnapsackNaive knapsack = new KnapsackNaive("D:\\Dropbox\\Learning\\Coursera\\knapsack1.txt");
        System.out.println(knapsack.getOptimalSolution());
    }

}
