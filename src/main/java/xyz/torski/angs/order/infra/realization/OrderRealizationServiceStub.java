package xyz.torski.angs.order.infra.realization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import xyz.torski.angs.order.domain.OrderRealizationCommand;
import xyz.torski.angs.order.domain.OrderRealizationService;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderRealizationServiceStub implements OrderRealizationService {

    private final ApplicationEventPublisher publisher;

    @Override
    public void requestRealization(OrderRealizationCommand command) {
        var orderRealizedEvent = new OrderRealizedEvent(command.getCartId(), command.getOrderId());
        publisher.publishEvent(orderRealizedEvent);
        log.info("Received OrderRealizationCommand {}", command);
    }
}
