package xyz.torski.angs.order.web.result;

import xyz.torski.angs.order.domain.Order;

public record OrderView(String id, String cartId, String userId, Order.OrderStatus status, int totalPrice) {
    public static OrderView fromDomain(Order order) {
        if (order == null) return null;
        return new OrderView(order.getId(), order.getCartId(), order.getUserId(), order.getStatus(), order.getTotalPrice());
    }
}
