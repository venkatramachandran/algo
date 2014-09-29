public class Board{
    private static final String SPACE = " ";
    private static final String NEWLINE = "\n";

    final int[][] board;
    private int dimension;

    /**
     * construct a board from an N-by-N array of blocks
     */
    public Board(int[][] blocks) {
      dimension = blocks.length;
      board = new int[dimension][dimension];
      for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
          board[i][j] = blocks[i][j];
        }
      }
    }

    public int dimension() {
      return dimension;
    }

    public int hamming() {
      int hammingScore = 0;
      for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
          if ((board[i][j] != 0) && (i*dimension + j + 1) != board[i][j]) {
            ++hammingScore;
            //System.out.println("Added 1 to hamming for:"+board[i][j]);
          }
        }
      }
      return hammingScore;
    }

    public int manhattan() {
      int manhattanScore = 0;
      for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
          if ((board[i][j] != 0) && (i*dimension + j + 1) != board[i][j]) {
            int currItem = board[i][j];
            int row = 0;
            int col = 0;
            if (currItem % dimension == 0) {
              row = (int) currItem/dimension - 1;
              col = dimension - 1;
            }
            else {
              row = (int) currItem / dimension;
              col = (int) currItem % dimension - 1;
            }
            manhattanScore += Math.abs(row - i) + Math.abs(col - j);
            //System.out.println("Added "+(Math.abs(row - i) + Math.abs(col - j))+" to hamming for:"+board[i][j]);
          }
        }
      }
      return manhattanScore;
    }

    public boolean isGoal() {
      boolean retVal = true;
      outer:
      for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
          if ((board[i][j] != 0) && board[i][j] != (i * dimension + j + 1)) {
            retVal = false;
            break outer;
          }
        }
      }
      return retVal;
    }

    public Board twin() {
      int[][] newboard = new int[dimension][dimension];
      int zero_row = -1;
      for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
          newboard[i][j] = board[i][j];
          if (newboard[i][j] == 0) {
            zero_row = i;
          }
        }
      }
      if (zero_row != 0) {
        //swap the first 2 elements in the first row
        int temp = newboard[0][0];
        newboard[0][0] = newboard[0][1];
        newboard[0][1] = temp;
      }
      else if (zero_row == 0) {
        //swap the first 2 elements in the last row
        int temp = newboard[dimension - 1][0];
        newboard[dimension - 1][0] = newboard[dimension - 1][1];
        newboard[dimension - 1][1] = temp;
      }

      Board b = new Board(newboard);
      return b;
    }

    public boolean equals(Object y) {
      if (!(y instanceof Board)) {
        return false;
      }
      Board that = (Board)y;
      if (dimension != that.dimension) {
        return false;
      }
      boolean retVal = true;
      outer:
      for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
          if (board[i][j] != that.board[i][j]) {
            retVal = false;
            break outer;
          }
        }
      }
      return retVal;
    }

    public Iterable<Board> neighbors() {
      java.util.LinkedList<Board> q = new java.util.LinkedList<Board>();
      int[][] newboard = new int[dimension][dimension];
      int zero_x = -1;
      int zero_y = -1;
      for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
          newboard[i][j] = board[i][j];
          if (newboard[i][j] == 0) {
            zero_x = i;
            zero_y = j;
          }
        }
      }
      //top
      if (zero_x - 1 >= 0) {
        int temp = newboard[zero_x][zero_y];
        newboard[zero_x][zero_y] = newboard[zero_x - 1][zero_y];
        newboard[zero_x - 1][zero_y] = temp;
        Board top = new Board(newboard);
        q.add(top);
        temp = newboard[zero_x][zero_y];
        newboard[zero_x][zero_y] = newboard[zero_x - 1][zero_y];
        newboard[zero_x - 1][zero_y] = temp;
      }


      //bottom
      if (zero_x + 1 < dimension) {
        int temp = newboard[zero_x][zero_y];
        newboard[zero_x][zero_y] = newboard[zero_x + 1][zero_y];
        newboard[zero_x + 1][zero_y] = temp;
        Board bottom = new Board(newboard);
        q.add(bottom);
        temp = newboard[zero_x][zero_y];
        newboard[zero_x][zero_y] = newboard[zero_x + 1][zero_y];
        newboard[zero_x + 1][zero_y] = temp;
      }

      //left
      if (zero_y - 1 >= 0) {
        int temp = newboard[zero_x][zero_y];
        newboard[zero_x][zero_y] = newboard[zero_x][zero_y - 1];
        newboard[zero_x][zero_y - 1] = temp;
        Board left = new Board(newboard);
        q.add(left);
        temp = newboard[zero_x][zero_y];
        newboard[zero_x][zero_y] = newboard[zero_x][zero_y - 1];
        newboard[zero_x][zero_y - 1] = temp;
      }

      //right
      if (zero_y + 1 < dimension) {
        int temp = newboard[zero_x][zero_y];
        newboard[zero_x][zero_y] = newboard[zero_x][zero_y + 1];
        newboard[zero_x][zero_y + 1] = temp;
        Board right = new Board(newboard);
        q.add(right);
        temp = newboard[zero_x][zero_y];
        newboard[zero_x][zero_y] = newboard[zero_x][zero_y + 1];
        newboard[zero_x][zero_y + 1] = temp;
      }
      return q;
    }

    public String toString() {
      StringBuilder retVal = new StringBuilder();
      retVal.append(dimension);
      retVal.append(NEWLINE);
      for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
          if (j == 0) {
            retVal.append(SPACE);
          }
          retVal.append(board[i][j]);
          if (j == dimension - 1) {
            retVal.append(NEWLINE);
          }
          else {
            retVal.append(SPACE);
          }
        }
      }
      return retVal.toString();
    }
}