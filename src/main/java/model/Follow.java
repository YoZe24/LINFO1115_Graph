package model;

import java.util.Objects;

public class Follow {
    private int follower;
    private int followed;
    private int timeStamp;

    public Follow(int follower, int followed, int timeStamp) {
        this.follower = follower;
        this.followed = followed;
        this.timeStamp = timeStamp;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public int getFollowed() {
        return followed;
    }

    public void setFollowed(int followed) {
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
        Follow follow = (Follow) o;
        return (follower == follow.follower &&
                followed == follow.followed) ||
                (followed == follow.follower &&
                follower == follow.followed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(follower, followed);
    }
}
