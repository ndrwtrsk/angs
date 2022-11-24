package xyz.torski.angs.order.domain;

import lombok.Getter;
import xyz.torski.angs.order.domain.payment.OrderPaymentCommand;
import xyz.torski.angs.order.domain.payment.OrderPaymentResult;
import xyz.torski.angs.order.domain.realization.OrderRealizationCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Cart {

    @Getter
    private final String id = UUID.randomUUID().toString();

    @Getter
    private final List<String> products = new ArrayList<>();

    @Getter
    private Order order;

    public Cart addToCart(AddToCartRequest request) {
        products.add(request.getProductStockId());
        return this;
    }

    public int cartSize() {
        return products.size();
    }

    public OrderPaymentCommand prepareOrder(FinalizeOrderRequest request, OrderProductStockRepository orderProductStockRepository) {
        // what if it was already paid for?
        var calculatedCart = calculateCart(orderProductStockRepository);
        var order = new Order(request.cartId(), request.userId(), calculatedCart.totalCartPrice());
        this.order = order;
        return order.prepareOrderPaymentCommand(request);
    }

    public CalculatedCart calculateCart(OrderProductStockRepository repo) {
        return new CalculatedCart(this, repo);
    }

    public Optional<OrderRealizationCommand> processOrderPaymentResult(OrderPaymentResult result) {
        if (result.success()) {
            order.successful();
            var command = new OrderRealizationCommand(id, order.getId(), order.getUserId(), products);
            return Optional.of(command);
        } else {
            order.paymentFailed(result.error());
            return Optional.empty();
        }
    }

    public void orderRealized() {
        this.order.successful();
    }

    public Order.OrderStatus getStatus() {
        if (order == null) return null;
        return order.getStatus();
    }
}
