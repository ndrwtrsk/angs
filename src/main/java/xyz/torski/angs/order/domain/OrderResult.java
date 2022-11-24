package xyz.torski.angs.order.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OrderResult{
     private boolean success;
     private String message;
     private Order madeOrder;

     public static OrderResult failedOrder(String message) {
         return new OrderResult(false, message, null);
     }

     public static OrderResult madeOrder(Order order) {
         return new OrderResult(true, null, order);
     }
}
