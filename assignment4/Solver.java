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

        if (c.isGoal() || c1.isGoal())  {
          if (c1.isGoal()) {
            solvable = false;
            moves = -1;
            solution = null;
          }
          else {
            solvable = true;
            moves = s.getMoves();
            solution = new java.util.LinkedList<Board>();
            while (s != null) {
              solution.push(s.getBoard());
              s = s.getPrev();
            }
            java.util.Collections.reverse(solution);
          }
          break;
        }
        else {
          for (Board x : c.neighbors()) {
            if (s.getPrev() == null || (s.getPrev() != null && !x.equals(s.getPrev().getBoard()))) {
              SearchNode xn = new SearchNode(x, s.getMoves() + 1, s);
              q.insert(xn);
              s.addNext(xn);
            }
          }
          for (Board x1 : c1.neighbors()) {
            if (s1.getPrev() == null || (s1.getPrev() != null && !x1.equals(s1.getPrev().getBoard()))) {
              SearchNode x1n = new SearchNode(x1, s1.getMoves() + 1, s1);
              q1.insert(x1n);
              s1.addNext(x1n);
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
      private SearchNode prev;
      private java.util.List<SearchNode> next;

      public SearchNode(Board b, int m, SearchNode p) {
        this.board = b;
        this.moves = m;
        this.prev = p;
        this.next = null;
      }

      public int getMoves() {
        return moves;
      }

      public Board getBoard() {
        return board;
      }

      public SearchNode getPrev() {
        return prev;
      }

      public void setPrev(SearchNode p) {
        prev = p;
      }

      public void addNext(SearchNode n) {
        if (next == null) {
          next = new java.util.ArrayList<SearchNode>();
        }
        if (n.getPrev() == null) {
          n.setPrev(this);
        }
        next.add(n);
      }

      public java.util.List<SearchNode> getNext() {
        return next;
      }
    }
}