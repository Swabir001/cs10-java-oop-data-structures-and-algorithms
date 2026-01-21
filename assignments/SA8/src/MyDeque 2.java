/**
 * SA-8: Deque Implementation
 * Author: Swabir Mohamed Bwana
 * Date: October 17, 2025
 *
 * Description:
 * Implements a generic double-ended queue (deque) using a doubly linked list.
 * Supports adding, removing, and accessing elements from both ends.
 * Conforms to the SimpleDeque interface.
 */

public class MyDeque<T> implements SimpleDeque<T> {

    // inner node class
    private class Node {
        T data;
        Node next;
        Node prev;

        Node(T data) {
            this.data = data;
        }
    }

    private Node head; // front of deque
    private Node tail; // back of deque
    private int size;

    // add item at front
    public void addFirst(T item) {
        Node newNode = new Node(item);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // add item at end
    public void addLast(T item) {
        Node newNode = new Node(item);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    // remove and return first item
    public T removeFirst() throws Exception {
        if (isEmpty()) throw new Exception("Deque is empty");
        T data = head.data;
        head = head.next;
        if (head != null) head.prev = null;
        else tail = null; // now empty
        size--;
        return data;
    }

    // remove and return last item
    public T removeLast() throws Exception {
        if (isEmpty()) throw new Exception("Deque is empty");
        T data = tail.data;
        tail = tail.prev;
        if (tail != null) tail.next = null;
        else head = null; // now empty
        size--;
        return data;
    }

    // peek first item
    public T getFirst() throws Exception {
        if (isEmpty()) throw new Exception("Deque is empty");
        return head.data;
    }

    // peek last item
    public T getLast() throws Exception {
        if (isEmpty()) throw new Exception("Deque is empty");
        return tail.data;
    }

    // remove all items
    public void clear() {
        head = tail = null;
        size = 0;
    }

    // check if item exists
    public boolean contains(T item) {
        Node current = head;
        while (current != null) {
            if (current.data.equals(item)) return true;
            current = current.next;
        }
        return false;
    }

    // true if deque is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // number of elements
    public int size() {
        return size;
    }
}


/*

Runtime Complexity:

addFirst(T item):        O(1)
addLast(T item):         O(1)
removeFirst():           O(1)
removeLast():            O(1)
getFirst():              O(1)
getLast():               O(1)
clear():                 O(1)
contains(T item):        O(n)
isEmpty():               O(1)
size():                  O(1)

All core operations (add/remove/get) run in constant time
because of the doubly linked list structure with head and tail pointers.
Only 'contains' is linear since it scans the list.
*/



