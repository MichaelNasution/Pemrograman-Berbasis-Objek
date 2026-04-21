package academic.model;

/**
 * @author 12S24003 Michael Nasution
 
 */




public class Enrollment {
    private String courseId;
    private String studentId;
    private String academicYear;
    private String semester;
    private Grade grade; // Current final grade
    private Grade originalGrade; // Grade before remedial, if any

    public Enrollment(String courseId, String studentId, String academicYear, String semester) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.academicYear = academicYear;
        this.semester = semester;
        this.grade = Grade.NONE; // Default grade is NONE
        this.originalGrade = Grade.NONE; // Default original grade
    }

    public String getCourseId() {
        return courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public String getSemester() {
        return semester;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Grade getOriginalGrade() {
        return originalGrade;
    }

    public void setOriginalGrade(Grade originalGrade) {
        this.originalGrade = originalGrade;
    }

    public String getDisplayGrade() {
        if (originalGrade != Grade.NONE && grade != Grade.NONE) {
            return grade.getGradeString() + "(" + originalGrade.getGradeString() + ")";
        }
        return grade.getGradeString();
    }

    @Override
    public String toString() {
        return String.join("|", courseId, studentId, academicYear, semester, getDisplayGrade());
    }

    // Helper to compare enrollments for sorting (latest enrollment)
    // Later academic year is greater. Same year, 'even' semester is greater than 'odd'.
    public int compareTo(Enrollment other) {
        // Compare academic year
        int thisStartYear = Integer.parseInt(this.getAcademicYear().split("/")[0]);
        int otherStartYear = Integer.parseInt(other.getAcademicYear().split("/")[0]);

        if (thisStartYear != otherStartYear) {
            return Integer.compare(thisStartYear, otherStartYear);
        }

        // If academic year is the same, compare semester (odd < even)
        int thisSemesterOrder = "odd".equalsIgnoreCase(this.getSemester()) ? 0 : ("even".equalsIgnoreCase(this.getSemester()) ? 1 : 2);
        int otherSemesterOrder = "odd".equalsIgnoreCase(other.getSemester()) ? 0 : ("even".equalsIgnoreCase(other.getSemester()) ? 1 : 2);
        return Integer.compare(thisSemesterOrder, otherSemesterOrder);
    }
}

