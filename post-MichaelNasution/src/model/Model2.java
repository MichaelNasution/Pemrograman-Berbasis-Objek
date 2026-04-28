package model;

import java.util.ArrayList;

public class Model2 {

    private ArrayList<String> kategoriList = new ArrayList<>();
    private ArrayList<Integer> stokList = new ArrayList<>();

    public void tambahData(int stok, String kategori) {
        stokList.add(stok);
        kategoriList.add(kategori);
    }

    public int hitungTotalKategori(String target) {
        int total = 0;

        for (int i = 0; i < kategoriList.size(); i++) {
            if (kategoriList.get(i).equalsIgnoreCase(target)) {
                total += stokList.get(i);
            }
        }

        return total;
    }
}