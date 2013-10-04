/**
 * Created by Sobercheg on 10/3/13.
 */
public interface ShortestPath {
    int distTo(int v);

    boolean hasPathTo(int v);

    Iterable<Edge> pathTo(int v);
}
