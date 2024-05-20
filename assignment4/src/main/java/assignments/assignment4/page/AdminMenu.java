package assignments.assignment4.page;

import java.text.NumberFormat;
import java.util.Locale;

import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.form.LoginForm;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminMenu extends MemberMenu {
        private Stage stage;
        private Scene scene;
        private User user;
        private Scene addRestaurantScene;
        private Scene addMenuScene;
        private Scene viewRestaurantsScene;
        private MainApp mainApp;
        private ListView<String> menuItemsListView = new ListView<>();
        private TextField addRestaurantInput = new TextField();;
        private TextField addMenuRestaurantNameInput = new TextField();;
        private TextField addMenuNameInput = new TextField();;
        private TextField addMenuPriceInput = new TextField();;
        private TextField searchRestaurantInput = new TextField();;
        private DropShadow dropShadow = new DropShadow();
        private static GridPane menuLayout = new GridPane();

        public AdminMenu(Stage stage, MainApp mainApp, User user) {
                this.stage = stage;
                this.mainApp = mainApp;
                this.user = user;
                this.scene = createBaseMenu();
                this.addRestaurantScene = createAddRestaurantForm();
                this.addMenuScene = createAddMenuForm();
                this.viewRestaurantsScene = createViewRestaurantsForm();

                dropShadow.setRadius(15);
                dropShadow.setOffsetX(2);
                dropShadow.setOffsetY(2);
                dropShadow.setColor(Color.rgb(225, 225, 225, 0.8));
        }

        @Override
        public Scene createBaseMenu() {
                Label addRestaurantTitleLabel = new Label("Add Restaurant");
                addRestaurantTitleLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

                Label addRestaurantDescriptionLabel = new Label(
                                "Easily expand your culinary options by adding new restaurants to explore and enjoy.");
                addRestaurantDescriptionLabel.setWrapText(true);
                addRestaurantDescriptionLabel.setMaxWidth(360);
                addRestaurantDescriptionLabel.setStyle("-fx-font-size: 16px;");

                Button addRestaurantButton = new Button("🍴  Add Restaurant");
                addRestaurantButton.setStyle("-fx-font-size: 14px; " +
                                "-fx-cursor: hand;" +
                                "-fx-font-weight: bold; " +
                                "-fx-text-fill: #FFFFFF;" +
                                "-fx-background-radius: 12px;" +
                                "-fx-background-color: #00AA13;" +
                                "-fx-padding: 10px 18px;");
                addRestaurantButton.setOnAction(e -> {
                        FadeTransition fadeTransition = new FadeTransition(Duration.millis(175), menuLayout);
                        fadeTransition.setToValue(0);
                        fadeTransition.setCycleCount(1);
                        fadeTransition.play();
                        fadeTransition.setOnFinished(f -> stage.setScene(addRestaurantScene));
                });

                VBox addRestaurantVbox = new VBox(12);
                addRestaurantVbox.setAlignment(Pos.CENTER_LEFT);
                addRestaurantVbox.setPadding(new Insets(24));
                addRestaurantVbox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
                addRestaurantVbox.setEffect(dropShadow);
                addRestaurantVbox.getChildren().addAll(addRestaurantTitleLabel, addRestaurantDescriptionLabel,
                                addRestaurantButton);

                Label addMenuTitleLabel = new Label("Add Restaurant Menu");
                addMenuTitleLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

                Label addMenuDescriptionLabel = new Label(
                                "Enhance your dining experience by adding new restaurant menus, featuring delicious dishes.");
                addMenuDescriptionLabel.setWrapText(true);
                addMenuDescriptionLabel.setMaxWidth(360);
                addMenuDescriptionLabel.setStyle("-fx-font-size: 16px;");

                Button addMenuButton = new Button("🍜  Add Menu");
                addMenuButton.setStyle("-fx-font-size: 14px; " +
                                "-fx-cursor: hand;" +
                                "-fx-font-weight: bold; " +
                                "-fx-text-fill: #FFFFFF;" +
                                "-fx-background-radius: 12px;" +
                                "-fx-background-color: #00AED6;" +
                                "-fx-padding: 10px 18px;");
                addMenuButton.setOnAction(e -> {
                        FadeTransition fadeTransition = new FadeTransition(Duration.millis(175), menuLayout);
                        fadeTransition.setToValue(0);
                        fadeTransition.setCycleCount(1);
                        fadeTransition.play();
                        fadeTransition.setOnFinished(f -> stage.setScene(addMenuScene));
                });

                VBox addMenuVbox = new VBox(12);
                addMenuVbox.setAlignment(Pos.CENTER_LEFT);
                addMenuVbox.setPadding(new Insets(24));
                addMenuVbox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
                addMenuVbox.setEffect(dropShadow);
                addMenuVbox.getChildren().addAll(addMenuTitleLabel, addMenuDescriptionLabel, addMenuButton);

                Label viewRestaurantsTitleLabel = new Label("View Restaurant Menu List");
                viewRestaurantsTitleLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

                Label viewRestaurantsDescriptionLabel = new Label(
                                "Discover a variety of delectable dishes by exploring our restaurant menu list.");
                viewRestaurantsDescriptionLabel.setWrapText(true);
                viewRestaurantsDescriptionLabel.setMaxWidth(360);
                viewRestaurantsDescriptionLabel.setStyle("-fx-font-size: 16px;");

                Button viewRestaurantsButton = new Button("🔍  View Restaurants");
                viewRestaurantsButton.setStyle("-fx-font-size: 14px; " +
                                "-fx-cursor: hand;" +
                                "-fx-font-weight: bold; " +
                                "-fx-text-fill: #FFFFFF;" +
                                "-fx-background-radius: 12px;" +
                                "-fx-background-color: #93328E;" +
                                "-fx-padding: 10px 18px;");
                viewRestaurantsButton.setOnAction(e -> {
                        FadeTransition fadeTransition = new FadeTransition(Duration.millis(175), menuLayout);
                        fadeTransition.setToValue(0);
                        fadeTransition.setCycleCount(1);
                        fadeTransition.play();
                        fadeTransition.setOnFinished(f -> stage.setScene(viewRestaurantsScene));
                });

                VBox viewRestaurantsVbox = new VBox(12);
                viewRestaurantsVbox.setAlignment(Pos.CENTER_LEFT);
                viewRestaurantsVbox.setPadding(new Insets(24));
                viewRestaurantsVbox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
                viewRestaurantsVbox.setEffect(dropShadow);
                viewRestaurantsVbox.getChildren().addAll(viewRestaurantsTitleLabel, viewRestaurantsDescriptionLabel,
                                viewRestaurantsButton);

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
                menuLayout.add(addRestaurantVbox, 0, 0);
                menuLayout.add(addMenuVbox, 1, 0);
                menuLayout.add(viewRestaurantsVbox, 0, 1);
                menuLayout.add(logOutVbox, 1, 1);

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
                headerHBox.setPadding(new Insets(36, 36, 150, 36));
                headerHBox.setStyle("-fx-background-color: #F9FAFB;");
                headerHBox.getChildren().addAll(logoLabel, userLabel);

                VBox outerVBox = new VBox();
                outerVBox.setAlignment(Pos.TOP_CENTER);
                outerVBox.setStyle("-fx-background-color: #F9FAFB;");
                outerVBox.getChildren().addAll(headerHBox, menuLayout);

                return new Scene(outerVBox, 1490, 840);
        }

        private Scene createAddRestaurantForm() {
                Label addRestaurantlabel = new Label("Add Restaurant");
                addRestaurantlabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
                Platform.runLater(() -> addRestaurantlabel.requestFocus());

                Label restaurantNamelabel = new Label("Restaurant name ");
                restaurantNamelabel.setStyle("-fx-font-size: 14px;");
                addRestaurantInput.setPromptText("Holycow!");
                addRestaurantInput.setStyle("-fx-background-color: #F9FAFB;" +
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
                        addRestaurantInput.clear();
                });

                Button saveButton = new Button("💾  Save");
                saveButton.setStyle("-fx-font-size: 14px; " +
                                "-fx-cursor: hand;" +
                                "-fx-font-weight: bold; " +
                                "-fx-text-fill: #FFFFFF;" +
                                "-fx-background-radius: 12px;" +
                                "-fx-background-color: #00AA13;" +
                                "-fx-padding: 10px 18px;");
                saveButton.setOnAction(event -> handleTambahRestoran(addRestaurantInput.getText()));

                HBox buttonHBox = new HBox(12);
                buttonHBox.setAlignment(Pos.CENTER_RIGHT);
                buttonHBox.setMaxWidth(450);
                buttonHBox.getChildren().addAll(cancelButton, saveButton);

                VBox innerVBox = new VBox(12);
                innerVBox.setAlignment(Pos.CENTER_LEFT);
                innerVBox.setPadding(new Insets(32));
                innerVBox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
                innerVBox.setMaxWidth(450);
                innerVBox.setEffect(dropShadow);
                innerVBox.getChildren().addAll(addRestaurantlabel, restaurantNamelabel, addRestaurantInput, buttonHBox);

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

                VBox outerVBox = new VBox();
                outerVBox.setAlignment(Pos.TOP_CENTER);
                outerVBox.setStyle("-fx-background-color: #F9FAFB;");
                outerVBox.getChildren().addAll(headerHBox, innerVBox);

                return new Scene(outerVBox, 1490, 840);
        }

        private Scene createAddMenuForm() {
                Label addMenuLabel = new Label("Add Restaurant Menu");
                addMenuLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
                Platform.runLater(() -> addMenuLabel.requestFocus());

                Label restaurantNameLabel = new Label("Restaurant name ");
                restaurantNameLabel.setStyle("-fx-font-size: 14px;");
                addMenuRestaurantNameInput.setPromptText("Holycow!");
                addMenuRestaurantNameInput.setStyle("-fx-background-color: #F9FAFB;" +
                                "-fx-font-size: 14px; " +
                                "-fx-border-color: #D1D5DB;" +
                                "-fx-border-radius: 12px;" +
                                "-fx-padding: 9px;");

                Label menuNameLabel = new Label("Menu item name ");
                menuNameLabel.setStyle("-fx-font-size: 14px;");
                addMenuNameInput.setPromptText("Tenderloin Steak");
                addMenuNameInput.setStyle("-fx-background-color: #F9FAFB;" +
                                "-fx-font-size: 14px; " +
                                "-fx-border-color: #D1D5DB;" +
                                "-fx-border-radius: 12px;" +
                                "-fx-padding: 9px;");

                Label priceLabel = new Label("Price ");
                priceLabel.setStyle("-fx-font-size: 14px;");
                addMenuPriceInput.setPromptText("100000");
                addMenuPriceInput.setStyle("-fx-background-color: #F9FAFB;" +
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
                        addMenuRestaurantNameInput.clear();
                        addMenuNameInput.clear();
                        addMenuPriceInput.clear();
                });

                Button saveButton = new Button("💾  Save");
                saveButton.setStyle("-fx-font-size: 14px; " +
                                "-fx-cursor: hand;" +
                                "-fx-font-weight: bold; " +
                                "-fx-text-fill: #FFFFFF;" +
                                "-fx-background-radius: 12px;" +
                                "-fx-background-color: #00AA13;" +
                                "-fx-padding: 10px 18px;");
                saveButton.setOnAction(event -> {
                        Restaurant restaurant = DepeFood.findRestaurant(addMenuRestaurantNameInput.getText());
                        Double price;
                        if (restaurant == null) {
                                showAlert("Invalid Input", "The restaurant you entered does not exist.",
                                                "Please make sure you have entered the correct restaurant name.",
                                                Alert.AlertType.ERROR);
                        } else {
                                try {
                                        price = Double.parseDouble(addMenuPriceInput.getText());
                                } catch (Exception e) {
                                        showAlert("Invalid Input", "Price must be a valid number.",
                                                        "Please enter a numeric value for the price.",
                                                        Alert.AlertType.ERROR);
                                        return;
                                }
                                handleTambahMenuRestoran(restaurant, addMenuNameInput.getText(), price);
                        }
                });

                HBox buttonHBox = new HBox(12);
                buttonHBox.setAlignment(Pos.CENTER_RIGHT);
                buttonHBox.setMaxWidth(450);
                buttonHBox.getChildren().addAll(cancelButton, saveButton);

                VBox innerVBox = new VBox(12);
                innerVBox.setAlignment(Pos.CENTER_LEFT);
                innerVBox.setPadding(new Insets(32));
                innerVBox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
                innerVBox.setMaxWidth(450);
                innerVBox.setEffect(dropShadow);
                innerVBox.getChildren().addAll(addMenuLabel, restaurantNameLabel, addMenuRestaurantNameInput,
                                menuNameLabel, addMenuNameInput, priceLabel, addMenuPriceInput, buttonHBox);

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
                headerHBox.setPadding(new Insets(36, 36, 180, 36));
                headerHBox.setStyle("-fx-background-color: #F9FAFB;");
                headerHBox.getChildren().addAll(logoLabel, userLabel);

                VBox outerVBox = new VBox();
                outerVBox.setAlignment(Pos.TOP_CENTER);
                outerVBox.setStyle("-fx-background-color: #F9FAFB;");
                outerVBox.getChildren().addAll(headerHBox, innerVBox);

                return new Scene(outerVBox, 1490, 840);
        }

        private Scene createViewRestaurantsForm() {
                Label viewRestaurantlabel = new Label("View Restaurant Menu List");
                viewRestaurantlabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
                viewRestaurantlabel.setMaxWidth(Double.MAX_VALUE);
                AnchorPane.setLeftAnchor(viewRestaurantlabel, 0.0);
                AnchorPane.setRightAnchor(viewRestaurantlabel, 0.0);
                Platform.runLater(() -> viewRestaurantlabel.requestFocus());

                Button searchButton = new Button("🔍");
                searchButton.setStyle("-fx-font-size: 20px; " +
                                "-fx-cursor: hand;" +
                                "-fx-font-weight: bold; " +
                                "-fx-text-fill: #FFFFFF;" +
                                "-fx-background-radius: 0px 12px 12px 0px;" +
                                "-fx-background-color: #00AA13;" +
                                "-fx-padding: 5px 18px;");
                searchButton.setOnAction(event -> {
                        Restaurant restaurant = DepeFood.findRestaurant(searchRestaurantInput.getText());
                        ObservableList<String> menuList = FXCollections.observableArrayList();

                        if (restaurant == null) {
                                showAlert("Invalid Input", "The restaurant you entered does not exist.",
                                                "Please make sure you have entered the correct restaurant name.",
                                                Alert.AlertType.ERROR);
                        } else {
                                Locale localeID = new Locale.Builder().setLanguage("id").setRegion("ID").build();
                                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(localeID);
                                for (Menu menu : restaurant.getMenu()) {
                                        double amount = menu.getHarga();
                                        String formattedAmount = currencyFormat.format(amount);
                                        menuList.add(menu.getNamaMakanan() + " - " + formattedAmount);
                                }
                                menuItemsListView.setItems(menuList);
                        }
                });

                HBox searchRestaurantHBox = new HBox(0);
                searchRestaurantHBox.setAlignment(Pos.CENTER);
                searchRestaurantHBox.setMaxWidth(560);
                searchRestaurantHBox.setPadding(new Insets(20, 0, 20, 0));
                searchRestaurantInput.setPromptText("Search restaurant");
                searchRestaurantInput.setStyle("-fx-background-color: #F9FAFB;" +
                                "-fx-font-size: 14px; " +
                                "-fx-border-color: #D1D5DB;" +
                                "-fx-border-radius: 12px 0px 0px 12px;" +
                                "-fx-padding: 9px;" +
                                "-fx-min-width: 280px;");
                searchRestaurantHBox.getChildren().addAll(searchRestaurantInput, searchButton);

                menuItemsListView.setMaxSize(400, 400);
                menuItemsListView.setMaxWidth(Double.MAX_VALUE);
                AnchorPane.setLeftAnchor(menuItemsListView, 0.0);
                AnchorPane.setRightAnchor(menuItemsListView, 0.0);
                menuItemsListView.setStyle("-fx-font-size: 15px;" +
                                "-fx-font-weight: bold;" +
                                "-fx-alignment: center;" +
                                "-fx-background-radius: 12px; " +
                                "-fx-padding: 10px;");

                Button cancelButton = new Button("⬅ Return");
                cancelButton.setStyle("-fx-font-size: 14px;" +
                                "-fx-cursor: hand;" +
                                "-fx-font-weight: bold;" +
                                "-fx-text-fill: #FFFFFF;" +
                                "-fx-background-radius: 12px;" +
                                "-fx-background-color: #EE2737;" +
                                "-fx-padding: 10px 18px;");
                cancelButton.setOnAction(event -> {
                        stage.setScene(scene);
                        animation();
                        searchRestaurantInput.clear();
                        menuItemsListView.getItems().clear();
                });

                VBox innerVBox = new VBox(12);
                innerVBox.setAlignment(Pos.CENTER_LEFT);
                innerVBox.setPadding(new Insets(32));
                innerVBox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
                innerVBox.setMaxWidth(560);
                innerVBox.setEffect(dropShadow);
                innerVBox.getChildren().addAll(viewRestaurantlabel, searchRestaurantHBox, menuItemsListView,
                                cancelButton);

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
                headerHBox.setPadding(new Insets(36, 36, 50, 36));
                headerHBox.setStyle("-fx-background-color: #F9FAFB;");
                headerHBox.getChildren().addAll(logoLabel, userLabel);

                VBox outerVBox = new VBox();
                outerVBox.setAlignment(Pos.TOP_CENTER);
                outerVBox.setStyle("-fx-background-color: #F9FAFB;");
                outerVBox.getChildren().addAll(headerHBox, innerVBox);

                return new Scene(outerVBox, 1490, 840);
        }

        private void handleTambahRestoran(String nama) {
                String validName = DepeFood.getValidRestaurantName(nama);
                if (!validName.equals(nama)) {
                        showAlert("Invalid Input", "Invalid Restaurant Name",
                                        "The restaurant name provided is not valid. Please enter a valid name.",
                                        Alert.AlertType.ERROR);
                } else {
                        DepeFood.handleTambahRestoran(nama);
                        showAlert("Success", "Restaurant Added Successfully",
                                        "The restaurant '" + nama + "' has been successfully added.",
                                        Alert.AlertType.INFORMATION);
                        addRestaurantInput.clear();
                }
        }

        private void handleTambahMenuRestoran(Restaurant restaurant, String itemName, double price) {
                if (itemName.length() > 0) {
                        DepeFood.handleTambahMenuRestoran(restaurant, itemName, price);
                        showAlert("Success", "Menu Added Successfully",
                                        "The menu item '" + itemName + "' with price Rp" + price + " has been added to "
                                                        +
                                                        restaurant.getNama() + " restaurant.",
                                        Alert.AlertType.INFORMATION);

                        addMenuRestaurantNameInput.clear();
                        addMenuNameInput.clear();
                        addMenuPriceInput.clear();
                } else {
                        showAlert("Invalid Input", "Invalid Menu Item", "Please enter a valid name for the menu item.",
                                        Alert.AlertType.ERROR);

                }
        }

        public static void animation() {
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(175), menuLayout);
                fadeTransition.setToValue(1);
                fadeTransition.setCycleCount(1);
                fadeTransition.play();
        }

        @Override
        public Scene getScene() {
                return scene;
        }
}
