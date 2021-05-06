package model;

import java.util.Objects;

public class Followed {
    private Designer follower;
    private Designer who;
    private int timeStamp;

    public Followed(Designer follower, Designer who, int timeStamp) {
        this.follower = follower;
        this.who = who;
        this.timeStamp = timeStamp;
    }

    public Designer getFollower() { return follower; }

    public void setFollower(Designer follower) { this.follower = follower; }

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
        return timeStamp == followed.timeStamp && follower.equals(followed.follower) && who.equals(followed.who);
    }

    @Override
    public int hashCode() {
        return Objects.hash(follower, who, timeStamp);
    }

    @Override
    public String toString() {
        return "Followed{" +
                "follower=" + follower +
                ", who=" + who +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
