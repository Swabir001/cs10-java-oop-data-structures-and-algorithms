/**
 * Author: Swabir Mohamed Bwana, '27
 * CS10
 * Assignment: SA-2 -> Reunion
 * Date: 09/22/2025
 * Class: Graduate
 * It's a subclass of Attendee, it has a graduation year, and a department. Overrides toString as well so it prints in the format:
 * "<name> '<gradYear> <department>"
*/
public class Graduate extends Attendee {

    private int gradYear;
    private String department;

    /**
     * Constructor for Graduate
     * @param name       graduate's name
     * @param rsvp       RSVP status
     * @param gradYear   year of graduation
     * @param department academic department
     */
    public Graduate(String name, boolean rsvp, int gradYear, String department) {
        super(name, rsvp); // call Attendee constructor
        this.gradYear = gradYear;
        this.department = department;
    }

    // Getters and Setters

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getGradYear() {
        return gradYear;
    }

    public void setGradYear(int gradYear) {
        this.gradYear = gradYear;
    }


//     Returns graduate's name, year, and department.

    @Override
    public String toString() {
        return super.toString() + " '" + gradYear + " " + department;
    }
}
