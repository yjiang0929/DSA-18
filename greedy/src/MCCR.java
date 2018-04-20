import java.util.ArrayList;

public class MCCR {
        public static int MCCR(EdgeWeightedGraph G) {
            IndexPQ<Integer> open = new IndexPQ<>(G.numberOfV());
            for (int i=1;i<G.numberOfV();i++) {
                open.insert(i,Integer.MAX_VALUE);
            }
            for (Edge side : G.edges(0)) {
                open.changeKey(side.other(0), side.weight());
            }
            int total = 0;
            ArrayList<Integer> traversed = new ArrayList<>();
            traversed.add(0);
            while (!open.isEmpty()) {
                int[] pair = open.delMin();
                total += pair[1];
                traversed.add(pair[0]);
                for (Edge side: G.edges(pair[0])) {
                    if (!traversed.contains(side.other(pair[0]))) {
                        open.decreaseKey(side.other(pair[0]), side.weight());
                    }
                }
            }
            return total;
        }

    }

