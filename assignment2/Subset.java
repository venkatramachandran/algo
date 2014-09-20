public class Subset {
   public static void main(String[] args) {
       int size = 0;
       try {
        size = Integer.parseInt(args[0]);
       }
       catch (NumberFormatException e) {
           throw new IllegalArgumentException();
       }
       catch (ArrayIndexOutOfBoundsException e) {
           throw new IllegalArgumentException();
       }
       double rnd = 0.0;
       if (size > 0) {
           rnd = 1.00d/size;
       }
       String s = null;
       RandomizedQueue<String> list = new RandomizedQueue<String>();
       int index = 0;
       while (!StdIn.isEmpty()) {
         s = StdIn.readString();
         if (index < size) {
           list.enqueue(s);
           index++;
         }
         else {
           if (StdRandom.uniform() < rnd) {
             list.dequeue();
             list.enqueue(s);
             index++;
           }
         }
       }
       if (size > list.size()) {
           throw new IllegalArgumentException();
       }
       if (size > 0) {
         for (int i = 0; i < size; i++) {
           StdOut.println(list.dequeue());
         }
       }
   }
}
