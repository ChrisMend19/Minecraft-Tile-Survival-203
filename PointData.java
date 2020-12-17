public class PointData {
    private int g;
    private int h;
    private int f;
    private PointData previous;
    private Point current;

    public PointData(PointData previous, Point current, int g, int h, int f){
        this.g = g;
        this.h = h;
        this.f = f;
        this.previous = previous;
        this.current = current;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public int getF() {
        return f;
    }

    public PointData getPrevious() {
        return previous;
    }

    public Point getCurrent() {
        return current;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void setPrevious(PointData previous) {
        this.previous = previous;
    }

    public void setCurrent(Point current) {
        this.current = current;
    }

    public boolean compareLowerG(PointData other){
        if(this.getG() > other.getG()+1){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Node: " +
                "g=" + g +
                ", h=" + h +
                ", f=" + f +
                ", previous=" + previous  +
                ", current=" + current + "\n";
    }


}

