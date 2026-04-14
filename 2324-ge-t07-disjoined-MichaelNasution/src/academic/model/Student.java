package academic.model;

/**
 * @MichaelNasution 12S24003 - Michael Pratama Nasution
 */
// src/model/Student.java


// src/academic/model/Student.java


public class Student {
    private String id;
    private String name;
    private String year;
    private String studyProgram;

    public Student(String id, String name, String year, String studyProgram) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.studyProgram = studyProgram;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public String getStudyProgram() {
        return studyProgram;
    }
}
