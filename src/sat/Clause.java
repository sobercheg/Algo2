package sat;

/**
 * Created by Sobercheg on 10/16/13.
 */
public class Clause {
    int x;
    int y;

    public Clause(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return (x < 0 ? "-" : "") + "x" + Math.abs(x) + " v " + (y < 0 ? "-" : "") + "x" + Math.abs(y);
    }
}
