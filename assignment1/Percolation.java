/**
 * Percolation.
 * @author venky
 *
 */
public class Percolation {

  /**
   * The union find object.
   */
  private WeightedQuickUnionUF uf;

  /**
   * the size of the matrix.
   */
  private int size;

  /**
   * matrix open sites.
   */
  private boolean[][] sites;

     /**
      * default constructor.
      * @param size1 size
      */
     public Percolation(final int size1) {
       super();
       if (size1 <= 0)
       {
           throw new IllegalArgumentException();
       }
       uf = new WeightedQuickUnionUF(size1 * size1 + 2);
       this.size = size1;
       this.sites = new boolean[size][size];
       for (int l1 = 0; l1 < sites.length; l1++) {
           for (int l2 = 0; l2 < sites[l1].length; l2++) {
              sites[l1][l2] = false;
           }
       }
     }

     /**
      * open a site.
      * @param i1 x coordinate.
      * @param j1 y coordinate
      */
     public void open(final int i1, final int j1) {
       validate(i1, j1);
       //transform i and j into the right array index
       int i = i1 - 1;
       int j = j1 - 1;
       sites[i][j] = true;
	   StdOut.println("open:"+i1+","+j1);
       //now find the top/bottom/left/right and connect them
       //this is left
       if ((j - 1 >= 0) && (sites[i][j - 1])) {
           int p = i * size + j + 1;
           int q = i * size + j;
           uf.union(p, q);
		   StdOut.println("left:"+p+","+q);
       }
       //this is right
       if ((j + 1 < this.size) && (sites[i][j + 1])) {
           int p = i * size + j + 1;
           int q = i * size + (j + 1) + 1;
           uf.union(p, q);
		   StdOut.println("right:"+p+","+q);
       }
       //this is top
       if ((i - 1 >= 0) && (sites[i - 1][j])) {
           int p = i * size + j + 1;
           int q = (i - 1) * size + j + 1;
           uf.union(p, q);
		   StdOut.println("top:"+p+","+q);
       }
       //this is bottom
       if ((i + 1 < this.size) && sites[i + 1][j]) {
           int p = i * size + j + 1;
           int q = (i + 1) * size + j + 1;
           uf.union(p, q);
		   StdOut.println("bottom:"+p+","+q);
       }

       //if the top or bottom row
       if (i == 0) {
           uf.union((j + 1), 0);
		   StdOut.println("top row:"+(j+1)+","+0);
       }
       if (i == (this.size - 1)) {
           int p = i * size + j + 1;
           uf.union(p, (this.size * this.size + 1));
		   StdOut.println("bottom row:"+p+","+(this.size * this.size + 1));
       }
	   StdOut.println("-----");
     }

     /**
      * is the site open?.
      * @param i x coordinate
      * @param j y coordinate
      * @return if the site is open
      */
     public boolean isOpen(final int i, final int j) {
       validate(i, j);
       return sites[i - 1][j - 1];
     }

     /**
      * is the site full?.
      * @param i x coordinate
      * @param j y coordinate
      * @return if the site is full
      */
     public boolean isFull(final int i, final int j) {
       validate(i, j);
       int p = (i - 1) * size + (j - 1) + 1;
       return uf.connected(p,0);
     }

     /**
      * does the system percolate?.
      * @return does the system percolate
      */
     public boolean percolates() {
       return uf.connected(this.size * this.size + 1, 0);
     }

     /**
      * validates the range for i and j.
      * @param i i
      * @param j j
      */
     private void validate(final int i, final int j) {
       if (i < 1 || i > size || j < 1 || j > size) {
         throw new ArrayIndexOutOfBoundsException();
       }
     }
}
