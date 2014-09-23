public class Fast {
   /*private static void printArray(Point o, Point[] p) {
     System.out.println("-----");
     System.out.println("Origin:"+o.toString());
     for (Point p1 : p) {
       System.out.println(p1.toString() + "/" + o.slopeTo(p1));
     }
     System.out.println("-----");
   }*/
   private static final String append = " -> ";
   public static void main(String[] args) {
       In in = new In(args[0]);
       int size = in.readInt();
       Point[] points = new Point[size];
       for (int i = 0; i < size; i++) {
           int x = in.readInt();
           int y = in.readInt();
           points[i] = new Point(x, y);
           points[i].draw();
       }
       StdDraw.setXscale(0, 32768);
       StdDraw.setYscale(0, 32768);

       StringBuilder sb = new StringBuilder();

       int index = 0;
       for (Point p : points) {
         java.util.Arrays.sort(points, index + 1, points.length, p.SLOPE_ORDER);
         double processingSlope = Double.NaN;

         for (int innerIndex = index; innerIndex < points.length; innerIndex++) {
           if (points.length - innerIndex > 4 && 
             p.slopeTo(points[innerIndex + 1]) == p.slopeTo(points[innerIndex + 2]) && p.slopeTo(points[innerIndex + 1]) == p.slopeTo(points[innerIndex + 3])
             && p.slopeTo(points[innerIndex + 1]) == p.slopeTo(points[innerIndex + 4])
             && (processingSlope != Double.NaN && processingSlope != p.slopeTo(points[innerIndex + 1]))) {
               Point[] sorted = new Point[]{points[index], points[innerIndex + 1], points[innerIndex + 2], points[innerIndex + 3], points[innerIndex + 4]};
               Insertion.sort(sorted);
               sb.delete(0, sb.length()).append(sorted[0]).
                               append(append).append(sorted[1]).append(append).
                               append(sorted[2]).append(append).append(sorted[3])
                               .append(append).append(sorted[4]);
               StdOut.println(sb.toString());
               sorted[0].drawTo(sorted[4]);
               processingSlope = p.slopeTo(points[innerIndex + 1]);
           }
           else if (points.length - innerIndex > 3 && 
             p.slopeTo(points[innerIndex + 1]) == p.slopeTo(points[innerIndex + 2]) && p.slopeTo(points[innerIndex + 1]) == p.slopeTo(points[innerIndex + 3])
             && (processingSlope != Double.NaN && processingSlope != p.slopeTo(points[innerIndex + 1]))) {
               Point[] sorted = new Point[]{points[index], points[innerIndex + 1], points[innerIndex + 2], points[innerIndex + 3]};
               Insertion.sort(sorted);
               sb.delete(0, sb.length()).append(sorted[0]).
                               append(append).append(sorted[1]).append(append).
                               append(sorted[2]).append(append).append(sorted[3]);
               StdOut.println(sb.toString());
               sorted[0].drawTo(sorted[3]);
               processingSlope = p.slopeTo(points[innerIndex + 1]);
           }
         }
         ++index;
      }
   }
}
