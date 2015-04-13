package beans;

public class Score implements Comparable<Score> {
    private Long    id;
    private Float   score;
    private Student student;

    /**
     * Constructor
     */
    public Score() {
        this.id = null;
        this.score = null;
        this.student = null;
    }

    /**
     * Constructor
     * @param score
     */
    public Score(Score score) {
        this.id = score.getId();
        this.score = score.getScore();
        this.student = score.getStudent();
    }

    /**
     * Constructor
     * @param student
     */
    public Score(Student student) {
        this.id = null;
        this.score = null;
        this.student = student;
    }

    /**
     * Returns score id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets score id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns score score
     * @return score
     */
    public Float getScore() {
        return score;
    }

    /**
     * Sets score score
     * @param score
     */
    public void setScore(Float score) {
        this.score = score;
    }

    /**
     * Returns score student
     * @return student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Sets score student
     * @param student
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Compares two scores students names
     * @param score
     */
    public int compareTo(Score score) {
        Student student1 = new Student(this.getStudent());
        Student student2 = new Student(score.getStudent());
        int     compId   = student1.getLastName().compareTo(student2.getLastName());

        return ((compId != 0) ? compId : 0);
    }
}
