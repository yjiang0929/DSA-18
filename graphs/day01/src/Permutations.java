import java.util.*;

public class Permutations {

    public static List<List<Integer>> permutations(List<Integer> A) {
        List<List<Integer>> permutations = new LinkedList<>();
        LinkedList<Integer> curr = new LinkedList<>();
        Set<Integer> unused = new HashSet(A);
        backtrack(curr, unused, permutations);
        return permutations;
    }

}
