package xyz.torski.angs.order.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.torski.angs.order.domain.payment.OrderPaymentCommand;
import xyz.torski.angs.order.domain.request.FinalizeOrderRequest;

import java.util.UUID;

import static xyz.torski.angs.order.domain.Order.OrderStatus.PAYMENT_FAILED;
import static xyz.torski.angs.order.domain.Order.OrderStatus.SUCCESFUL;

@Getter
@RequiredArgsConstructor
public class Order {

    private final String id = UUID.randomUUID().toString();
    private final String cartId;
    private final String userId;
    private final int totalPrice;

    private OrderStatus status = OrderStatus.PAYMENT_REQUESTED;

    private String error;

    // Not saving paymentDetails. This seems to be counterintuitive as there may be senstive data in such a structure.
    // To be verified.
    public OrderPaymentCommand prepareOrderPaymentCommand(FinalizeOrderRequest request) {
        return new OrderPaymentCommand(cartId, userId, id, request.paymentDetails(), totalPrice);
    }

    void successful() {
        this.status = SUCCESFUL;
    }

    void paymentFailed(String errorMessage) {
        this.status = PAYMENT_FAILED;
        this.error = errorMessage;
    }

    public enum OrderStatus {
        PAYMENT_REQUESTED, SUCCESFUL, PAYMENT_FAILED
    }
}
