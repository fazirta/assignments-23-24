package assignments.assignment4.components;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Order;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.CustomerMenu;

public class BillPrinter {
    private Stage stage; // Variabel untuk menyimpan panggung (Stage) aplikasi
    private MainApp mainApp; // Variabel untuk menyimpan aplikasi utama (MainApp)
    private User user; // Variabel untuk menyimpan informasi pengguna (User)
    private Label billLabel = new Label(); // Label untuk menampilkan tagihan

    public BillPrinter(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user;
    }

    // Method untuk membuat tampilan form pencetak tagihan
    private Scene createBillPrinterForm() {
        VBox outerVBox = new VBox(); // VBox luar untuk menempatkan elemen-elemen antarmuka pengguna
        outerVBox.setAlignment(Pos.TOP_CENTER);
        outerVBox.setStyle("-fx-background-color: #F9FAFB;"); // Menetapkan warna latar belakang untuk VBox luar

        VBox innerVBox = new VBox(12);
        innerVBox.setAlignment(Pos.CENTER_LEFT); // Mengatur penempatan VBox dalam ke kiri tengah
        innerVBox.setPadding(new Insets(32));
        innerVBox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
        innerVBox.setMaxWidth(450); // Menetapkan lebar maksimum VBox dalam.

        DropShadow dropShadow = new DropShadow(); // Membuat efek bayangan
        dropShadow.setRadius(15);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        dropShadow.setColor(Color.rgb(225, 225, 225, 0.8)); // Menetapkan warna efek bayangan

        innerVBox.setEffect(dropShadow); // Menetapkan efek bayangan pada VBox dalam

        Label billPrinterlabel = new Label("Bill"); // Label untuk judul tagihan
        billPrinterlabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        Platform.runLater(() -> billPrinterlabel.requestFocus());

        billLabel.setStyle("-fx-font-size: 16px;");

        Button cancelButton = new Button("⬅  Return"); // Tombol untuk kembali ke menu pelanggan
        cancelButton.setStyle("-fx-font-size: 14px; " +
                "-fx-cursor: hand;" +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-background-radius: 12px;" +
                "-fx-background-color: #EE2737;" +
                "-fx-padding: 10px 18px;");
        cancelButton.setOnAction(event -> {
            stage.setScene(CustomerMenu.getPrintBillScene());
            CustomerMenu.printBillAnimation();
        });

        innerVBox.getChildren().addAll(billPrinterlabel, billLabel, cancelButton);

        Label logoLabel = new Label("🅓 DepeFood"); // Label untuk logo aplikasi
        logoLabel.setStyle("-fx-font-family: Verdana, sans-serif;" +
                "-fx-font-size: 24px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #00AA13;");

        Label userLabel = new Label("👤  " + user.getNama()); // Label untuk nama pengguna
        userLabel.setStyle("-fx-font-family: Verdana, sans-serif;" +
                "-fx-font-size: 18px;");

        HBox headerHBox = new HBox(); // HBox untuk header aplikasi
        headerHBox.setAlignment(Pos.CENTER);
        headerHBox.setMaxWidth(Double.MAX_VALUE); // Menetapkan lebar maksimum HBox header
        headerHBox.setSpacing(1080);
        headerHBox.setPadding(new Insets(36, 36, 250, 36));
        headerHBox.setStyle("-fx-background-color: #F9FAFB;"); // Menetapkan warna latar belakang untuk HBox header

        headerHBox.getChildren().addAll(logoLabel, userLabel); // Menambahkan elemen-elemen ke dalam HBox header

        outerVBox.getChildren().addAll(headerHBox, innerVBox); // Menambahkan elemen-elemen ke dalam VBox luar

        return new Scene(outerVBox, 1490, 840); // Mengembalikan tampilan dengan VBox luar, lebar 1490, dan tinggi 840
    }

    // Method untuk mencetak tagihan berdasarkan ID pesanan
    public void printBill(String orderId) {
        Order order = DepeFood.getOrderOrNull(orderId); // Mengambil objek pesanan berdasarkan ID
        String outputBillString = outputBill(order); // Menghasilkan string tagihan dari pesanan
        billLabel.setText(outputBillString); // Menetapkan teks tagihan ke label tagihan
    }

    // Method untuk menghasilkan string tagihan dari pesanan.
    protected String outputBill(Order order) {
        DecimalFormat decimalFormat = new DecimalFormat(); // Membuat objek untuk memformat angka desimal
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.'); // Menetapkan pemisah grup sebagai titik
        decimalFormat.setDecimalFormatSymbols(symbols);
        return String.format("Order ID: %s%n" +
                "Tanggal Pemesanan: %s%n" +
                "Restaurant: %s%n" +
                "Lokasi Pengiriman: %s%n" +
                "Status Pengiriman: %s%n" +
                "Pesanan:%n%s%n" +
                "Biaya Ongkos Kirim: Rp%s%n" +
                "Total Biaya: Rp%s%n",
                order.getOrderId(),
                order.getTanggal(),
                order.getRestaurant().getNama(),
                user.getLokasi(),
                !order.getOrderFinished() ? "Not Finished" : "Finished",
                outputMenu(order),
                decimalFormat.format(order.getOngkir()),
                decimalFormat.format(order.getTotalHarga()));
    }

    // Method untuk menghasilkan string pesanan dari pesanan.
    protected String outputMenu(Order order) {
        StringBuilder pesananBuilder = new StringBuilder(); // StringBuilder untuk menyusun string pesanan
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(); // Membuat simbol untuk pemformatan angka desimal
        symbols.setGroupingSeparator('\u0000');
        decimalFormat.setDecimalFormatSymbols(symbols); // Menetapkan simbol ke objek pemformatan angka desimal
        for (Menu menu : order.getSortedMenu()) {
            pesananBuilder.append("    - ").append(menu.getNamaMakanan()).append(" ")
                    .append(decimalFormat.format(menu.getHarga())).append("\n");
        }
        if (pesananBuilder.length() > 0) {
            pesananBuilder.deleteCharAt(pesananBuilder.length() - 1); // Menghapus karakter baris baru terakhir
        }
        return pesananBuilder.toString();
    }

    // Method untuk mendapatkan tampilan dari pencetak tagihan.
    public Scene getScene() {
        return this.createBillPrinterForm();
    }

    protected void showAlert(String title, String header, String content, Alert.AlertType c) {
        Alert alert = new Alert(c);
        alert.setTitle(title); // Menetapkan judul alert
        alert.setHeaderText(header);
        alert.setContentText(content); // Menetapkan teks konten alert
        alert.showAndWait();
    }
}
