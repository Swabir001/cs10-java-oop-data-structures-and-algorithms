public class Laughter extends Thread {
    public String laugh;
    private static String result = "";

    public void run() {
        result += laugh;
        result += laugh;
    }

    public static void main(String[] args) throws Exception {
        Laughter ha = new Laughter();
        ha.laugh = "ha";

        Laughter hee = new Laughter();
        hee.laugh = "hee";

        ha.start();
        hee.start();
        ha.join();
        hee.join();

        System.out.println(result);
    }
}

/*

(a) Problem 1

Possible values of result when both threads finish:
- "hahaheehee"
- "heeheehaha"
- or interleaved results like:
  "haheehahee", "haheeheeha", "heehaheeha", etc.
Because both threads modify the shared static variable at the same time,
their operations overlap and cause a kinda unpredictable order (race condition).

To prevent that overlap:
Add a synchronized block inside run():
    public void run() {
        synchronized (Laughter.class) {
            result += laugh;
            result += laugh;
        }
    }


(b) Problem 2

If we remove 'static' from result:
- Each thread gets its own copy of result.
- 'ha' thread builds "haha"
- 'hee' thread builds "heehee"
- But the main method never accesses those individual copies,
  so the printed result would be an empty string or unchanged value.
- No interference occurs because there is no shared variable.


(c) Problem 3

Difference between Deadlock and Starvation:

Deadlock:
- Two or more threads wait for each other forever.
- Example: Thread A has lock X and needs Y,
            Thread B has lock Y and needs X.
  Both stop permanently. No progress at all.

Starvation:
- One thread keeps waiting because others always get priority.
- It’s not blocked forever, but never gets a chance to run.
- Example: a low-priority thread is constantly skipped
  while higher-priority threads keep running.

*/
