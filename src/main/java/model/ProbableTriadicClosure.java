package model;

import java.util.Objects;

public class ProbableTriadicClosure {
    private int designer;
    private int followed;
    private int probableFollowed;
    private int timeStamp,timeStampRealised;

    public ProbableTriadicClosure(int designer, int followed, int probableFollowed, int timeStamp) {
        this.designer = designer;
        this.followed = followed;
        this.probableFollowed = probableFollowed;
        this.timeStamp = timeStamp;
        this.timeStampRealised = 0;
    }

    public int getDesigner() {
        return designer;
    }

    public void setDesigner(int designer) {
        this.designer = designer;
    }

    public int getFollowed() {
        return followed;
    }

    public void setFollowed(int followed) {
        this.followed = followed;
    }

    public int getProbableFollowed() {
        return probableFollowed;
    }

    public void setProbableFollowed(int probableFollowed) {
        this.probableFollowed = probableFollowed;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getTimeStampRealised() {
        return timeStampRealised;
    }

    public void setTimeStampRealised(int timeStampRealised) {
        this.timeStampRealised = timeStampRealised;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProbableTriadicClosure that = (ProbableTriadicClosure) o;
        return designer == that.designer &&
                followed == that.followed &&
                probableFollowed == that.probableFollowed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(designer, followed, probableFollowed);
    }
}
