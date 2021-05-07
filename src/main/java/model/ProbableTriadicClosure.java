package model;

import java.util.Objects;

public class ProbableTriadicClosure {
    private int designer;
    private int follow1;
    private int probableFollowed;
    private int timeStamp,timeStampRealised;

    public ProbableTriadicClosure(int designer, int follow1, int probableFollowed, int timeStamp) {
        this.designer = designer;
        this.follow1 = follow1;
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

    public int getFollow1() {
        return follow1;
    }

    public void setFollow1(int follow1) {
        this.follow1 = follow1;
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
                follow1 == that.follow1 &&
                probableFollowed == that.probableFollowed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(designer, follow1, probableFollowed);
    }
}
