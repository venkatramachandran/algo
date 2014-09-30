public class Solver {

    private static final java.util.Comparator<SearchNode> CMP = new java.util.Comparator<SearchNode>() {
      public int compare(SearchNode b1, SearchNode b2) {
        return (b1.getMoves()+b1.getBoard().manhattan()) - (b2.getMoves()+b2.getBoard().manhattan());
      }
    };

    private int moves = 0;
    private boolean solvable = false;
    private java.util.LinkedList<Board> solution;

    public Solver(Board initial) {

      solvable = true;

      solution = new java.util.LinkedList<Board>();
      java.util.LinkedList<Board> solution1 = new java.util.LinkedList<Board>();

      MinPQ<SearchNode> q = new MinPQ<SearchNode>(CMP);
      Board b = initial;
      q.insert(new SearchNode(b, 0, null));

      MinPQ<SearchNode> q1 = new MinPQ<SearchNode>(CMP);
      Board b1 = b.twin();
      q1.insert(new SearchNode(b1, 0, null));

      while (true) {

        SearchNode s = q.delMin();
        SearchNode s1 = q1.delMin();

        Board c = s.getBoard();
        Board c1 = s1.getBoard();

        while (!q.isEmpty()) q.delMin();
        while (!q1.isEmpty()) q1.delMin();

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
          ++moves;
          for (Board x : c.neighbors()) {
            if (s.getPrev() == null || (s.getPrev() != null && !x.equals(s.getPrev()))) {
              q.insert(new SearchNode(x, moves, c));
            }
          }
          for (Board x1 : c1.neighbors()) {
            if (s1.getPrev() == null || (s1.getPrev() != null && !x1.equals(s1.getPrev()))) {
              q1.insert(new SearchNode(x1, moves, c1));
            }
          }
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

    private class SearchNode {
      private Board board;
      private int moves;
      private Board prev;

      public SearchNode(Board b, int m, Board p) {
        this.board = b;
        this.moves = m;
        this.prev = p;
      }

      public int getMoves() {
        return moves;
      }

      public Board getBoard() {
        return board;
      }

      public Board getPrev() {
        return prev;
      }
    }
}