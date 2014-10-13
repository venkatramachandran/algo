import java.util.Comparator;
import java.util.List;

public class KdTree {

   private Node head;
   private int size;
   public static final Comparator<Point2D> X_ORDER = new XOrder();
   public static final Comparator<Point2D> Y_ORDER = new YOrder();

   // construct an empty set of points
   public KdTree() {
     head = null;
     size = 0;
   }

   // is the set empty?
   public boolean isEmpty() {
     return size == 0;
   }

   // number of points in the set
   public int size() {
     return size;
   }

   // add the point to the set (if it is not already in the set)
   public void insert(Point2D p) {
     head = insert(head, p, true);
     ++size;
   }

   private Node insert(Node x, Point2D p, boolean xlevel) {
     if (x == null) return new Node(p, xlevel, new RectHV(0, 0, 1, 1));

     int cmp = xlevel ? X_ORDER.compare(p, x.getPoint()) : Y_ORDER.compare(p, x.getPoint());

     if      (cmp < 0) x.setLeft(insert(x.getLeft(),  p, !xlevel));
     else if (cmp > 0) x.setRight(insert(x.getRight(), p, !xlevel));
     return x;
   }

   // does the set contain point p?
   public boolean contains(Point2D p) {
     return contains(head, p, true);
   }

    private boolean contains(Node x, Point2D p, boolean xlevel) {
      // Return value associated with key in the subtree rooted at x;
      // return null if key not present in subtree rooted at x.
      if (x == null) return false;

      int cmp = xlevel ? X_ORDER.compare(p, x.getPoint()) : Y_ORDER.compare(p, x.getPoint());

      if (cmp < 0) return contains(x.getLeft(), p, !xlevel);
      else if (cmp > 0) return contains(x.getRight(), p, !xlevel);
      else return x.getPoint().equals(p);
    }

   // draw all points to standard draw
   public void draw() {
     StdDraw.line(0, 0, 0, 1);
     StdDraw.line(0, 1, 1, 1);
     StdDraw.line(1, 1, 1, 0);
     StdDraw.line(1, 0, 0, 0);
     draw(head, true);
   }

   private void draw(Node x, boolean xlevel) {
     if (xlevel) {
       StdDraw.setPenColor(StdDraw.RED);
       //draw a line between ymin/ymax on x
       StdDraw.line(x.getPoint().x(), x.getRect().ymin(), x.getPoint().x(), x.getRect().ymax());
       StdDraw.setPenRadius(0.01);
       StdDraw.setPenColor(StdDraw.BLACK);
       StdDraw.point(x.getPoint().x(),x.getPoint().y());
       StdDraw.setPenRadius();
     }
     else {
       StdDraw.setPenColor(StdDraw.BLUE);
       //draw a line between xmin/xmax on y
       StdDraw.line(x.getRect().xmin(), x.getPoint().y(), x.getRect().xmax(), x.getPoint().y());
       StdDraw.setPenRadius(0.01);
       StdDraw.setPenColor(StdDraw.BLACK);
       StdDraw.point(x.getPoint().x(),x.getPoint().y());
       StdDraw.setPenRadius();
     }
     if (x.getRight() != null) draw(x.getRight(), !xlevel);
     if (x.getLeft() != null) draw(x.getLeft(), !xlevel);
   }

   // all points that are inside the rectangle
   public Iterable<Point2D> range(RectHV rect) {
     java.util.ArrayList<Point2D> range = new java.util.ArrayList<Point2D>();
     java.util.Stack<Node> nodes = new java.util.Stack<Node>();
     if (head.getLeft() != null) nodes.push(head.getLeft());
     if (head.getRight() != null) nodes.push(head.getRight());
     while (!nodes.isEmpty()) {
       Node n = nodes.pop();
       if (n.getRect().intersects(rect)) {
         if (rect.contains(n.getPoint())) {
           range.add(n.getPoint());
         }
         if (n.getLeft() != null) nodes.push(n.getLeft());
         if (n.getRight() != null) nodes.push(n.getRight());
       }
     }
     return range;
   }

   // a nearest neighbor in the set to point p; null if the set is empty
   public Point2D nearest(Point2D p) {
     if (size == 0) {
       return null;
     }
     else {
       //TODO
       return null;
     }
   }

   // unit testing of the methods (optional)
   public static void main(String[] args) {
     In in = new In(args[0]);
     KdTree kdtree = new KdTree();
     while (!in.isEmpty()) {
       double x = in.readDouble();
       double y = in.readDouble();
       Point2D p = new Point2D(x, y);
       kdtree.insert(p);
     }
     kdtree.draw();
   }

    private static class DistanceToOrder implements java.util.Comparator<Point2D> {
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

    // compare points according to their x-coordinate
    private static class XOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            if (p.x() < q.x()) return -1;
            if (p.x() > q.x()) return +1;
            return 0;
        }
    }

    // compare points according to their y-coordinate
    private static class YOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            if (p.y() < q.y()) return -1;
            if (p.y() > q.y()) return +1;
            return 0;
        }
    }

    private static class Node {
       private Point2D p;      // the point
       private Node lb;        // the left/bottom subtree
       private Node rt;        // the right/top subtree
       private boolean xlevel; //whether we are at the x split level
       private RectHV rect;

       public Node(Point2D p) {
         this(p, null, null, true, null);
       }

       public Node(Point2D p, boolean xlevel, RectHV rect) {
         this(p, null, null, xlevel, rect);
       }

       public Node(Point2D p, Node lb, Node rt, boolean xlevel, RectHV rect) {
         this.p = p;
         this.lb = lb;
         this.rt = rt;
         this.xlevel = xlevel;
         this.rect = rect;
       }

       public Point2D getPoint() {
         return p;
       }

       public void setLeft(Node lb) {
         this.lb = lb;

         double xmin = rect.xmin();
         double ymin = rect.ymin();
         double xmax = rect.xmax();
         double ymax = rect.ymax();

         if (xlevel) {
           //set left
           lb.setRect(new RectHV(xmin, ymin, p.x(), ymax));
         }
         else {
           //set bottom
           lb.setRect(new RectHV(xmin, ymin, xmax, p.y()));
         }
       }

       public void setRight(Node rt) {
         this.rt = rt;

         double xmin = rect.xmin();
         double ymin = rect.ymin();
         double xmax = rect.xmax();
         double ymax = rect.ymax();

         if (xlevel) {
           //set right
           rt.setRect(new RectHV(p.x(), ymin, xmax, ymax));
         }
         else {
           //set top
           rt.setRect(new RectHV(xmin, p.y(), xmax, ymax));
         }

       }

       public Node getLeft() {
         return lb;
       }

       public Node getRight() {
         return rt;
       }

       public boolean getXLevel() {
         return xlevel;
       }

       public RectHV getRect() {
         return rect;
       }

       public void setRect(RectHV rect) {
         this.rect = rect;
       }
    }
}