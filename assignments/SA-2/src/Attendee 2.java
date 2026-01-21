/**
 * Author: Swabir Mohamed Bwana, '27
 * CS10
 * Assignment: SA-2 -> Reunion
 * Date: 09/22/2025
 * Class: Attendee
 * It represents a person who might attend the reunion.
 * Each attendee has a name and an RSVP status (true if attending, false otherwise).
 */
public class Attendee {

    private String name;
    private boolean rsvp;

    /**
     * My Constructor
     * @param name person's name
     * @param rsvp whether they RSVP'd (true or false)
     */
    public Attendee(String name, boolean rsvp) {
        this.name = name;
        this.rsvp = rsvp;
    }

    @Override
    public String toString() {
        return name;
    }

    // Getters and Setters

    public boolean isRsvp() {
        return rsvp;
    }

    public void setRsvp(boolean rsvp) {
        this.rsvp = rsvp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
