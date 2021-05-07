package model;

import java.util.Objects;

public class Edge {
    private int v,w;

    public Edge(int v, int w) {
        this.v = v;
        this.w = w;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return v == edge.v &&
                w == edge.w;
    }

    @Override
    public int hashCode() {
        return Objects.hash(v, w);
    }
}

