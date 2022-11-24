package xyz.torski.angs.order.infra.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import xyz.torski.angs.order.domain.payment.OrderPaymentCommand;
import xyz.torski.angs.order.domain.payment.PaymentService;

@Component
@RequiredArgsConstructor
@Slf4j
class PaymentServiceStub implements PaymentService {

    private final ApplicationEventPublisher publisher;

    @Override
    public void requestPayment(OrderPaymentCommand command) {
        log.info("OrderPaymentCommand received {}", command);
        publisher.publishEvent(new OrderPaymentEvent(command.cartId(), command.orderId(), command.userId(), true, null));
    }
}
