public class Brute {
   public static void main(String[] args) {
       String append = " -> ";
       In in = new In(args[0]);
       int size = in.readInt();
       Point[] points = new Point[size];
       StdDraw.setXscale(0, 32768);
       StdDraw.setYscale(0, 32768);
       for (int i = 0; i < size; i++) {
           int x = in.readInt();
           int y = in.readInt();
           points[i] = new Point(x, y);
           points[i].draw();
       }

       StringBuffer sb = new StringBuffer();

       for (int p = 0; p < size; p++) {
           Point p1 = points[p];
           for (int q = p + 1; q < size; q++) {
               Point q1 = points[q];
               double slope1 = p1.slopeTo(q1);
               for (int r = q + 1; r < size; r++) {
                   Point r1 = points[r];
                   double slope2 = p1.slopeTo(r1);
                   for (int s = r + 1; s < size; s++) {
                       Point s1 = points[s];
                       double slope3 = p1.slopeTo(s1);
                       if (slope1 == slope2 && slope2 == slope3 && slope1 == slope3) {
                           //order the points
                           Point[] sorted = new Point[]{p1, q1, r1, s1};
                           Insertion.sort(sorted);
                           sb.delete(0, sb.length()).append(sorted[0]).
                             append(append).append(sorted[1]).append(append).
                             append(sorted[2]).append(append).append(sorted[3]);
                           StdOut.println(sb.toString());
                           sorted[0].drawTo(sorted[3]);
                       }
                   }
               }
           }
       }
   }
}
