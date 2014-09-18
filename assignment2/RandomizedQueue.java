public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node<Item> head;
    private int size;

   public RandomizedQueue() {
       head = null;
       size = 0;
   }

   public boolean isEmpty() {
       return (head == null);
   }

   public int size() {
       return size;
   }

   public void enqueue(Item item) {
       if (item == null) {
           throw new NullPointerException();
       }
       if (head == null) {
           head = new Node<Item>(item);
       }
       else {
           Node<Item> oldhead = head;
           head = new Node<Item>(item, oldhead);
       }
       size++;
   }

   public Item dequeue() {
       if (size() == 0) {
           throw new java.util.NoSuchElementException();
       }
       int index = StdRandom.uniform(0, size());
       int i = 0;

       if (index == 0) {
           Item retval = head.getItem();
           head = head.getNext();
           size--;
           return retval;
       }
       else {
           Node<Item> node = head;
           while (i < index - 1) {
               node = node.getNext();
               ++i;
           }
           Item retval = node.getNext().getItem();
           node.setNext(node.getNext().getNext());
           size--;
           return retval;
       }
   }

   public Item sample() {
       if (size() == 0) {
           throw new java.util.NoSuchElementException();
       }
       int index = StdRandom.uniform(0, size());
       int i = 0;

       if (index == 0) {
           Item retval = head.getItem();
           return retval;
       }
       else {
           Node<Item> node = head;
           while (i < index - 1) {
               node = node.getNext();
               ++i;
           }
           Item retval = node.getNext().getItem();
           return retval;
       }
    }

   public java.util.Iterator<Item> iterator() {
       return new RandomQueueIterator(head, size());
   }

/*   public static void main(String[] args) {
       long times = 1024L;
       boolean print = false;
       System.out.printf("%13s %7s %4s %4s %4s %4s \n", "N", "seconds", "t1", "t2", "t3", "t4");
       for (int loop = 0; loop < 12; loop++) {
           RandomizedQueue<Integer> list = new RandomizedQueue<Integer>();
           long startTime = System.currentTimeMillis();
           times = 50000000;//times * (long)Math.pow(4,loop);
           double t1 = 0, t2 = 0, t3 = 0, t4 = 0;
           int p1 = 0, p2 = 0, p3 = 0, p4 = 0;
           for (int i = 0; i < times; i++)
           {
               double rnd = StdRandom.uniform();
               if (rnd < 0.2) {
                   ++p1;
                   long start=System.currentTimeMillis();
                   list.enqueue(1);
                   long end = System.currentTimeMillis();
                   t1 += (end - start);
               }
               else if (rnd >= 0.2 && rnd < 0.4) {
                   ++p2;
                   long start = System.currentTimeMillis();
                   list.isEmpty();
                   long end = System.currentTimeMillis();
                   t2 += (end - start);
               }
               else if (rnd >= 0.4 && rnd < 0.6) {
                   ++p3;
                   long start = System.currentTimeMillis();
                   list.size();
                   long end = System.currentTimeMillis();
                   t3 += (end - start);
               }
               else if (rnd >= 0.6 && rnd < 0.8) {
                   try {
                     ++p4;
                     long start = System.currentTimeMillis();
                     list.dequeue();
                     long end = System.currentTimeMillis();
                     t4 += (end - start);
                   }
                   catch (java.util.NoSuchElementException e){}
               }
               else if (rnd >= 0.8) {
                   try {
                     list.sample();
                   }
                   catch (java.util.NoSuchElementException e){}
               }
           }
           long endTime = System.currentTimeMillis();
           double time_in_s = ((double)(endTime - startTime))/1000.0d;
           String s1 = (t1 + " " +t2 + " " +t3 + " " +t4);
           t1 = t1 / (p1 * 1000.0d);
           t2 = t2 / (p2 * 1000.0d);
           t3 = t3 / (p3 * 1000.0d);
           t4 = t4 / (p4 * 1000.0d);
           System.out.printf("%13d %7.2f %2.2f %2.2f %2.2f %2.2f\n", times, time_in_s, t1, t2, t3, t4);
           System.out.println(s1);
           System.out.println(p1 + " " +p2 + " " +p3 + " " +p4);
       }
   }*/

   private class RandomQueueIterator implements java.util.Iterator<Item> {

       private Item[] items;
       private int index;

       public RandomQueueIterator(Node<Item> item, int size) {
           this.items = (Item[]) new Object[size];
           Node<Item> item1 = item;
           this.index = 0;
           int i = 0;
           while (item1 != null) {
               items[i++] = item1.getItem();
               item1 = item1.getNext();
           }
           StdRandom.shuffle(items);
       }

       public boolean hasNext() {
           return index < items.length;
       }

       public Item next() {
           if (index == items.length) {
               throw new java.util.NoSuchElementException();
           }
           Item retval = items[index];
           index++;
           return retval;
       }

       public void remove() {
           throw new UnsupportedOperationException();
       }

   }

   private class Node<Item> {
     private Item item;
     private Node<Item> next;

     public Node(Item i) {
         this.item = i;
         this.next = null;
     }

     public Node(Item i, Node<Item> n) {
         this.item = i;
         this.next = n;
     }

     public Item getItem() {
         return this.item;
     }

     public Node<Item> getNext() {
         return this.next;
     }

     public void setNext(Node<Item> n) {
         this.next = n;
     }

   }
}