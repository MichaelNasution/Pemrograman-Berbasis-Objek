package academic.driver;

import academic.model.Course;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver1 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<Course> courses = new ArrayList<>();

        while (input.hasNextLine()) {
            String line = input.nextLine();

            // Gunakan .trim() untuk menangani spasi di awal/akhir baris "---"
            if (line.trim().equals("---")) {
                break; // Berhenti jika input adalah "---"
            }

            String[] segments = line.split("#");
            // Asumsi format input: code#name#credits#passingGrade
            if (segments.length == 4) {
                String code = segments[0];
                String name = segments[1];
                int credits = Integer.parseInt(segments[2]);
                String passingGrade = segments[3];
                Course course = new Course(code, name, credits, passingGrade);
                courses.add(course);
            } else {
                System.err.println("Warning: Invalid input format for Course. Skipping: " + line);
            }
        }

        input.close();

        for (Course course : courses) {
            System.out.println(course.getFormattedOutput());
        }
    }
}