public class PointSET {

   private java.util.TreeSet<Point2D> points;

   // construct an empty set of points
   public PointSET() {
     points = new java.util.TreeSet<Point2D>();
   }

   // is the set empty?
   public boolean isEmpty() {
     return points.isEmpty();
   }

   // number of points in the set
   public int size() {
     return points.size();
   }

   // add the point to the set (if it is not already in the set)
   public void insert(Point2D p) {
     points.add(p);
   }

   // does the set contain point p?
   public boolean contains(Point2D p) {
     return points.contains(p);
   }

   // draw all points to standard draw
   public void draw() {
     for (Point2D p : points) {
       p.draw();
     }
   }

   // all points that are inside the rectangle
   public Iterable<Point2D> range(RectHV rect) {
     java.util.ArrayList<Point2D> range = new java.util.ArrayList<Point2D>();
     for (Point2D p : points) {
       if (rect.contains(p)) {
         range.add(p);
       }
     }
     return range;
   }

   // a nearest neighbor in the set to point p; null if the set is empty
   public Point2D nearest(Point2D p) {
     if (points == null || size() == 0) {
       return null;
     }
     else {
       java.util.TreeSet<Point2D> list = new java.util.TreeSet<Point2D>(new DistanceToOrder(p));
       list.addAll(points);
       return list.first();
     }
   }

   // unit testing of the methods (optional)
   public static void main(String[] args) {
   }

    private class DistanceToOrder implements java.util.Comparator<Point2D> {
        private Point2D orig;

        public DistanceToOrder(Point2D orig) {
          this.orig = orig;
        }

        public int compare(Point2D p, Point2D q) {
            double dist1 = orig.distanceSquaredTo(p);
            double dist2 = orig.distanceSquaredTo(q);
            if      (dist1 < dist2) return -1;
            else if (dist1 > dist2) return +1;
            else                    return  0;
        }
    }
}