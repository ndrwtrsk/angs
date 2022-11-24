package xyz.torski.angs.search.infra;

import xyz.torski.angs.stock.domain.ProductStockPublishedEvent;

public record IndexedProductStock(String id, String name) {

    static IndexedProductStock fromEvent(ProductStockPublishedEvent event) {
        return new IndexedProductStock(event.id(), event.name());
    }
}
