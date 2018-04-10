import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Board definition for the 8 Puzzle challenge
 */
public class Board {

    private int n;
    public int[][] tiles;

    // Create a 2D array representing the solved board state
    private int[][] goal = {{}};

    /*
     * Set the global board size and tile state
     */
    public Board(int[][] b) {
        tiles = b;
        n = b.length;

        goal = new int[b.length][b.length];
        for (int i=0;i<b.length;i++) {
            for (int j=0;j<b.length;j++) {
                if (i==b.length-1 && j==b.length-1) {
                    goal[i][j] = 0;
                } else {
                    goal[i][j] = i*b.length+j + 1;
                }
            }
        }
    }

    /*
     * Size of the board 
     (equal to 3 for 8 puzzle, 4 for 15 puzzle, 5 for 24 puzzle, etc)
     */
    private int size() {
        return n;
    }

    /*
     * Sum of the manhattan distances between the tiles and the goal
     */
    public int manhattan() {
        int sum = 0;
        for (int i=0;i<size();i++) {
            for (int j=0;j<size();j++) {
                if (tiles[i][j] != 0) {
                    sum += Math.abs(i-(tiles[i][j]-1) / size()) + Math.abs(j-(tiles[i][j]-1) % size());
                }
            }
        }
        return sum;
    }

    /*
     * Compare the current state to the goal state
     */
    public boolean isGoal() {
        for (int i=0;i<size();i++) {
            for (int j=0;j<size();j++) {
                if (tiles[i][j]!= goal[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * Returns true if the board is solvable
     * Research how to check this without exploring all states
     */
    public boolean solvable() {
        List<Integer> temp = new ArrayList<>();
        for (int i=0;i<size();i++) {
            for (int j=0;j<size();j++) {
                temp.add(tiles[i][j]);
            }
        }
        int inv = 0;
        for (int i=0;i<temp.size();i++) {
            for (int j=i+1;j<temp.size();j++) {
                if (temp.get(i) > temp.get(j) && temp.get(i)!=0 && temp.get(j)!=0){
                    inv += 1;
                }
            }
        }
        if (inv % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Return all neighboring boards in the state tree
     */
    public Iterable<Board> neighbors() {
        List<Board> result = new ArrayList<>();
        int[][] replace = {{0,1},{1,0},{0,-1},{-1,0}};
        int zero_x = 0;
        int zero_y = 0;
        for (int i=0;i<size();i++) {
            for (int j=0;j<size();j++) {
                if (tiles[i][j] == 0) {
                    zero_x = i;
                    zero_y = j;
                    break;
                }
            }
        }
        for (int i=0;i<4;i++) {
            int new_x = zero_x+replace[i][0];
            int new_y = zero_y+replace[i][1];
            if (new_x>=0 && new_y>=0 && new_x<size() && new_y<size()) {
                tiles[zero_x][zero_y] = tiles[new_x][new_y];
                tiles[new_x][new_y] = 0;
                int[][] temp_tiles = new int[size()][size()];
                for (int j = 0; j < size(); j++) {
                    System.arraycopy(tiles[j], 0, temp_tiles[j], 0, size());
                }
                result.add(new Board(temp_tiles));
                tiles[new_x][new_y] = tiles[zero_x][zero_y];
                tiles[zero_x][zero_y] = 0;
            }
        }
        return result;
    }

    /*
     * Check if this board equals a given board state
     */
    @Override
    public boolean equals(Object x) {
        // Check if the board equals an input Board object
        if (x == this) return true;
        if (x == null) return false;
        if (!(x instanceof Board)) return false;
        // Check if the same size
        Board y = (Board) x;
        if (y.tiles.length != n || y.tiles[0].length != n) {
            return false;
        }
        // Check if the same tile configuration
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != y.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // DEBUG - Your solution can include whatever output you find useful
        int[][] initState = {{1, 0, 3}, {2, 4, 5}, {6, 7, 8}};
        Board board = new Board(initState);

        System.out.println("Size: " + board.size());
        System.out.println("Solvable: " + board.solvable());
        System.out.println("Manhattan: " + board.manhattan());
        System.out.println("Is goal: " + board.isGoal());
        System.out.println("Neighbors:");
        Iterable<Board> it = board.neighbors();
    }
}
