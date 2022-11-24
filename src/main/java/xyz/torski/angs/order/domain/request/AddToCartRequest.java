package xyz.torski.angs.order.domain.request;


import lombok.Getter;

public class AddToCartRequest {

    @Getter
    private String cartId;

    @Getter
    private final String productStockId;

    public AddToCartRequest(String productStockId) {
        this.productStockId = productStockId;
    }

    public AddToCartRequest(String cartId, String productStockId) {
        this.cartId = cartId;
        this.productStockId = productStockId;
    }

    public boolean noCartIdSpecified() {
        return cartId == null;
    }
}
