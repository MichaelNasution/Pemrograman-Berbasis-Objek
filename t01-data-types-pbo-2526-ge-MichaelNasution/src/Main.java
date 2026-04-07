import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Baca token pertama (Label Soal) sebagai penentu arah
        if (scanner.hasNext()) {
            String menu = scanner.next();

            // 2. Dispatcher Logic (Switch-Case)
            switch (menu) {
                case "Soal1":
                    solveSoal1(scanner);
                    break;
                case "Soal2":
                    solveSoal2(scanner);
                    break;
                case "Soal3":
                    solveSoal3(scanner);
                    break;
                case "Soal4":
                    solveSoal4(scanner);
                    break;
                case "Soal5":
                    solveSoal5(scanner);
                    break;
                default:
                    System.out.println("Soal tidak ditemukan!");
            }
        }
        scanner.close();
    }

   // ==========================================
    // LOGIKA SOAL 1: Integer Overflow
    // ==========================================
    private static void solveSoal1(Scanner scanner) {
        // Pastikan ada input angka pertama
        if (scanner.hasNextInt()) {
            int a = scanner.nextInt();
            
            // Pastikan ada input angka kedua
            if (scanner.hasNextInt()) {
                int b = scanner.nextInt();
                
                // Hapus variabel 'resultCheck' yang menyebabkan warning.
                // Kita langsung gunakan int sum.
                int sum = a + b;
                boolean isOverflow = false;

                // Logika Deteksi Overflow (Positif + Positif = Negatif)
                if (a > 0 && b > 0 && sum < 0) {
                    isOverflow = true;
                }
                // Logika Deteksi Underflow (Negatif + Negatif = Positif/Nol)
                else if (a < 0 && b < 0 && sum >= 0) {
                    isOverflow = true;
                }

                if (isOverflow) {
                    System.out.println("OVERFLOW");
                } else {
                    System.out.println(sum);
                }
            }
        }
    }

    // ==========================================
    // LOGIKA SOAL 2: Float vs Double
    // ==========================================
    private static void solveSoal2(Scanner scanner) {
        if (scanner.hasNextDouble()) {
            double x = scanner.nextDouble();
            if (scanner.hasNextDouble()) {
                double y = scanner.nextDouble();
                PrecisionComparator comparator = new PrecisionComparator(x, y);
                System.out.printf("%.6f\n", comparator.calculateDifference());
            }
        }
    }

    // ==========================================
    // LOGIKA SOAL 3: Primitive vs Wrapper
    // ==========================================
    private static void solveSoal3(Scanner scanner) {
        if (scanner.hasNextInt()) {
            int n = scanner.nextInt();
            IntegerManager manager = new IntegerManager(n);
            manager.incrementAndCompare();
        }
    }

    // ==========================================
    // LOGIKA SOAL 4: String Immutability
    // ==========================================
    private static void solveSoal4(Scanner scanner) {
        if (scanner.hasNext()) {
            String s = scanner.next();
            StringManager manager = new StringManager(s);
            manager.modifyAndCompare();
        }
    }

    // ==========================================
    // LOGIKA SOAL 5: Parsing & Type Safety
    // ==========================================
    private static void solveSoal5(Scanner scanner) {
        if (scanner.hasNextInt()) {
            int num = scanner.nextInt();
            if (scanner.hasNextDouble()) {
                double mul = scanner.nextDouble();
                if (scanner.hasNextBoolean()) {
                    boolean sign = scanner.nextBoolean();
                    TypeProcessor processor = new TypeProcessor(num, mul, sign);
                    System.out.printf("%.2f\n", processor.calculate());
                }
            }
        }
    }
}

// ==========================================
// HELPER CLASSES (Non-Public / Package Private)
// ==========================================

// Helper untuk Soal 2
class PrecisionComparator {
    private double num1;
    private double num2;

    public PrecisionComparator(double num1, double num2) {
        this.num1 = num1;
        this.num2 = num2;
    }

    public double calculateDifference() {
        float sumFloat = (float) num1 + (float) num2;
        double sumDouble = num1 + num2;
        return Math.abs(sumFloat - sumDouble);
    }
}

// Helper untuk Soal 3
class IntegerManager {
    private Integer a;
    private Integer b;

    public IntegerManager(int n) {
        this.a = n;
        this.b = a;
    }

    public void incrementAndCompare() {
        this.a = this.a + 1;
        boolean isSameObject = (this.a == this.b);
        boolean isSameValue = this.a.equals(this.b);
        System.out.println("==: " + isSameObject);
        System.out.println("equals: " + isSameValue);
    }
}

// Helper untuk Soal 4
class StringManager {
    private String a;
    private String b;

    public StringManager(String s) {
        this.a = s;
        this.b = new String(s);
    }

    public void modifyAndCompare() {
        this.a = this.a + "X";
        boolean isSameReference = (this.a == this.b);
        boolean isSameContent = this.a.equals(this.b);
        System.out.println("==: " + isSameReference);
        System.out.println("equals: " + isSameContent);
    }
}

// Helper untuk Soal 5
class TypeProcessor {
    private int number;
    private double multiplier;
    private boolean keepSign;

    public TypeProcessor(int number, double multiplier, boolean keepSign) {
        this.number = number;
        this.multiplier = multiplier;
        this.keepSign = keepSign;
    }

    public double calculate() {
        double result = number * multiplier;
        if (!keepSign) {
            result = result * -1;
        }
        return result;
    }
}