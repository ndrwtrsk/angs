package xyz.torski.angs.order.domain;

import lombok.Getter;

@Getter
public class VerifiableOrderRealizationService implements OrderRealizationService {

    private OrderRealizationCommand lastReceivedCommand;

    @Override
    public void requestRealization(OrderRealizationCommand command) {
        this.lastReceivedCommand = command;
    }
}
