package model;

import java.util.Objects;

public class Followed {
    private Designer who;
    private int timeStamp;

    public Followed(Designer who, int timeStamp) {
        this.who = who;
        this.timeStamp = timeStamp;
    }

    public Designer getWho() {
        return who;
    }

    public void setWho(Designer who) {
        this.who = who;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Followed followed = (Followed) o;
        return timeStamp == followed.timeStamp && this.who.equals(followed.who);
    }

    @Override
    public int hashCode() {
        return Objects.hash(who, timeStamp);
    }

    @Override
    public String toString() {
        return "Followed{" +
                "who=" + who +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
