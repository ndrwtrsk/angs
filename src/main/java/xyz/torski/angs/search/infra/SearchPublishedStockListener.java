package xyz.torski.angs.search.infra;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.torski.angs.stock.domain.ProductStockPublishedEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchPublishedStockListener {

    private final ProductStockIndex productStockIndex;

    @EventListener
    public void onPublishedStock(ProductStockPublishedEvent event) {
        log.info("Accepting ProductStockPublishedEvent");
        productStockIndex.acceptAndIndexProductStock(new IndexableProductStock(event));
    }
}
