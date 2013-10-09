package shortestpath;

/**
* Created by Sobercheg on 10/4/13.
*/
final class VertexMarker implements Comparable<VertexMarker> {
    private final int vertex;
    private final int distance;

    VertexMarker(int vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    @Override
    public int compareTo(VertexMarker o) {
        return Integer.compare(distance, o.distance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VertexMarker that = (VertexMarker) o;

        if (distance != that.distance) return false;
        if (vertex != that.vertex) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = vertex;
        result = 31 * result + distance;
        return result;
    }

    public int getVertex() {
        return vertex;
    }


    public int getDistance() {
        return distance;
    }
}
