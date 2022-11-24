package xyz.torski.angs.order.infra.realization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.torski.angs.order.domain.OrderService;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderRealizationListener {

    private final OrderService orderService;

    @EventListener
    public void onOrderRealizedEvent(OrderRealizedEvent event) {
        log.info("Received OrderRealizedEvent {}", event);
        orderService.processOrderRealizationEvent(event.cartId());
    }

}
