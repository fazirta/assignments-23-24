package assignments.assignment3.systemCLI;

import java.util.ArrayList;
import java.util.Arrays;

import assignments.assignment3.core.Menu;
import assignments.assignment3.core.Restaurant;

public class AdminSystemCLI extends UserSystemCLI {
    @Override
    protected boolean handleMenu(int command, ArrayList<Restaurant> restoList) {
        switch (command) {
            case 1 -> handleTambahRestoran(restoList); // Menambahkan restoran baru
            case 2 -> handleHapusRestoran(restoList); // Menghapus restoran
            case 3 -> {
                return false; // Mengembalikan false untuk keluar dari loop
            }
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true; // Mengembalikan true untuk melanjutkan loop
    }

    @Override
    protected void displayMenu() {
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    private void handleTambahRestoran(ArrayList<Restaurant> restoList) {
        System.out.println("--------------Tambah Restoran---------------");
        Restaurant restaurant = null;
        // Melakukan loop untuk memvalidasi nama restoran dan menambahkan menu
        while (restaurant == null) {
            String namaRestaurant = getValidRestaurantName(restoList);
            restaurant = new Restaurant(namaRestaurant);
            restaurant = handleTambahMenuRestaurant(restaurant);
        }
        restoList.add(restaurant); // Menambahkan restoran ke daftar restoran
        // Menampilkan pesan bahwa restoran berhasil terdaftar
        System.out.println("Restaurant " + restaurant.getNama() + " Berhasil terdaftar.");
    }

    private String getValidRestaurantName(ArrayList<Restaurant> restoList) {
        String name = "";
        boolean isRestaurantNameValid = false;

        while (!isRestaurantNameValid) {
            System.out.print("Nama: ");
            String inputName = input.nextLine().trim();
            // Memeriksa apakah restoran dengan nama yang sama sudah ada
            boolean isRestaurantExist = restoList.stream()
                    .anyMatch(restoran -> restoran.getNama().toLowerCase().equals(inputName.toLowerCase()));
            boolean isRestaurantNameLengthValid = inputName.length() >= 4; // Memeriksa panjang nama restoran

            if (isRestaurantExist) {
                System.out.printf("Restoran dengan nama %s sudah pernah terdaftar. Mohon masukkan nama yang berbeda!%n",
                        inputName); // Menampilkan pesan jika nama restoran sudah ada
                System.out.println();
            } else if (!isRestaurantNameLengthValid) {
                // Menampilkan pesan jika nama restoran tidak valid
                System.out.println("Nama Restoran tidak valid! Minimal 4 karakter diperlukan.");
                System.out.println();
            } else {
                // Mengatur nama restoran dan menandai bahwa nama restoran valid
                name = inputName;
                isRestaurantNameValid = true;
            }
        }
        return name; // Mengembalikan nama restoran yang valid
    }

    private Restaurant handleTambahMenuRestaurant(Restaurant restoran) {
        System.out.print("Jumlah Makanan: ");
        int jumlahMenu = Integer.parseInt(input.nextLine().trim()); // Meminta jumlah makanan dari user
        boolean isMenuValid = true;
        for (int i = 0; i < jumlahMenu; i++) { // Memproses setiap input makanan
            String inputValue = input.nextLine().trim();
            String[] splitter = inputValue.split(" ");
            String hargaStr = splitter[splitter.length - 1];
            boolean isDigit = hargaStr.chars().allMatch(Character::isDigit); // Memeriksa apakah harga merupakan angka
            String namaMenu = String.join(" ", Arrays.copyOfRange(splitter, 0, splitter.length - 1));
            if (isDigit) {
                // Jika harga valid, menambahkan menu ke restoran
                int hargaMenu = Integer.parseInt(hargaStr);
                restoran.addMenu(new Menu(namaMenu, hargaMenu));
            } else {
                // Jika harga tidak valid, tandai bahwa menu tidak valid
                isMenuValid = false;
            }
        }
        if (!isMenuValid) {
            System.out.println("Harga menu harus bilangan bulat!"); // Menampilkan pesan jika harga menu tidak valid
            System.out.println();
        }

        return isMenuValid ? restoran : null; // Mengembalikan restoran jika semua menu valid
    }

    private void handleHapusRestoran(ArrayList<Restaurant> restoList) {
        System.out.println("--------------Hapus Restoran----------------");
        boolean removed = false;
        while (!removed) {
            System.out.print("Nama Restoran: ");
            String nama = input.nextLine(); // Meminta nama restoran dari user
            // Menghapus restoran jika ditemukan
            removed = restoList.removeIf(restoran -> restoran.getNama().equalsIgnoreCase(nama));
            // Menampilkan pesan sesuai dengan keberhasilan penghapusan
            if (removed)
                System.out.println("Restoran berhasil dihapus.");
            else
                System.out.println("Restoran tidak terdaftar pada sistem.");
            System.out.println();
        }
    }
}
