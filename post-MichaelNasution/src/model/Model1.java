package model;
import java.util.HashMap;

public class Model1 {

    private HashMap<String, String> kotaMap = new HashMap<>();
    private HashMap<String, Integer> hargaMap = new HashMap<>();
    private HashMap<String, String> kategoriMap = new HashMap<>();

    public Model1() {
        kotaMap.put("MDN", "Medan");
        kotaMap.put("BLG", "Balige");
        kotaMap.put("JKT", "Jakarta");
        kotaMap.put("SBY", "Surabaya");

        hargaMap.put("MDN", 8000);
        hargaMap.put("BLG", 5000);
        hargaMap.put("JKT", 12000);
        hargaMap.put("SBY", 13000);

        kategoriMap.put("MDN", "Dalam Pulau");
        kategoriMap.put("BLG", "Dalam Pulau");
        kategoriMap.put("JKT", "Luar Pulau");
        kategoriMap.put("SBY", "Luar Pulau");
    }

    public String getKota(String kode) {
        return kotaMap.get(kode);
    }

    public int getHarga(String kode) {
        return hargaMap.get(kode);
    }

    public String getKategori(String kode) {
        return kategoriMap.get(kode);
    }

    public double hitungBeratButet(double input) {
        return input + 1; // tambahan 1kg
    }

    public double hitungBeratUcok(double input) {
        return 1.5 * input;
    }

    public double hitungTotalBerat(double butet, double ucok) {
        return butet + ucok;
    }

    public double hitungOngkir(double totalBerat, int hargaPerKg) {
        return totalBerat * hargaPerKg;
    }

    public double hitungDiskon(double totalBerat, double ongkir) {
        if (totalBerat > 10) {
            return ongkir * 0.1;
        }
        return 0;
    }

    public boolean isAsuransiGratis(String kategori) {
        return kategori.equals("Luar Pulau");
    }
}