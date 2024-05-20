package assignments.assignment4.page;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import assignments.assignment1.OrderGenerator;
import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Order;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DepeFoodPaymentSystem;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.BillPrinter;
import assignments.assignment4.components.form.LoginForm;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomerMenu extends MemberMenu {
    // Atribut-atribut yang diperlukan untuk menu pelanggan
    private Stage stage;
    private Scene scene;
    private Scene addOrderScene;
    private static Scene printBillScene;
    private Scene payBillScene;
    private Scene cekSaldoScene;
    private BillPrinter billPrinter;
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private ComboBox<String> paymentComboBox = new ComboBox<>();
    private MainApp mainApp;
    private List<Restaurant> restoList = new ArrayList<>();
    private User user;
    private DatePicker orderDatePicker = new DatePicker();
    private ListView<String> menuItemsListView = new ListView<>();
    private TextField billPrinterInput = new TextField();
    private TextField payBillInput = new TextField();
    private static GridPane menuLayout = new GridPane();
    private static VBox printBillLayout = new VBox(12);
    private static Label balanceLabel = new Label();
    private DropShadow dropShadow = new DropShadow();

    // Konstruktor untuk inisialisasi menu pelanggan
    public CustomerMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user;
        // Membuat tampilan menu dasar dan tampilan tambah pesanan
        this.scene = createBaseMenu();
        this.addOrderScene = createTambahPesananForm();
        // Membuat objek BillPrinter untuk mencetak tagihan
        this.billPrinter = new BillPrinter(stage, mainApp, this.user);
        // Membuat tampilan cetak tagihan dan tampilan bayar tagihan
        printBillScene = createBillPrinter();
        this.payBillScene = createBayarBillForm();
        // Membuat tampilan cek saldo
        this.cekSaldoScene = createCekSaldoScene();

        // Konfigurasi bayangan untuk elemen-elemen GUI
        dropShadow.setRadius(15);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        dropShadow.setColor(Color.rgb(225, 225, 225, 0.8));
    }

    @Override
    // Membuat tampilan dasar menu pelanggan
    public Scene createBaseMenu() {
        Label addOrderTitleLabel = new Label("Add Order");
        addOrderTitleLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        Label addOrderDescriptionLabel = new Label(
                "Effortlessly place new orders, customizing dishes to your liking for a delightful dining experience.");
        addOrderDescriptionLabel.setWrapText(true);
        addOrderDescriptionLabel.setMaxWidth(360);
        addOrderDescriptionLabel.setStyle("-fx-font-size: 16px;");

        // Tombol untuk menambahkan pesanan baru
        Button addOrderButton = new Button("🛒  Add Order");
        addOrderButton.setStyle("-fx-font-size: 14px; " +
                "-fx-cursor: hand;" +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-background-radius: 12px;" +
                "-fx-background-color: #00AA13;" +
                "-fx-padding: 10px 18px;");
        addOrderButton.setOnAction(e -> {
            restoList = DepeFood.getRestoList();
            ObservableList<String> restoNameList = FXCollections.observableArrayList();
            for (Restaurant restaurant : restoList) {
                restoNameList.add(restaurant.getNama());
            }
            restaurantComboBox.setItems(restoNameList);
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(175), menuLayout);
            fadeTransition.setToValue(0);
            fadeTransition.setCycleCount(1);
            fadeTransition.play();
            fadeTransition.setOnFinished(f -> stage.setScene(addOrderScene));
        });

        VBox addOrderVbox = new VBox(12);
        addOrderVbox.setAlignment(Pos.CENTER_LEFT);
        addOrderVbox.setPadding(new Insets(24));
        addOrderVbox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
        addOrderVbox.setEffect(dropShadow);
        addOrderVbox.getChildren().addAll(addOrderTitleLabel, addOrderDescriptionLabel, addOrderButton);

        Label printBillTitleLabel = new Label("Print Bill");
        printBillTitleLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        Label printBillDescriptionLabel = new Label(
                "Easily generate and print detailed bills for seamless payment transactions and record-keeping.");
        printBillDescriptionLabel.setWrapText(true);
        printBillDescriptionLabel.setMaxWidth(360);
        printBillDescriptionLabel.setStyle("-fx-font-size: 16px;");

        Button printBillButton = new Button("📜  Print Bill");
        printBillButton.setStyle("-fx-font-size: 14px; " +
                "-fx-cursor: hand;" +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-background-radius: 12px;" +
                "-fx-background-color: #00AED6;" +
                "-fx-padding: 10px 18px;");
        printBillButton.setOnAction(e -> {
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(175), menuLayout);
            fadeTransition.setToValue(0);
            fadeTransition.setCycleCount(1);
            fadeTransition.play();
            fadeTransition.setOnFinished(f -> stage.setScene(printBillScene));
        });

        VBox printBillVbox = new VBox(12);
        printBillVbox.setAlignment(Pos.CENTER_LEFT);
        printBillVbox.setPadding(new Insets(24));
        printBillVbox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
        printBillVbox.setEffect(dropShadow);
        printBillVbox.getChildren().addAll(printBillTitleLabel, printBillDescriptionLabel, printBillButton);

        Label payBillTitleLabel = new Label("Pay Bill");
        payBillTitleLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        Label payBillDescriptionLabel = new Label(
                "Swiftly settle your dining expenses with secure payment options for hassle-free transactions.");
        payBillDescriptionLabel.setWrapText(true);
        payBillDescriptionLabel.setMaxWidth(360);
        payBillDescriptionLabel.setStyle("-fx-font-size: 16px;");

        Button payBillButton = new Button("💸  Pay Bill");
        payBillButton.setStyle("-fx-font-size: 14px; " +
                "-fx-cursor: hand;" +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-background-radius: 12px;" +
                "-fx-background-color: #93328E;" +
                "-fx-padding: 10px 18px;");
        payBillButton.setOnAction(e -> {
            ObservableList<String> paymentList = FXCollections.observableArrayList();
            paymentList.add("Credit Card");
            paymentList.add("Debit");
            paymentComboBox.setItems(paymentList);
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(175), menuLayout);
            fadeTransition.setToValue(0);
            fadeTransition.setCycleCount(1);
            fadeTransition.play();
            fadeTransition.setOnFinished(f -> stage.setScene(payBillScene));
        });

        VBox payBillVbox = new VBox(12);
        payBillVbox.setAlignment(Pos.CENTER_LEFT);
        payBillVbox.setPadding(new Insets(24));
        payBillVbox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
        payBillVbox.setEffect(dropShadow);
        payBillVbox.getChildren().addAll(payBillTitleLabel, payBillDescriptionLabel, payBillButton);

        Label cekSaldoTitleLabel = new Label("Check Balance");
        cekSaldoTitleLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        Label cekSaldoDescriptionLabel = new Label(
                "Stay in control of your finances by checking your balance and monitoring transaction history.");
        cekSaldoDescriptionLabel.setWrapText(true);
        cekSaldoDescriptionLabel.setMaxWidth(360);
        cekSaldoDescriptionLabel.setStyle("-fx-font-size: 16px;");

        Button cekSaldoButton = new Button("💵  Check Balance");
        cekSaldoButton.setStyle("-fx-font-size: 14px; " +
                "-fx-cursor: hand;" +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-background-radius: 12px;" +
                "-fx-background-color: #DF1995;" +
                "-fx-padding: 10px 18px;");
        cekSaldoButton.setOnAction(e -> {
            Locale localeID = new Locale.Builder().setLanguage("id").setRegion("ID").build();
            balanceLabel.setText(NumberFormat.getCurrencyInstance(localeID).format(user.getSaldo()));
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(175), menuLayout);
            fadeTransition.setToValue(0);
            fadeTransition.setCycleCount(1);
            fadeTransition.play();
            fadeTransition.setOnFinished(f -> stage.setScene(cekSaldoScene));
        });

        VBox cekSaldoVbox = new VBox(12);
        cekSaldoVbox.setAlignment(Pos.CENTER_LEFT);
        cekSaldoVbox.setPadding(new Insets(24));
        cekSaldoVbox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
        cekSaldoVbox.setEffect(dropShadow);
        cekSaldoVbox.getChildren().addAll(cekSaldoTitleLabel, cekSaldoDescriptionLabel, cekSaldoButton);

        Label logOutTitleLabel = new Label("Log Out");
        logOutTitleLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        Label logOutDescriptionLabel = new Label(
                "To log out from the application, click the button below. You will be redirected to the login screen.");
        logOutDescriptionLabel.setWrapText(true);
        logOutDescriptionLabel.setMaxWidth(360);
        logOutDescriptionLabel.setStyle("-fx-font-size: 16px;");

        Button logOutButton = new Button("🚪  Log Out");
        logOutButton.setStyle("-fx-font-size: 14px; " +
                "-fx-cursor: hand;" +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-background-radius: 12px;" +
                "-fx-background-color: #EE2737;" +
                "-fx-padding: 10px 18px;");
        logOutButton.setOnAction(e -> {
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(175), menuLayout);
            fadeTransition.setToValue(0);
            fadeTransition.setCycleCount(1);
            fadeTransition.play();
            fadeTransition.setOnFinished(f -> {
                mainApp.logout();
                LoginForm.animation();
            });
        });

        VBox logOutVbox = new VBox(12);
        logOutVbox.setAlignment(Pos.CENTER_LEFT);
        logOutVbox.setPadding(new Insets(24));
        logOutVbox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
        logOutVbox.setEffect(dropShadow);
        logOutVbox.getChildren().addAll(logOutTitleLabel, logOutDescriptionLabel, logOutButton);

        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setHgap(32);
        menuLayout.setVgap(32);
        menuLayout.setStyle("-fx-background-color: #F9FAFB;");
        menuLayout.add(addOrderVbox, 0, 0);
        menuLayout.add(printBillVbox, 1, 0);
        menuLayout.add(payBillVbox, 0, 1);
        menuLayout.add(cekSaldoVbox, 1, 1);
        menuLayout.add(logOutVbox, 0, 2);

        Label logoLabel = new Label("🅓 DepeFood");
        logoLabel.setStyle("-fx-font-family: Verdana, sans-serif;" +
                "-fx-font-size: 24px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #00AA13;");

        Label userLabel = new Label("👤  " + user.getNama());
        userLabel.setStyle("-fx-font-family: Verdana, sans-serif;" +
                "-fx-font-size: 18px;");

        HBox headerHbox = new HBox();
        headerHbox.setAlignment(Pos.CENTER);
        headerHbox.setMaxWidth(Double.MAX_VALUE);
        headerHbox.setSpacing(1080);
        headerHbox.setPadding(new Insets(36, 36, 50, 36));
        headerHbox.setStyle("-fx-background-color: #F9FAFB;");
        headerHbox.getChildren().addAll(logoLabel, userLabel);

        VBox outerVbox = new VBox();
        outerVbox.setAlignment(Pos.TOP_CENTER);
        outerVbox.setStyle("-fx-background-color: #F9FAFB;");
        outerVbox.getChildren().addAll(headerHbox, menuLayout);

        return new Scene(outerVbox, 1490, 840);
    }

    private Scene createTambahPesananForm() {
        Label addOrderLabel = new Label("New Order");
        addOrderLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        addOrderLabel.setPadding(new Insets(0, 0, 25, 0));
        Platform.runLater(() -> addOrderLabel.requestFocus());

        Label restaurantNameLabel = new Label("Restaurant ");
        restaurantNameLabel.setStyle("-fx-font-size: 14px;");
        restaurantNameLabel.setPadding(new Insets(10, 0, 0, 0));

        restaurantComboBox.setPromptText("Select a restaurant");
        restaurantComboBox.setStyle("-fx-background-color: #F9FAFB;" +
                "-fx-cursor: hand;" +
                "-fx-font-size: 14px; " +
                "-fx-border-color: #D1D5DB;" +
                "-fx-border-radius: 12px;" +
                "-fx-padding: 9px;");
        restaurantComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                Restaurant restaurant = DepeFood.findRestaurant(newValue);
                ObservableList<String> menuList = FXCollections.observableArrayList();
                for (Menu menu : restaurant.getMenu()) {
                    menuList.add(menu.getNamaMakanan());
                }
                menuItemsListView.setItems(menuList);
            }
        });

        Label dateLabel = new Label("Order date ");
        dateLabel.setStyle("-fx-font-size: 14px;");
        dateLabel.setPadding(new Insets(10, 0, 0, 0));
        orderDatePicker.getEditor().setDisable(true);
        orderDatePicker.getEditor().setOpacity(1);
        orderDatePicker.setStyle("-fx-background-color: #F9FAFB;" +
                "-fx-font-size: 14px; " +
                "-fx-border-color: #D1D5DB;" +
                "-fx-border-radius: 12px;" +
                "-fx-padding: 9px;");

        VBox leftVbox = new VBox(12);
        leftVbox.setAlignment(Pos.CENTER_LEFT);
        leftVbox.setStyle("-fx-background-color: #FFFFFF;");
        leftVbox.setMinWidth(360);
        leftVbox.getChildren().addAll(restaurantNameLabel, restaurantComboBox, dateLabel,
                orderDatePicker);

        Label menuItemsLabel = new Label("Select your desired menu items");
        menuItemsLabel.setStyle("-fx-font-size: 14px;");
        menuItemsLabel.setPadding(new Insets(10, 0, 0, 0));

        menuItemsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        menuItemsListView.setMaxSize(400, 400);
        menuItemsListView.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(menuItemsListView, 0.0);
        AnchorPane.setRightAnchor(menuItemsListView, 0.0);
        menuItemsListView.setStyle("-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-alignment: center;" +
                "-fx-background-radius: 12px; " +
                "-fx-padding: 10px;");

        Button cancelButton = new Button("❌  Cancel");
        cancelButton.setStyle("-fx-font-size: 14px; " +
                "-fx-cursor: hand;" +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-background-radius: 12px;" +
                "-fx-background-color: #EE2737;" +
                "-fx-padding: 10px 18px;");
        cancelButton.setOnAction(event -> {
            stage.setScene(scene);
            animation();
            restaurantComboBox.valueProperty().set(null);
            orderDatePicker.setValue(null);
            menuItemsListView.getItems().clear();
        });

        Button saveButton = new Button("🛒  Order");
        saveButton.setStyle("-fx-font-size: 14px; " +
                "-fx-cursor: hand;" +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-background-radius: 12px;" +
                "-fx-background-color: #00AA13;" +
                "-fx-padding: 10px 18px;");
        saveButton.setOnAction(event -> {
            if (restaurantComboBox.getValue() == null || orderDatePicker.getValue() == null ||
                    menuItemsListView.getSelectionModel().getSelectedItems().isEmpty()) {
                showAlert("Incomplete Information", "Please Complete the Form",
                        "Please ensure that you have selected a restaurant, a date, and at least one menu item.",
                        Alert.AlertType.ERROR);

            } else {
                try {
                    List<String> list = new ArrayList<>(menuItemsListView.getSelectionModel().getSelectedItems());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String formattedValue = (orderDatePicker.getValue()).format(formatter);
                    handleBuatPesanan(restaurantComboBox.getValue(), formattedValue, list);
                } catch (Exception e) {
                    showAlert("Error", "Failed to Add Order",
                            "An error occurred while adding the order. Please try again later.", Alert.AlertType.ERROR);
                }
            }
        });

        HBox buttonHbox = new HBox(12);
        buttonHbox.setAlignment(Pos.CENTER_RIGHT);
        buttonHbox.setMaxWidth(450);
        buttonHbox.getChildren().addAll(cancelButton, saveButton);

        VBox rightVbox = new VBox(12);
        rightVbox.setAlignment(Pos.CENTER_LEFT);
        rightVbox.setStyle("-fx-background-color: #FFFFFF");
        rightVbox.setMinWidth(360);
        rightVbox.getChildren().addAll(menuItemsLabel, menuItemsListView, buttonHbox);

        HBox innerHbox = new HBox(12);
        innerHbox.setAlignment(Pos.CENTER);
        innerHbox.setStyle("-fx-background-color: #FFFFFF;");
        innerHbox.getChildren().addAll(leftVbox, rightVbox);

        VBox innerVbox = new VBox(12);
        innerVbox.setPadding(new Insets(36));
        innerVbox.setAlignment(Pos.CENTER_LEFT);
        innerVbox.setMaxWidth(720);
        innerVbox.setEffect(dropShadow);
        innerVbox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
        innerVbox.getChildren().addAll(addOrderLabel, innerHbox);

        Label logoLabel = new Label("🅓 DepeFood");
        logoLabel.setStyle("-fx-font-family: Verdana, sans-serif;" +
                "-fx-font-size: 24px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #00AA13;");

        Label userLabel = new Label("👤  " + user.getNama());
        userLabel.setStyle("-fx-font-family: Verdana, sans-serif;" +
                "-fx-font-size: 18px;");

        HBox headerHbox = new HBox(1080);
        headerHbox.setAlignment(Pos.CENTER);
        headerHbox.setMaxWidth(Double.MAX_VALUE);
        headerHbox.setPadding(new Insets(36, 36, 50, 36));
        headerHbox.setStyle("-fx-background-color: #F9FAFB;");
        headerHbox.getChildren().addAll(logoLabel, userLabel);

        VBox outerVbox = new VBox();
        outerVbox.setAlignment(Pos.TOP_CENTER);
        outerVbox.setStyle("-fx-background-color: #F9FAFB;");
        outerVbox.getChildren().addAll(headerHbox, innerVbox);

        return new Scene(outerVbox, 1490, 840);
    }

    private Scene createBillPrinter() {
        Label billPrinterLabel = new Label("Print Bill");
        billPrinterLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        Platform.runLater(() -> billPrinterLabel.requestFocus());

        Label orderIdLabel = new Label("Order ID");
        orderIdLabel.setStyle("-fx-font-size: 14px;");
        billPrinterInput.setPromptText("HOLY1802202468D8");
        billPrinterInput.setStyle("-fx-background-color: #F9FAFB;" +
                "-fx-font-size: 14px; " +
                "-fx-border-color: #D1D5DB;" +
                "-fx-border-radius: 12px;" +
                "-fx-padding: 9px;");

        Button cancelButton = new Button("❌  Cancel");
        cancelButton.setStyle("-fx-font-size: 14px; " +
                "-fx-cursor: hand;" +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-background-radius: 12px;" +
                "-fx-background-color: #EE2737;" +
                "-fx-padding: 10px 18px;");
        cancelButton.setOnAction(event -> {
            stage.setScene(scene);
            animation();
            billPrinterInput.clear();
        });

        Button printButton = new Button("📜  Print");
        printButton.setStyle("-fx-font-size: 14px; " +
                "-fx-cursor: hand;" +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-background-radius: 12px;" +
                "-fx-background-color: #00AA13;" +
                "-fx-padding: 10px 18px;");
        printButton.setOnAction(event -> {
            String orderId = billPrinterInput.getText();
            if (!orderId.isEmpty()) {
                if (user.isOrderBelongsToUser(orderId)) {
                    FadeTransition fadeTransition = new FadeTransition(Duration.millis(175), printBillLayout);
                    fadeTransition.setToValue(0);
                    fadeTransition.setCycleCount(1);
                    fadeTransition.play();
                    fadeTransition.setOnFinished(f -> {
                        stage.setScene(billPrinter.getScene());
                        billPrinter.printBill(orderId);
                    });
                } else {
                    showAlert("Invalid Input", "Order ID not found",
                            "The provided Order ID does not match any orders associated with your account.",
                            Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Invalid Input", "Empty Order ID", "Please provide a valid Order ID.", Alert.AlertType.ERROR);

            }
        });

        HBox buttonHbox = new HBox(12);
        buttonHbox.setAlignment(Pos.CENTER_RIGHT);
        buttonHbox.setMaxWidth(450);
        buttonHbox.getChildren().addAll(cancelButton, printButton);

        printBillLayout.setAlignment(Pos.CENTER_LEFT);
        printBillLayout.setPadding(new Insets(32));
        printBillLayout.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
        printBillLayout.setMaxWidth(450);
        printBillLayout.setEffect(dropShadow);
        printBillLayout.getChildren().addAll(billPrinterLabel, orderIdLabel, billPrinterInput, buttonHbox);

        Label logoLabel = new Label("🅓 DepeFood");
        logoLabel.setStyle("-fx-font-family: Verdana, sans-serif;" +
                "-fx-font-size: 24px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #00AA13;");

        Label userLabel = new Label("👤  " + user.getNama());
        userLabel.setStyle("-fx-font-family: Verdana, sans-serif;" +
                "-fx-font-size: 18px;");

        HBox headerHbox = new HBox();
        headerHbox.setAlignment(Pos.CENTER);
        headerHbox.setMaxWidth(Double.MAX_VALUE);
        headerHbox.setSpacing(1080);
        headerHbox.setPadding(new Insets(36, 36, 250, 36));
        headerHbox.setStyle("-fx-background-color: #F9FAFB;");
        headerHbox.getChildren().addAll(logoLabel, userLabel);

        VBox outerVbox = new VBox();
        outerVbox.setAlignment(Pos.TOP_CENTER);
        outerVbox.setStyle("-fx-background-color: #F9FAFB;");
        outerVbox.getChildren().addAll(headerHbox, printBillLayout);

        return new Scene(outerVbox, 1490, 840);
    }

    private Scene createBayarBillForm() {
        Label payBillLabel = new Label("Pay Bill");
        payBillLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        Platform.runLater(() -> payBillLabel.requestFocus());

        Label orderIdLabel = new Label("Order ID");
        orderIdLabel.setStyle("-fx-font-size: 14px;");
        payBillInput.setPromptText("HOLY1802202468D8");
        payBillInput.setStyle("-fx-background-color: #F9FAFB;" +
                "-fx-font-size: 14px; " +
                "-fx-border-color: #D1D5DB;" +
                "-fx-border-radius: 12px;" +
                "-fx-padding: 9px;");

        Label paymentLabel = new Label("Payment method");
        paymentLabel.setStyle("-fx-font-size: 14px;");
        paymentLabel.setPadding(new Insets(10, 0, 0, 0));

        paymentComboBox.setPromptText("Select payment method");
        paymentComboBox.setStyle("-fx-background-color: #F9FAFB;" +
                "-fx-cursor: hand;" +
                "-fx-font-size: 14px; " +
                "-fx-border-color: #D1D5DB;" +
                "-fx-border-radius: 12px;" +
                "-fx-padding: 9px;");

        Button cancelButton = new Button("❌  Cancel");
        cancelButton.setStyle("-fx-font-size: 14px; " +
                "-fx-cursor: hand;" +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-background-radius: 12px;" +
                "-fx-background-color: #EE2737;" +
                "-fx-padding: 10px 18px;");
        cancelButton.setOnAction(event -> {
            stage.setScene(scene);
            animation();
            payBillInput.clear();
        });

        Button payButton = new Button("💳  Pay");
        payButton.setStyle("-fx-font-size: 14px; " +
                "-fx-cursor: hand;" +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-background-radius: 12px;" +
                "-fx-background-color: #00AA13;" +
                "-fx-padding: 10px 18px;");
        payButton.setOnAction(event -> {
            String orderId = payBillInput.getText();
            if (!orderId.isEmpty() && paymentComboBox.getValue() != null) {
                if (user.isOrderBelongsToUser(orderId)) {
                    int pilihanPembayaran = paymentComboBox.getValue().equals("Credit Card") ? 0 : 1;
                    handleBayarBill(orderId, pilihanPembayaran);
                } else {
                    showAlert("Invalid Input", "Order ID not found",
                            "The provided Order ID does not match any orders associated with your account.",
                            Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Incomplete Information", "Please Complete the Form",
                        "Please ensure that you have entered a valid Order ID and selected a payment method.",
                        Alert.AlertType.ERROR);
            }
        });

        HBox buttonHbox = new HBox(12);
        buttonHbox.setAlignment(Pos.CENTER_RIGHT);
        buttonHbox.setMaxWidth(450);
        buttonHbox.getChildren().addAll(cancelButton, payButton);

        VBox innerVbox = new VBox(12);
        innerVbox.setAlignment(Pos.CENTER_LEFT);
        innerVbox.setPadding(new Insets(32));
        innerVbox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
        innerVbox.setMaxWidth(450);
        innerVbox.setEffect(dropShadow);
        innerVbox.getChildren().addAll(payBillLabel, orderIdLabel, payBillInput, paymentLabel, paymentComboBox,
                buttonHbox);

        Label logoLabel = new Label("🅓 DepeFood");
        logoLabel.setStyle("-fx-font-family: Verdana, sans-serif;" +
                "-fx-font-size: 24px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #00AA13;");

        Label userLabel = new Label("👤  " + user.getNama());
        userLabel.setStyle("-fx-font-family: Verdana, sans-serif;" +
                "-fx-font-size: 18px;");

        HBox headerHbox = new HBox();
        headerHbox.setAlignment(Pos.CENTER);
        headerHbox.setMaxWidth(Double.MAX_VALUE);
        headerHbox.setSpacing(1080);
        headerHbox.setPadding(new Insets(36, 36, 250, 36));
        headerHbox.setStyle("-fx-background-color: #F9FAFB;");
        headerHbox.getChildren().addAll(logoLabel, userLabel);

        VBox outerVbox = new VBox();
        outerVbox.setAlignment(Pos.TOP_CENTER);
        outerVbox.setStyle("-fx-background-color: #F9FAFB;");
        outerVbox.getChildren().addAll(headerHbox, innerVbox);

        return new Scene(outerVbox, 1490, 840);
    }

    private Scene createCekSaldoScene() {
        Label cekSaldoLabel = new Label("Balance");
        cekSaldoLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        Platform.runLater(() -> cekSaldoLabel.requestFocus());

        Label depepayLabel = new Label("👛 depepay");
        depepayLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < user.getNomorTelepon().length(); i++) {
            sb.append(user.getNomorTelepon().charAt(i));
            if ((i + 1) % 4 == 0 && i != user.getNomorTelepon().length() - 1) {
                sb.append("  ");
            }
        }

        Label numberLabel = new Label(sb.toString());
        numberLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF");
        balanceLabel.setStyle("-fx-font-size: 27px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        Label namaLabel = new Label(user.getNama());
        namaLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF");

        VBox balanceVbox = new VBox(12);
        balanceVbox.setAlignment(Pos.CENTER_LEFT);
        balanceVbox.setPadding(new Insets(24));
        balanceVbox.setStyle("-fx-background-color: #29ABE2; -fx-background-radius: 10px;");
        balanceVbox.setMaxWidth(400);
        balanceVbox.setEffect(dropShadow);
        balanceVbox.getChildren().addAll(depepayLabel, numberLabel, balanceLabel, namaLabel);

        Button cancelButton = new Button("⬅  Return");
        cancelButton.setStyle("-fx-font-size: 14px; " +
                "-fx-cursor: hand;" +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-background-radius: 12px;" +
                "-fx-background-color: #EE2737;" +
                "-fx-padding: 10px 18px;");
        cancelButton.setOnAction(event -> {
            stage.setScene(scene);
            animation();
        });

        VBox innerVbox = new VBox(12);
        innerVbox.setAlignment(Pos.CENTER_LEFT);
        innerVbox.setPadding(new Insets(32));
        innerVbox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
        innerVbox.setMaxWidth(450);
        innerVbox.setEffect(dropShadow);
        innerVbox.getChildren().addAll(cekSaldoLabel, balanceVbox, cancelButton);

        Label logoLabel = new Label("🅓 DepeFood");
        logoLabel.setStyle("-fx-font-family: Verdana, sans-serif;" +
                "-fx-font-size: 24px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #00AA13;");

        Label userLabel = new Label("👤  " + user.getNama());
        userLabel.setStyle("-fx-font-family: Verdana, sans-serif;" +
                "-fx-font-size: 18px;");

        HBox headerHbox = new HBox();
        headerHbox.setAlignment(Pos.CENTER);
        headerHbox.setMaxWidth(Double.MAX_VALUE);
        headerHbox.setSpacing(1080);
        headerHbox.setPadding(new Insets(36, 36, 250, 36));
        headerHbox.setStyle("-fx-background-color: #F9FAFB;");
        headerHbox.getChildren().addAll(logoLabel, userLabel);

        VBox outerVbox = new VBox();
        outerVbox.setAlignment(Pos.TOP_CENTER);
        outerVbox.setStyle("-fx-background-color: #F9FAFB;");
        outerVbox.getChildren().addAll(headerHbox, innerVbox);

        return new Scene(outerVbox, 1490, 840);
    }

    private void handleBuatPesanan(String namaRestoran, String tanggalPemesanan, List<String> menuItems) {
        try {
            // Mencari restoran berdasarkan nama
            Restaurant restaurant = DepeFood.findRestaurant(namaRestoran);
            String OrderID = OrderGenerator.generateOrderID(namaRestoran, tanggalPemesanan, user.getNomorTelepon());
            // Membuat objek pesanan dengan informasi yang diberikan
            Order order = new Order(
                    OrderID,
                    tanggalPemesanan, OrderGenerator.calculateDeliveryCost(user.getLokasi()),
                    restaurant, DepeFood.getMenuRequest(restaurant, menuItems));
            // Menambahkan pesanan ke riwayat pesanan pengguna
            user.addOrderHistory(order);
            // Menampilkan pemberitahuan sukses dengan ID pesanan yang baru saja dibuat
            showAlert("Success", "Order Placed Successfully",
                    "Your order with ID " + OrderID + " has been placed successfully.", Alert.AlertType.INFORMATION);
            // Mengosongkan pilihan restoran, tanggal pemesanan, dan daftar menu
            restaurantComboBox.valueProperty().set(null);
            orderDatePicker.setValue(null);
            menuItemsListView.getItems().clear();
        } catch (Exception e) {
            // Menampilkan pemberitahuan jika terjadi kesalahan saat menambahkan pesanan
            showAlert("Error", "Failed to Add Order",
                    "An error occurred while adding the order. Please try again later.", Alert.AlertType.ERROR);
        }
    }

    private void handleBayarBill(String orderID, int pilihanPembayaran) {
        try {
            // Mendapatkan pesanan berdasarkan ID
            Order order = DepeFood.getOrderOrNull(orderID);
            // Memeriksa apakah pesanan sudah selesai atau belum
            if (!order.getOrderFinished()) {
                // Memeriksa jenis pembayaran yang dipilih pengguna
                DepeFoodPaymentSystem paymentSystem = user.getPaymentSystem();
                boolean isCreditCard = paymentSystem instanceof CreditCardPayment;
                // Menampilkan pemberitahuan jika jenis pembayaran tidak sesuai
                if ((isCreditCard && pilihanPembayaran == 1) ||
                        (!isCreditCard && pilihanPembayaran == 0)) {
                    showAlert("Error", "Failed to Pay Bill",
                            "Payment failed. Please check your payment method and try again.", Alert.AlertType.ERROR);
                } else {
                    // Memproses pembayaran dan mengurangi saldo pengguna
                    long amountToPay = paymentSystem.processPayment(user.getSaldo(), (long) order.getTotalHarga());
                    long saldoLeft = user.getSaldo() - amountToPay;
                    user.setSaldo(saldoLeft);
                    // Memperbarui status pesanan
                    DepeFood.handleUpdateStatusPesanan(order);
                    // Menampilkan pemberitahuan pembayaran berhasil
                    showAlert("Success", "Payment Successful",
                            "Payment was successful. Thank you for your transaction.", Alert.AlertType.INFORMATION);
                }
            } else {
                // Menampilkan pemberitahuan jika pesanan sudah selesai
                showAlert("Error", "Order Finished",
                        "This order has already been finished. Payment cannot be processed.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            // Menampilkan pemberitahuan jika terjadi kesalahan saat pembayaran
            showAlert("Error", "Failed to Pay Bill", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static void animation() {
        // Animasi fading untuk layout menu
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(175), menuLayout);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }

    public static void printBillAnimation() {
        // Animasi fading untuk layout cetak tagihan
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(175), printBillLayout);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    public static Scene getPrintBillScene() {
        return printBillScene;
    }
}
