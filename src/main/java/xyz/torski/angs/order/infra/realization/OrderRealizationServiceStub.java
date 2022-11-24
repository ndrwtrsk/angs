package xyz.torski.angs.order.infra.realization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import xyz.torski.angs.order.domain.realization.OrderRealizationCommand;
import xyz.torski.angs.order.domain.realization.OrderRealizationService;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderRealizationServiceStub implements OrderRealizationService {

    private final ApplicationEventPublisher publisher;

    @Override
    public void requestRealization(OrderRealizationCommand command) {
        var orderRealizedEvent = new OrderRealizedEvent(command.cartId(), command.orderId());
        publisher.publishEvent(orderRealizedEvent);
        log.info("Received OrderRealizationCommand {}", command);
    }
}
