import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  * Author: Swabir M. Bwana
 *  * Date: 10/12/2025
 *  * SA-6
 *  * Dartmouth CS10, Fall 2025
 *
 * BSTSet implements the SimpleSet interface using a Binary Search Tree (BST) to store data.
 *
 * The assignment hints:
 *
 * Hint 1:
 * - Create an instance variable called 'root', which is the root of a BST.
 * - Each Set value is stored as the BST's Key (don't use the Value, so we set it to Void/null).
 * - Do NOT extend the BST class. Instead, we *use* it (composition).
 * - Set functionality (add, remove, contains) is done by calling BST methods.
 *
 * Hint 2:
 * - We make BSTSet implement Iterable so we can loop over it with "for-each".
 * - We create a private inner class SetIterator that implements Iterator<T>.
 * - The SetIterator has a List that stores the BST keys in sorted (in-order) order.
 * - hasNext() and next() are implemented based on that list.
 *
 */

public class BSTSet<T extends Comparable<T>> implements SimpleSet<T> {

    // This is the root of our BST which stores all Set items as keys.
    // The Value type is Void coz we don't need to store any value.
    private BST<T, Void> root;

    // Keeps track of how many elements are in the set
    private int size;

    // Constructor: creates an empty set
    public BSTSet() {
        root = null;
        size = 0;
    }

    /**
     * Adds a new item to the set if it is not already present.
     * It returns true if added successfully, false if it already exists.
     */
    @Override
    public boolean add(T item) {
        // If the tree is empty, create the root
        if (root == null) {
            root = new BST<>(item, null);
            size++;
            return true;
        }

        // If the item already exists, don't add it again
        if (contains(item)) {
            return false;
        }

        // Otherwise, insert into the BST
        root.insert(item, null);
        size++;
        return true;
    }

    /**
     * Checks if the given item is already in the set.
     * Returns true if found, false otherwise.
     */
    @Override
    public boolean contains(T item) {
        if (root == null) return false;
        try {
            root.find(item); // BST will throw an exception if not found
            return true;
        } catch (InvalidKeyException e) {
            return false;
        }
    }

    /**
     * Removes an item from the set if it exists.
     * Returns true if removed successfully, false otherwise.
     */
    @Override
    public boolean remove(T item) {
        if (root == null || !contains(item)) return false;

        try {
            // BST delete() returns a possibly new root
            root = root.delete(item);
            size--;
            return true;
        } catch (InvalidKeyException e) {
            return false;
        }
    }

    /**
     * Removes all elements from the set.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns true if the set has no elements.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns how many elements are currently in the set.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns an iterator so we can loop through the Set with "for (T x : set)".
     * The iterator is based on a private inner class that we define below.
     */
    @Override
    public Iterator<T> iterator() {
        return new SetIterator();
    }

    /**
     * Private inner class that implements an Iterator over our BSTSet.
     *
     * We follow hint 2 whereby:
     * - We create a List<T> called 'elements' to store all BST keys.
     * - In the constructor, we do an in-order traversal of the BST and
     *   add each key to the list (this makes them sorted).
     * - hasNext() checks if we still have elements left.
     * - next() returns the next element in the list.
     */
    private class SetIterator implements Iterator<T> {
        private List<T> elements = new ArrayList<>(); // stores keys in sorted order
        private int index = 0; // keeps track of current position

        public SetIterator() {
            inorder(root); // fill the list by traversing the BST
        }

        // Recursive helper for in-order traversal
        private void inorder(BST<T, Void> node) {
            if (node == null) return;
            inorder(node.getLeft());
            elements.add(node.getKey());
            inorder(node.getRight());
        }

        @Override
        public boolean hasNext() {
            return index < elements.size();
        }

        @Override
        public T next() {
            return elements.get(index++);
        }
    }

    /**
     * Basic test cases to check that the Set works
     */
    public static void main(String[] args) {
        BSTSet<Integer> set = new BSTSet<>();

        System.out.println("Adding elements 5, 2, 8, 1, 5 (5 is duplicate)");
        set.add(5);
        set.add(2);
        set.add(8);
        set.add(1);
        set.add(5); // duplicate, should not increase size

        System.out.println("Set size: " + set.size()); // should be 4
        System.out.println("Contains 8? " + set.contains(8)); // true
        System.out.println("Contains 10? " + set.contains(10)); // false

        System.out.println("\nElements in sorted order:");
        for (int x : set) {
            System.out.print(x + " ");
        }
        System.out.println();

        System.out.println("\nRemoving 2...");
        set.remove(2);

        System.out.println("Set size after removing 2: " + set.size());
        System.out.println("Contains 2? " + set.contains(2));

        System.out.println("\nElements after removal:");
        for (int x : set) {
            System.out.print(x + " ");
        }
        System.out.println();
    }
}
