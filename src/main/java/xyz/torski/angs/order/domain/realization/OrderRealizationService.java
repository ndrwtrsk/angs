package xyz.torski.angs.order.domain.realization;

public interface OrderRealizationService {
    void requestRealization(OrderRealizationCommand command);
}
