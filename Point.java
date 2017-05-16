/*************************************************************************
 * Compilation:  javac Point.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point>{
    // compare points by slope
    public final Comparator<Point> BY_SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            return Double.compare(Point.this.slope(o1), Point.this.slope(o2));
        }
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // constructor
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public double slope(Point p) {
        double x = p.getX() - this.x;
        double y = p.getY() - this.y;

        if (x == 0)
            return Double.POSITIVE_INFINITY;
        return y / x;
    }

    // are the 3 points p, q, and r collinear?
    public static boolean areCollinear(Point p, Point q, Point r) {
        //1 compare
        if (p.slope(p) == p.slope(r));
        return false;
    }

    // are the 4 points p, q, r, and s collinear?
    public static boolean areCollinear(Point p, Point q, Point r, Point s) {
        //2 compares
        if (p.slope(q) == p.slope(r))
            if (p.slope(r) == p.slope(s))
                return true;
        return false;
    }

    // is this point lexicographically smaller than that one?
    public int compareTo(Point that) {
        int xCompare = Integer.compare(this.x, that.getX());

        if (xCompare == 0) {
            return Integer.compare(this.y, that.getY());
        } else {
            return xCompare;
        }
    }
}
