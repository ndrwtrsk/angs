package xyz.torski.angs.order.infra;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.torski.angs.order.domain.OrderProductStock;
import xyz.torski.angs.stock.domain.ProductStockPublishedEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderPublishedStockListener { // todo think about renaming?

    private final InMemoryOrderProductStockRepository stockRepository;

    @EventListener
    public void onPublishedStock(ProductStockPublishedEvent event) {
        log.info("Accepting ProductStockPublishedEvent");
        stockRepository.add(new OrderProductStock(event.id(), event.name()));
    }

}
