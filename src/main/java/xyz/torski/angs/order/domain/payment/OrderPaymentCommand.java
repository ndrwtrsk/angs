package xyz.torski.angs.order.domain.payment;

public record OrderPaymentCommand(String cartId, String userId, String orderId, String paymentDetails,
                                  int chargeAmount) {
    @Override
    public String toString() {
        return "OrderPaymentCommand{" +
                "cartId='" + cartId + '\'' +
                ", userId='" + userId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", chargeAmount=" + chargeAmount +
                '}';
    }
}
