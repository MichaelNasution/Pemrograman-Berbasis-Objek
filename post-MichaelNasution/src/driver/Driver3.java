package driver;

import model.Model3.*;
import java.util.*;

public class Driver3 {

    static Scanner input = new Scanner(System.in);
    static ArrayList<Menu> menuList = new ArrayList<>();

    public static void main(String[] args) {

        initMenu();

        System.out.print("Nama: ");
        String name = input.nextLine();

        System.out.print("Role (1=Mahasiswa, 2=Staff): ");
        int role = Integer.parseInt(input.nextLine());

        User user = (role == 1) ? new Mahasiswa(name) : new Staff(name);

        Order order = new Order(user);

        // ===== INPUT PESANAN =====
        while (true) {
            showMenu();

            System.out.print("Pilih menu (ketik --- untuk PESAN): ");
            String choice = input.nextLine();

            if (choice.equals("---")) break;

            try {
                int index = Integer.parseInt(choice);

                if (index < 0 || index >= menuList.size()) {
                    System.out.println("Menu tidak valid!");
                    continue;
                }

                System.out.print("Jumlah: ");
                int qty = Integer.parseInt(input.nextLine());

                order.addItem(new OrderItem(menuList.get(index), qty));

            } catch (Exception e) {
                System.out.println("Input harus angka atau ---");
            }
        }

        // ===== DELIVERY =====
        if (user instanceof Staff) {
            System.out.print("Delivery? (y/n): ");
            String d = input.nextLine();

            if (d.equalsIgnoreCase("y")) {
                System.out.print("Lokasi: ");
                String loc = input.nextLine();
                order.setDelivery(true, loc);
            }
        }

        // ===== PAYMENT (FIXED OPTION) =====
        while (true) {
            System.out.println("\nMetode Pembayaran:");
            System.out.println("1. Cash");
            System.out.println("2. QRIS");
            System.out.print("Pilih: ");

            String payChoice = input.nextLine();

            if (payChoice.equals("1")) {
                order.setPaymentMethod("Cash");
                break;
            } else if (payChoice.equals("2")) {
                order.setPaymentMethod("QRIS");
                break;
            } else {
                System.out.println("Pilihan tidak valid!");
            }
        }

        order.printReceipt();
    }

    static void initMenu() {
        menuList.add(new Menu("Kopi Hitam", 5000));
        menuList.add(new Menu("Teh Manis", 5000));
        menuList.add(new Menu("Cappucino", 7000));
        menuList.add(new Menu("Tempe Goreng", 10000));
        menuList.add(new Menu("Nugget", 15000));
        menuList.add(new Menu("Indomie", 12000));
    }

    static void showMenu() {
        System.out.println("\n=== MENU ===");
        for (int i = 0; i < menuList.size(); i++) {
            System.out.println(i + ". " + menuList.get(i).name + " - " + menuList.get(i).price);
        }
    }
}