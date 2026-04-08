package model;

import java.util.*;

public class Solution {

    private static final int RASIO_UCOK = 2;

    private Map<String, Menu> menuMap = new HashMap<>();
    private List<OrderItem> orders = new ArrayList<>();
    private int totalKotor = 0;

    public Solution() {
        initMenu();
    }

    private void initMenu() {
        menuMap.put("NGS", new Menu("NGS", "Nasi Goreng Spesial", 15000));
        menuMap.put("AP", new Menu("AP", "Ayam Penyet", 20000));
        menuMap.put("SA", new Menu("SA", "Sate Ayam", 25000));
        menuMap.put("BU", new Menu("BU", "Bakso Urat", 18000));
        menuMap.put("MAP", new Menu("MAP", "Mie Ayam Pangsit", 15000));
        menuMap.put("GG", new Menu("GG", "Gado-Gado", 15000));
        menuMap.put("SAM", new Menu("SAM", "Soto Ayam", 17000));
        menuMap.put("RD", new Menu("RD", "Rendang Daging", 25000));
        menuMap.put("IB", new Menu("IB", "Ikan Bakar", 35000));
        menuMap.put("NUK", new Menu("NUK", "Nasi Uduk Komplit", 20000));
    }

    public void addOrder(String kode, int porsiButet) {
        Menu menu = menuMap.get(kode);
        if (menu == null) return;

        int porsiUcok = porsiButet * RASIO_UCOK;
        int totalPorsi = porsiButet + porsiUcok;
        int subtotal = totalPorsi * menu.getHarga();

        totalKotor += subtotal;
        orders.add(new OrderItem(menu, totalPorsi, subtotal));
    }

    public void printStruk() {
        System.out.println("==============================================================");
        System.out.printf("%-25s %-10s %-10s %-10s\n",
                "Menu", "Porsi", "Harga", "Total");
        System.out.println("==============================================================");

        for (OrderItem item : orders) {
            System.out.printf("%-25s %-10d %-10d %-10d\n",
                    item.getMenu().getNama(),
                    item.getTotalPorsi(),
                    item.getMenu().getHarga(),
                    item.getSubtotal());
        }

        System.out.println("==============================================================");
        System.out.printf("%-47s %-10d\n", "Total Pembayaran", totalKotor);

        printKupon();
    }

    private void printKupon() {
        String kupon = getKupon();
        if (!kupon.equals("Tidak Ada")) {
            System.out.println("Selamat! Anda mendapatkan " + kupon);
        }
    }

    private String getKupon() {
        if (totalKotor >= 500000) return "Kupon 25%";
        else if (totalKotor >= 400000) return "Kupon 20%";
        else if (totalKotor >= 300000) return "Kupon 15%";
        else if (totalKotor >= 200000) return "Kupon 10%";
        else if (totalKotor >= 100000) return "Kupon 5%";
        else return "Tidak Ada";
    }

    // ======================= SOAL 2 =======================

    public int sumKelompok(int[] data, int k) {

        int startIndex = (k - 1) * k / 2;
        int endIndex = startIndex + k - 1;

        if (startIndex >= data.length) {
            return 0;
        }

        int sum = 0;

        for (int i = startIndex; i <= endIndex && i < data.length; i++) {
            sum += data[i];
        }

        return sum;
    }

    // ======================= SOAL 3 =======================

    interface LaundryService {
        void calculateCost();
    }

    class Laundry implements LaundryService {
        private String nama;
        private double berat;
        private double biaya;

        private static final double HARGA_PER_KG = 7000;
        private static final double MAX_BERAT = 7.0;

        public Laundry(String nama, double berat) {
            this.nama = nama;
            this.berat = berat;
        }

        public boolean isValid() {
            return berat <= MAX_BERAT;
        }

        @Override
        public void calculateCost() {
            biaya = berat * HARGA_PER_KG;
        }

        public String getNama() { return nama; }
        public double getBerat() { return berat; }
        public double getBiaya() { return biaya; }
    }

    public void processLaundry() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Masukkan jumlah mahasiswa: ");
        int n = sc.nextInt();
        sc.nextLine();

        Laundry[] daftar = new Laundry[n];
        double totalPendapatan = 0;

        for (int i = 0; i < n; i++) {
            System.out.println("\nMahasiswa ke-" + (i + 1));

            System.out.print("Nama: ");
            String nama = sc.nextLine();

            System.out.print("Berat laundry (kg): ");
            double berat = sc.nextDouble();
            sc.nextLine();

            Laundry lb = new Laundry(nama, berat);

            if (!lb.isValid()) {
                System.out.println("Berat melebihi 7kg! Laundry ditolak.");
            } else {
                lb.calculateCost();
                daftar[i] = lb;
                totalPendapatan += lb.getBiaya();
                System.out.println("Biaya: Rp " + lb.getBiaya());
            }
        }

        System.out.println("\n===== RINGKASAN LAUNDRY =====");
        for (Laundry lb : daftar) {
            if (lb != null) {
                System.out.println("Nama: " + lb.getNama() +
                        " | Berat: " + lb.getBerat() +
                        " kg | Biaya: Rp " + lb.getBiaya());
            }
        }

        System.out.println("\nTotal Pendapatan Laundry: Rp " + totalPendapatan);
    }
}

/* ======================= SUPPORT CLASS ======================= */

class Menu {
    private String kode;
    private String nama;
    private int harga;

    public Menu(String kode, String nama, int harga) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
    }

    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public int getHarga() { return harga; }
}

class OrderItem {
    private Menu menu;
    private int totalPorsi;
    private int subtotal;

    public OrderItem(Menu menu, int totalPorsi, int subtotal) {
        this.menu = menu;
        this.totalPorsi = totalPorsi;
        this.subtotal = subtotal;
    }

    public Menu getMenu() { return menu; }
    public int getTotalPorsi() { return totalPorsi; }
    public int getSubtotal() { return subtotal; }
}