package xyz.torski.angs.order.web.result;

import xyz.torski.angs.order.domain.OrderResult;

public record OrderResultView(boolean success, String message, OrderView order) {
    public static OrderResultView fromDomain(OrderResult orderResult) {
        var order = OrderView.fromDomain(orderResult.getMadeOrder());
        return new OrderResultView(orderResult.isSuccess(), orderResult.getMessage(), order);
    }
}
