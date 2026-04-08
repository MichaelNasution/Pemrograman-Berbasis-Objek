package academic.driver;

import academic.model.Student; // Sekarang menggunakan model Student yang sebenarnya
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver2 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<Student> students = new ArrayList<>(); // List sekarang menyimpan objek Student

        while (true) {
            String line = input.nextLine();

            if (line.trim().equals("---")) {
                break; // Berhenti jika input adalah "---"
            }

            // Asumsi format input: id#name#entryYear#major
            String[] segments = line.split("#");
            if (segments.length == 4) {
                String id = segments[0];
                String name = segments[1];
                String entryYear = segments[2];
                String major = segments[3];
                Student student = new Student(id, name, entryYear, major); // Membuat objek Student
                students.add(student);
            } else {
                System.err.println("Warning: Invalid input format for Student. Skipping: " + line);
            }
        }

        input.close();

        for (Student student : students) {
            System.out.println(student.getFormattedOutput());
        }
    }
}