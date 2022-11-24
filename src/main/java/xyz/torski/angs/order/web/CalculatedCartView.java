package xyz.torski.angs.order.web;

import xyz.torski.angs.order.domain.CalculatedCart;
import xyz.torski.angs.order.domain.Order;

import java.util.List;

public record CalculatedCartView(String cartId, Order.OrderStatus orderStatus, List<ProductStockView> products, int totalPrice) {

    public static CalculatedCartView fromCalculatedCart(CalculatedCart cart) {
        var products = ProductStockView.fromDomain(cart.getCartProducts());
        return new CalculatedCartView(cart.getId(), cart.getOrderStatus(), products, cart.totalCartPrice());
    }

}
