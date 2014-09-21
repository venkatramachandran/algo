/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new PointComparator(this);       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        int x0 = this.x;
        int y0 = this.y;
        int x1 = that.x;
        int y1 = that.y;
        double slope = 0.0d;
        if (x1 == x0 && y1 == y0) {
            slope = Double.POSITIVE_INFINITY;
        }
        else if (x1 != x0 && y1 == y0) {
            slope = +0.0d;
        }
        else if (x1 != x0 && y1 == y0) {
            slope = Double.NEGATIVE_INFINITY;
        }
        else {
            slope = (double) (y1 - y0) / (double) (x1 - x0);
        }
        return slope;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        int retval = 0;
        if (this.y < that.y) {
            retval = -1;
        }
        else if (this.y == that.y && this.x < that.x) {
            retval = -1;
        }
        else if (this.y == that.y && this.x > that.x) {
            retval = 1;
        }
        return retval;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }

    private class PointComparator implements Comparator<Point> {
        private Point x0;

        PointComparator(Point x0) {
            this.x0 = x0;
        }

        public int compare(Point x1, Point x2) {
            int retval = 0;
            double slope1 = x0.slopeTo(x1);
            double slope2 = x0.slopeTo(x2);

            if (slope1 < slope2) {
                retval = -1;
            }
            else if (slope1 > slope2) {
                retval = +1;
            }
            return retval;
        }
    }
}