/**
 * @author 12S24003 Michael Nasution
 
 */

package academic.model;

public enum Grade {
    A("A", 4.0),
    AB("AB", 3.5),
    B("B", 3.0),
    BC("BC", 2.5),
    C("C", 2.0),
    D("D", 1.0),
    E("E", 0.0),
    NONE("None", 0.0), // Tambahkan ini untuk handle grade "None"
    T("T", 0.0); // Tambahkan ini untuk handle grade "T" (belum ada nilai)

    private final String gradeString;
    private final double point;

    Grade(String gradeString, double point) {
        this.gradeString = gradeString;
        this.point = point;
    }

    public String getGradeString() {
        return gradeString;
    }

    public double getPoint() {
        return point;
    }

    public static Grade fromString(String gradeString) {
        for (Grade g : Grade.values()) {
            if (g.gradeString.equalsIgnoreCase(gradeString)) {
                return g;
            }
        }
        // Fallback for unknown grades, or throw an exception
        return NONE; // Default to NONE for unrecognized grades
    }

    // Method to compare grades (higher point is better)
    public boolean isBetterThan(Grade other) {
        return this.point > other.point;
    }

    // Method to compare grades (same point is equal, higher point is better)
    public static int compare(Grade g1, Grade g2) {
        return Double.compare(g1.getPoint(), g2.getPoint());
    }
}
