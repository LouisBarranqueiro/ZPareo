package beans;

import java.util.Set;

public class Test implements Comparable<Test> {
    private Long       id;
    private String     title;
    private String     date;
    private TestFormat format;
    private Teacher    teacher;
    private Group      group;
    private Subject    subject;
    private Set<Score> scores;
    private Float      average;
    private Float      coefficient;

    /**
     * Constructeur
     */
    public Test() {
        this.id = null;
        this.title = null;
        this.date = null;
        this.format = null;
        this.teacher = null;
        this.group = null;
        this.subject = null;
        this.scores = null;
        this.average = null;
        this.coefficient = null;
    }

    /**
     * Constructeur
     * @param test
     */
    public Test(Test test) {
        this.id = test.getId();
        this.title = test.getTitle();
        this.date = test.getDate();
        this.format = test.getFormat();
        this.teacher = test.getTeacher();
        this.group = test.getGroup();
        this.subject = test.getSubject();
        this.scores = test.getScores();
        this.average = test.getAverage();
        this.coefficient = test.getCoefficient();
    }

    /**
     * Return test id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets test id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns test title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets test title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns test date
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets test date
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns test format
     * @return format
     */
    public TestFormat getFormat() {
        return format;
    }

    /**
     * Sets test format
     * @param format
     */
    public void setFormat(TestFormat format) {
        this.format = format;
    }

    /**
     * Returns test teacher
     * @return teacher
     */
    public Teacher getTeacher() {
        return teacher;
    }

    /**
     * Sets test teacher
     * @param teacher
     */
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    /**
     * Returns test group
     * @return group
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Sets test group
     * @param group
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * Returns test subject
     * @return subject
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Sets test subject
     * @param subject
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /**
     * Returns test scores
     * @return scores
     */
    public Set<Score> getScores() {
        return scores;
    }

    /**
     * Sets test scores
     * @param scores
     */
    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    /**
     * Returns test average
     * @return average
     */
    public Float getAverage() {
        return average;
    }

    /**
     * Sets test average
     * @param average
     */
    public void setAverage(Float average) {
        this.average = average;
    }

    /**
     * Returns test coefficient
     * @return coefficient
     */
    public Float getCoefficient() {
        return coefficient;
    }

    /**
     * Sets test coefficient
     * @param coefficient
     */
    public void setCoefficient(Float coefficient) {
        this.coefficient = coefficient;
    }

    /**
     * Compares two test id
     * @param test2
     */
    public int compareTo(Test test2) {
        int compId = test2.getId().compareTo(this.getId());

        return ((compId != 0) ? compId : 0);
    }

}
