package xyz.torski.angs.order.web;

import xyz.torski.angs.order.domain.request.FinalizeOrderRequest;

public record FinalizeOrderWebRequest(String userId, String cartId, String paymentDetails) {
    public FinalizeOrderRequest toDomain() {
        return new FinalizeOrderRequest(userId, cartId, paymentDetails);
    }
}
