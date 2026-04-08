package academic.driver;

import academic.model.Enrollment; // Sekarang menggunakan model Enrollment yang sebenarnya
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver3 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<Enrollment> enrollments = new ArrayList<>(); // List sekarang menyimpan objek Enrollment

        while (true) {
            String line = input.nextLine();

            if (line.trim().equals("---")) {
                break; // Berhenti jika input adalah "---"
            }

            // Asumsi format input: courseId#studentId#academicYear#semester
            String[] segments = line.split("#");

            if (segments.length == 4) {
                String courseId = segments[0];
                String studentId = segments[1];
                String academicYear = segments[2];
                String semester = segments[3];

                // Buat objek Enrollment baru dengan grade default "None"
                Enrollment enrollmentEntry = new Enrollment(courseId, studentId, academicYear, semester);
                enrollments.add(enrollmentEntry);
            } else {
                System.err.println("Warning: Invalid input format for Enrollment. Skipping: " + line);
            }
        }

        input.close();

        for (Enrollment enrollmentEntry : enrollments) {
            System.out.println(enrollmentEntry.getFormattedOutput());
        }
    }
}