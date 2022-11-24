package xyz.torski.angs.order.domain.payment;

public interface PaymentService {
    void requestPayment(OrderPaymentCommand command);
}
