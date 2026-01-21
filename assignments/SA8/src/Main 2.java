/**
 * SA-8: Deque Test
 * Author: Swabir Mohamed Bwana
 * Date: October 17, 2025
 *
 * Description:
 * Tests the MyDeque class by adding, removing, and checking elements.
 * Verifies that all deque methods work as expected.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        MyDeque<Integer> dq = new MyDeque<>();

        dq.addFirst(10);
        dq.addLast(20);
        dq.addFirst(5);

        System.out.println(dq.getFirst()); // 5
        System.out.println(dq.getLast());  // 20

        dq.removeFirst(); // removes 5
        dq.removeLast();  // removes 20

        System.out.println(dq.contains(10)); // true
        System.out.println(dq.size());       // 1
        System.out.println(dq.isEmpty());    // false
    }
}
