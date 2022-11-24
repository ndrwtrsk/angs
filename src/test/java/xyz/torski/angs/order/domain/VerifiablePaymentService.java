package xyz.torski.angs.order.domain;

import lombok.Getter;
import xyz.torski.angs.order.domain.payment.OrderPaymentCommand;
import xyz.torski.angs.order.domain.payment.PaymentService;

public class VerifiablePaymentService implements PaymentService {

    @Getter
    private OrderPaymentCommand lastReceivedCommand;

    @Override
    public void requestPayment(OrderPaymentCommand command) {
        this.lastReceivedCommand = command;
    }

}
