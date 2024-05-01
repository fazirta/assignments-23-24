package assignments.assignment3;

import java.util.ArrayList;
import java.util.Scanner;

import assignments.assignment3.core.Restaurant;
import assignments.assignment3.core.User;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment3.systemCLI.AdminSystemCLI;
import assignments.assignment3.systemCLI.CustomerSystemCLI;

public class MainMenu {
    private final Scanner input;
    private final LoginManager loginManager;
    private static ArrayList<Restaurant> restoList;
    private static ArrayList<User> userList;

    public MainMenu(Scanner in, LoginManager loginManager) {
        this.input = in;
        this.loginManager = loginManager;
        restoList = new ArrayList<Restaurant>(); // Menginisialisasi restoList sebagai ArrayList kosong
        initUser(); // Memanggil method initUser() untuk menginisialisasi daftar pengguna
    }

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu(new Scanner(System.in),
                new LoginManager(new AdminSystemCLI(), new CustomerSystemCLI()));
        mainMenu.run();
    }

    public void run() {
        printHeader(); // Mencetak header
        boolean exit = false;
        while (!exit) {
            startMenu();
            int choice = input.nextInt(); // Mendapatkan choice dari User
            input.nextLine(); // Membersihkan newline dari input
            switch (choice) {
                case 1 -> login(); // Jika pilihan adalah 1, panggil method login()
                case 2 -> exit = true; // Jika pilihan adalah 2, set exit menjadi true untuk keluar dari loop
                default -> System.out.println("Pilihan tidak valid, silakan coba lagi."); // Cetak pesan error
            }
        }
        System.out.println("Terima kasih telah menggunakan DepeFood!"); // Cetak pesan di akhir program
        input.close();
    }

    private void login() {
        System.out.println("\nSilakan Login:"); // Cetak pesan untuk meminta login
        System.out.print("Nama: "); // Meminta input nama
        String nama = input.nextLine(); // Membaca input nama dari user
        System.out.print("Nomor Telepon: "); // Meminta input nomor telepon
        String noTelp = input.nextLine(); // Membaca input nomor telepon dari user

        User userLoggedIn = getUser(nama, noTelp); // Mendapatkan pengguna yang sedang login

        if (userLoggedIn == null) {
            System.out.println("Pengguna dengan data tersebut tidak ditemukan!"); // Jika pengguna tidak ditemukan
        } else {
            System.out.printf("Selamat datang %s!\n", userLoggedIn.getNama()); // Cetak pesan selamat datang
            loginManager.getSystem(userLoggedIn.role).run(restoList, userLoggedIn);// Jalankan sistem
        }
    }

    private User getUser(String nama, String nomorTelepon) {
        for (User user : userList) { // Melakukan iterasi pada userList
            // Memeriksa apakah nama dan nomor telepon pengguna cocok dengan parameter
            if (user.getNama().equals(nama.trim()) && user.getNomorTelepon().equals(nomorTelepon.trim())) {
                return user; // Mengembalikan pengguna jika ditemukan
            }
        }
        return null; // Mengembalikan null jika pengguna tidak ditemukan
    }

    private static void printHeader() {
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    private static void startMenu() {
        System.out.println("\nSelamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void initUser() {
        userList = new ArrayList<User>();

        userList.add(
                new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer", new DebitPayment(), 500000));
        userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer",
                new CreditCardPayment(), 2000000));
        userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer",
                new DebitPayment(), 750000));
        userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer",
                new CreditCardPayment(), 1800000));
        userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer", new DebitPayment(),
                650000));

        userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin", new CreditCardPayment(), 0));
        userList.add(
                new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin", new CreditCardPayment(), 0));
    }
}
