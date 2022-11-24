package xyz.torski.angs.order.web.request;

import xyz.torski.angs.order.domain.request.AddToCartRequest;

public record AddToCartWebRequest(String cartId, String productStockId) {
    public AddToCartRequest toDomain() {
        return new AddToCartRequest(cartId, productStockId);
    }
}
