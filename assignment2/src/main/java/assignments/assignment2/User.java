package main.java.assignments.assignment2;

import java.util.ArrayList; // Mengimpor class ArrayList dari package java.util

public class User {
    private String nama; // Nama user
    private String nomorTelepon; // Nomor telepon user
    private String email; // Email user
    private String lokasi; // Lokasi user
    private String role; // Role User (Customer atau Admin)
    private ArrayList<Order> orderHistory; // Riwayat pesanan user

    public User(String nama, String nomorTelepon, String email, String lokasi, String role) {
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
        orderHistory = new ArrayList<Order>(); // Inisialisasi ArrayList untuk history pesanan
    }

    // Method untuk mendapatkan nama user
    public String getNama() {
        return nama;
    }

    // Method untuk mengatur nama user
    public void setNama(String nama) {
        this.nama = nama;
    }

    // Method untuk mendapatkan nomor telepon user
    public String getNomorTelepon() {
        return nomorTelepon;
    }

    // Method untuk mengatur nomor telepon user
    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    // Method untuk mendapatkan email user
    public String getEmail() {
        return email;
    }

    // Method untuk mengatur email user
    public void setEmail(String email) {
        this.email = email;
    }

    // Method untuk mendapatkan lokasi user
    public String getLokasi() {
        return lokasi;
    }

    // Method untuk mengatur lokasi user
    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    // Method untuk mendapatkan peran user
    public String getRole() {
        return role;
    }

    // Method untuk mengatur peran user
    public void setRole(String role) {
        this.role = role;
    }

    // Method untuk mendapatkan riwayat pesanan user
    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

    // Method untuk menambahkan pesanan ke dalam riwayat pesanan user
    public void addOrderHistory(Order order) {
        orderHistory.add(order);
    }
}