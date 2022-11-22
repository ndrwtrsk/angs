package xyz.torski.angs.order.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cart {

    @Getter
    private final String id = UUID.randomUUID().toString();
    private final List<String> products = new ArrayList<>();

    public Cart addToCart(AddToCartRequest request) {
        products.add(request.getProductStockId());
        return this;
    }

    public int cartSize() {
        return products.size();
    }
}
