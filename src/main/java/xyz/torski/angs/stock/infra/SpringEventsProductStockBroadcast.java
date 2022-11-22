package xyz.torski.angs.stock.infra;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import xyz.torski.angs.stock.domain.ProductStockBroadcast;
import xyz.torski.angs.stock.domain.ProductStockPublishedEvent;

@Component
@RequiredArgsConstructor
@Slf4j
class SpringEventsProductStockBroadcast implements ProductStockBroadcast {

    private final ApplicationEventPublisher publisher;

    @Override
    public void broadcastEvent(ProductStockPublishedEvent event) {
        log.info("Broadcasting ProductStockPublishedEvent");
        publisher.publishEvent(event);
    }
}
