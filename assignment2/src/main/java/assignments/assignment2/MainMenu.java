package assignments.assignment2;

import java.util.Arrays; // Mengimpor class Arrays dari package java.util
import java.util.ArrayList;
import java.util.Scanner;
import assignments.assignment1.*;

public class MainMenu {
  private static final Scanner input = new Scanner(System.in);
  private static ArrayList<Restaurant> restoList;
  private static ArrayList<User> userList;

  public static void main(String[] args) {
    boolean programRunning = true;

    // Inisialisasi array list dan user awal
    restoList = new ArrayList<Restaurant>();
    initUser();

    while (programRunning) {
      printHeader();
      startMenu();
      int command = input.nextInt();
      input.nextLine();

      if (command == 1) {
        User userLoggedIn;

        // Looping untuk login user
        while (true) {
          System.out.println("\nSilakan Login:");
          System.out.print("Nama: ");
          String nama = input.nextLine();
          System.out.print("Nomor Telepon: ");
          String noTelp = input.nextLine();

          // Validasi user
          if (getUser(nama, noTelp) != null) {
            userLoggedIn = getUser(nama, noTelp);
            System.out.println("Selamat Datang " + userLoggedIn.getNama() + "!");
            break;
          } else {
            System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
          }
        }

        boolean isLoggedIn = true;

        if (userLoggedIn.getRole() == "Customer") {
          while (isLoggedIn) {
            menuCustomer();
            int commandCust = input.nextInt();
            input.nextLine();

            switch (commandCust) {
              case 1 -> handleBuatPesanan(userLoggedIn);
              case 2 -> handleCetakBill(userLoggedIn);
              case 3 -> handleLihatMenu();
              case 4 -> handleUpdateStatusPesanan(userLoggedIn);
              case 5 -> isLoggedIn = false;
              default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
            }
          }
        } else {
          while (isLoggedIn) {
            menuAdmin();
            int commandAdmin = input.nextInt();
            input.nextLine();

            switch (commandAdmin) {
              case 1 -> handleTambahRestoran();
              case 2 -> handleHapusRestoran();
              case 3 -> isLoggedIn = false;
              default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
            }
          }
        }
      } else if (command == 2) {
        programRunning = false;
      } else {
        System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
      }
    }
    System.out.println("\nTerima kasih telah menggunakan DepeFood ^___^");
  }

  public static User getUser(String nama, String nomorTelepon) {
    // Looping setiap user dalam userList
    for (User user : userList) {
      // Memeriksa apakah nama pengguna dan nomor telepon pengguna cocok
      if (user.getNama().equals(nama) && user.getNomorTelepon().equals(nomorTelepon)) {
        return user; // Mengembalikan pengguna jika ditemukan yang cocok
      }
    }
    return null; // Mengembalikan null jika tidak ada pengguna yang cocok
  }

  public static void handleBuatPesanan(User userLoggedIn) {
    String tanggal;
    int ongkir;
    Restaurant currentResto;
    Menu[] currentItems;

    // Looping untuk memproses pembuatan pesanan
    while (true) {
      boolean namaRestoValidator = false;
      boolean namaMakananValidator = true;
      currentResto = null;

      System.out.print("Nama Restoran: ");
      String namaRestoran = input.nextLine();

      // Validasi restoran
      for (Restaurant resto : restoList) {
        if (resto.getNama().equalsIgnoreCase(namaRestoran)) {
          currentResto = resto;
          namaRestoValidator = true;
          break;
        }
      }

      if (!namaRestoValidator) {
        System.out.println("Restoran tidak terdaftar pada sistem.\n");
        continue;
      }

      // Memasukkan tanggal pesanan
      System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
      tanggal = input.nextLine();

      // Validasi tanggal pesanan
      if (tanggal.length() != 10) {
        System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)!\n");
        continue;
      }

      // Memasukkan jumlah pesanan dan makanan yang diinginkan
      System.out.print("Jumlah Pesanan: ");
      int jumlahPesanan = input.nextInt();

      // Membuat array baru untuk menyimpan menu yang dipesan
      currentItems = new Menu[jumlahPesanan];

      input.nextLine(); // Membersihkan buffer

      // Looping untuk memproses setiap pesanan
      for (int i = 0; i < jumlahPesanan; i++) {
        boolean found = false;
        String userInput = input.nextLine();
        // Looping untuk memeriksa setiap menu di restoran
        for (int j = 0; j < currentResto.getMenu().size(); j++) {
          // Memeriksa apakah menu ditemukan
          if (currentResto.getMenu().get(j).getNamaMakanan().equalsIgnoreCase(userInput)) {
            found = true;
            currentItems[j] = currentResto.getMenu().get(j); // Menambahkan menu yang dipesan ke dalam array
            break;
          }
        }
        if (!found) {
          namaMakananValidator = false;
        }
      }

      // Jika ada menu yang tidak valid
      if (!namaMakananValidator) {
        System.out.println("Mohon memesan menu yang tersedia di Restoran!\n");
        continue;
      }

      // Menghasilkan ID pesanan
      String orderID = OrderGenerator.generateOrderID(currentResto.getNama(), tanggal, userLoggedIn.getNomorTelepon());

      // Menentukan biaya pengiriman berdasarkan lokasi
      switch (userLoggedIn.getLokasi().toUpperCase()) {
        case "P":
          ongkir = 10000;
          break;
        case "U":
          ongkir = 20000;
          break;
        case "T":
          ongkir = 35000;
          break;
        case "S":
          ongkir = 40000;
          break;
        case "B":
          ongkir = 60000;
          break;
        default:
          ongkir = 0;
          break;
      }

      // Menambahkan pesanan ke riwayat pengguna
      userLoggedIn.addOrderHistory(new Order(orderID, tanggal, ongkir, currentResto, currentItems));

      System.out.printf("Pesanan dengan ID %s diterima!", orderID);

      break;
    }
  }

  public static void handleCetakBill(User userLoggedIn) {
    // Mencetak header tagihan
    System.out.println("-----------------Cetak Bill-----------------");

    Order currentOrder = null;
    boolean orderIDValidator = false;
    // Looping untuk memvalidasi ID pesanan
    while (!orderIDValidator) {
      // Meminta pengguna untuk memasukkan ID pesanan
      System.out.print("Masukkan Order ID: ");
      String orderID = input.nextLine();

      // Iterasi melalui riwayat pesanan pengguna yang sedang masuk
      for (Order order : userLoggedIn.getOrderHistory()) {
        // Memeriksa apakah ID pesanan dalam riwayat cocok
        if (order.getOrderID().equalsIgnoreCase(orderID)) {
          orderIDValidator = true;
          currentOrder = order; // Menyimpan pesanan yang cocok ke dalam currentOrder
          break;
        }
      }

      // Memeriksa jika ID pesanan tidak valid
      if (!orderIDValidator) {
        System.out.println("Order ID tidak dapat ditemukan.\n");
      }
    }

    // Mendapatkan status pengiriman
    String statusPengiriman = currentOrder.isOrderFinished() ? "Finished" : "Not Finished";

    // Menampilkan informasi bill
    System.out.printf("\nBill:%n" +
        "Order ID: %s%n" +
        "Tanggal Pemesanan: %s%n" +
        "Restaurant: %s%n" +
        "Lokasi Pengiriman: %s%n" +
        "Status Pengiriman: %s%n" +
        "Pesanan: %n",
        currentOrder.getOrderID(),
        currentOrder.getTanggalPemesanan(),
        currentOrder.getRestaurant().getNama(),
        userLoggedIn.getLokasi(),
        statusPengiriman);

    int totalBiaya = (int) currentOrder.getBiayaOngkosKirim();

    // Iterasi melalui setiap menu dalam pesanan
    for (Menu item : currentOrder.getItems()) {
      int currentHarga = (int) item.getHarga(); // Mengkonversi harga item dari tipe double ke tipe int
      totalBiaya += currentHarga; // Menambahkan harga makanan ke total biaya
      System.out.printf("- %s %d%n", item.getNamaMakanan(), currentHarga); // Menampilkan nama makanan dan harganya
    }

    // Menampilkan biaya ongkos kirim dan total biaya keseluruhan
    System.out.printf("Biaya Ongkos Kirim: Rp %d%nTotal Biaya: Rp %d%n",
        currentOrder.getBiayaOngkosKirim(),
        totalBiaya);
  }

  public static void handleLihatMenu() {
    System.out.println("-----------------Lihat Menu-----------------"); // Menampilkan header lihat menu

    Restaurant currentResto = null;
    boolean namaRestoValidator = false;
    // Looping untuk memeriksa nama restoran yang valid
    while (!namaRestoValidator) {
      // Meminta pengguna untuk memasukkan nama restoran
      System.out.print("Nama Restoran: ");
      String namaRestoran = input.nextLine();

      // Iterasi melalui daftar restoran untuk mencari restoran dengan nama yang cocok
      for (Restaurant restoran : restoList) {
        // Memeriksa apakah nama restoran dalam daftar cocok
        if (restoran.getNama().equalsIgnoreCase(namaRestoran)) {
          namaRestoValidator = true;
          currentResto = restoran; // Menyimpan restoran yang cocok ke dalam currentResto
          break;
        }
      }

      // Memeriksa jika nama restoran tidak valid
      if (!namaRestoValidator) {
        System.out.println("Restoran tidak terdaftar pada sistem.\n");
      }
    }

    System.out.println("Menu: "); // Menampilkan label untuk daftar menu

    // Menampilkan daftar menu dari restoran yang dipilih
    for (int i = 0; i < currentResto.getMenu().size(); i++) {
      int currentHarga = (int) currentResto.getMenu().get(i).getHarga(); // Mendapatkan harga makanan
      // Menampilkan nomor urut, nama makanan, dan harganya
      System.out.printf("%d. %s %d%n", i + 1, currentResto.getMenu().get(i).getNamaMakanan(), currentHarga);
    }
  }

  public static void handleUpdateStatusPesanan(User userLoggedIn) {
    while (true) {
      Order currentOrder = null;
      boolean orderIDValidator = false;

      // Meminta pengguna untuk memasukkan ID pesanan
      System.out.print("Order ID: ");
      String orderID = input.nextLine();

      // Iterasi melalui riwayat pesanan pengguna yang sedang masuk
      for (Order order : userLoggedIn.getOrderHistory()) {
        // Memeriksa apakah ID pesanan dalam riwayat cocok
        if (order.getOrderID().equalsIgnoreCase(orderID)) {
          orderIDValidator = true;
          currentOrder = order; // Menyimpan pesanan yang cocok ke dalam currentOrder
          break;
        }
      }

      // Memeriksa jika ID pesanan tidak valid
      if (!orderIDValidator) {
        System.out.println("Order ID tidak dapat ditemukan.\n");
        continue;
      }

      // Meminta user untuk status pesanan
      System.out.print("Status: ");
      String status = input.nextLine();

      int currentStatus = -1;
      int isOrderFinished = currentOrder.isOrderFinished() ? 1 : 0; // Mendapatkan status pesanan yang sebenarnya

      // Memeriksa status pesanan yang dimasukkan user
      if (status.equalsIgnoreCase("Selesai") || status.equalsIgnoreCase("Finished")) {
        currentStatus = 1; // Jika status diubah menjadi "Selesai" atau "Finished", currentStatus menjadi 1
      } else if (status.equalsIgnoreCase("Belum Selesai") || status.equalsIgnoreCase("Not Finished")) {
        currentStatus = 0; // Jika status diubah menjadi "Belum Selesai" atau "Not Finished", currentStatus
        // menjadi 0
      }

      // Memeriksa apakah status pesanan telah diubah atau tidak valid
      if (currentStatus == isOrderFinished || currentStatus == -1) {
        System.out.printf("Status pesanan dengan ID %s tidak berhasil diupdate!%n%n", currentOrder.getOrderID());
        continue;
      }
      // Mengatur status pesanan sesuai dengan input user
      currentOrder.setOrderFinished((currentStatus == 1) ? true : false);

      System.out.printf("Status pesanan dengan ID %s berhasil diupdate!%n", currentOrder.getOrderID());

      break;
    }
  }

  public static void handleTambahRestoran() {
    System.out.println("--------------Tambah Restoran--------------"); // Menampilkan header penambahan restoran

    Restaurant currentResto;

    while (true) {
      boolean namaValidator = true;
      System.out.print("Nama: ");
      String nama = input.nextLine();

      // Memeriksa apakah panjang nama restoran memenuhi syarat
      if (nama.length() < 4) {
        System.out.println("Nama Restoran tidak valid!\n");
        continue;
      }

      for (Restaurant resto : restoList) {
        if (resto.getNama().equalsIgnoreCase(nama)) {
          namaValidator = false;
          break;
        }
      }

      // Memeriksa jika nama restoran sudah terdaftar sebelumnya
      if (!namaValidator) {
        System.out.printf("Restoran dengan nama %s sudah pernah terdaftar. Mohon masukkan nama yang berbeda!%n%n",
            nama);
        continue;
      }

      currentResto = new Restaurant(nama); // Membuat instance baru dari kelas Restaurant

      boolean menuValidator = true;
      System.out.print("Jumlah Makanan: ");
      int jumlahMakanan = input.nextInt();

      input.nextLine(); // Membersihkan buffer

      for (int i = 0; i < jumlahMakanan; i++) {
        String userInput = input.nextLine(); // Membaca input menu dari user
        String[] splited = userInput.split("\\s+"); // Membagi input menjadi array berdasarkan spasi

        // Memeriksa apakah input sesuai dengan format yang diharapkan
        if (splited.length >= 2) {
          // Mengambil nama makanan dari input
          String namaMakanan = ((splited.length == 2) ? splited[0]
              : String.join(" ", Arrays.copyOfRange(splited, 0, splited.length - 1)));
          // Mengambil harga makanan dari input
          String harga = splited[splited.length - 1];

          // Memeriksa apakah harga makanan merupakan bilangan bulat
          if (harga.chars().allMatch(Character::isDigit)) {
            // Menambahkan menu baru ke dalam restoran
            currentResto.addMenu(new Menu(namaMakanan, Double.parseDouble(harga)));
          } else {
            menuValidator = false;
          }
        } else {
          menuValidator = false;
        }
      }

      // Memeriksa jika terdapat masalah dengan format menu
      if (!menuValidator) {
        System.out.println("Harga menu harus bilangan bulat!\n");
        continue;
      }

      break;
    }

    restoList.add(currentResto); // Menambahkan restoran yang baru dibuat ke dalam daftar restoran
    System.out.printf("Restaurant %s berhasil terdaftar.", currentResto.getNama());
  }

  public static void handleHapusRestoran() {
    System.out.println("---------------Hapus Restoran---------------"); // Menampilkan header penghapusan restoran

    boolean removed = false;

    while (!removed) {
      // Meminta pengguna untuk memasukkan nama restoran yang akan dihapus
      System.out.print("Nama Restoran: ");
      String nama = input.nextLine();

      // Menghapus restoran dari daftar berdasarkan nama
      removed = restoList.removeIf(restoran -> restoran.getNama().equalsIgnoreCase(nama));

      // Memeriksa apakah restoran berhasil dihapus atau tidak
      if (removed) {
        System.out.println("Restoran berhasil dihapus.");
      } else {
        System.out.println("Restoran tidak terdaftar pada sistem.\n");
      }
    }
  }

  public static void initUser() {
    userList = new ArrayList<User>();
    userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer"));
    userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer"));
    userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer"));
    userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer"));
    userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer"));

    userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin"));
    userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin"));
  }

  public static void printHeader() {
    System.out.println("\n>>=======================================<<");
    System.out.println("|| ___                 ___             _ ||");
    System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
    System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
    System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
    System.out.println("||          |_|                          ||");
    System.out.println(">>=======================================<<");
  }

  public static void startMenu() {
    System.out.println("Selamat datang di DepeFood!");
    System.out.println("--------------------------------------------");
    System.out.println("Pilih menu:");
    System.out.println("1. Login");
    System.out.println("2. Keluar");
    System.out.println("--------------------------------------------");
    System.out.print("Pilihan menu: ");
  }

  public static void menuAdmin() {
    System.out.println("\n--------------------------------------------");
    System.out.println("Pilih menu:");
    System.out.println("1. Tambah Restoran");
    System.out.println("2. Hapus Restoran");
    System.out.println("3. Keluar");
    System.out.println("--------------------------------------------");
    System.out.print("Pilihan menu: ");
  }

  public static void menuCustomer() {
    System.out.println("\n--------------------------------------------");
    System.out.println("Pilih menu:");
    System.out.println("1. Buat Pesanan");
    System.out.println("2. Cetak Bill");
    System.out.println("3. Lihat Menu");
    System.out.println("4. Update Status Pesanan");
    System.out.println("5. Keluar");
    System.out.println("--------------------------------------------");
    System.out.print("Pilihan menu: ");
  }
}