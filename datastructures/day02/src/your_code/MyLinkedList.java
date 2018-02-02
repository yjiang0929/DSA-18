package your_code;

public class MyLinkedList {

    private Node head;
    private Node tail;
    private int size;

    private class Node {
        Chicken val;
        Node prev;
        Node next;

        private Node(Chicken d, Node prev, Node next) {
            this.val = d;
            this.prev = prev;
            this.next = next;
        }
    }

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(Chicken c) {
        addLast(c);
    }

    public Chicken pop() {
        return removeLast();
    }

    public void addLast(Chicken c) {
        if (size == 0) {
            addFirst(c);
        } else {
            Node temp = new Node (c, tail, null);
            tail.next = temp;
            tail = temp;
            size ++;
        }
    }

    public void addFirst(Chicken c) {
        if (size == 0) {
            head = new Node (c,null, null);
            tail = head;
            size ++;
        } else {
            Node temp = new Node(c, null, head);
            head.prev = temp;
            head = temp;
            size ++;
        }
    }

    public Chicken get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            Node temp = head;
            while (index > 0) {
                temp = temp.next;
                index --;
            }
            return temp.val;
        }
    }

    public Chicken remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            if (index == 0) {
                return removeFirst();
            } else {
                if (index == size-1) {
                    return removeLast();
                } else {
                    Node temp = head;
                    while (index > 1) {
                        temp = temp.next;
                        index --;
                    }
                    Chicken res = temp.next.val;
                    temp.next = temp.next.next;
                    temp.next.prev = temp;
                    size --;
                    return res;
                }
            }
        }
    }

    public Chicken removeFirst() {
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        } else {
            if (size == 1) {
                Chicken temp = head.val;
                head = null;
                tail = null;
                size --;
                return temp;
            } else {
                Chicken temp = head.val;
                head = head.next;
                head.prev = null;
                size --;
                return temp;
            }
        }
    }

    public Chicken removeLast() {
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        } else {
            if (size == 1) {
                Chicken temp = head.val;
                head = null;
                tail = null;
                size --;
                return temp;
            } else {
                Chicken temp = tail.val;
                tail = tail.prev;
                tail.next = null;
                size --;
                return temp;
            }
        }
    }
}