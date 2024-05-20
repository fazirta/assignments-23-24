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
    private Stage stage;
    private MainApp mainApp;
    private User user;
    private Label billLabel = new Label();

    public BillPrinter(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user;
    }

    private Scene createBillPrinterForm() {
        VBox outerVBox = new VBox();
        outerVBox.setAlignment(Pos.TOP_CENTER);
        outerVBox.setStyle("-fx-background-color: #F9FAFB;");

        VBox innerVBox = new VBox(12);
        innerVBox.setAlignment(Pos.CENTER_LEFT);
        innerVBox.setPadding(new Insets(32));
        innerVBox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
        innerVBox.setMaxWidth(450);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(15);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        dropShadow.setColor(Color.rgb(225, 225, 225, 0.8));

        innerVBox.setEffect(dropShadow);

        Label billPrinterlabel = new Label("Bill");
        billPrinterlabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        Platform.runLater(() -> billPrinterlabel.requestFocus());

        billLabel.setStyle("-fx-font-size: 16px;");

        Button cancelButton = new Button("⬅  Return");
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

        Label logoLabel = new Label("🅓 DepeFood");
        logoLabel.setStyle("-fx-font-family: Verdana, sans-serif;" +
                "-fx-font-size: 24px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #00AA13;");

        Label userLabel = new Label("👤  " + user.getNama());
        userLabel.setStyle("-fx-font-family: Verdana, sans-serif;" +
                "-fx-font-size: 18px;");

        HBox headerHBox = new HBox();
        headerHBox.setAlignment(Pos.CENTER);
        headerHBox.setMaxWidth(Double.MAX_VALUE);
        headerHBox.setSpacing(1080);
        headerHBox.setPadding(new Insets(36, 36, 250, 36));
        headerHBox.setStyle("-fx-background-color: #F9FAFB;");

        headerHBox.getChildren().addAll(logoLabel, userLabel);

        outerVBox.getChildren().addAll(headerHBox, innerVBox);

        return new Scene(outerVBox, 1490, 840);
    }

    public void printBill(String orderId) {
        Order order = DepeFood.getOrderOrNull(orderId);
        String outputBillString = outputBill(order);
        billLabel.setText(outputBillString);
    }

    protected String outputBill(Order order) {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
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

    protected String outputMenu(Order order) {
        StringBuilder pesananBuilder = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('\u0000');
        decimalFormat.setDecimalFormatSymbols(symbols);
        for (Menu menu : order.getSortedMenu()) {
            pesananBuilder.append("    - ").append(menu.getNamaMakanan()).append(" ")
                    .append(decimalFormat.format(menu.getHarga())).append("\n");
        }
        if (pesananBuilder.length() > 0) {
            pesananBuilder.deleteCharAt(pesananBuilder.length() - 1);
        }
        return pesananBuilder.toString();
    }

    public Scene getScene() {
        return this.createBillPrinterForm();
    }

    protected void showAlert(String title, String header, String content, Alert.AlertType c) {
        Alert alert = new Alert(c);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
