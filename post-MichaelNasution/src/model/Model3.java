package model;

import java.util.*;

public class Model3 {

    public static class Menu {
        public String name;
        public int price;

        public Menu(String name, int price) {
            this.name = name;
            this.price = price;
        }
    }

    public static abstract class User {
        public String name;

        public User(String name) {
            this.name = name;
        }

        public abstract String getRole();
    }

    public static class Mahasiswa extends User {
        public Mahasiswa(String name) {
            super(name);
        }

        public String getRole() {
            return "Mahasiswa";
        }
    }

    public static class Staff extends User {
        public Staff(String name) {
            super(name);
        }

        public String getRole() {
            return "Staff/Dosen";
        }
    }

    public static class OrderItem {
        public Menu menu;
        public int qty;

        public OrderItem(Menu menu, int qty) {
            this.menu = menu;
            this.qty = qty;
        }

        public int getSubtotal() {
            return menu.price * qty;
        }
    }

    public static class DeliveryService {
        public static final int DELIVERY_FEE = 5000;
    }

    public static class Order {
        public User user;
        public ArrayList<OrderItem> items = new ArrayList<>();
        public String paymentMethod;
        public boolean delivery;
        public String location;

        public Order(User user) {
            this.user = user;
        }

        public void addItem(OrderItem item) {
            items.add(item);
        }

        // ✅ FIX 1
        public void setPaymentMethod(String method) {
            this.paymentMethod = method;
        }

        // ✅ FIX 2
        public void setDelivery(boolean delivery, String location) {
            this.delivery = delivery;
            this.location = location;
        }

        public int calculateTotal() {
            int total = 0;
            for (OrderItem item : items) {
                total += item.getSubtotal();
            }
            if (delivery) {
                total += DeliveryService.DELIVERY_FEE;
            }
            return total;
        }

        public void printReceipt() {
            System.out.println("\n===== STRUK =====");
            System.out.println("Nama: " + user.name);
            System.out.println("Role: " + user.getRole());

            for (OrderItem item : items) {
                System.out.println(item.menu.name + " x" + item.qty + " = " + item.getSubtotal());
            }

            if (delivery) {
                System.out.println("Delivery ke: " + location);
                System.out.println("Biaya: " + DeliveryService.DELIVERY_FEE);
            }

            System.out.println("Metode: " + paymentMethod);
            System.out.println("TOTAL: " + calculateTotal());
            System.out.println("=================\n");
        }
    }
}