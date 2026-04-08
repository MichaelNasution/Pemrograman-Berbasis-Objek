package academic.model;

/**
 * Representasi pendaftaran mata kuliah (enrollment) oleh seorang mahasiswa.
 */
public class Enrollment {
    private String courseId;
    private String studentId;
    private String academicYear;
    private String semester;
    private String grade; // Atribut untuk menyimpan grade, default "None"

    // Konstruktor utama untuk inisialisasi enrollment
    // Otomatis menetapkan grade "None" jika tidak diberikan di input awal
    public Enrollment(String courseId, String studentId, String academicYear, String semester) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.academicYear = academicYear;
        this.semester = semester;
        this.grade = "None"; // Grade default saat pendaftaran
    }

    // Konstruktor tambahan jika grade sudah langsung ada saat pembuatan objek
    public Enrollment(String courseId, String studentId, String academicYear, String semester, String grade) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.academicYear = academicYear;
        this.semester = semester;
        this.grade = grade;
    }

    // --- Getters ---
    public String getCourseId() {
        return courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public String getSemester() {
        return semester;
    }

    public String getGrade() {
        return grade;
    }

    // --- Setters (Jika diperlukan, untuk mengubah grade di kemudian hari) ---
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * Mengembalikan representasi Enrollment dalam format yang diminta:
     * CourseID|StudentID|AcademicYear|Semester|Grade
     * @return String representasi Enrollment
     */
    public String getFormattedOutput() {
        return String.format("%s|%s|%s|%s|%s", courseId, studentId, academicYear, semester, grade);
    }
}