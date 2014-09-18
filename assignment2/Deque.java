public class Deque<Item> implements Iterable<Item> {
    private Node<Item> head;
    private Node<Item> tail;
    private int size;

   public Deque() {
       head = null;
       tail = null;
       size = 0;
   }

   public boolean isEmpty() {
       return head == null;
   }

   public int size() {
       return size;
   }

   public void addFirst(Item item) {
       if (item == null) {
           throw new NullPointerException();
       }
       if (head == null) {
           head = new Node<Item>(item);
           tail = head;
       }
       else {
           Node<Item> oldhead = head;
           head = new Node<Item>(item, oldhead);
           oldhead.setPrev(head);
       }
       size++;
   }

   public void addLast(Item item) {
       if (item == null) {
           throw new NullPointerException();
       }
       if (head == null) {
           head = new Node<Item>(item);
           tail = head;
       }
       else {
           Node<Item> oldtail = tail;
           tail = new Node<Item>(item, null, oldtail);
           oldtail.setNext(tail);
       }
       size++;
   }

   public Item removeFirst() {
       if (size() == 0) {
           throw new java.util.NoSuchElementException();
       }
       Item retval = head.getItem();
       head = head.getNext();
       if (head != null) {
           head.setPrev(null);
       }
       size--;
       return retval;
   }

   public Item removeLast() {
       if (size() == 0) {
           throw new java.util.NoSuchElementException();
       }

       if (size() == 1) {
           return removeFirst();
       }
       else {
           Item retval = tail.getItem();
           tail = tail.getPrev();
           tail.setNext(null);
           size--;
           return retval;
       }
   }

   public java.util.Iterator<Item> iterator() {
       return new DequeIterator(head);
   }

   /*public static void main(String[] args) {
       long times = 1024L;
       boolean print = false;
       System.out.printf("%13s %7s %4s %4s %4s %4s \n", "N", "seconds", "t1", "t2", "t3", "t4");
       for (int loop = 0; loop < 12; loop++) {
           Deque<Integer> list = new Deque<Integer>();
           long startTime = System.currentTimeMillis();
           times = times * (long)Math.pow(4,loop);
           double t1 = 0, t2 = 0, t3 = 0, t4 = 0;
           int p1 = 0, p2 = 0, p3 = 0, p4 = 0;
           int add1 = 0;
           for (int i = 0; i < times; i++)
           {
               double rnd = StdRandom.uniform();
               if (rnd < 0.25) {
                   ++p1;
                   long start = System.currentTimeMillis();
                   list.addFirst(++add1);
                   long end = System.currentTimeMillis();
                   t1 += (end - start);
                   if (print) System.out.println("Call " + add1 + ",Added to first, size:"+list.size());
               }
               else if (rnd >= 0.25 && rnd < 0.50) {
                   ++p2;
                   long start = System.currentTimeMillis();
                   list.addLast(++add1);
                   long end = System.currentTimeMillis();
                   t2 += (end - start);
                   if (print) System.out.println("Call " + add1 + ",Added to last, size:"+list.size());
               }
               else if (rnd >= 0.5 && rnd < 0.75) {
                   try {
                     ++p3;
                     long start = System.currentTimeMillis();
                     int rem1 = list.removeFirst();
                     long end = System.currentTimeMillis();
                     t3 += (end - start);
                     if (print) System.out.println("Call " + rem1 + ",removed from first, size:"+list.size());
                   }
                   catch (java.util.NoSuchElementException e){}//if (print) System.out.println("call "+i+" threw exception on removeFirst");}
               }
               else if (rnd >= 0.75) {
                   try {
                     ++p4;
                     long start = System.currentTimeMillis();
                     int rem1 = list.removeLast();
                     long end = System.currentTimeMillis();
                     t4 += (end - start);
                     if (print) System.out.println("Call " + rem1 + ",removed from last, size:"+list.size());
                   }
                   catch (java.util.NoSuchElementException e){}//if (print) System.out.println("call "+i+" threw exception on removeLast");}
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
           //System.out.println(s1);
           //System.out.println(p1 + " " +p2 + " " +p3 + " " +p4);
       }
   }

   /*public static void main(String[] args) {
       Deque<Integer> list = new Deque<Integer>();
       list.addLast(1);
       list.addFirst(2);
       list.removeLast();
   }*/

   private class DequeIterator implements java.util.Iterator<Item> {

       private Node<Item> item;

       public DequeIterator(Node<Item> item) {
           this.item = item;
       }

       public boolean hasNext() {
           return item != null;
       }

       public Item next() {
           if (item == null) {
               throw new java.util.NoSuchElementException();
           }
           Item retval = item.getItem();
           item = item.getNext();
           return retval;
       }

       public void remove() {
           throw new UnsupportedOperationException();
       }

   }

   private class Node<Item> {
     private Item item;
     private Node<Item> next;
     private Node<Item> prev;

     public Node(Item i) {
         this(i, null, null);
     }

     public Node(Item i, Node<Item> n) {
         this(i, n, null);
     }

     public Node(Item i, Node<Item> n, Node<Item> p) {
         this.item = i;
         this.next = n;
         this.prev = p;
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

     public void setPrev(Node<Item> p) {
         this.prev = p;
     }

     public Node<Item> getPrev() {
         return this.prev;
     }
   }
}