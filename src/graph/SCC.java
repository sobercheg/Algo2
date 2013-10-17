package graph;

/**
 * Created by Sobercheg on 10/16/13.
 */
public interface SCC {
    boolean stronglyConnected(int u, int v);

    int id(int v);

    int count();
}
