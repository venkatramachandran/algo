public class Fast {
   /*private static void printArray(Point o, Point[] p) {
     System.out.println("-----");
     System.out.println("Origin:"+o.toString());
     for (Point p1 : p) {
       System.out.println(p1.toString() + "/" + o.slopeTo(p1));
     }
     System.out.println("-----");
   }*/
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
       
       int index = 0;
       for (Point p : points) {
         if (size - index - 1 > 2) {         
           Point[] arr = new Point[size - index - 1];
           System.arraycopy(points, index + 1, arr, 0, size - index - 1);
           java.util.Arrays.sort(arr, p.SLOPE_ORDER);
           //printArray(p, arr);
           if (arr.length >= 4 && p.slopeTo(arr[0]) == p.slopeTo(arr[1]) && p.slopeTo(arr[0]) == p.slopeTo(arr[2]) && p.slopeTo(arr[0]) == p.slopeTo(arr[3])) {
             //System.out.println("Found a 5 match!");
             Point[] sorted = new Point[]{p, arr[0], arr[1], arr[2], arr[3]};
             Insertion.sort(sorted);
             StdOut.println(sorted[0] + " -> " + sorted[1] + " -> " + sorted[2] + " -> " + sorted[3] + " -> " + sorted[4]);
             sorted[0].drawTo(sorted[4]);
            }
            else if (arr.length >= 3 && p.slopeTo(arr[0]) == p.slopeTo(arr[1]) && p.slopeTo(arr[0]) == p.slopeTo(arr[2])) {
             //System.out.println("Found a 4 match!");
             Point[] sorted = new Point[]{p, arr[0], arr[1], arr[2]};
             Insertion.sort(sorted);
             StdOut.println(sorted[0] + " -> " + sorted[1] + " -> " + sorted[2] + " -> " + sorted[3]);
             sorted[0].drawTo(sorted[3]);
            }
         }
         ++index;
      }
   }
}
