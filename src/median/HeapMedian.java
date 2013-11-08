package median;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Sobercheg on 11/7/13.
 */
public class HeapMedian {

    private static final int INITIAL_CAPACITY = 10;
    private Queue<Long> minHeap = new PriorityQueue<Long>(INITIAL_CAPACITY, new Comparator<Long>() {
        @Override
        public int compare(Long o1, Long o2) {
            return o2.compareTo(o1);
        }
    });

    private Queue<Long> maxHeap = new PriorityQueue<Long>(INITIAL_CAPACITY);

    private double currMedian = Double.MIN_VALUE;

    public void addElement(long val) {

        if (minHeap.size() == 0) {
            minHeap.offer(val);
        } else if (maxHeap.size() == 0) {
            maxHeap.offer(val);
        } else if (val < currMedian) {
            maxHeap.offer(val);
        } else {
            minHeap.offer(val);
        }

        if (maxHeap.size() == 1 && minHeap.size() == 1 && minHeap.peek() < maxHeap.peek()) {
            // swap queues
            Queue<Long> tmp = minHeap;
            minHeap = maxHeap;
            maxHeap = tmp;
        }

        // queue balancing
        if (maxHeap.size() - minHeap.size() > 1) {
            minHeap.offer(maxHeap.poll());
        } else if (minHeap.size() - maxHeap.size() > 1) {
            maxHeap.offer(minHeap.poll());
        }

        // calculate median
        if (maxHeap.size() > minHeap.size()) {
            currMedian = maxHeap.peek();
        } else if (maxHeap.size() < minHeap.size()) {
            currMedian = minHeap.peek();
        } else {
            currMedian = ((double) maxHeap.peek() + minHeap.peek()) / 2;
        }

    }

    public double getCurrentMedian() {
        return currMedian;
    }

    public static void main(String[] args) {
        HeapMedian median = new HeapMedian();
        System.out.println("Empty median: " + median.getCurrentMedian());
        median.addElement(2);
        System.out.println("{2} median: " + median.getCurrentMedian());
        median.addElement(3);
        System.out.println("{2,3} median: " + median.getCurrentMedian());
        median.addElement(4);
        System.out.println("{2,3,4} median: " + median.getCurrentMedian());
        median.addElement(5);
        System.out.println("{2,3,4,5} median: " + median.getCurrentMedian());
        median.addElement(6);
        System.out.println("{2,3,4,5,6} median: " + median.getCurrentMedian());


    }

}
