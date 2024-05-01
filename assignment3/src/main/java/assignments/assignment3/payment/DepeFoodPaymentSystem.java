package assignments.assignment3.payment;

import assignments.assignment3.core.Order;
import assignments.assignment3.core.User;

public interface DepeFoodPaymentSystem {
    // Method untuk memproses pembayaran
    public boolean processPayment(User user, Order order, long amount);
}
