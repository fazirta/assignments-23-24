package assignments.assignment3.systemCLI;

import java.util.ArrayList;
import java.util.Scanner;

import assignments.assignment3.core.Restaurant;
import assignments.assignment3.core.User;

public abstract class UserSystemCLI {
    protected Scanner input = new Scanner(System.in);
    protected User userLoggedIn;

    // Method untuk menjalankan sistem CLI
    public void run(ArrayList<Restaurant> restoList, User userLoggedIn) {
        this.userLoggedIn = userLoggedIn;

        boolean isLoggedIn = true;
        while (isLoggedIn) {
            displayMenu(); // Menampilkan menu sistem
            int command = input.nextInt(); // Membaca perintah dari user
            input.nextLine(); // Membersihkan newline dari input
            isLoggedIn = handleMenu(command, restoList); // Menangani perintah dari user
        }
    }

    // Method abstrak untuk menampilkan menu sistem
    abstract void displayMenu();

    // Method abstrak untuk menangani perintah dari user
    abstract boolean handleMenu(int command, ArrayList<Restaurant> restoList);
}
