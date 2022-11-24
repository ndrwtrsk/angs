package xyz.torski.angs.order.web;

import xyz.torski.angs.order.domain.Order;

public record OrderView(String id, String cartId, String userId, int totalPrice) {
    public static OrderView fromDomain(Order order) {
        return new OrderView(order.getId(), order.getCartId(), order.getUserId(), order.getTotalPrice());
    }
}
