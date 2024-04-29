package assignments.assignment3.core;

public class Menu {
    private String namaMakanan; // Nama makanan
    private double harga; // Harga makanan

    public Menu(String namaMakanan, double harga) {
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }

    // Method untuk mendapatkan nama makanan
    public String getNamaMakanan() {
        return namaMakanan;
    }

    // Method untuk mengatur nama makanan
    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    // Method untuk mendapatkan harga makanan
    public double getHarga() {
        return harga;
    }

    // Method untuk mengatur harga makanan
    public void setHarga(double harga) {
        this.harga = harga;
    }
}