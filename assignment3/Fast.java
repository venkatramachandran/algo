public class Fast {
   public static void main(String[] args) {
       In in = new In(args[0]);
       int size = in.readInt();
       Point[] points = new Point[size];
       for (int i = 0; i < size; i++) {
           int x = in.readInt();
           int y = in.readInt();
           points[i] = new Point(x, y);
       }
       StdDraw.setXscale(0, 32768);
       StdDraw.setYscale(0, 32768);
       
       int index = 0;
       for (Point p : points) {
         p.draw();
         if (size - index > 2) {         
           Point[] arr = new Point[size - index];
           System.arraycopy(points, index, arr, 0, size - index);
           java.util.Arrays.sort(arr, p.SLOPE_ORDER);
           if (p.slopeTo(arr[0]) == p.slopeTo(arr[1]) && p.slopeTo(arr[0]) == p.slopeTo(arr[2])) {
             Point[] sorted = new Point[]{p, arr[0], arr[1], arr[2]};
             Insertion.sort(sorted);
             StdOut.println(sorted[0] + " -> " + sorted[1] + " -> " + sorted[2] + " -> " + sorted[3]);
             p.drawTo(arr[0]);
             p.drawTo(arr[1]);
             p.drawTo(arr[2]);
            }
         }
      }
   }
}
