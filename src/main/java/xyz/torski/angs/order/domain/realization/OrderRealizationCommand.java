package xyz.torski.angs.order.domain.realization;

import java.util.List;

public record OrderRealizationCommand(String cartId, String orderId, String userId, List<String> productsToDeliver) {
    @Override
    public String toString() {
        return "OrderRealizationCommand{" +
                "cartId='" + cartId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", productsToDeliver=" + productsToDeliver +
                '}';
    }
}
