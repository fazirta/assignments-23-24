package assignments.assignment3.systemCLI;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import assignments.assignment1.OrderGenerator;
import assignments.assignment3.core.Menu;
import assignments.assignment3.core.Order;
import assignments.assignment3.core.Restaurant;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;

public class CustomerSystemCLI extends UserSystemCLI {
    @Override
    protected boolean handleMenu(int choice, ArrayList<Restaurant> restoList) {
        switch (choice) {
            case 1 -> handleBuatPesanan(restoList); // Memanggil method untuk membuat pesanan
            case 2 -> handleCetakBill(); // Memanggil method untuk mencetak bill
            case 3 -> handleLihatMenu(restoList); // Memanggil method untuk melihat menu restoran
            case 4 -> handleBayarBill(); // Memanggil method untuk membayar bill
            case 5 -> handleCekSaldo(); // Memanggil method untuk mengecek saldo
            case 6 -> {
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
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Bayar Bill");
        System.out.println("5. Cek Saldo");
        System.out.println("6. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    private void handleBuatPesanan(ArrayList<Restaurant> restoList) {
        System.out.println("--------------Buat Pesanan----------------");
        while (true) { // Melakukan loop untuk memproses pembuatan pesanan
            System.out.print("Nama Restoran: "); // Meminta nama restoran dari user
            String restaurantName = input.nextLine().trim();
            Restaurant restaurant = getRestaurantByName(restaurantName, restoList); // Mencari restoran berdasarkan nama
            if (restaurant == null) { // Jika restoran tidak ditemukan
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): "); // Meminta tanggal pemesanan dari user
            String tanggalPemesanan = input.nextLine().trim();
            if (!validateDate(tanggalPemesanan)) { // Validasi format tanggal
                System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)");
                System.out.println();
                continue;
            }
            System.out.print("Jumlah Pesanan: "); // Meminta jumlah pesanan dari user
            int jumlahPesanan = Integer.parseInt(input.nextLine().trim());
            System.out.println("Order: ");
            List<String> listMenuPesananRequest = new ArrayList<>();
            for (int i = 0; i < jumlahPesanan; i++) { // Meminta menu yang dipesan dari user
                listMenuPesananRequest.add(input.nextLine().trim());
            }
            if (!validateRequestPesanan(restaurant, listMenuPesananRequest)) { // Validasi menu yang dipesan
                System.out.println("Mohon memesan menu yang tersedia di Restoran!");
                continue;
            }
            Order order = new Order(
                    OrderGenerator.generateOrderID(restaurantName, tanggalPemesanan, userLoggedIn.getNomorTelepon()),
                    tanggalPemesanan,
                    calculateDeliveryCost(userLoggedIn.getLokasi()),
                    restaurant,
                    getMenuRequest(restaurant, listMenuPesananRequest)); // Membuat pesanan baru
            System.out.printf("Pesanan dengan ID %s diterima!%n", order.getOrderId());
            userLoggedIn.addOrderHistory(order); // Menambahkan pesanan ke history pesanan user
            return; // Keluar dari loop setelah pesanan berhasil dibuat
        }
    }

    private Restaurant getRestaurantByName(String name, ArrayList<Restaurant> restoList) {
        // Mencari restoran berdasarkan nama dengan menggunakan stream
        Optional<Restaurant> restaurantMatched = restoList.stream()
                .filter(restoran -> restoran.getNama().toLowerCase().equals(name.toLowerCase())).findFirst();
        if (restaurantMatched.isPresent()) {
            return restaurantMatched.get(); // Mengembalikan restoran jika ditemukan
        }
        return null; // Mengembalikan null jika restoran tidak ditemukan
    }

    private boolean validateRequestPesanan(Restaurant restaurant, List<String> listMenuPesananRequest) {
        // Memeriksa apakah semua menu yang dipesan tersedia di restoran
        return listMenuPesananRequest.stream().allMatch(
                pesanan -> restaurant.getMenu().stream().anyMatch(menu -> menu.getNamaMakanan().equals(pesanan)));
    }

    private Menu[] getMenuRequest(Restaurant restaurant, List<String> listMenuPesananRequest) {
        Menu[] menu = new Menu[listMenuPesananRequest.size()]; // Membuat array untuk menyimpan menu pesanan
        for (int i = 0; i < menu.length; i++) { // Iterasi melalui setiap elemen dalam listMenuPesananRequest
            for (Menu existMenu : restaurant.getMenu()) { // Memeriksa setiap menu yang tersedia di restoran
                if (existMenu.getNamaMakanan().equals(listMenuPesananRequest.get(i))) {
                    menu[i] = existMenu; // Menambahkan menu ke array
                }
            }
        }
        return menu; // Mengembalikan array menu pesanan
    }

    private boolean validateDate(String tanggalPemesanan) {
        // Memeriksa apakah tanggal pemesanan sesuai dengan format yang diinginkan
        if (tanggalPemesanan == null || !tanggalPemesanan.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return false;
        }
        String[] parts = tanggalPemesanan.split("/"); // Memisahkan tanggal, bulan, dan tahun
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        if (year < 0 || month < 1 || month > 12 || day < 1 || day > 31) { // Memeriksa tanggal, bulan, dan tahun
            return false;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) { // Memeriksa jumlah hari dalam bulan tertentu
            return day <= 30;
        }
        return true;
    }

    private int calculateDeliveryCost(String lokasi) {
        // Menghitung biaya pengiriman berdasarkan lokasi user yang sedang login
        switch (lokasi.toUpperCase()) {
            case "P":
                return 10000;
            case "U":
                return 20000;
            case "T":
                return 35000;
            case "S":
                return 40000;
            case "B":
                return 60000;
            default:
                return 0; // Mengembalikan 0 jika lokasi tidak valid
        }
    }

    private void handleCetakBill() {
        System.out.println("--------------Cetak Bill----------------");
        while (true) { // Melakukan loop untuk mencetak bill
            System.out.print("Masukkan Order ID: "); // Meminta order ID dari user
            String orderId = input.nextLine().trim();
            Order order = getOrderOrNull(orderId); // Mendapatkan order berdasarkan ID
            if (order == null) { // Jika order tidak ditemukan
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            System.out.println("");
            System.out.print(outputBillPesanan(order)); // Mencetak bill pesanan
            return; // Keluar dari loop setelah bill dicetak
        }
    }

    private Order getOrderOrNull(String orderId) {
        // Mencari order berdasarkan ID dalam history pesanan user
        for (Order order : userLoggedIn.getOrderHistory()) {
            if (order.getOrderId().equals(orderId)) {
                return order; // Mengembalikan order jika ditemukan
            }
        }
        return null; // Mengembalikan null jika order tidak ditemukan
    }

    private String outputBillPesanan(Order order) {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(symbols);
        return String.format("Bill:%n" +
                "Order ID: %s%n" +
                "Tanggal Pemesanan: %s%n" +
                "Lokasi Pengiriman: %s%n" +
                "Status Pengiriman: %s%n" +
                "Pesanan:%n%s%n" +
                "Biaya Ongkos Kirim: Rp %s%n" +
                "Total Biaya: Rp %s%n",
                order.getOrderId(),
                order.getTanggal(),
                userLoggedIn.getLokasi(),
                !order.getOrderFinished() ? "Not Finished" : "Finished",
                getMenuPesananOutput(order),
                decimalFormat.format(order.getOngkir()),
                decimalFormat.format(order.getTotalHarga()));
    }

    private String getMenuPesananOutput(Order order) {
        StringBuilder pesananBuilder = new StringBuilder(); // StringBuilder untuk menyusun output pesanan
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('\u0000');
        decimalFormat.setDecimalFormatSymbols(symbols);
        // Mengambil setiap menu dari order dan menambahkannya ke StringBuilder
        for (Menu menu : order.getSortedMenu()) {
            pesananBuilder.append("- ").append(menu.getNamaMakanan()).append(" ")
                    .append(decimalFormat.format(menu.getHarga())).append("\n");
        }
        // Menghapus newline terakhir jika pesanan tidak kosong
        if (pesananBuilder.length() > 0) {
            pesananBuilder.deleteCharAt(pesananBuilder.length() - 1);
        }
        return pesananBuilder.toString(); // Mengembalikan output pesanan sebagai string
    }

    private void handleLihatMenu(ArrayList<Restaurant> restoList) {
        System.out.println("--------------Lihat Menu----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String restaurantName = input.nextLine().trim(); // Meminta nama restoran dari user
            Restaurant restaurant = getRestaurantByName(restaurantName, restoList); // Mencari restoran berdasarkan nama
            if (restaurant == null) { // Jika restoran tidak ditemukan
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.print(restaurant.printMenu()); // Menampilkan menu restoran
            System.out.println();
            return; // Keluar dari loop setelah menu restoran ditampilkan
        }
    }

    private void handleBayarBill() {
        System.out.println("--------------Bayar Bill----------------");
        Order order;
        while (true) { // Melakukan loop untuk memproses pembayaran
            System.out.print("Masukkan Order ID: "); // Meminta order ID dari user
            String orderId = input.nextLine().trim();
            order = getOrderOrNull(orderId);
            if (order == null) { // Jika order tidak ditemukan
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            } else if (order.getOrderFinished()) {
                System.out.println("Pesanan dengan ID ini sudah lunas!\n");
                continue;
            }
            System.out.println();
            System.out.println(outputBillPesanan(order)); // Menampilkan bill pesanan
            // Meminta user memilih metode pembayaran
            System.out.print("Opsi Pembayaran:\n1. Credit Card\n2. Debit\nPilihan Metode Pembayaran: ");
            int choice = Integer.parseInt(input.nextLine().trim());
            // Menetapkan metode pembayaran berdasarkan pilihan user
            if (choice == 1) {
                userLoggedIn.setPayment(new CreditCardPayment());
            } else if (choice == 2) {
                userLoggedIn.setPayment(new DebitPayment());
            } else {
                System.out.println("Perintah tidak diketahui, silakan coba kembali");
                continue;
            }
            boolean success = userLoggedIn.getPayment().processPayment(userLoggedIn, order,
                    (long) order.getTotalHarga()); // Memproses pembayaran dan mengecek keberhasilannya
            if (success)
                break; // Keluar dari loop jika pembayaran berhasil
        }
    }

    private void handleCekSaldo() {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('\u0000');
        decimalFormat.setDecimalFormatSymbols(symbols);
        // Menampilkan sisa saldo user
        System.out.printf("Sisa saldo sebesar Rp %s%n", decimalFormat.format(userLoggedIn.getSaldo()));
    }
}
