package main.java.assignments.assignment2;

import java.util.ArrayList; // Mengimpor class ArrayList dari package java.util

public class Restaurant {
    private String nama; // Nama restoran
    private ArrayList<Menu> menu; // Daftar menu yang ditawarkan oleh restoran

    public Restaurant(String nama) {
        this.nama = nama;
        menu = new ArrayList<Menu>(); // Inisialisasi ArrayList untuk menu
    }

    // Method untuk mendapatkan nama restoran
    public String getNama() {
        return nama;
    }

    // Method untuk mengatur nama restoran
    public void setNama(String nama) {
        this.nama = nama;
    }

    // Method untuk mendapatkan daftar menu yang ditawarkan oleh restoran
    public ArrayList<Menu> getMenu() {
        return menu;
    }

    // Method untuk menambahkan menu ke dalam daftar menu restoran
    public void addMenu(Menu menu) {
        this.menu.add(menu);
    }
}