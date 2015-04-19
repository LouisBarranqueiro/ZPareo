package beans;

public class Student extends User implements Comparable<Student> {
    private Group     group;
    private Gradebook gradebook;

    /**
     * Constructor
     */
    public Student() {
        super();
        this.group = null;
        this.gradebook = null;
    }

    /**
     * Constructor
     * @param student
     */
    public Student(Student student) {
        super(student);
        this.group = student.getGroup();
        this.gradebook = student.getGradebook();
    }

    /**
     * Returns student group
     * @return group
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Sets student group
     * @param group
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * Returns student gradebook
     * @return gradebook
     */
    public Gradebook getGradebook() {
        return gradebook;
    }

    /**
     * Sets student gradebook
     * @param gradebook
     */
    public void setGradebook(Gradebook gradebook) {
        this.gradebook = gradebook;
    }

    /**
     * Compares two student id
     * @param student2
     */
    public int compareTo(Student student2) {
        int compId = student2.getId().compareTo(this.getId());

        return ((compId != 0) ? compId : 0);
    }

}