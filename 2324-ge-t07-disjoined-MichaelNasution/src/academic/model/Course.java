package academic.model;

/**
 * @MichaelNasution 12S24003 - Michael Pratama Nasution
 */
// src/model/Course.java


// src/academic/model/Course.java


public class Course {
    private String code;
    private String name;
    private int credit;
    private String passingGrade;

    public Course(String code, String name, int credit, String passingGrade) {
        this.code = code;
        this.name = name;
        this.credit = credit;
        this.passingGrade = passingGrade;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getCredit() {
        return credit;
    }

    public String getPassingGrade() {
        return passingGrade;
    }
}
