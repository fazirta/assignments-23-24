package assignments.assignment4.page;

import javafx.scene.Scene;
import javafx.scene.control.Alert;

public abstract class MemberMenu {
    // Atribut untuk menyimpan tampilan
    private Scene scene;

    // Metode abstrak untuk membuat tampilan dasar menu
    abstract protected Scene createBaseMenu();

    // Metode untuk menampilkan dialog pesan
    protected void showAlert(String title, String header, String content, Alert.AlertType c) {
        Alert alert = new Alert(c);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Metode untuk mendapatkan tampilan
    public Scene getScene() {
        return this.scene;
    }
}
