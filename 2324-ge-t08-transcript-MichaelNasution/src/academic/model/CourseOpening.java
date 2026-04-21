package academic.model;

/**
 * @author 12S24003 Michael Nasution
 
 */
 

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class CourseOpening {
    private String courseId;
    private String academicYear;
    private String semester;
    private Set<String> lecturerInitials; // Using initials to reference lecturers

    public CourseOpening(String courseId, String academicYear, String semester, Set<String> lecturerInitials) {
        this.courseId = courseId;
        this.academicYear = academicYear;
        this.semester = semester;
        this.lecturerInitials = lecturerInitials;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public String getSemester() {
        return semester;
    }

    public Set<String> getLecturerInitials() {
        return lecturerInitials;
    }

    @Override
    public String toString() {
        String lecturers = String.join(",", lecturerInitials);
        return String.join("|", courseId, academicYear, semester, lecturers);
    }

    public String getDetails(Course course, Map<String, Lecturer> lecturersMap) {
        List<String> lecturerInfos = new ArrayList<>();
        for (String initial : lecturerInitials) {
            Lecturer l = lecturersMap.values().stream()
                    .filter(lec -> lec.getInitial().equals(initial))
                    .findFirst().orElse(null);
            if (l != null) {
                lecturerInfos.add(l.getInitial() + " (" + l.getEmail() + ")");
            }
        }
        String lecturerString = String.join(";", lecturerInfos);
        return String.join("|", course.getId(), course.getName(), String.valueOf(course.getSks()), course.getGrade(), academicYear, semester, lecturerString);
    }

    // Helper to compare academic years and semesters for sorting
    public int compareTo(CourseOpening other) {
        // Semester first: odd < even
        int thisSem = "odd".equalsIgnoreCase(this.semester) ? 0 : 1;
        int otherSem = "odd".equalsIgnoreCase(other.semester) ? 0 : 1;
        
        if (thisSem != otherSem) {
            return Integer.compare(thisSem, otherSem);
        }

        // Then year
        int thisStartYear = Integer.parseInt(this.getAcademicYear().split("/")[0]);
        int otherStartYear = Integer.parseInt(other.getAcademicYear().split("/")[0]);
        return Integer.compare(thisStartYear, otherStartYear);
    }
}
