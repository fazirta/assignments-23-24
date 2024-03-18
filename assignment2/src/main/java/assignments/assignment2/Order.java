package assignments.assignment2;

import java.util.ArrayList; // Mengimpor class ArrayList dari package java.util

public class Order {
    private String orderID; // ID pesanan
    private String tanggalPemesanan; // Tanggal pemesanan
    private int biayaOngkosKirim; // Biaya pengiriman
    private Restaurant restaurant; // Restoran yang menerima pesanan
    private ArrayList<Menu> items; // Daftar menu yang dipesan
    private boolean orderFinished; // Status pesanan (selesai atau belum)

    public Order(String orderId, String tanggal, int ongkir, Restaurant resto, Menu[] items) {
        this.orderID = orderId;
        this.tanggalPemesanan = tanggal;
        this.biayaOngkosKirim = ongkir;
        this.restaurant = resto;

        // Inisialisasi ArrayList untuk items
        this.items = new ArrayList<Menu>();

        // Menambahkan menu-menu ke ArrayList items
        for (Menu item : items) {
            this.items.add(item);
        }
        this.orderFinished = false; // Set status pesanan menjadi belum selesai
    }

    // Method untuk mendapatkan ID pesanan
    public String getOrderID() {
        return orderID;
    }

    // Method untuk mengatur ID pesanan
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    // Method untuk mendapatkan tanggal pemesanan
    public String getTanggalPemesanan() {
        return tanggalPemesanan;
    }

    // Method untuk mengatur tanggal pemesanan
    public void setTanggalPemesanan(String tanggalPemesanan) {
        this.tanggalPemesanan = tanggalPemesanan;
    }

    // Method untuk mendapatkan biaya ongkos kirim
    public int getBiayaOngkosKirim() {
        return biayaOngkosKirim;
    }

    // Method untuk mengatur biaya ongkos kirim
    public void setBiayaOngkosKirim(int biayaOngkosKirim) {
        this.biayaOngkosKirim = biayaOngkosKirim;
    }

    // Method untuk mendapatkan restoran penerima pesanan
    public Restaurant getRestaurant() {
        return restaurant;
    }

    // Method untuk mengatur restoran penerima pesanan
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    // Method untuk mendapatkan daftar menu yang dipesan
    public ArrayList<Menu> getItems() {
        return items;
    }

    // Method untuk menambahkan menu ke dalam daftar pesanan
    public void addItems(Menu item) {
        items.add(item);
    }

    // Method untuk mendapatkan status pesanan (selesai atau belum)
    public boolean isOrderFinished() {
        return orderFinished;
    }

    // Method untuk mengatur status pesanan (selesai atau belum)
    public void setOrderFinished(boolean orderFinished) {
        this.orderFinished = orderFinished;
    }
}