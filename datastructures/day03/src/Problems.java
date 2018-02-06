import java.util.*;

public class Problems {

    public static class Node {
        int val;
        Node next;

        Node(int d) {
            this.val = d;
            next = null;
        }
    }

    public static List<Integer> removeKDigits(int[] A, int k) {
        if (A.length == 0) {
            throw new IndexOutOfBoundsException();
        }
        LinkedList<Integer> small = new LinkedList<>();
        small.addLast(A[0]);
        int i = 1;
        while (k>0) {
            if (i==A.length) {
                small.removeLast();
                k--;
            } else {
                if (small.size() > 0 && A[i] < small.peekLast()) {
                    small.removeLast();
                    k--;
                } else {
                    small.addLast(A[i]);
                    i++;
                }
            }
        }
        while (i<A.length) {
            small.addLast(A[i]);
            i++;
        }
        return small;
    }

    public static boolean isPalindrome(Node n) {
        if (n == null) {
            return true;
        }
        Node head = n;
        Integer len = 1;
        while (head.next!=null) {
            len++;
            head = head.next;
        }
        head = n;
        Integer half = len / 2;
        Node mid = n;
        Integer i = 0;
        while (i<half) {
            mid = mid.next;
            i++;
        }
        Node prev = mid;
        Node tail = mid.next;
        Node next = null;
        while (tail!=null) {
            next = tail.next;
            tail.next = prev;
            prev = tail;
            tail = next;
        }
        tail = prev;
        i = 0;
        while (i<half){
            if (head.val != tail.val) {
                return false;
            }
            head = head.next;
            tail = tail.next;
            i++;
        }
        return true;
    }

    public static String infixToPostfix(String s) {
        Integer i = 0;
        Stack<Character> operators = new Stack<>();
        String res = "";
        Integer para = 0;
        while (i<s.length()) {
            if (s.charAt(i) == ' ') {
                i++;
            }
            if (s.charAt(i) == '(') {
                para++;
            } else {
                if (s.charAt(i) >='0' && s.charAt(i) <= '9') {
                    res = res + s.charAt(i) + ' ';
                } else {
                    if (s.charAt(i) == ')') {
                        res = res + operators.pop() + ' ';
                        para--;
                    } else {
                        if (para>0 || operators.size() == 0) {
                            operators.push(s.charAt(i));
                        } else {
                            res = res + operators.pop() + ' ';
                            operators.push(s.charAt(i));
                        }

                    }
                }
            }
            i++;
        }
        if (operators.size()!=0) {
            res = res + operators.pop() + ' ';
        }
        return res.substring(0,res.length()-1);
    }

}
