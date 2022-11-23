package xyz.torski.angs.order.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Order {

    private final String id = UUID.randomUUID().toString();
    private final String cartId;
    private final String userId;
    private final int totalPrice;

    // Not saving paymentDetails. This seems to be counterintuitive as there may be senstive data in such a structure.
    // To be verified.
    public OrderPaymentCommand prepareOrderPaymentCommand(FinalizeOrderRequest request) {
        return new OrderPaymentCommand(cartId, userId, id, request.paymentDetails(), totalPrice);
    }

    public enum OrderStatus {
        PAYMENT_REQUESTED
    }
}
