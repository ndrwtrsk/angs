package xyz.torski.angs.order.domain.request;

/**
 * @param paymentDetails it's a string value for now, this will change depending on the payment platform/method we use
 */
public record FinalizeOrderRequest(String userId, String cartId, String paymentDetails) {

    public boolean isCartIdEmpty() {
        return cartId == null;
    }

    public boolean isUserIdEmpty() {
        return userId == null;
    }

    public boolean arePaymentDetailsEmpty() {
        return paymentDetails == null;
    }
}
