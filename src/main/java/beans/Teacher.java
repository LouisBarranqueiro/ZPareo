package beans;

import java.util.Set;

public class Teacher extends User implements Comparable<Teacher> {
    private Set<Subject> subjects;
    private Set<Group>   groups;

    /**
     * Constructor
     */
    public Teacher() {
        super();
        this.subjects = null;
        this.groups = null;
    }

    /**
     * Constructor
     * @param teacher
     */
    public Teacher(Teacher teacher) {
        super(teacher);
        this.subjects = teacher.getSubjects();
        this.groups = teacher.getGroups();
    }

    /**
     * Returns teacher subjects
     * @return subjects
     */
    public Set<Subject> getSubjects() {
        return subjects;
    }

    /**
     * Sets teacher subjects
     * @param subjects
     */
    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    /**
     * Returns teacher groups
     * @return groups
     */
    public Set<Group> getGroups() {
        return groups;
    }

    /**
     * Sets teacher groups
     * @param groups
     */
    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    /**
     * Compares two teacher id
     * @param teacher2
     */
    public int compareTo(Teacher teacher2) {
        int compId = teacher2.getId().compareTo(this.getId());

        return ((compId != 0) ? compId : 0);
    }

}