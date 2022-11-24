package xyz.torski.angs.order.infra.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import xyz.torski.angs.order.domain.Order;
import xyz.torski.angs.order.domain.OrderPaymentCommand;
import xyz.torski.angs.order.domain.PaymentService;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceStub implements PaymentService {

    private final ApplicationEventPublisher publisher;

    @Override
    public void requestPayment(OrderPaymentCommand command) {
        log.info("OrderPaymentCommand received {}", command);
        publisher.publishEvent(new OrderPaymentEvent(command.getCartId(), command.getOrderId(), command.getUserId(), true, null));
    }
}
