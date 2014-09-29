public class Board {
    private static final String SPACE = " ";
    private static final String NEWLINE = "\n";

    private final int[][] board;
    private int dimension;
    private int manhattanScore = -1;
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
      if (manhattanScore == -1) {
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
      int zeroRow = -1;
      for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
          newboard[i][j] = board[i][j];
          if (newboard[i][j] == 0) {
            zeroRow = i;
          }
        }
      }
      if (zeroRow != 0) {
        //swap the first 2 elements in the first row
        int temp = newboard[0][0];
        newboard[0][0] = newboard[0][1];
        newboard[0][1] = temp;
      }
      else if (zeroRow == 0) {
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
      Board that = (Board) y;
      if (dimension != that.dimension()) {
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
      int zeroX = -1;
      int zeroY = -1;
      for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
          newboard[i][j] = board[i][j];
          if (newboard[i][j] == 0) {
            zeroX = i;
            zeroY = j;
          }
        }
      }
      //top
      if (zeroX - 1 >= 0) {
        int temp = newboard[zeroX][zeroY];
        newboard[zeroX][zeroY] = newboard[zeroX - 1][zeroY];
        newboard[zeroX - 1][zeroY] = temp;
        Board top = new Board(newboard);
        q.add(top);
        temp = newboard[zeroX][zeroY];
        newboard[zeroX][zeroY] = newboard[zeroX - 1][zeroY];
        newboard[zeroX - 1][zeroY] = temp;
      }


      //bottom
      if (zeroX + 1 < dimension) {
        int temp = newboard[zeroX][zeroY];
        newboard[zeroX][zeroY] = newboard[zeroX + 1][zeroY];
        newboard[zeroX + 1][zeroY] = temp;
        Board bottom = new Board(newboard);
        q.add(bottom);
        temp = newboard[zeroX][zeroY];
        newboard[zeroX][zeroY] = newboard[zeroX + 1][zeroY];
        newboard[zeroX + 1][zeroY] = temp;
      }

      //left
      if (zeroY - 1 >= 0) {
        int temp = newboard[zeroX][zeroY];
        newboard[zeroX][zeroY] = newboard[zeroX][zeroY - 1];
        newboard[zeroX][zeroY - 1] = temp;
        Board left = new Board(newboard);
        q.add(left);
        temp = newboard[zeroX][zeroY];
        newboard[zeroX][zeroY] = newboard[zeroX][zeroY - 1];
        newboard[zeroX][zeroY - 1] = temp;
      }

      //right
      if (zeroY + 1 < dimension) {
        int temp = newboard[zeroX][zeroY];
        newboard[zeroX][zeroY] = newboard[zeroX][zeroY + 1];
        newboard[zeroX][zeroY + 1] = temp;
        Board right = new Board(newboard);
        q.add(right);
        temp = newboard[zeroX][zeroY];
        newboard[zeroX][zeroY] = newboard[zeroX][zeroY + 1];
        newboard[zeroX][zeroY + 1] = temp;
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