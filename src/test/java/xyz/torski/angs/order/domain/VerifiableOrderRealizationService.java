package xyz.torski.angs.order.domain;

import lombok.Getter;
import xyz.torski.angs.order.domain.realization.OrderRealizationCommand;
import xyz.torski.angs.order.domain.realization.OrderRealizationService;

@Getter
public class VerifiableOrderRealizationService implements OrderRealizationService {

    private OrderRealizationCommand lastReceivedCommand;

    @Override
    public void requestRealization(OrderRealizationCommand command) {
        this.lastReceivedCommand = command;
    }
}
