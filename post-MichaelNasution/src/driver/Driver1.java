package driver;

import java.util.Scanner;
import model.Model1;

public class Driver1 {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Model1 model = new Model1();

        while (true) {
            String kode = input.nextLine();

            if (kode.equals("END")) {
                break;
            }

            double beratInput = input.nextDouble();
            input.nextLine();

            String kota = model.getKota(kode);
            int harga = model.getHarga(kode);
            String kategori = model.getKategori(kode);

            double beratButet = model.hitungBeratButet(beratInput);
            double beratUcok = model.hitungBeratUcok(beratInput);
            double totalBerat = model.hitungTotalBerat(beratButet, beratUcok);

            double ongkir = model.hitungOngkir(totalBerat, harga);
            double diskon = model.hitungDiskon(totalBerat, ongkir);
            double totalBayar = ongkir - diskon;

            System.out.println("\n===== STRUK DEL-EXPRESS =====");
            System.out.println("Kota Tujuan      : " + kota);
            System.out.println("Berat Butet      : " + beratButet + " kg");
            System.out.println("Berat Ucok       : " + beratUcok + " kg");
            System.out.println("Total Berat      : " + totalBerat + " kg");
            System.out.println("Total Ongkir     : Rp " + ongkir);

            if (diskon > 0) {
                System.out.println("Diskon           : Rp " + diskon);
            }

            System.out.println("Total Bayar      : Rp " + totalBayar);

            if (model.isAsuransiGratis(kategori)) {
                System.out.println("Promo            : Asuransi Gratis");
            }

            if (diskon > 0) {
                System.out.println("Promo            : Diskon 10%");
            }

            System.out.println("=============================\n");
        }

        input.close();
    }
}