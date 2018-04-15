import javax.print.DocFlavor;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

// this is our implementation of a rubiks cube. It is your job to use A* or some other search algorithm to write a
// solve() function
public class RubiksCube {

    private BitSet cube;
    private BitSet solved;

    public static HashMap<Set<Integer>,ArrayList<Integer>> location_dict;
    public static HashMap<Set<Integer>,ArrayList<Integer>> color_dict;
    public static ArrayList<ArrayList<Integer>> cubie_list;

    // initialize a solved rubiks cube
    public RubiksCube() {
        // 24 colors to store, each takes 3 bits
        cube = new BitSet(24 * 3);
        for (int side = 0; side < 6; side++) {
            for (int i = 0; i < 4; i++) {
                setColor(side * 4 + i, side);
            }
        }

        location_dict = new HashMap<>();
        color_dict = new HashMap<>();
        cubie_list = new ArrayList<>();

        // define color mappings
        color_dict.put(new HashSet<>(Arrays.asList(0,1,2)), new ArrayList<>(Arrays.asList(0,0,1)));
        color_dict.put(new HashSet<>(Arrays.asList(1,2,3)), new ArrayList<>(Arrays.asList(0,0,0)));
        color_dict.put(new HashSet<>(Arrays.asList(0,2,4)), new ArrayList<>(Arrays.asList(0,1,1)));
        color_dict.put(new HashSet<>(Arrays.asList(2,3,4)), new ArrayList<>(Arrays.asList(0,1,0)));
        color_dict.put(new HashSet<>(Arrays.asList(0,1,5)), new ArrayList<>(Arrays.asList(1,0,1)));
        color_dict.put(new HashSet<>(Arrays.asList(1,3,5)), new ArrayList<>(Arrays.asList(1,0,0)));
        color_dict.put(new HashSet<>(Arrays.asList(0,4,5)), new ArrayList<>(Arrays.asList(1,1,1)));
        color_dict.put(new HashSet<>(Arrays.asList(3,4,5)), new ArrayList<>(Arrays.asList(1,1,0)));

        // define location mappings
        location_dict.put(new HashSet<>(Arrays.asList(2,5,8)), new ArrayList<>(Arrays.asList(0,0,1)));
        location_dict.put(new HashSet<>(Arrays.asList(6,11,14)), new ArrayList<>(Arrays.asList(0,0,0)));
        location_dict.put(new HashSet<>(Arrays.asList(1,9,17)), new ArrayList<>(Arrays.asList(0,1,1)));
        location_dict.put(new HashSet<>(Arrays.asList(10,18,13)), new ArrayList<>(Arrays.asList(0,1,0)));
        location_dict.put(new HashSet<>(Arrays.asList(3,4,20)), new ArrayList<>(Arrays.asList(1,0,1)));
        location_dict.put(new HashSet<>(Arrays.asList(7,15,23)), new ArrayList<>(Arrays.asList(1,0,0)));
        location_dict.put(new HashSet<>(Arrays.asList(0,16,21)), new ArrayList<>(Arrays.asList(1,1,1)));
        location_dict.put(new HashSet<>(Arrays.asList(12,19,22)), new ArrayList<>(Arrays.asList(1,1,0)));

        cubie_list.add(new ArrayList<>(Arrays.asList(0,16,21)));
        cubie_list.add(new ArrayList<>(Arrays.asList(1,9,17)));
        cubie_list.add(new ArrayList<>(Arrays.asList(2,5,8)));
        cubie_list.add(new ArrayList<>(Arrays.asList(3,4,20)));
        cubie_list.add(new ArrayList<>(Arrays.asList(12,19,22)));
        cubie_list.add(new ArrayList<>(Arrays.asList(10,13,18)));
        cubie_list.add(new ArrayList<>(Arrays.asList(6,11,14)));
        cubie_list.add(new ArrayList<>(Arrays.asList(7,15,23)));
    }


    // initialize a rubiks cube with the input bitset
    private RubiksCube(BitSet s) {
        cube = (BitSet) s.clone();
    }

    // creates a copy of the rubics cube
    public RubiksCube(RubiksCube r) {
        cube = (BitSet) r.cube.clone();
    }

    // return true if this rubik's cube is equal to the other rubik's cube
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RubiksCube))
            return false;
        RubiksCube other = (RubiksCube) obj;
        return other.cube.equals(cube);
    }

    /**
     * return a hashCode for this rubik's cube.
     * <p>
     * Your hashCode must follow this specification:
     * if A.equals(B), then A.hashCode() == B.hashCode()
     * <p>
     * Note that this does NOT mean:
     * if A.hashCode() == B.hashCode(), then A.equals(B)
     */
    @Override
    public int hashCode() {
        return cube.hashCode();
    }

    public boolean isSolved() {
        return this.equals(new RubiksCube());
    }

    // takes in 3 bits where bitset.get(0) is the MSB, returns the corresponding int
    private static int bitsetToInt(BitSet s) {
        int i = 0;
        if (s.get(0)) i |= 4;
        if (s.get(1)) i |= 2;
        if (s.get(2)) i |= 1;
        return i;
    }

    // takes in a number 0-5, returns a length-3 bitset, where bitset.get(0) is the MSB
    private static BitSet intToBitset(int i) {
        BitSet s = new BitSet(3);
        if (i % 2 == 1) s.set(2, true);
        i /= 2;
        if (i % 2 == 1) s.set(1, true);
        i /= 2;
        if (i % 2 == 1) s.set(0, true);
        return s;
    }

    // index from 0-23, color from 0-5
    private void setColor(int index, int color) {
        BitSet colorBitset = intToBitset(color);
        for (int i = 0; i < 3; i++)
            cube.set(index * 3 + i, colorBitset.get(i));
    }


    // index from 0-23, returns a number from 0-5
    private int getColor(int index) {
        return bitsetToInt(cube.get(index * 3, (index + 1) * 3));
    }

    // given a list of rotations, return a rubik's cube with the rotations applied
    public RubiksCube rotate(List<Character> c) {
        RubiksCube rub = this;
        for (char r : c) {
            rub = rub.rotate(r);
        }
        return rub;
    }


    // Given a character in ['u', 'U', 'r', 'R', 'f', 'F'], return a new rubik's cube with the rotation applied
    // Do not modify this rubik's cube.
    public RubiksCube rotate(char c) {
        int[] faceFrom = null;
        int[] faceTo = null;
        int[] sidesFrom = null;
        int[] sidesTo = null;
        // colors move from the 'from' variable to the 'to' variable
        switch (c) {
            case 'u': // clockwise
            case 'U': // counterclockwise
                faceFrom = new int[]{0, 1, 2, 3};
                faceTo = new int[]{1, 2, 3, 0};
                sidesFrom = new int[]{4, 5, 8, 9, 17, 16, 21, 20};
                sidesTo = new int[]{21, 20, 4, 5, 8, 9, 17, 16};
                break;
            case 'r':
            case 'R':
                faceFrom = new int[]{8, 9, 10, 11};
                faceTo = new int[]{9, 10, 11, 8};
                sidesFrom = new int[]{6, 5, 2, 1, 17, 18, 13, 14};
                sidesTo = new int[]{2, 1, 17, 18, 13, 14, 6, 5};
                break;
            case 'f':
            case 'F':
                faceFrom = new int[]{4, 5, 6, 7};
                faceTo = new int[]{5, 6, 7, 4};
                sidesFrom = new int[]{3, 2, 8, 11, 14, 15, 23, 20};
                sidesTo = new int[]{8, 11, 14, 15, 23, 20, 3, 2};
                break;
            default:
                System.out.println(c);
                assert false;
        }
        // if performing a counter-clockwise rotation, swap from and to
        if (Character.isUpperCase(c)) {
            int[] temp;
            temp = faceFrom;
            faceFrom = faceTo;
            faceTo = temp;
            temp = sidesFrom;
            sidesFrom = sidesTo;
            sidesTo = temp;
        }
        RubiksCube res = new RubiksCube(cube);
        for (int i = 0; i < faceFrom.length; i++) res.setColor(faceTo[i], this.getColor(faceFrom[i]));
        for (int i = 0; i < sidesFrom.length; i++) res.setColor(sidesTo[i], this.getColor(sidesFrom[i]));
        return res;
    }

    // returns a random scrambled rubik's cube by applying random rotations
    public static RubiksCube scrambledCube(int numTurns) {
        RubiksCube r = new RubiksCube();
        char[] listTurns = getScramble(numTurns);
        for (int i = 0; i < numTurns; i++) {
            r = r.rotate(listTurns[i]);
        }
        return r;
    }

    public static char[] getScramble(int size) {
        char[] listTurns = new char[size];
        for (int i = 0; i < size; i++) {
            switch (ThreadLocalRandom.current().nextInt(0, 6)) {
                case 0:
                    listTurns[i] = 'u';
                    break;
                case 1:
                    listTurns[i] = 'U';
                    break;
                case 2:
                    listTurns[i] = 'r';
                    break;
                case 3:
                    listTurns[i] = 'R';
                    break;
                case 4:
                    listTurns[i] = 'f';
                    break;
                case 5:
                    listTurns[i] = 'F';
                    break;
            }
        }
        return listTurns;
    }

    private int distance() {
        int total = 0;
        for (ArrayList<Integer> cubie : cubie_list) {
            HashSet<Integer> temp_color = new HashSet<>();
            HashSet<Integer> temp_loc = new HashSet<>();
            for (Integer i:cubie) {
                temp_color.add(getColor(i));
                temp_loc.add(i);
            }
            ArrayList<Integer> correct_location = color_dict.get(temp_color);
            ArrayList<Integer> current_location = location_dict.get(temp_loc);
            for (int j=0;j<3;j++) {
                total += Math.abs(correct_location.get(j) - current_location.get(j));
            }
        }
        return total;
    }

    public class State {
        private RubiksCube cube;
        private int moves;                       // g-cost in A*
        public int cost;                         // f-cost in A*
        private State prev;
        private char rotation;

        public State(RubiksCube cube, int moves, State prev, char rotation) {
            this.cube = cube;
            this.moves = moves;
            this.prev = prev;
            this.rotation = rotation;
            cost = findCost();
        }

        public int findCost() {
            int g = this.moves;
            int f = this.cube.distance();
            int sum = f + g;
            return sum;
        }

        @Override
        public boolean equals(Object s) {
            if (s == this) return true;
            if (s == null) return false;
            if (!(s instanceof State)) return false;
            return ((State) s).cube.equals(this.cube);
        }
    }

    private ArrayList<State> neighbors(State current) {
        char[] rotations = {'u', 'U', 'r', 'R', 'f', 'F'};
        ArrayList<State> result = new ArrayList<>();
        for (int i=0;i<6;i++) {
            result.add(new State(current.cube.rotate(rotations[i]),current.moves+1, current, rotations[i]));
        }
        return result;
    }

    // return the list of rotations needed to solve a rubik's cube
    public List<Character> solve() {

        Queue<State> open = new PriorityQueue<>(5, (a,b) -> a.findCost() - b.findCost());
        Map<State, Integer> minCostVisited = new HashMap<>();
        open.add(new State(this, 0, null, ' '));

        while (open.peek() != null) {
            State q = open.poll();

            Iterable<State> successors = neighbors(q);

            // look at each successor
            for (State u: successors) {
                if (u.cube.isSolved()) {
                    List<Character> answer = new ArrayList<>();
                    while (u.rotation != ' ') {
                        answer.add(u.rotation);
                        u = u.prev;
                    }
                    Collections.reverse(answer);
                    return answer;
                }

                // check if we have visited u
                // if visited cost is higher than u
                // re-visit u
                if (minCostVisited.containsKey(u)) {
                    if (minCostVisited.get(u) > u.findCost()) {
                        open.add(u);
                    }
                } else { // we have never visited u - add to open
                    open.add(u);
                }
            }

            // add q to minCostVisited - as it is a visited node
            minCostVisited.put(q, q.findCost());
        }

        return new ArrayList<>();
    }

}