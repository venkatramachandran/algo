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
      manhattanScore = manhattan();
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
        manhattanScore = 0;
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
      int zeroCol = -1;
      for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
          newboard[i][j] = board[i][j];
          if (newboard[i][j] == 0) {
            zeroRow = i;
            zeroCol = j;
          }
        }
      }

      int swapx = -1;
      int swapy = 0;

      if (zeroRow == 0) {
        swapx = 1;
      }
      else if (zeroRow == dimension - 1) {
        swapx = dimension - 2;
      }
      else {
        swapx = zeroRow + 1;
      }

      int temp = newboard[swapx][swapy];
      newboard[swapx][swapy] = newboard[swapx][swapy + 1];
      newboard[swapx][swapy + 1] = temp;

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
      Board left = null, right = null, top = null, bottom = null;

      int[][] newboard = new int[dimension][dimension];
      int zeroX = -1;
      int zeroY = -1;
      int temp = -1;
      for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
          newboard[i][j] = board[i][j];
          if (newboard[i][j] == 0) {
            zeroX = i;
            zeroY = j;
          }
        }
      }

      //right
      if (zeroY + 1 < dimension) {
        temp = newboard[zeroX][zeroY];
        newboard[zeroX][zeroY] = newboard[zeroX][zeroY + 1];
        newboard[zeroX][zeroY + 1] = temp;

        right = new Board(newboard);

        temp = newboard[zeroX][zeroY];
        newboard[zeroX][zeroY] = newboard[zeroX][zeroY + 1];
        newboard[zeroX][zeroY + 1] = temp;
      }

      //left
      if (zeroY - 1 >= 0) {
        temp = newboard[zeroX][zeroY];
        newboard[zeroX][zeroY] = newboard[zeroX][zeroY - 1];
        newboard[zeroX][zeroY - 1] = temp;

        left = new Board(newboard);

        temp = newboard[zeroX][zeroY];
        newboard[zeroX][zeroY] = newboard[zeroX][zeroY - 1];
        newboard[zeroX][zeroY - 1] = temp;
      }

      //top
      if (zeroX - 1 >= 0) {
        temp = newboard[zeroX][zeroY];
        newboard[zeroX][zeroY] = newboard[zeroX - 1][zeroY];
        newboard[zeroX - 1][zeroY] = temp;

        top = new Board(newboard);

        temp = newboard[zeroX][zeroY];
        newboard[zeroX][zeroY] = newboard[zeroX - 1][zeroY];
        newboard[zeroX - 1][zeroY] = temp;
      }


      //bottom
      if (zeroX + 1 < dimension) {
        temp = newboard[zeroX][zeroY];
        newboard[zeroX][zeroY] = newboard[zeroX + 1][zeroY];
        newboard[zeroX + 1][zeroY] = temp;

        bottom = new Board(newboard);

        temp = newboard[zeroX][zeroY];
        newboard[zeroX][zeroY] = newboard[zeroX + 1][zeroY];
        newboard[zeroX + 1][zeroY] = temp;
      }

      if (left != null) q.add(left);
      if (right != null) q.add(right);
      if (top != null) q.add(top);
      if (bottom != null) q.add(bottom);

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