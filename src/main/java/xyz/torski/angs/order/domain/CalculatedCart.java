package xyz.torski.angs.order.domain;

import lombok.Getter;

import java.util.List;
import java.util.Optional;

public class CalculatedCart {

    private final Cart cart;

    @Getter
    private final List<OrderProductStock> cartProducts;

    public CalculatedCart(Cart cart, OrderProductStockRepository productStockRepository) {
        this.cart = cart;
        this.cartProducts = cart.getProducts().stream()
                .map(productStockRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public String getId() {
        return cart.getId();
    }

    public Order.OrderStatus getOrderStatus() {
        return cart.getStatus();
    }

    public int cartSize() {
        return cartProducts.size();
    }

    public int totalCartPrice() {
        return 0; //TODO!
    }
}
