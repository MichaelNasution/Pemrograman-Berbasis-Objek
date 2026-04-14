package academic.model;

/**
 * @MichaelNasution 12S24003 - Michael Pratama Nasution
 */
// src/model/CourseOpening.java


// src/academic/model/CourseOpening.java


import java.util.List;

public class CourseOpening {
    private String courseCode;
    private String academicYear;
    private Semester semester;
    private List<Lecturer> lecturers;

    public CourseOpening(String courseCode, String academicYear, Semester semester, List<Lecturer> lecturers) {
        this.courseCode = courseCode;
        this.academicYear = academicYear;
        this.semester = semester;
        this.lecturers = lecturers;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public Semester getSemester() {
        return semester;
    }

    public List<Lecturer> getLecturers() {
        return lecturers;
    }
}
