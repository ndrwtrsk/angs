package xyz.torski.angs.order.infra.realization;

public record OrderRealizedEvent(String cartId, String orderId) {
    @Override
    public String toString() {
        return "OrderRealizedEvent{" +
                "cartId='" + cartId + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
