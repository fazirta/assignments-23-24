package assignments.assignment3.payment;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import assignments.assignment3.core.Order;
import assignments.assignment3.core.User;

public class CreditCardPayment implements DepeFoodPaymentSystem {
    // Persentase biaya transaksi untuk pembayaran dengan kartu kredit
    private final double TRANSACTION_FEE_PERCENTAGE = 0.02;

    @Override
    public boolean processPayment(User user, Order order, long amount) {
        // Memeriksa apakah saldo pengguna mencukupi
        if (user.getSaldo() < amount + countTransactionFee(amount)) {
            System.out.println("Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain");
            return false; // Mengembalikan false jika saldo tidak mencukupi
        }
        long transactionFee = countTransactionFee(amount); // Menghitung biaya transaksi
        long totalAmount = amount + transactionFee; // Menentukan total jumlah pembayaran
        // Mengurangi saldo pengguna dan menambah saldo restoran
        user.setSaldo(user.getSaldo() - totalAmount);
        order.getRestaurant().setSaldo(order.getRestaurant().getSaldo() + amount);
        order.setOrderFinished(true); // Menandai pesanan sebagai selesai
        // Memformat jumlah pembayaran dan biaya transaksi, lalu menampilkannya
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('\u0000');
        decimalFormat.setDecimalFormatSymbols(symbols);
        System.out.printf("Berhasil Membayar Bill sebesar Rp %s dengan biaya transaksi sebesar Rp %s%n",
                decimalFormat.format(amount), decimalFormat.format(transactionFee));
        return true; // Mengembalikan true karena pembayaran berhasil
    }

    // Method untuk menghitung biaya transaksi
    private long countTransactionFee(long amount) {
        double transactionFee = TRANSACTION_FEE_PERCENTAGE * amount;
        return (long) transactionFee;
    }
}
