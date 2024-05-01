package assignments.assignment3.core;

import java.util.ArrayList;

import assignments.assignment3.payment.DepeFoodPaymentSystem;

public class User {
    private String nama;
    private String nomorTelepon;
    private String email;
    private ArrayList<Order> orderHistory;
    public String role;
    private String lokasi;
    private DepeFoodPaymentSystem payment;
    private long saldo;

    public User(String nama, String nomorTelepon, String email, String lokasi, String role,
            DepeFoodPaymentSystem payment, long saldo) {
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
        this.payment = payment;
        this.saldo = saldo;
        orderHistory = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public String getNama() {
        return nama;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void addOrderHistory(Order order) {
        orderHistory.add(order);
    }

    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

    public DepeFoodPaymentSystem getPayment() {
        return payment;
    }

    public void setPayment(DepeFoodPaymentSystem payment) {
        this.payment = payment;
    }

    public long getSaldo() {
        return saldo;
    }

    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }

    public boolean isOrderBelongsToUser(String orderId) {
        // Memeriksa apakah order ID terdapat dalam history pesanan user
        for (Order order : orderHistory) {
            if (order.getOrderId().equals(orderId)) {
                return true; // Mengembalikan true jika order ID ditemukan
            }
        }
        return false; // Mengembalikan false jika order ID tidak ditemukan
    }

    @Override
    public String toString() {
        return String.format("User dengan nama %s dan nomor telepon %s", nama, nomorTelepon);
    }
}
