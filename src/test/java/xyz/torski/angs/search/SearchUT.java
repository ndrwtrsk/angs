package xyz.torski.angs.search;

import org.junit.jupiter.api.Test;
import xyz.torski.angs.search.infra.ProductStockIndex;
import xyz.torski.angs.search.infra.SearchPublishedStockListener;
import xyz.torski.angs.stock.domain.ProductStockPublishedEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchUT {

    private final ProductStockIndex index = new ProductStockIndex();
    private final SearchPublishedStockListener listener = new SearchPublishedStockListener(index);

    @Test
    public void acceptingStockPublishedEventShouldIndexTheStock() {
        //given
        var event = new ProductStockPublishedEvent("id", "name");

        //when
        listener.onPublishedStock(event);

        //then
        var indexedStock = index.findAll().get(0);
        assertEquals("id", indexedStock.getId());
        assertEquals("name", indexedStock.getName());
    }
}
