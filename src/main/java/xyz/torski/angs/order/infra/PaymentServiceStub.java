package xyz.torski.angs.order.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.torski.angs.order.domain.OrderPaymentCommand;
import xyz.torski.angs.order.domain.PaymentService;

@Component
@Slf4j
public class PaymentServiceStub implements PaymentService {
    @Override
    public void requestPayment(OrderPaymentCommand command) {
        log.info("OrderPaymentCommand received {}", command);
    }
}
