package xyz.torski.angs.order.domain;

import lombok.Getter;

public class VerifiablePaymentService implements PaymentService{

    @Getter
    private OrderPaymentCommand lastReceivedCommand;

    @Override
    public void requestPayment(OrderPaymentCommand command) {
        this.lastReceivedCommand = command;
    }

}
