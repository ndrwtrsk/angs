package xyz.torski.angs.order.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.torski.angs.order.domain.OrderRealizationCommand;
import xyz.torski.angs.order.domain.OrderRealizationService;

@Component
@Slf4j
public class OrderRealizationServiceStub implements OrderRealizationService {
    @Override
    public void requestRealization(OrderRealizationCommand command) {
        log.info("Received OrderRealizationCommand {}", command);
    }
}
