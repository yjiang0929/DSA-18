import jdk.nashorn.api.tree.Tree;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> {
    TreeNode<T> root;
    private int size;

    public int size() {
        return size;
    }

    public boolean contains(T key) {
        return find(root, key) != null;
    }

    /**
     * Add a node to the BST. Internally calls insert to recursively find the new node's place
     */
    public boolean add(T key) {
        if (find(root, key) != null) return false;
        root = insert(root, key);
        size++;
        return true;
    }

    public void addAll(T[] keys) {
        for (T k : keys)
            add(k);
    }

    public List<T> inOrderTraversal() {
        // Runtime O(N)
        ArrayList<T> result = new ArrayList<>();
        if (root == null) {
            return result;
        } else {
            if (root.hasLeftChild()) {
                root = root.leftChild;
                result.addAll(inOrderTraversal());
                root = root.parent;
            }
            result.add(root.key);
            if (root.hasRightChild()) {
                root = root.rightChild;
                result.addAll(inOrderTraversal());
                root = root.parent;
            }
        }
        return result;
    }

    /**
     * Deletes a node from the BST using the following logic:
     * 1. If the node has a left child, replace it with its predecessor
     * 2. Else if it has a right child, replace it with its successor
     * 3. If it has no children, simply its parent's pointer to it
     */
    public boolean delete(T key) {
        TreeNode<T> toDelete = find(root, key);
        if (toDelete == null) {
            System.out.println("Key does not exist");
            return false;
        }
        TreeNode<T> deleted = delete(toDelete);
        if (toDelete == root) {
            root = deleted;
        }
        size--;
        return true;
    }

    private TreeNode<T> delete(TreeNode<T> n) {
        // Recursive base case
        if (n == null) return null;

        TreeNode<T> replacement;
        boolean isCase3 = false;

        if (n.isLeaf())
            // Case 1: no children
            replacement = null;
        else if (n.hasRightChild() != n.hasLeftChild())
            // Case 2: one child
            replacement = (n.hasRightChild()) ? n.rightChild : n.leftChild; // replacement is the non-null child
        else {
            // Case 3: two children
            replacement = findSuccessor(n);
            delete(replacement);
            n.key = replacement.key;
            isCase3 = true;
        }

        // Put the replacement in its correct place, and set the parent.
        if (!isCase3) {
            n.replaceWith(replacement);
            return replacement;
        } else {
            return n;
        }
    }

    public T findPredecessor(T key) {
        TreeNode<T> n = find(root, key);
        if (n != null) {
            TreeNode<T> predecessor = findPredecessor(n);
            if (predecessor != null)
                return predecessor.key;
        }
        return null;
    }

    public T findSuccessor(T key) {
        TreeNode<T> n = find(root, key);
        if (n != null) {
            TreeNode<T> successor = findSuccessor(n);
            if (successor != null)
                return successor.key;
        }
        return null;
    }

    private TreeNode<T> findPredecessor(TreeNode<T> n) {
        // Worst Case Runtime: O(N)
        if (n.hasLeftChild()) {
            TreeNode<T> predecessor = n.leftChild;
            while (predecessor.hasRightChild()) {
                predecessor = predecessor.rightChild;
            }
            return predecessor;
        } else {
            TreeNode<T> predecessor = n.parent;
            if (predecessor==null) { return null; };
            while (predecessor.hasLeftChild() && predecessor.leftChild==n) {
                n = predecessor;
                predecessor = predecessor.parent;
                if (predecessor == null) {
                    return null;
                }
            }
            return predecessor;
        }
    }

    private TreeNode<T> findSuccessor(TreeNode<T> n) {
        // Worst Case Runtime: O(N)
        if (n.hasRightChild()) {
            TreeNode<T> successor = n.rightChild;
            while (successor.hasLeftChild()) {
                successor = successor.leftChild;
            }
            return successor;
        } else {
            TreeNode<T> successor = n.parent;
            if (successor == null) {return null;}
            while (successor.hasRightChild() && successor.rightChild == n) {
                n = successor;
                successor = successor.parent;
                if (successor == null) {
                    return null;
                }
            }
            return successor;
        }
    }

    /**
     * Returns a node with the given key in the BST, or null if it doesn't exist.
     */
    private TreeNode<T> find(TreeNode<T> currentNode, T key) {
        if (currentNode == null)
            return null;
        int cmp = key.compareTo(currentNode.key);
        if (cmp < 0)
            return find(currentNode.leftChild, key);
        else if (cmp > 0)
            return find(currentNode.rightChild, key);
        return currentNode;
    }

    /**
     * Recursively insert a new node into the BST
     */
    private TreeNode<T> insert(TreeNode<T> node, T key) {
        if (node == null) return new TreeNode<>(key);

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.leftChild = insert(node.leftChild, key);
            node.leftChild.parent = node;
        } else {
            node.rightChild = insert(node.rightChild, key);
            node.rightChild.parent = node;
        }
        return node;
    }
}
