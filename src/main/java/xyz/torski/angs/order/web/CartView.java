package xyz.torski.angs.order.web;

import lombok.Data;
import xyz.torski.angs.order.domain.Cart;

import java.util.List;

@Data
public class CartView {
    private String cartId;
    private List<String> productIds;

    public CartView(Cart cart) {
        this.cartId = cart.getId();
        this.productIds = cart.getProducts();
    }
}
