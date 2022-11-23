package xyz.torski.angs.order.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString(exclude = "paymentDetails")
public class OrderPaymentCommand{
    private final String cartId;
    private final String userId;
    private final String orderId;
    private final String paymentDetails;
    private final int chargeAmount;
}
