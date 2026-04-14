package academic.driver;

import academic.model.Course;
import academic.model.CourseOpening;
import academic.model.Enrollment;
import academic.model.Lecturer;
import academic.model.Semester;
import academic.model.Student;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Driver1 {
    private static final Map<String, Lecturer> lecturers = new LinkedHashMap<>();
    private static final Map<String, Lecturer> lecturersByInitial = new LinkedHashMap<>();
    private static final Map<String, Student> students = new LinkedHashMap<>();
    private static final Map<String, Course> courses = new LinkedHashMap<>();
    private static final Map<String, CourseOpening> courseOpenings = new LinkedHashMap<>();
    private static final Map<String, List<Enrollment>> enrollmentsByCourseOpening = new LinkedHashMap<>();
    private static final Map<String, List<Enrollment>> enrollmentsByStudent = new LinkedHashMap<>();
    private static final List<Enrollment> allEnrollments = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }

            if (line.equals("---") || line.equalsIgnoreCase("---END---")) {
                break;
            }

            String[] parts = line.split("#");
            String command = parts[0];

            switch (command) {
                case "lecturer-add":
                    addLecturer(parts);
                    break;
                case "student-add":
                    addStudent(parts);
                    break;
                case "student-detail":
                case "student-details":
                    showStudentDetail(parts);
                    break;
                case "course-add":
                    addCourse(parts);
                    break;
                case "course-open":
                    addCourseOpening(parts);
                    break;
                case "enrollment-add":
                    addEnrollment(parts);
                    break;
                case "enrollment-grade":
                case "grading":
                    gradeEnrollment(parts);
                    break;
                case "remedial":
                case "enrollment-remedial":
                    remedialEnrollment(parts);
                    break;
                case "course-history":
                    showCourseHistory(parts);
                    break;
                default:
                    break;
            }
        }

        printSummary();
        scanner.close();
    }

    private static void addLecturer(String[] parts) {
        String id = parts[1];
        String name = parts[2];
        String initial = parts[3];
        String email = parts[4];
        String studyProgram = parts[5];

        Lecturer lecturer = new Lecturer(id, name, initial, email, studyProgram);
        lecturers.put(id, lecturer);
        lecturersByInitial.put(initial, lecturer);
    }

    private static void addStudent(String[] parts) {
        String id = parts[1];
        String name = parts[2];
        String year = parts[3];
        String studyProgram = parts[4];

        Student student = new Student(id, name, year, studyProgram);
        students.put(id, student);
    }

    private static void showStudentDetail(String[] parts) {
        Student student = students.get(parts[1]);
        if (student == null) {
            return;
        }

        List<Enrollment> studentEnrollments = new ArrayList<>(
            enrollmentsByStudent.getOrDefault(student.getId(), new ArrayList<>())
        );
        studentEnrollments.sort(Comparator
            .comparing((Enrollment enrollment) -> enrollment.getCourseOpening().getAcademicYear())
            .thenComparingInt(enrollment -> enrollment.getCourseOpening().getSemester().getSortOrder())
            .thenComparing(enrollment -> enrollment.getCourseOpening().getCourseCode()));
        System.out.println(formatStudentDetailSummary(student, studentEnrollments));
    }

    private static void addCourse(String[] parts) {
        String code = parts[1];
        String name = parts[2];
        int credit = Integer.parseInt(parts[3]);
        String passingGrade = parts[4];

        courses.put(code, new Course(code, name, credit, passingGrade));
    }

    private static void addCourseOpening(String[] parts) {
        String courseCode = parts[1];
        String academicYear = parts[2];
        Semester semester = Semester.fromValue(parts[3]);
        String[] lecturerInitials = parts[4].split(",");

        if (!courses.containsKey(courseCode)) {
            return;
        }

        List<Lecturer> assignedLecturers = new ArrayList<>();
        for (String initial : lecturerInitials) {
            Lecturer lecturer = lecturersByInitial.get(initial);
            if (lecturer == null) {
                return;
            }
            assignedLecturers.add(lecturer);
        }

        String courseOpeningId = getCourseOpeningId(courseCode, academicYear, semester);
        courseOpenings.put(courseOpeningId, new CourseOpening(courseCode, academicYear, semester, assignedLecturers));
        enrollmentsByCourseOpening.putIfAbsent(courseOpeningId, new ArrayList<>());
    }

    private static void addEnrollment(String[] parts) {
        String courseCode = parts[1];
        String studentId = parts[2];
        String academicYear = parts[3];
        Semester semester = Semester.fromValue(parts[4]);

        Student student = students.get(studentId);
        if (student == null) {
            return;
        }

        String courseOpeningId = getCourseOpeningId(courseCode, academicYear, semester);
        CourseOpening courseOpening = courseOpenings.get(courseOpeningId);
        if (courseOpening == null) {
            return;
        }

        List<Enrollment> enrollments = enrollmentsByCourseOpening.get(courseOpeningId);
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().getId().equals(studentId)) {
                return;
            }
        }

        Enrollment enrollment = new Enrollment(student, courseOpening);
        enrollments.add(enrollment);
        enrollmentsByStudent.computeIfAbsent(studentId, key -> new ArrayList<>()).add(enrollment);
        allEnrollments.add(enrollment);
    }

    private static void gradeEnrollment(String[] parts) {
        String courseCode = parts[1];
        String studentId = parts[2];
        String academicYear = parts[3];
        Semester semester = Semester.fromValue(parts[4]);
        String grade = parts[5];

        Enrollment enrollment = findEnrollment(courseCode, studentId, academicYear, semester);
        if (enrollment != null) {
            enrollment.setGrade(grade);
        }
    }

    private static void remedialEnrollment(String[] parts) {
        String courseCode = parts[1];
        String studentId = parts[2];
        String academicYear = parts[3];
        Semester semester = Semester.fromValue(parts[4]);
        String grade = parts[5];

        Enrollment enrollment = findEnrollment(courseCode, studentId, academicYear, semester);
        if (enrollment != null && enrollment.getGrade() != null) {
            String currentGrade = enrollment.getGrade();
            enrollment.applyRemedial(grade, currentGrade);
        }
    }

    private static void showCourseHistory(String[] parts) {
        String courseCode = parts[1];
        Course course = courses.get(courseCode);
        if (course == null) {
            return;
        }

        List<CourseOpening> relevantCourseOpenings = new ArrayList<>();
        for (CourseOpening courseOpening : courseOpenings.values()) {
            if (courseOpening.getCourseCode().equals(courseCode)) {
                relevantCourseOpenings.add(courseOpening);
            }
        }

        relevantCourseOpenings.sort(Comparator
            .comparingInt((CourseOpening courseOpening) -> courseOpening.getSemester().getSortOrder())
            .thenComparing(CourseOpening::getAcademicYear));

        for (CourseOpening courseOpening : relevantCourseOpenings) {
            System.out.println(formatCourseHistoryHeader(course, courseOpening));

            String courseOpeningId = getCourseOpeningId(
                courseOpening.getCourseCode(),
                courseOpening.getAcademicYear(),
                courseOpening.getSemester()
            );

            for (Enrollment enrollment : enrollmentsByCourseOpening.getOrDefault(courseOpeningId, new ArrayList<>())) {
                System.out.println(formatEnrollment(enrollment));
            }
        }
    }

    private static Enrollment findEnrollment(String courseCode, String studentId, String academicYear, Semester semester) {
        String courseOpeningId = getCourseOpeningId(courseCode, academicYear, semester);
        List<Enrollment> enrollments = enrollmentsByCourseOpening.getOrDefault(courseOpeningId, new ArrayList<>());

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().getId().equals(studentId)) {
                return enrollment;
            }
        }

        return null;
    }

    private static void printSummary() {
        for (Lecturer lecturer : lecturers.values()) {
            System.out.println(formatLecturer(lecturer));
        }

        for (Course course : courses.values()) {
            System.out.println(formatCourse(course));
        }

        for (Student student : students.values()) {
            System.out.println(formatStudent(student));
        }

        for (Enrollment enrollment : allEnrollments) {
            System.out.println(formatEnrollment(enrollment));
        }
    }

    private static String formatLecturer(Lecturer lecturer) {
        return lecturer.getId()
            + "|" + lecturer.getName()
            + "|" + lecturer.getInitial()
            + "|" + lecturer.getEmail()
            + "|" + lecturer.getStudyProgram();
    }

    private static String formatCourse(Course course) {
        return course.getCode()
            + "|" + course.getName()
            + "|" + course.getCredit()
            + "|" + course.getPassingGrade();
    }

    private static String formatStudent(Student student) {
        return student.getId()
            + "|" + student.getName()
            + "|" + student.getYear()
            + "|" + student.getStudyProgram();
    }

    private static String formatEnrollment(Enrollment enrollment) {
        CourseOpening courseOpening = enrollment.getCourseOpening();
        return courseOpening.getCourseCode()
            + "|" + enrollment.getStudent().getId()
            + "|" + courseOpening.getAcademicYear()
            + "|" + courseOpening.getSemester()
            + "|" + formatGradeForDisplay(enrollment);
    }

    private static String formatCourseHistoryHeader(Course course, CourseOpening courseOpening) {
        return course.getCode()
            + "|" + course.getName()
            + "|" + course.getCredit()
            + "|" + course.getPassingGrade()
            + "|" + courseOpening.getAcademicYear()
            + "|" + courseOpening.getSemester()
            + "|" + formatLecturerList(courseOpening.getLecturers());
    }

    private static String formatLecturerList(List<Lecturer> lecturers) {
        List<String> values = new ArrayList<>();
        for (Lecturer lecturer : lecturers) {
            values.add(lecturer.getInitial() + " (" + lecturer.getEmail() + ")");
        }
        return String.join(",", values);
    }

    private static String getCourseOpeningId(String courseCode, String academicYear, Semester semester) {
        return courseCode + "|" + academicYear + "|" + semester;
    }

    private static String formatGradeForDisplay(Enrollment enrollment) {
        String grade = enrollment.getGrade();
        if (grade == null) {
            return "None";
        }

        if (enrollment.isRemedialApplied()) {
            return grade + "(" + enrollment.getPreviousGrade() + ")";
        }

        return grade;
    }

    private static int getGradeRank(String grade) {
        if (grade == null) {
            return Integer.MAX_VALUE;
        }

        switch (grade.toUpperCase()) {
            case "A":
                return 0;
            case "AB":
                return 1;
            case "B":
                return 2;
            case "BC":
                return 3;
            case "C":
                return 4;
            case "D":
                return 5;
            case "E":
                return 6;
            default:
                return Integer.MAX_VALUE - 1;
        }
    }

    private static String formatStudentDetailSummary(Student student, List<Enrollment> studentEnrollments) {
        Map<String, Enrollment> latestEnrollmentByCourse = new LinkedHashMap<>();
        for (Enrollment enrollment : studentEnrollments) {
            String courseCode = enrollment.getCourseOpening().getCourseCode();
            Enrollment current = latestEnrollmentByCourse.get(courseCode);
            if (current == null || compareEnrollmentPeriod(enrollment, current) > 0) {
                latestEnrollmentByCourse.put(courseCode, enrollment);
            }
        }

        double totalPoints = 0.0;
        int totalPassedCredits = 0;
        for (Enrollment enrollment : latestEnrollmentByCourse.values()) {
            String grade = enrollment.getGrade();
            if (grade == null) {
                continue;
            }

            Course course = courses.get(enrollment.getCourseOpening().getCourseCode());
            if (course == null) {
                continue;
            }

            if (isPassed(grade, course.getPassingGrade())) {
                int credit = course.getCredit();
                totalPassedCredits += credit;
                totalPoints += getGradePoint(grade) * credit;
            }
        }

        double gpa = totalPassedCredits == 0 ? 0.0 : totalPoints / totalPassedCredits;
        return student.getId()
            + "|" + student.getName()
            + "|" + student.getYear()
            + "|" + student.getStudyProgram()
            + "|" + String.format("%.2f", gpa)
            + "|" + totalPassedCredits;
    }

    private static int compareEnrollmentPeriod(Enrollment first, Enrollment second) {
        int yearCompare = first.getCourseOpening().getAcademicYear()
            .compareTo(second.getCourseOpening().getAcademicYear());
        if (yearCompare != 0) {
            return yearCompare;
        }

        return Integer.compare(
            first.getCourseOpening().getSemester().getSortOrder(),
            second.getCourseOpening().getSemester().getSortOrder()
        );
    }

    private static boolean isPassed(String grade, String passingGrade) {
        return getGradeRank(grade) <= getGradeRank(passingGrade);
    }

    private static double getGradePoint(String grade) {
        switch (grade.toUpperCase()) {
            case "A":
                return 4.0;
            case "AB":
                return 3.5;
            case "B":
                return 3.0;
            case "BC":
                return 2.5;
            case "C":
                return 2.0;
            case "D":
                return 1.0;
            case "E":
                return 0.0;
            default:
                return 0.0;
        }
    }
}
