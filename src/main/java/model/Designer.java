package model;

import java.util.HashSet;
import java.util.Objects;

public class Designer {
    private int id;
    private String location;
    private HashSet<Shot> shots;

    public Designer(int id) {
        this.id = id;
        this.shots = new HashSet<>();
    }

    public Designer(int id, String location, HashSet<Shot> shots) {
        this.id = id;
        this.location = location;
        this.shots = shots;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public HashSet<Shot> getShots() {
        return shots;
    }

    public void setShots(HashSet<Shot> shots) {
        this.shots = shots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Designer designer = (Designer) o;
        return id == designer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Designer{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", shots=" + shots +
                '}';
    }
}