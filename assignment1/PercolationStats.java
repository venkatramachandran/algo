/**
 * The statistics.
 * @author venky
 *
 */
public class PercolationStats {

       /**
        * the 3 sigma value.
        */
       private static final double THREESIGMAZ = 1.96d;

       /**
        * size.
        */
       private int size;

       /**
        * simulations.
        */
       private int simulations;

       /**
        * the open sites.
        */
       private int[] openSites;

       /**
        * perform T independent computational experiments on an N-by-N grid.
        * @param n size
        * @param t simulations
        */
       public PercolationStats(final int n, final int t) {
           super();
           size = n;
           simulations = t;
           if (size <= 0 || simulations <= 0)
           {
               throw new IllegalArgumentException();
           }
           openSites = new int[simulations];
           Percolation p;
           //run the simulation
           for (int i = 0; i < simulations; i++) {
               p = new Percolation(size);
               int runs = 0;
               while (!p.percolates()) {
                   int x = StdRandom.uniform(size) + 1;
                   int y = StdRandom.uniform(size) + 1;
                   if (!p.isOpen(x, y)) {
                       p.open(x, y);
                       ++runs;
                   }
               }
               openSites[i] = runs;
           }
       }

       /**
        * sample mean of percolation threshold.
        * @return mean
        */
       public double mean() {
           double retVal = 0.0d;
           for (int i = 0; i < openSites.length; i++) {
               retVal += openSites[i];
           }
           return (retVal / simulations)/(size * size);
       }

       /**
        * sample standard deviation of percolation threshold.
        * @return standard deviation.
        */
       public double stddev() {
           double retVal = 0.0d;
           double mean = mean();
           for (int i = 0; i < openSites.length; i++) {
               retVal += (((double) openSites[i] / (size * size) - mean) * ((double) openSites[i] / (size * size) - mean));
           }
           return Math.sqrt(retVal / (simulations - 1));
       }

       /**
        * returns lower bound of the 95% confidence interval.
        * @return lower bound.
        */
       public double confidenceLo() {
           double mean = mean();
           double stddev = stddev();
           double retVal = mean - ((THREESIGMAZ * stddev)
                   / Math.sqrt(simulations));
           return retVal;
       }

       /**
        * returns upper bound of the 95% confidence interval.
        * @return upper bound
        */
       public double confidenceHi() {
           double mean = mean();
           double stddev = stddev();
           double retVal = mean + ((THREESIGMAZ * stddev)
                   / Math.sqrt(simulations));
           return retVal;
       }

       /**
        * the main method.
        * @param args command line arguments.
        */
       public static void main(final String[] args) {
           int size = -1;
           int times = -1;
           try
           {
               size = Integer.parseInt(args[0]);
               times = Integer.parseInt(args[1]);
           }
           catch (NumberFormatException nfe)
           {
               throw new IllegalArgumentException();
           }
           catch (ArrayIndexOutOfBoundsException aioobe)
           {
                throw new IllegalArgumentException();
           }

           PercolationStats ps = new PercolationStats(size, times);
           StdOut.println("mean                    = " + ps.mean());
           StdOut.println("stddev                  = " + ps.stddev());
           StdOut.println("95% confidence interval = "
            + ps.confidenceLo() + ", " + ps.confidenceHi());
       }
}
