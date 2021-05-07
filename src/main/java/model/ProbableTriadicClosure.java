package model;

import java.util.Objects;

public class ProbableTriadicClosure {
    private int designer;
    private int follow1;
    private int follow2;
    private int timeStamp;

    public ProbableTriadicClosure(int designer, int follow1,int follow2, int timeStamp) {
        this.designer = designer;
        this.follow1 = follow1;
        this.follow2 = follow2;
        this.timeStamp = timeStamp;
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

    public int getFollow2() {
        return follow2;
    }

    public void setFollow2(int follow2) {
        this.follow2 = follow2;
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
        ProbableTriadicClosure that = (ProbableTriadicClosure) o;
        return designer == that.designer &&
                follow1 == that.follow1 &&
                follow2 == that.follow2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(designer, follow1, follow2);
    }
}
