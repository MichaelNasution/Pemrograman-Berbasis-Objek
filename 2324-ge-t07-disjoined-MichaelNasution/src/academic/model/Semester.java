package academic.model;

public enum Semester {
    ODD("odd", 0),
    EVEN("even", 1),
    SHORT("short", 2);

    private final String value;
    private final int sortOrder;

    Semester(String value, int sortOrder) {
        this.value = value;
        this.sortOrder = sortOrder;
    }

    public static Semester fromValue(String value) {
        for (Semester semester : values()) {
            if (semester.value.equalsIgnoreCase(value)) {
                return semester;
            }
        }

        throw new IllegalArgumentException("Unknown semester: " + value);
    }

    public int getSortOrder() {
        return sortOrder;
    }

    @Override
    public String toString() {
        return value;
    }
}
