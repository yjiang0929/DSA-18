import java.util.*;

public class NQueens {


    /**
     * Checks the 45° and 135° diagonals for an existing queen. For example, if the board is a 5x5
     * and you call checkDiagonal(board, 3, 1), The positions checked for an existing queen are
     * marked below with an `x`. The location (3, 1) is marked with an `o`.
     *
     * ....x
     * ...x.
     * x.x..
     * .o...
     * .....
     *
     * Returns true if a Queen is found.
     *
     * Do not modify this function (the tests use it)
     */
    public static boolean checkDiagonal(char[][] board, int r, int c) {
        int y = r - 1;
        int x = c - 1;
        while (y >= 0 && x >= 0) {
            if (board[y][x] == 'Q') return true;
            x--;
            y--;
        }
        y = r - 1;
        x = c + 1;
        while (y >= 0 && x < board[0].length) {
            if (board[y][x] == 'Q') return true;
            x++;
            y--;
        }
        return false;
    }


    /**
     * Creates a deep copy of the input array and returns it
     */
    private static char[][] copyOf(char[][] A) {
        char[][] B = new char[A.length][A[0].length];
        for (int i = 0; i < A.length; i++)
            System.arraycopy(A[i], 0, B[i], 0, A[0].length);
        return B;
    }

    public static void backtrack(char[][] board, LinkedList<Integer> used, Set<Integer> unused, List<char[][]> answers){
        if (unused.isEmpty()) {
            answers.add(copyOf(board));
        }
        for (int i: new LinkedList<>(unused)) {
            board[used.size()][i] = 'Q';
            if (!checkDiagonal(board, used.size(),i)) {
                used.addLast(i);
                unused.remove(i);
                backtrack(board, used, unused, answers);
                unused.add(i);
                used.removeLast();
            }
            board[used.size()][i] = '.';
        }
    }

    public static List<char[][]> nQueensSolutions(int n) {
        List<char[][]> answers = new ArrayList<>();
        char[][] board = new char[n][n];
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }
        Set<Integer> unused = new HashSet();
        for (int i=0;i<n;i++) {
            unused.add(i);
        }
        LinkedList<Integer> used = new LinkedList<>();
        backtrack(board, used, unused, answers);
        return answers;
    }

}
