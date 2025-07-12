/** A class that represents a path via pursuit curves. */
public class Path {

    private Point curr;
    private Point next;
    public Path(double x,double y) {
        curr = new Point(0,0);
        next = new Point(x,y);
        double z = curr.getX();

    }
    public double getCurrX() {
        return curr.getX();
    }
    public double getCurrY() {
        return curr.getY();
    }
    public double getNextX() {
        return next.getX();
    }
    public double getNextY() {
        return next.getY();
    }
    public Point getCurrentPoint() {
        return curr;
    }
    public void setCurrentPoint(Point point) {
        curr.setX(point.getX());
        curr.setY(point.getY());

    }
    public void iterate(double dx,double dy) {
        curr.setX(getNextX());
        curr.setY(getNextY());
        next.setX(getNextX() + dx);
        next.setY(getNextY() + dy);
    }
}
