package academic.driver;

import academic.model.Course;
import academic.model.Student;
import academic.model.Enrollment;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Driver utama untuk Task 4, mengelola penyimpanan dan tampilan
 * dari Course, Student, dan Enrollment secara bersamaan.
 */
public class Driver4 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Tiga list terpisah untuk menyimpan masing-masing entitas
        List<Course> courses = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        List<Enrollment> enrollments = new ArrayList<>();

        while (true) {
            String line = input.nextLine();

            if (line.trim().equals("---")) {
                break; // Berhenti jika input adalah "---"
            }

            // Pecah baris input berdasarkan "#"
            String[] segments = line.split("#");

            // Pastikan ada segmen untuk command
            if (segments.length == 0) {
                System.err.println("Warning: Empty input line. Skipping.");
                continue;
            }

            // PERBAIKAN: Tambahkan .trim() di sini untuk membersihkan spasi dari command
            String command = segments[0].trim(); // Command ada di segmen pertama, bersihkan spasi

            switch (command) {
                case "course-add":
                    // Format: course-add#12S2203#Object-oriented Programming#3#C
                    if (segments.length == 5) { // Command + 4 data
                        String code = segments[1];
                        String name = segments[2];
                        int credits = Integer.parseInt(segments[3]); // Credits sebagai integer
                        String passingGrade = segments[4];
                        Course course = new Course(code, name, credits, passingGrade);
                        courses.add(course);
                    } else {
                        System.err.println("Warning: Invalid 'course-add' format. Expected 4 data segments. Skipping: " + line);
                    }
                    break;

                case "student-add":
                    // Format: student-add#12S20999#Wiro Sableng#2020#Information Systems
                    if (segments.length == 5) { // Command + 4 data
                        String id = segments[1];
                        String name = segments[2];
                        String entryYear = segments[3];
                        String major = segments[4];
                        Student student = new Student(id, name, entryYear, major);
                        students.add(student);
                    } else {
                        System.err.println("Warning: Invalid 'student-add' format. Expected 4 data segments. Skipping: " + line);
                    }
                    break;

                case "enrollment-add":
                    // Format: enrollment-add#12S2203#12S20999#2021/2022#even
                    if (segments.length == 5) { // Command + 4 data
                        String courseId = segments[1];
                        String studentId = segments[2];
                        String academicYear = segments[3];
                        String semester = segments[4];
                        // Grade default adalah "None" yang diatur di konstruktor Enrollment
                        Enrollment enrollment = new Enrollment(courseId, studentId, academicYear, semester);
                        enrollments.add(enrollment);
                    } else {
                        System.err.println("Warning: Invalid 'enrollment-add' format. Expected 4 data segments. Skipping: " + line);
                    }
                    break;

                default:
                    System.err.println("Warning: Unknown command encountered: " + command + ". Skipping line: " + line);
                    break;
            }
        }

        input.close(); // Tutup scanner setelah selesai membaca

        // Tampilkan semua data dengan urutan yang diminta: Course, Student, Enrollment
        for (Course course : courses) {
            System.out.println(course.getFormattedOutput());
        }

        for (Student student : students) {
            System.out.println(student.getFormattedOutput());
        }

        for (Enrollment enrollment : enrollments) {
            System.out.println(enrollment.getFormattedOutput());
        }
    }
}