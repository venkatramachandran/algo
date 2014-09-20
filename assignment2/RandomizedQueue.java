public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

   public RandomizedQueue() {
       items = (Item[]) new Object[1];
       size = 0;
   }

   public boolean isEmpty() {
       return (size == 0);
   }

   public int size() {
       return size;
   }

   public void enqueue(Item item) {
       if (item == null) {
           throw new NullPointerException();
       }
       if (size == items.length) {
           resize(size * 2);
       }
       items[size++] = item;
   }

   public Item dequeue() {
       if (size() == 0) {
           throw new java.util.NoSuchElementException();
       }
       int index = StdRandom.uniform(0, size());
       Item retval = items[index];
       items[index] = items[size - 1];
       --size;
       if (size > 0 && size == items.length/4) {
           resize(items.length/2);
       }
       return retval;
   }

   public Item sample() {
       if (size() == 0) {
           throw new java.util.NoSuchElementException();
       }
       int index = StdRandom.uniform(0, size());
       return items[index];
    }

   public java.util.Iterator<Item> iterator() {
       return new RandomQueueIterator(items, size());
   }

    private void resize(int capacity) {
      Item[] copy = (Item[]) new Object[capacity];
      for (int i = 0; i < size; i++) {
        copy[i] = items[i];
      }
      items = copy;
    }

   private class RandomQueueIterator implements java.util.Iterator<Item> {

       private Item[] items;
       private int index;

       public RandomQueueIterator(Item[] iitems, int size) {
           this.items = (Item[]) new Object[size];
           for (int i = 0; i < size; i++) {
               items[i] = iitems[i];
           }
           StdRandom.shuffle(items);
           index = 0;
       }

       public boolean hasNext() {
           return index < items.length;
       }

       public Item next() {
           if (index == items.length) {
               throw new java.util.NoSuchElementException();
           }
           Item retval = items[index];
           items[index] = null;
           index++;
           return retval;
       }

       public void remove() {
           throw new UnsupportedOperationException();
       }

   }
}