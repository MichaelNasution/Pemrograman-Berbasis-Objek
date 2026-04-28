package driver;

import java.util.Scanner;
import model.Model2;

public class Driver2 {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Model2 model = new Model2();

        int N = input.nextInt();
        input.nextLine(); // clear buffer

        for (int i = 0; i < N; i++) {
            String line = input.nextLine();
            String[] parts = line.split(" ");

            int stok = Integer.parseInt(parts[0]);
            String kategori = parts[1];

            model.tambahData(stok, kategori);
        }

        String target = input.nextLine();

        int total = model.hitungTotalKategori(target);

        System.out.println("Total stok kategori " + target + " = " + total + " pcs");

        input.close();
    }
}