public class Solver {

    private int moves = 0;
    private boolean solvable = false;
    private java.util.LinkedList<Board> solution;

    public Solver(Board initial) {
      solvable = true;

      solution = new java.util.LinkedList<Board>();
      java.util.LinkedList<Board> solution1 = new java.util.LinkedList<Board>();
      
      Board prev = null;
      Board prev1 = null;
      
      MinPQ<Board> q = new MinPQ<Board>();
      Board b = initial;
      q.insert(b);
      
      MinPQ<Board> q1 = new MinPQ<Board>();
      Board b1 = b.twin();
      q1.insert(b1);
      
      while (true) {
        
        Board c = q.delMin();
        Board c1 = q1.delMin();
        
        solution.add(c);
        solution1.add(c1);
        
        if (c.isGoal() || c1.isGoal())  {
          if (c1.isGoal()) {
            solvable = false;
            moves = -1;
            solution = null;
          }
          break;
        }
        else {
          for (Board x : c.neighbors()) {
            if (prev == null || (prev != null && !x.equals(prev))) {
              q.insert(x);
            }
          }
          for (Board x1 : c1.neighbors()) {
            if (prev1 == null || (prev1 != null && !x1.equals(prev1))) {
              q1.insert(x1);
            }
          }
          prev = c;
          prev1 = c1;
          ++moves;
        }
      }
    }

    public boolean isSolvable() {
      return solvable;
    }

    public int moves() {
      return moves;
    }

    public Iterable<Board> solution() {
      return solution;
    }

    public static void main(String[] args) {
      // create initial board from file
      In in = new In(args[0]);
      int N = in.readInt();
      if (N < 2 || N > 127) {
        throw new IllegalArgumentException();
      }
      int[][] blocks = new int[N][N];
      for (int i = 0; i < N; i++) {
          for (int j = 0; j < N; j++) {
              blocks[i][j] = in.readInt();
          }
      }
      Board initial = new Board(blocks);

      // solve the puzzle
      Solver solver = new Solver(initial);

      // print solution to standard output
      if (!solver.isSolvable())
          StdOut.println("No solution possible");
      else {
          StdOut.println("Minimum number of moves = " + solver.moves());
          for (Board board : solver.solution())
              StdOut.println(board);
      }
    }
}