package academic.driver;

/**
 * @author 12S24003 Michael Nasution
 
 */



import academic.model.*;
import academic.model.InputParser;
import academic.model.Grade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
// import java.util.Comparator; // Not directly used with Collections.sort in main

public class Driver1 {

    private static final Map<String, Lecturer> lecturers = new LinkedHashMap<>();
    private static final Map<String, Course> courses = new LinkedHashMap<>();
    private static final Map<String, Student> students = new LinkedHashMap<>();
    // Key: courseId + academicYear + semester
    private static final Map<String, CourseOpening> courseOpenings = new LinkedHashMap<>(); 
    // Key: courseId + studentId + academicYear + semester
    private static final Map<String, Enrollment> globalEnrollments = new LinkedHashMap<>(); 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line;

        while (scanner.hasNextLine() && !(line = scanner.nextLine()).equals("---")) {
            List<String> command = InputParser.parseCommand(line);
            executeCommand(command);
        }

        // Final dump
        lecturers.values().forEach(System.out::println);
        courses.values().forEach(System.out::println);
        students.values().forEach(System.out::println);
        globalEnrollments.values().forEach(System.out::println);

        scanner.close();
    }

    private static void executeCommand(List<String> command) {
        String commandType = command.get(0);

        switch (commandType) {
            case "lecturer-add":
                addLecturer(command);
                break;
            case "course-add":
                addCourse(command);
                break;
            case "student-add":
                addStudent(command);
                break;
            case "course-open":
                openCourse(command);
                break;
            case "enrollment-add":
                addEnrollment(command);
                break;
            case "enrollment-grade":
                gradeEnrollment(command);
                break;
            case "enrollment-remedial":
                remedialEnrollment(command);
                break;
            case "student-details":
                printStudentDetails(command);
                break;
            case "student-transcript":
                printStudentTranscript(command);
                break;
            case "course-history":
                printCourseHistory(command);
                break;
            default:
                // Ignore unknown commands as per problem context
                break;
        }
    }

    private static void addLecturer(List<String> command) {
        String id = command.get(1);
        String name = command.get(2);
        String initial = command.get(3);
        String email = command.get(4);
        String studyProgram = command.get(5);
        if (!lecturers.containsKey(id)) {
            lecturers.put(id, new Lecturer(id, name, initial, email, studyProgram));
        }
    }

    private static void addCourse(List<String> command) {
        String id = command.get(1);
        String name = command.get(2);
        int sks = Integer.parseInt(command.get(3));
        String grade = command.get(4); // Default grade for the course, e.g., "D"
        if (!courses.containsKey(id)) {
            courses.put(id, new Course(id, name, sks, grade));
        }
    }

    private static void addStudent(List<String> command) {
        String id = command.get(1);
        String name = command.get(2);
        String entryYear = command.get(3);
        String studyProgram = command.get(4);
        if (!students.containsKey(id)) {
            students.put(id, new Student(id, name, entryYear, studyProgram));
        }
    }

    private static void openCourse(List<String> command) {
        String courseId = command.get(1);
        String academicYear = command.get(2);
        String semester = command.get(3);
        String[] lecturerInitialsArray = command.get(4).split(",");
        Set<String> lecturerInitials = new LinkedHashSet<>();
        for (String initial : lecturerInitialsArray) {
            lecturerInitials.add(initial);
        }

        String key = courseId + "#" + academicYear + "#" + semester; // Changed key format for clarity and consistency
        if (!courseOpenings.containsKey(key)) {
            courseOpenings.put(key, new CourseOpening(courseId, academicYear, semester, lecturerInitials));
        }
    }

    private static void addEnrollment(List<String> command) {
        String courseId = command.get(1);
        String studentId = command.get(2);
        String academicYear = command.get(3);
        String semester = command.get(4);

        // Check if student and course exist
        if (students.containsKey(studentId) && courses.containsKey(courseId)) {
            String enrollmentKey = courseId + "#" + studentId + "#" + academicYear + "#" + semester;
            if (!globalEnrollments.containsKey(enrollmentKey)) {
                Enrollment newEnrollment = new Enrollment(courseId, studentId, academicYear, semester);
                students.get(studentId).addEnrollment(newEnrollment);
                globalEnrollments.put(enrollmentKey, newEnrollment);
            }
        }
    }

    private static void gradeEnrollment(List<String> command) {
        String courseId = command.get(1);
        String studentId = command.get(2);
        String academicYear = command.get(3);
        String semester = command.get(4);
        String gradeString = command.get(5);

        String enrollmentKey = courseId + "#" + studentId + "#" + academicYear + "#" + semester;
        Enrollment enrollment = globalEnrollments.get(enrollmentKey);

        if (enrollment != null) {
            if (enrollment.getGrade() == Grade.NONE) {
                enrollment.setGrade(Grade.fromString(gradeString));
            }
        }
    }

    private static void remedialEnrollment(List<String> command) {
        String courseId = command.get(1);
        String studentId = command.get(2);
        String academicYear = command.get(3);
        String semester = command.get(4);
        String newGradeString = command.get(5);

        String enrollmentKey = courseId + "#" + studentId + "#" + academicYear + "#" + semester;
        Enrollment enrollment = globalEnrollments.get(enrollmentKey);

        if (enrollment != null) {
            if (enrollment.getGrade() != Grade.NONE && enrollment.getOriginalGrade() == Grade.NONE) {
                enrollment.setOriginalGrade(enrollment.getGrade());
                enrollment.setGrade(Grade.fromString(newGradeString));
            }
        }
    }

    private static void printStudentDetails(List<String> command) {
        String studentId = command.get(1);
        Student student = students.get(studentId);

        if (student != null) {
            String[] performance = student.getCumulativePerformance(courses);
            System.out.println(String.join("|", student.getId(), student.getName(), student.getEntryYear(), student.getStudyProgram(), performance[0], performance[1]));
        }
    }

    private static void printStudentTranscript(List<String> command) {
        String studentId = command.get(1);
        Student student = students.get(studentId);

        if (student != null) {
            // First, print cumulative performance
            String[] performance = student.getCumulativePerformance(courses);
            System.out.println(String.join("|", student.getId(), student.getName(), student.getEntryYear(), student.getStudyProgram(), performance[0], performance[1]));

            // Then, print sorted transcript entries
            List<Student.TranscriptCourseEntry> transcriptEntries = student.getTranscriptEntries();
            for (Student.TranscriptCourseEntry entry : transcriptEntries) {
                System.out.println(entry.toString());
            }
        }
    }

    private static void printCourseHistory(List<String> command) {
        String courseId = command.get(1);
        Course course = courses.get(courseId);
        if (course == null) return;

        // Find all openings for this course, sorted historically
        List<CourseOpening> relevantOpenings = courseOpenings.values().stream()
                .filter(co -> co.getCourseId().equals(courseId))
                .sorted((co1, co2) -> co1.compareTo(co2))
                .collect(Collectors.toList());

        for (CourseOpening co : relevantOpenings) {
            System.out.println(co.getDetails(course, lecturers));
            
            // Print all enrollments for this specific opening
            for (Enrollment e : globalEnrollments.values()) {
                if (e.getCourseId().equals(courseId) && 
                    e.getAcademicYear().equals(co.getAcademicYear()) && 
                    e.getSemester().equals(co.getSemester())) {
                    System.out.println(e.toString());
                }
            }
        }
    }
}
