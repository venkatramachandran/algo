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
       String s = null;
       RandomizedQueue<String> list = new RandomizedQueue<String>();
       while (!StdIn.isEmpty()) {
         s = StdIn.readString();
         list.enqueue(s);
       }
       if (size > list.size()) {
           throw new IllegalArgumentException();
       }
       for (int i = 0; i < size; i++) {
           StdOut.println(list.dequeue());
       }
   }
}
