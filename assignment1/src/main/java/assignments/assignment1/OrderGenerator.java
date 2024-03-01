package assignments.assignment1;

import java.util.Scanner;

public class OrderGenerator {

  private static final Scanner input = new Scanner(System.in);

  /*
   * Method  ini untuk menampilkan header
   */
  public static void showHeader() {
    System.out.println(">>=======================================<<");
    System.out.println("|| ___                 ___             _ ||");
    System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
    System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
    System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
    System.out.println("||          |_|                          ||");
    System.out.println(">>=======================================<<");
    System.out.println();
  }

  /*
   * Method  ini untuk menampilkan menu
   */
  public static void showMenu() {
    System.out.println("Pilih menu:");
    System.out.println("1. Generate Order ID");
    System.out.println("2. Generate Bill");
    System.out.println("3. Keluar");
  }

  /*
   * Method  ini untuk menampilkan spacer
   */
  public static void showSpacer() {
    System.out.println("------------------------------------------");
  }

  /*
   * Method ini digunakan untuk membuat ID
   * dari nama restoran, tanggal order, dan nomor telepon
   *
   * @return String Order ID dengan format sesuai pada dokumen soal
   */
  public static String generateOrderID(
    String namaRestoran,
    String tanggalOrder,
    String noTelepon
  ) {
    String orderID =
      namaRestoran.substring(0, 4).toUpperCase() +
      tanggalOrder.substring(0, 2) +
      tanggalOrder.substring(3, 5) +
      tanggalOrder.substring(6, 10);

    int jumlahDigitNomorTelepon = 0; // Variabel untuk menyimpan jumlah digit nomor telepon

    // Menghitung jumlah digit nomor telepon
    for (int i = 0; i < noTelepon.length(); i++) {
      jumlahDigitNomorTelepon += Character.getNumericValue(noTelepon.charAt(i)); // Menambahkan nilai numerik dari karakter nomor telepon ke variabel
    }

    jumlahDigitNomorTelepon = jumlahDigitNomorTelepon % 100; // Mengambil sisa pembagian dari jumlah digit nomor telepon dengan 100

    String jumlahDigitNomorTeleponStr = String.valueOf(jumlahDigitNomorTelepon); // Mengonversi jumlah digit nomor telepon ke dalam bentuk string

    if (jumlahDigitNomorTeleponStr.length() == 1) { // Jika panjang string jumlah digit nomor telepon adalah 1
      jumlahDigitNomorTeleponStr = "0" + jumlahDigitNomorTeleponStr; // Tambahkan "0" di depan string
    }

    orderID += jumlahDigitNomorTeleponStr; // Menambahkan jumlah digit nomor telepon ke Order ID

    int checksum1 = 0;
    int checksum2 = 0;

    // Menghitung checksum
    for (int i = 0; i < orderID.length(); i++) {
      if (i % 2 == 0) { // Jika indeks genap
        checksum1 += Character.getNumericValue(orderID.charAt(i));
      } else { // Jika indeks ganjil
        checksum2 += Character.getNumericValue(orderID.charAt(i));
      }
    }

    checksum1 = checksum1 % 36; // Mengambil sisa pembagian dari checksum pertama dengan 36
    checksum2 = checksum2 % 36; // Mengambil sisa pembagian dari checksum kedua dengan 36

    orderID += Character.toUpperCase(Character.forDigit(checksum1, 36)); // Menambahkan checksum pertama ke Order ID dalam bentuk huruf kapital
    orderID += Character.toUpperCase(Character.forDigit(checksum2, 36)); // Menambahkan checksum kedua ke Order ID dalam bentuk huruf kapital

    return orderID; // Mengembalikan Order ID yang telah dibuat
  }

  /*
   * Method ini digunakan untuk membuat bill
   * dari order id dan lokasi
   *
   * @return String Bill dengan format sesuai di bawah:
   *          Bill:
   *          Order ID: [Order ID]
   *          Tanggal Pemesanan: [Tanggal Pemesanan]
   *          Lokasi Pengiriman: [Kode Lokasi]
   *          Biaya Ongkos Kirim: [Total Ongkos Kirim]
   */
  public static String generateBill(String orderID, String lokasi) {
    String harga = ""; // Variabel untuk menyimpan harga

    // Menentukan harga berdasarkan lokasi pengiriman
    switch (lokasi.toUpperCase()) {
      case "P":
        harga = "10.000";
        break;
      case "U":
        harga = "20.000";
        break;
      case "T":
        harga = "35.000";
        break;
      case "S":
        harga = "40.000";
        break;
      case "B":
        harga = "60.000";
        break;
      default:
        harga = ""; // Jika lokasi tidak valid, harga akan kosong
        break;
    }

    return (
      "Bill:\nOrder ID: " +
      orderID +
      "\nTanggal Pemesanan: " +
      orderID.substring(4, 6) +
      "/" +
      orderID.substring(6, 8) +
      "/" +
      orderID.substring(8, 12) +
      "\nLokasi Pengiriman: " +
      lokasi.toUpperCase() +
      "\nBiaya Ongkos Kirim: Rp " +
      harga +
      "\n"
    ); // Mengembalikan bill dengan format yang ditentukan
  }

  public static void main(String[] args) {
    showHeader(); // Memanggil method showHeader untuk menampilkan header

    while (true) { // Loop utama program
      showMenu(); // Memanggil method showMenu untuk menampilkan menu
      showSpacer(); // Memanggil method showSpacer untuk menampilkan spacer

      System.out.print("Pilihan menu: "); // Meminta pengguna untuk memilih menu
      String pilihanMenu = input.nextLine(); // Membaca input pengguna

      if (pilihanMenu.equals("1")) { // Jika pengguna memilih menu 1
        while (true) {
          System.out.print("\nNama Restoran: "); // Meminta pengguna untuk memasukkan nama restoran
          String namaRestoran = input.nextLine(); // Membaca input nama restoran dari pengguna

          // Memeriksa apakah nama restoran minimal 4 huruf
          if (namaRestoran.length() < 4) {
            System.out.println("Nama Restoran tidak valid!\n");
            continue;
          }

          System.out.print("Tanggal Pemesanan: "); // Meminta pengguna untuk memasukkan tanggal pemesanan
          String tanggalOrder = input.nextLine(); // Membaca input tanggal pemesanan dari pengguna

          // Memeriksa apakah tanggal dalam format DD/MM/YYYY
          if (tanggalOrder.length() != 10) {
            System.out.println("Tanggal Pemesanan dalam format DD/MM/YYYY!\n");
            continue;
          }

          System.out.print("No. Telpon: "); // Meminta pengguna untuk memasukkan nomor telepon
          String noTelepon = input.nextLine(); // Membaca input nomor telepon dari pengguna

          // Memeriksa apakah nomor telepon dalam bentuk bilangan bulat positif
          if (!noTelepon.matches("[0-9]+")) {
            System.out.println(
              "Harap masukkan nomor telepon dalam bentuk bilangan bulat positif.\n"
            );
            continue;
          }

          System.out.println(
            "Order ID " +
            generateOrderID(namaRestoran, tanggalOrder, noTelepon) +
            " diterima!"
          ); // Menampilkan Order ID yang telah dibuat

          break; // Keluar dari loop
        }
      } else if (pilihanMenu.equals("2")) { // Jika pengguna memilih menu 2
        String orderID;
        String lokasi;

        while (true) {
          System.out.print("Order ID: ");
          orderID = input.nextLine();

          // validasi order ID
          if (orderID.length() < 16) {
            System.out.println("Order ID minimal 16 karakter\n");
            continue;
          }

          // validasi checksum
          int checksum1 = 0;
          int checksum2 = 0;

          for (int i = 0; i < orderID.length() - 2; i++) { // Loop untuk menghitung checksum
            if (i % 2 == 0) { // Jika indeks genap
              checksum1 += Character.getNumericValue(orderID.charAt(i));
            } else { // Jika indeks ganjil
              checksum2 += Character.getNumericValue(orderID.charAt(i));
            }
          }

          checksum1 = checksum1 % 36; // Mengambil sisa pembagian dari checksum pertama dengan 36
          checksum2 = checksum2 % 36; // Mengambil sisa pembagian dari checksum kedua dengan 36

          if (
            orderID.charAt(orderID.length() - 2) !=
            Character.toUpperCase(Character.forDigit(checksum1, 36)) ||
            orderID.charAt(orderID.length() - 1) !=
            Character.toUpperCase(Character.forDigit(checksum2, 36))
          ) { // Jika checksum tidak valid
            System.out.println("Silahkan masukkan Order ID yang valid!\n");
            continue;
          }

          System.out.print("Lokasi Pengiriman: "); // Meminta pengguna untuk memasukkan lokasi pengiriman
          lokasi = input.nextLine(); // Membaca input lokasi pengiriman dari pengguna

          // validasi lokasi
          if ("PUTSB".indexOf(lokasi.toUpperCase()) == -1) { // Jika lokasi pengiriman tidak valid
            System.out.println(
              "Harap masukkan lokasi pengiriman yang ada pada jangkauan!\n"
            ); // Menampilkan pesan kesalahan
            continue;
          }

          System.out.println("\n" + generateBill(orderID, lokasi)); // Menampilkan bill yang telah dibuat

          break;
        }
      } else if (pilihanMenu.equals("3")) { // Jika pengguna memilih menu 3
        System.out.println("Terima kasih telah menggunakan DepeFood!");
        break; // Keluar dari loop utama
      }

      showSpacer(); // Memanggil method showSpacer untuk menampilkan spacer
    }
  }
}
