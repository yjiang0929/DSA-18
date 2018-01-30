public class MyArrayList {
    private Cow[] elems;
    private int size;
    private int count = 0;

    // Runtime: O(1)
    public MyArrayList() {
        elems = new Cow[5];
        size = 5;
    }

    // Runtime: O(1)
    public MyArrayList(int capacity) {
        elems = new Cow[capacity];
        size = capacity;
    }

    public void inc_size() {
        Cow[] bigger = new Cow[size * 2];
        System.arraycopy(elems,0,bigger, 0, size);
        elems = bigger;
        size = size * 2;
    }

    public void dec_size() {
        Cow[] smaller = new Cow[size / 2];
        System.arraycopy(elems, 0, smaller, 0, size / 4);
        elems = smaller;
        size = size / 2;
    }

    // Runtime: O(1)*
    public void add(Cow c) {
        if (count >= size) {
            inc_size();
        }
        elems[count] = c;
        count ++;
    }

    // Runtime: O(1)
    public int size() {
        return count;
    }

    // Runtime: O(1)
    public Cow get(int index) {
        if (index < size && elems[index] != null) {
            return elems[index];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    // Runtime: O(n)
    public Cow remove(int index) {
        if (index >= count) {
            throw new IndexOutOfBoundsException();
        } else {
            if (count <= (size/4) && size>2) {
                dec_size();
            }
            Cow temp = elems[index];
            for (int i=index;i<count-1;i++){
                elems[i] = elems[i+1];
            }
            count --;
            return temp;
        }
    }

    // Runtime: O(n)
    public void add(int index, Cow c) {
        if (index > count) {
            throw new IndexOutOfBoundsException();
        } else {
            if (count >= size){
                inc_size();
            }
            for (int i=index+1;i<count;i++){
                elems[i] = elems[i-1];
            }
            elems[index] = c;
            count ++;
        }
    }
}
