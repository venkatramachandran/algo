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
         /*if (size - index - 1 > 2) {
           Point[] arr = new Point[size - index - 1];
           System.arraycopy(points, index + 1, arr, 0, size - index - 1);
           java.util.Arrays.sort(arr, p.SLOPE_ORDER);
           //printArray(p, arr);
           if (arr.length >= 4 && p.slopeTo(arr[0]) == p.slopeTo(arr[1]) && p.slopeTo(arr[0]) == p.slopeTo(arr[2]) && p.slopeTo(arr[0]) == p.slopeTo(arr[3])) {
             //System.out.println("Found a 5 match!");
             Point[] sorted = new Point[]{p, arr[0], arr[1], arr[2], arr[3]};
             Insertion.sort(sorted);
             sb.delete(0, sb.length()).append(sorted[0]).
                             append(append).append(sorted[1]).append(append).
                             append(sorted[2]).append(append).append(sorted[3])
                             .append(append).append(sorted[4]);
             StdOut.println(sb.toString());
             sorted[0].drawTo(sorted[4]);
            }
            else if (arr.length >= 3 && p.slopeTo(arr[0]) == p.slopeTo(arr[1]) && p.slopeTo(arr[0]) == p.slopeTo(arr[2])) {
             //System.out.println("Found a 4 match!");
             Point[] sorted = new Point[]{p, arr[0], arr[1], arr[2]};
             Insertion.sort(sorted);
             sb.delete(0, sb.length()).append(sorted[0]).
                             append(append).append(sorted[1]).append(append).
                             append(sorted[2]).append(append).append(sorted[3]);
             StdOut.println(sb.toString());
             sorted[0].drawTo(sorted[3]);
            }
         }*/

         java.util.Arrays.sort(points, index + 1, points.length, p.SLOPE_ORDER);
         if (points.length - index > 4 && 
           p.slopeTo(points[index + 1]) == p.slopeTo(points[index + 2]) && p.slopeTo(points[index + 1]) == p.slopeTo(points[index + 3])
           && p.slopeTo(points[index + 1]) == p.slopeTo(points[index + 4])) {
             Point[] sorted = new Point[]{points[index], points[index + 1], points[index + 2], points[index + 3], points[index + 4]};
             Insertion.sort(sorted);
             sb.delete(0, sb.length()).append(sorted[0]).
                             append(append).append(sorted[1]).append(append).
                             append(sorted[2]).append(append).append(sorted[3])
                             .append(append).append(sorted[4]);
             StdOut.println(sb.toString());
             sorted[0].drawTo(sorted[4]);
         }
         else if (points.length - index > 3 && 
           p.slopeTo(points[index + 1]) == p.slopeTo(points[index + 2]) && p.slopeTo(points[index + 1]) == p.slopeTo(points[index + 3])) {
             Point[] sorted = new Point[]{points[index], points[index + 1], points[index + 2], points[index + 3]};
             Insertion.sort(sorted);
             sb.delete(0, sb.length()).append(sorted[0]).
                             append(append).append(sorted[1]).append(append).
                             append(sorted[2]).append(append).append(sorted[3]);
             StdOut.println(sb.toString());
             sorted[0].drawTo(sorted[3]);
         }
         ++index;
      }
   }
}
