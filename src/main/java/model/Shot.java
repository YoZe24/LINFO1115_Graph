package model;

import java.util.Objects;

public class Shot {
    private int id;
    private int nbLikes;

    public Shot(int id, int nbLikes) {
        this.id = id;
        this.nbLikes = nbLikes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbLikes() {
        return nbLikes;
    }

    public void setNbLikes(int nbLikes) {
        this.nbLikes = nbLikes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shot shot = (Shot) o;
        return id == shot.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Shot{" +
                "id=" + id +
                ", nbLikes=" + nbLikes +
                '}';
    }
}
