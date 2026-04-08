package academic.model;

/**
 * Representasi mahasiswa dalam sistem akademik.
 */
public class Student {
    private String id;
    private String name;
    private String entryYear; // Tahun masuk
    private String major;     // Jurusan

    public Student(String id, String name, String entryYear, String major) {
        this.id = id;
        this.name = name;
        this.entryYear = entryYear;
        this.major = major;
    }

    // --- Getters ---
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEntryYear() {
        return entryYear;
    }

    public String getMajor() {
        return major;
    }

    /**
     * Mengembalikan representasi Student dalam format yang diminta:
     * ID|Name|EntryYear|Major
     * @return String representasi Student
     */
    public String getFormattedOutput() {
        return String.format("%s|%s|%s|%s", id, name, entryYear, major);
    }
}