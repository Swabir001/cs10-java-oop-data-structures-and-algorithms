/**
 * Author: Swabir Mohamed Bwana, '27
 * CS10
 * Assignment: SA-2 -> Reunion
 * Date: 09/22/2025
 * Class: Reunion
 * Tracks attendees for the reunion using an array
 * Provides methods to add attendees, update RSVP status, and print a summary
 * of who has RSVP'd and who has not.
 */
public class Reunion {
    private Attendee[] attendees; // array of attendees
    private int max;              // max allowed
    private int count;            // current number added

    /**
     * Constructor for Reunion
     * @param max maximum number of attendees
     */
    public Reunion(int max) {
        this.max = max;
        this.attendees = new Attendee[max];
        this.count = 0;
    }

    /**
     * Adds an attendee, only if there is space.
     * Prints an error if the reunion is already full.
     * @param a attendee (or graduate) to add
     */
    public void addAttendee(Attendee a) {
        if (count >= max) {
            System.out.println("Unable to accommodate " + a + ", max attendees is " + attendees.length);
            return;
        }
        this.attendees[count] = a;
        count++;
        System.out.println("Added " + a + ", rsvp: " + a.isRsvp());
    }

    /**
     * Updates RSVP status for a person, matched by name.
     * @param name   the person's name
     * @param status new RSVP value
     */
    public void rsvp(String name, boolean status) {
        for (int i = 0; i < count; i++) {
            if (attendees[i].getName().equals(name)) {
                attendees[i].setRsvp(status);
                System.out.println("Set RSVP to " + status + " for " + name);
                return;
            }
        }
        System.out.println(name + " not found");
    }

    /**
     * Prints all attendees
     * @return formatted string of attendees
     */
    @Override
    public String toString() {
        String yes = "Attendees who have RSVP:\n";
        String no = "Attendees who have NOT RSVP:\n";

        for (int i = 0; i < count; i++) {
            if (attendees[i].isRsvp()) {
                yes += "  " + attendees[i] + "\n";
            } else {
                no += "  " + attendees[i] + "\n";
            }
        }
        return yes + "\n" + no;
    }
}
