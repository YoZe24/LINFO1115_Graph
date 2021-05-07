package model;

import java.util.Objects;

public class Follow2 {
    private Designer follower;
    private Designer followed;
    private int timeStamp;

    public Follow2(Designer follower, Designer followed, int timeStamp) {
        this.follower = follower;
        this.followed = followed;
        this.timeStamp = timeStamp;
    }

    public Designer getFollower() { return follower; }

    public void setFollower(Designer follower) { this.follower = follower; }

    public Designer getFollowed() {
        return followed;
    }

    public void setFollowed(Designer followed) {
        this.followed = followed;
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
        Follow2 follow = (Follow2) o;
        return timeStamp == follow.timeStamp && follower.equals(follow.follower) && followed.equals(follow.followed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(follower, followed, timeStamp);
    }

    @Override
    public String toString() {
        return "Followed{" +
                "follower=" + follower +
                ", who=" + followed +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
