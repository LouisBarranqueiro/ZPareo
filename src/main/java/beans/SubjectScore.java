package beans;

import java.util.Set;

public class SubjectScore implements Comparable<SubjectScore> {
    private Long      id;
    private Subject   subject;
    private Float     average;
    private Set<Test> tests;

    /**
     * Constructor
     */
    public SubjectScore() {
        this.id = null;
        this.subject = null;
        this.average = null;
        this.tests = null;
    }

    /**
     * Constructor
     * @param subjectScore
     */
    public SubjectScore(SubjectScore subjectScore) {
        this.id = subjectScore.getId();
        this.subject = subjectScore.getSubject();
        this.average = subjectScore.getAverage();
        this.tests = subjectScore.getTests();
    }

    /**
     * Returns matterscore id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets matterscore id
     * @return id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns matterscore subject
     * @return subject
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Sets matterscore subject
     * @param subject
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /**
     * Returns matterscore average
     * @return average
     */
    public Float getAverage() {
        return average;
    }

    /**
     * Sets matterscore average
     * @param average
     */
    public void setAverage(Float average) {
        this.average = average;
    }

    /**
     * Returns matterscore tests
     * @return tests
     */
    public Set<Test> getTests() {
        return tests;
    }

    /**
     * Sets matterscore tests
     * @param tests
     */
    public void setTests(Set<Test> tests) {
        this.tests = tests;
    }

    /**
     * Compares two matterscore name
     * @param subjectScore2
     */
    public int compareTo(SubjectScore subjectScore2) {
        Subject subject1 = new Subject(this.getSubject());
        Subject subject2 = new Subject(subjectScore2.getSubject());
        int     compId   = subject2.getName().compareTo(subject1.getName());

        return ((compId != 0) ? compId : 0);
    }

}
