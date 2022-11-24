package xyz.torski.angs.order.domain.payment;

public record OrderPaymentResult(String cartId, String orderId, String userId, boolean success, String error) {
}
