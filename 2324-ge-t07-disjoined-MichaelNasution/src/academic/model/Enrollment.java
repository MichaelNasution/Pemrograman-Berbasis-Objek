package academic.model;

/**
 * @MichaelNasution 12S24003 - Michael Pratama Nasution
 */
// src/model/Enrollment.java


// src/academic/model/Enrollment.java


public class Enrollment {
    private Student student;
    private CourseOpening courseOpening;
    private String grade;
    private String previousGrade;
    private boolean remedialApplied;

    public Enrollment(Student student, CourseOpening courseOpening) {
        this.student = student;
        this.courseOpening = courseOpening;
        this.grade = null;
        this.previousGrade = null;
        this.remedialApplied = false;
    }

    public Student getStudent() {
        return student;
    }

    public CourseOpening getCourseOpening() {
        return courseOpening;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
        this.previousGrade = null;
        this.remedialApplied = false;
    }

    public void applyRemedial(String finalGrade, String previousGrade) {
        this.grade = finalGrade;
        this.previousGrade = previousGrade;
        this.remedialApplied = true;
    }

    public boolean isRemedialApplied() {
        return remedialApplied;
    }

    public String getPreviousGrade() {
        return previousGrade;
    }
}
