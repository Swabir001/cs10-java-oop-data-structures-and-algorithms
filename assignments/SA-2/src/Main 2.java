/**
 * Author: Swabir
 * CS10
 * Assignment: SA-2 -> Reunion
 * Date: 09/22/2025
 * Class: Main, runs the program. It creates a Reunion, adds attendees (both regular Attendees
 * and Graduates), and shows how RSVP updates work.
 */
public class Main {
    public static void main(String[] args) {
        // create a Reunion object that can hold a max of 5 people
        Reunion r = new Reunion(5);

        // add some attendees
        r.addAttendee(new Attendee("Alice", true));
        r.addAttendee(new Attendee("Bob", false));
        r.addAttendee(new Graduate("Charlie", true, 22, "Government")); // graduate
        r.addAttendee(new Graduate("Denise", false, 21, "Econ"));
        r.addAttendee(new Graduate("Elvis", true, 23, "Computer Science"));

        // try to add one more than the max (should print an error)
        r.addAttendee(new Graduate("Falcon", true, 26, "Biology"));

        System.out.println();

        // print the current list of attendees, will use Reunion's toString
        System.out.println(r);

        // test updating RSVP statuses
        System.out.println("Update rsvp status");
        r.rsvp("George", true); // should print that George not found in the list
        r.rsvp("Bob", true);    // Bob now changes RSVP to yes

        // print the updated reunion list
        System.out.println(r);
    }
}
