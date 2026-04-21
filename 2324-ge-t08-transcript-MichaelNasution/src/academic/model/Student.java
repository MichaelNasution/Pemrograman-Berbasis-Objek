package academic.model;

/**
 * @author 12S24003 Michael Nasution
 
 */



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {
    private String id;
    private String name;
    private String entryYear;
    private String studyProgram;
    private List<Enrollment> enrollments;

    public Student(String id, String name, String entryYear, String studyProgram) {
        this.id = id;
        this.name = name;
        this.entryYear = entryYear;
        this.studyProgram = studyProgram;
        this.enrollments = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEntryYear() {
        return entryYear;
    }

    public String getStudyProgram() {
        return studyProgram;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
    }

    /**
     * Calculates the cumulative GPA for the student.
     * Only considers the latest valid enrollment for each course.
     *
     * @param courses Map of all courses to get SKS.
     * @return Formatted GPA string (e.g., "3.50") and total credits.
     */
    public String[] getCumulativePerformance(Map<String, Course> courses) {
        Map<String, Enrollment> latestEnrollments = getLatestEnrollmentsByCourse();

        double totalGradePoints = 0;
        int totalCredits = 0;

        for (Enrollment enrollment : latestEnrollments.values()) {
            // Only count courses with a valid grade (not E, T, or None for GPA calculation)
            if (enrollment.getGrade() != Grade.E && enrollment.getGrade() != Grade.T && enrollment.getGrade() != Grade.NONE) { 
                Course course = courses.get(enrollment.getCourseId());
                if (course != null) {
                    totalGradePoints += enrollment.getGrade().getPoint() * course.getSks();
                    totalCredits += course.getSks();
                }
            }
        }

        if (totalCredits == 0) {
            return new String[]{"0.00", "0"}; // No valid enrollments or no SKS
        }

        double gpa = totalGradePoints / totalCredits;
        return new String[]{String.format("%.2f", gpa), String.valueOf(totalCredits)};
    }

    /**
     * Retrieves the latest enrollment for each course, considering remedial.
     *
     * @return Map where key is courseId and value is the latest effective enrollment.
     */
    private Map<String, Enrollment> getLatestEnrollmentsByCourse() {
        Map<String, Enrollment> effectiveEnrollments = new HashMap<>();

        // Sort all enrollments by academic year and semester to easily find the latest
        List<Enrollment> sortedEnrollments = new ArrayList<>(this.enrollments);
        // Sort in ascending order (earliest first), so when iterating, the last one for a course is the latest
        Collections.sort(sortedEnrollments, new Comparator<Enrollment>() {
            @Override
            public int compare(Enrollment e1, Enrollment e2) {
                return e1.compareTo(e2);
            }
        });

        for (Enrollment enrollment : sortedEnrollments) {
            // "Bila dilakukan pengambilan suatu mata kuliah lebih dari satu kali, maka hanya pencapaian pada pengambilan terakhir akan ditampilkan."
            // This means if a student enrolls in the same course multiple times, only the latest one counts.
            // Since sortedEnrollments is already sorted historically, putting into map will naturally keep the latest.
            effectiveEnrollments.put(enrollment.getCourseId(), enrollment);
        }
        return effectiveEnrollments;
    }

    /**
     * Generates a sorted list of transcript entries for the student.
     * This method uses a static nested class as required for "Nested Constructs".
     *
     * @return List of TranscriptCourseEntry objects, sorted historically.
     */
    public List<TranscriptCourseEntry> getTranscriptEntries() {
        Map<String, Enrollment> latestEnrollments = getLatestEnrollmentsByCourse();
        List<TranscriptCourseEntry> transcriptEntries = new ArrayList<>();

        for (Enrollment enrollment : latestEnrollments.values()) {
            // Only include enrollments with a grade other than 'T' or 'None' in transcript output
            if (enrollment.getGrade() != Grade.T && enrollment.getGrade() != Grade.NONE) {
                transcriptEntries.add(new TranscriptCourseEntry(
                        enrollment.getCourseId(),
                        enrollment.getStudentId(),
                        enrollment.getAcademicYear(),
                        enrollment.getSemester(),
                        enrollment.getDisplayGrade()
                ));
            }
        }

        // Sort transcript entries historically
        Collections.sort(transcriptEntries, new Comparator<TranscriptCourseEntry>() {
            @Override
            public int compare(TranscriptCourseEntry t1, TranscriptCourseEntry t2) {
                // Compare academic year
                int thisStartYear = Integer.parseInt(t1.academicYear.split("/")[0]);
                int otherStartYear = Integer.parseInt(t2.academicYear.split("/")[0]);

                if (thisStartYear != otherStartYear) {
                    return Integer.compare(thisStartYear, otherStartYear);
                }
                // If academic year is the same, compare semester (odd < even)
                int thisSemesterOrder = "odd".equalsIgnoreCase(t1.semester) ? 0 : ("even".equalsIgnoreCase(t1.semester) ? 1 : 2);
                int otherSemesterOrder = "odd".equalsIgnoreCase(t2.semester) ? 0 : ("even".equalsIgnoreCase(t2.semester) ? 1 : 2);
                return Integer.compare(thisSemesterOrder, otherSemesterOrder);
            }
        });

        return transcriptEntries;
    }

    // Nested Constructs: Static Nested Class to represent a single course entry in the transcript
    public static class TranscriptCourseEntry {
        private String courseId;
        private String studentId;
        private String academicYear;
        private String semester;
        private String displayGrade;

        public TranscriptCourseEntry(String courseId, String studentId, String academicYear, String semester, String displayGrade) {
            this.courseId = courseId;
            this.studentId = studentId;
            this.academicYear = academicYear;
            this.semester = semester;
            this.displayGrade = displayGrade;
        }

        @Override
        public String toString() {
            return String.join("|", courseId, studentId, academicYear, semester, displayGrade);
        }
    }

    @Override
    public String toString() {
        return String.join("|", id, name, entryYear, studyProgram);
    }
}

