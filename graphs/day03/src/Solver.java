/**
 * Solver definition for the 8 Puzzle challenge
 * Construct a tree of board states using A* to find a path to the goal
 */

import java.util.*;

public class Solver{

    public int minMoves = -1;
    private State currentState;
    private boolean solved = false;
    private PriorityQueue<State> open = new PriorityQueue<>();
    private LinkedList<State> closed = new LinkedList<>();

    /**
     * State class to make the cost calculations simple
     * This class holds a board state and all of its attributes
     */
    private class State implements Comparable<State> {
        // Each state needs to keep track of its cost and the previous state
        private Board board;
        private int moves; // equal to g-cost in A*
        public int cost; // equal to f-cost in A*
        private State prev;

        public State(Board board, int moves, State prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;

            cost = moves + board.manhattan();
        }

        @Override
        public boolean equals(Object s) {
            if (s == this) return true;
            if (s == null) return false;
            if (!(s instanceof State)) return false;
            return ((State) s).board.equals(this.board);
        }

        @Override
        public int compareTo(State otherState){
            return Integer.compare(cost, otherState.cost);
        }

    }

    /*
     * Return the root state of a given state
     */
    /*private State root(State state) {
        // TODO: Your code here
        return null;
    }*/

    /*
     * A* Solver
     * Find a solution to the initial board using A* to generate the state tree
     * and a identify the shortest path to the the goal state
     */
    public Solver(Board initial) { // Solver Wrapper
        currentState = new State(initial, 0, null);
        if (isSolvable()){
            currentState.cost = 0;
            open.offer(currentState);

            while (!open.isEmpty()){
                State q = open.poll();
                for (Board u : q.board.neighbors()){
                    if (u.isGoal()){
                        minMoves = currentState.moves + 1;
                        return;
                    }
                    State u_state = new State(u, q.moves+1, q);

                    boolean ignore = false;
                    for (State n : open){
                        if (n.equals(u_state) && n.cost < u_state.cost) {
                            ignore = true;
                            break;
                        }
                    }
                    if (!ignore) {
                        for (State n : closed){
                            if (n.equals(u_state) && n.cost < u_state.cost){
                                ignore = true;
                                break;
                            }
                        }
                    }
                    if (!ignore){
                        u_state.prev = q;
                        open.offer(u_state);
                    }
                }

                closed.add(q);
            }
        }
    }


    /*
     * Is the input board a solvable state
     * Research how to check this without exploring all states
     */
    public boolean isSolvable() {
        return currentState.board.solvable();
    }

    /*
     * Return the sequence of boards in a shortest solution, null if unsolvable
     */
    /*public Iterable<Board> solution() { //Solver Iterator

        return null;
    }*/

    //public void solution() {
    //}

    public State find(Iterable<State> iter, Board b) {
        for (State s : iter) {
            if (s.board.equals(b)) {
                return s;
            }
        }
        return null;
    }

    /*
     * Debugging space
     */
    public static void main(String[] args) {
        int[][] initState = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board initial = new Board(initState);

        Solver solver = new Solver(initial);
    }


}
