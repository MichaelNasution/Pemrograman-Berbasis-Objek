package academic.model;

/**
 * @author 12S24003 Michael Nasution
 
 */
 



public class Course {
    private String id;
    private String name;
    private int sks; // Satuan Kredit Semester
    private String grade; // Default grade, if any

    public Course(String id, String name, int sks, String grade) {
        this.id = id;
        this.name = name;
        this.sks = sks;
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSks() {
        return sks;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return String.join("|", id, name, String.valueOf(sks), grade);
    }
}