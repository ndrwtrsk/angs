package xyz.torski.angs.order.infra.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.torski.angs.order.domain.OrderService;

@Component
@RequiredArgsConstructor
@Slf4j
class OrderPaymentSuccessfulListener {

    private final OrderService orderService;

    @EventListener
    public void onEvent(OrderPaymentEvent event) {
        log.info("Received OrderPaymentEvent {}", event);
        orderService.processOrderPaymentResult(event.toDomain());
    }

}
