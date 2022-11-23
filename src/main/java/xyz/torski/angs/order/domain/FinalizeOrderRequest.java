package xyz.torski.angs.order.domain;

import lombok.Getter;

/**
 * @param paymentDetails it's a string value for now, this will change depending on the payment platform/method we use
 */
public record FinalizeOrderRequest(String userId, String cartId, String paymentDetails) {

    boolean isCartIdEmpty() {
        return cartId == null;
    }

    boolean isUserIdEmpty() {
        return userId == null;
    }

    boolean arePaymentDetailsEmpty() {
        return paymentDetails == null;
    }
}
