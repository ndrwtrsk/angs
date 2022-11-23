package xyz.torski.angs.order.domain;

public record OrderPaymentResult(String cartId, String orderId, String userId, boolean success, String error) {
}
