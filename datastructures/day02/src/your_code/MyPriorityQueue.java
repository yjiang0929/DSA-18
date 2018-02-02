package your_code;

import jdk.dynalink.linker.LinkerServices;

import java.util.LinkedList;

/**
 * An implementation of a priority Queue
 */
public class MyPriorityQueue {

    private LinkedList<Integer> ll;

    public MyPriorityQueue() {
        ll = new LinkedList<>();
    }

    public void enqueue(int item) {
        ll.add(item);
    }

    /**
     * Return and remove the largest item on the queue.
     */
    public int dequeueMax() {
        int max_value = 0;
        int max_index = 0;
        for (int i=0;i<ll.size();i++) {
            if (ll.get(i) > max_value) {
                max_value = ll.get(i);
                max_index = i;
            }
        }
        return ll.remove(max_index);
    }

}