package assignments.assignment4.components.form;

import assignments.assignment3.DepeFood;
import assignments.assignment3.User;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;

public class LoginForm {
    private Stage stage;
    private MainApp mainApp;
    private TextField nameInput = new TextField();
    private TextField phoneInput = new TextField();
    private static VBox outerVBox = new VBox();

    public LoginForm(Stage stage, MainApp mainApp) {
        this.stage = stage;
        this.mainApp = mainApp;
    }

    public Scene createLoginForm() {
        Label logoLabel = new Label("🅓 DepeFood");
        logoLabel.setStyle("-fx-font-family: Verdana, sans-serif;" +
                "-fx-font-size: 24px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #00AA13;");
        logoLabel.setMaxWidth(Double.MAX_VALUE);
        logoLabel.setAlignment(Pos.CENTER);
        logoLabel.setPadding(new Insets(0, 0, 25, 0));
        Platform.runLater(() -> logoLabel.requestFocus());

        Label infoLabel = new Label("Sign in to your account");
        infoLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Name");
        nameLabel.setStyle("-fx-font-size: 14px;");
        nameLabel.setPadding(new Insets(10, 0, 0, 0));
        nameInput.setPromptText("Fazil");
        nameInput.setStyle("-fx-background-color: #F9FAFB;" +
                "-fx-font-size: 14px; " +
                "-fx-border-color: #D1D5DB;" +
                "-fx-border-radius: 12px;" +
                "-fx-padding: 9px;");
        nameInput.setMaxWidth(Double.MAX_VALUE);

        Label phoneLabel = new Label("Phone Number");
        phoneLabel.setStyle("-fx-font-size: 14px;");
        phoneLabel.setPadding(new Insets(10, 0, 0, 0));
        phoneInput.setPromptText("080811236789");
        phoneInput.setStyle("-fx-background-color: #F9FAFB;" +
                "-fx-font-size: 14px; " +
                "-fx-border-color: #D1D5DB;" +
                "-fx-border-radius: 12px;" +
                "-fx-padding: 9px;");
        phoneInput.setMaxWidth(Double.MAX_VALUE);

        Label spaceLabel = new Label(" ");
        spaceLabel.setStyle("-fx-font-size: 1px;");

        Button loginButton = new Button("Sign in");
        loginButton.setStyle("-fx-font-size: 14px; " +
                "-fx-cursor: hand;" +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-background-radius: 12px;" +
                "-fx-background-color: #00AA13;" +
                "-fx-padding: 9px;");
        loginButton.setOnAction(event -> handleLogin());
        loginButton.setMaxWidth(Double.MAX_VALUE);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(15);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        dropShadow.setColor(Color.rgb(225, 225, 225, 0.8));

        VBox innerVBox = new VBox(12);
        innerVBox.setAlignment(Pos.CENTER_LEFT);
        innerVBox.setPadding(new Insets(32));
        innerVBox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
        innerVBox.setMaxWidth(450);
        innerVBox.setEffect(dropShadow);
        innerVBox.getChildren().addAll(infoLabel, nameLabel, nameInput, phoneLabel, phoneInput, spaceLabel,
                loginButton);

        outerVBox.setAlignment(Pos.CENTER);
        outerVBox.setPadding(new Insets(100));
        outerVBox.setStyle("-fx-background-color: #F9FAFB;");
        outerVBox.getChildren().addAll(logoLabel, innerVBox);

        return new Scene(outerVBox, 1490, 840);
    }

    private void handleLogin() {
        String name = nameInput.getText();
        String phone = phoneInput.getText();

        if (name.length() > 0 && phone.length() > 0) {
            User user = DepeFood.handleLogin(name, phone);
            if (user != null) {
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(175), outerVBox);
                fadeTransition.setToValue(0);
                fadeTransition.setCycleCount(1);
                fadeTransition.setOnFinished(event -> {
                    nameInput.clear();
                    phoneInput.clear();
                    if (user.getRole().equals("Admin")) {
                        mainApp.setUser(user);
                        if (mainApp.getScene(user.getNama()) == null) {
                            Scene adminScene = new AdminMenu(stage, mainApp, user).getScene();
                            mainApp.addScene(user.getNama(), adminScene);
                        }
                        mainApp.setScene(mainApp.getScene(user.getNama()));
                        AdminMenu.animation();
                    } else {
                        mainApp.setUser(user);
                        if (mainApp.getScene(user.getNama()) == null) {
                            Scene customerScene = new CustomerMenu(stage, mainApp, user).getScene();
                            mainApp.addScene(user.getNama(), customerScene);
                        }
                        mainApp.setScene(mainApp.getScene(user.getNama()));
                        CustomerMenu.animation();
                    }
                });
                fadeTransition.play();
            } else {
                showAlert("Invalid Credentials", "Invalid username or phone number.",
                        "Please enter valid credentials.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Invalid Input", "Name or phone number field is empty.",
                    "Please fill in both name and phone number fields.", Alert.AlertType.ERROR);
        }
    }

    protected void showAlert(String title, String header, String content, Alert.AlertType c) {
        Alert alert = new Alert(c);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void animation() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(175), outerVBox);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }

    public Scene getScene() {
        return this.createLoginForm();
    }

}
