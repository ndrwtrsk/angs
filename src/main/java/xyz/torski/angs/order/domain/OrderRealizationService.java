package xyz.torski.angs.order.domain;

public interface OrderRealizationService {
    void requestRealization(OrderRealizationCommand command);
}
