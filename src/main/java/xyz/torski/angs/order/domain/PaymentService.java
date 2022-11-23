package xyz.torski.angs.order.domain;

public interface PaymentService {

    void requestPayment(OrderPaymentCommand command);

}
