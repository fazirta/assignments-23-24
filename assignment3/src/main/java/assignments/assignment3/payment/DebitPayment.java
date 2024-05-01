package assignments.assignment3.payment;

import assignments.assignment3.core.Order;
import assignments.assignment3.core.User;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class DebitPayment implements DepeFoodPaymentSystem {
    // Batas minimum total harga pesanan untuk menggunakan metode pembayaran ini
    private final double MINIMUM_TOTAL_PRICE = 50000.0;

    @Override
    public boolean processPayment(User user, Order order, long amount) {
        // Memeriksa apakah jumlah pesanan memenuhi batas minimum
        if (amount < MINIMUM_TOTAL_PRICE) {
            System.out.println("Jumlah pesanan < 50000 mohon menggunakan metode pembayaran yang lain");
            return false; // Mengembalikan false jika jumlah pesanan kurang dari batas minimum
        }
        // Mengurangi saldo pengguna dan menambah saldo restoran
        user.setSaldo(user.getSaldo() - amount);
        order.getRestaurant().setSaldo(order.getRestaurant().getSaldo() + amount);
        order.setOrderFinished(true); // Menandai pesanan sebagai selesai

        // Memformat jumlah pembayaran dan menampilkannya
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('\u0000');
        decimalFormat.setDecimalFormatSymbols(symbols);
        System.out.printf("Berhasil Membayar Bill sebesar Rp %s%n",
                decimalFormat.format(amount));
        return true; // Mengembalikan true karena pembayaran berhasil
    }
}
