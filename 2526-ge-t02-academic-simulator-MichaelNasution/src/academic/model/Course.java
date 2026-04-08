package academic.model;

/**
 * Representasi mata kuliah dalam sistem akademik.
 */
public class Course {
    private String code;
    private String name;
    private int credits;
    private String passingGrade; // Contoh: A, B, C, D, E

    public Course(String code, String name, int credits, String passingGrade) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.passingGrade = passingGrade;
    }

    // --- Getters ---
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public String getPassingGrade() {
        return passingGrade;
    }

    /**
     * Mengembalikan representasi Course dalam format yang diminta:
     * Code|Name|Credits|PassingGrade
     * @return String representasi Course
     */
    public String getFormattedOutput() {
        return String.format("%s|%s|%d|%s", code, name, credits, passingGrade);
    }
}