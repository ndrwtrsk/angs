package xyz.torski.angs.search.infra;

import lombok.Getter;
import xyz.torski.angs.stock.domain.ProductStockPublishedEvent;

@Getter
public class IndexableProductStock {
    private final String id;
    private final String name;

    IndexableProductStock(ProductStockPublishedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
    }
}
