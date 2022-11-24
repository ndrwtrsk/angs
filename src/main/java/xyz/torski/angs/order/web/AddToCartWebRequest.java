package xyz.torski.angs.order.web;

import xyz.torski.angs.order.domain.AddToCartRequest;

public record AddToCartWebRequest(String cartId, String productStockId) {
    public AddToCartRequest toDomain() {
        return new AddToCartRequest(cartId, productStockId);
    }
}
