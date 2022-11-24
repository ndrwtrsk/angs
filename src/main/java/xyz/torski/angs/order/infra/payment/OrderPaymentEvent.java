package xyz.torski.angs.order.infra.payment;

import xyz.torski.angs.order.domain.payment.OrderPaymentResult;

public record OrderPaymentEvent(String cartId, String orderId, String userId, boolean success, String error) {
    public OrderPaymentResult toDomain() {
        return new OrderPaymentResult(cartId, orderId, userId, success, error);
    }

    @Override
    public String toString() {
        return "OrderPaymentEvent{" +
                "cartId='" + cartId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", success=" + success +
                ", error='" + error + '\'' +
                '}';
    }
}
