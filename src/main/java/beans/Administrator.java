package beans;

public class Administrator extends User implements Comparable<Administrator> {
    /**
     * Constructor
     */
    public Administrator() {
        super();
    }

    /**
     * Constructor
     * @param administrator
     */
    public Administrator(Administrator administrator) {
        super(administrator);
    }

    /**
     * Compares two administrators's id
     * @param administrator2
     */
    public int compareTo(Administrator administrator2) {
        int compId = administrator2.getId().compareTo(this.getId());

        return ((compId != 0) ? compId : 0);
    }

}
