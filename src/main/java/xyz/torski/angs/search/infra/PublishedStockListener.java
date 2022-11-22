package xyz.torski.angs.search.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.torski.angs.stock.domain.ProductStockPublishedEvent;

@Component
@RequiredArgsConstructor
public class PublishedStockListener {

    private final ProductStockIndex productStockIndex;

    @EventListener
    public void onPublishedStock(ProductStockPublishedEvent event) {
        productStockIndex.acceptAndIndexProductStock(new IndexableProductStock(event));
    }
}
