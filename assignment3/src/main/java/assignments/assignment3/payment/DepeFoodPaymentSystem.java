package assignments.assignment3.payment;

import assignments.assignment3.core.Order;
import assignments.assignment3.core.User;

public interface DepeFoodPaymentSystem {

    public long processPayment(long saldo, long amount) throws Exception;
}