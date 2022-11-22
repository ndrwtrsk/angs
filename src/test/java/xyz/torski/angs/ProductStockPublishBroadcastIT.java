package xyz.torski.angs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.torski.angs.order.infra.InMemoryOrderProductStockRepository;
import xyz.torski.angs.search.infra.ProductStockIndex;
import xyz.torski.angs.stock.domain.ProductStockBroadcast;
import xyz.torski.angs.stock.domain.ProductStockPublishedEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductStockPublishBroadcastIT {

    @Autowired
    private ProductStockBroadcast broadcast;

    @Autowired
    private InMemoryOrderProductStockRepository orderProductStockRepository;

    @Autowired
    private ProductStockIndex productStockIndex;

    @Test
    public void shouldUpdateStateOfDomainsLocalCopyOfProductStock() {
        //when
        broadcast.broadcastEvent(new ProductStockPublishedEvent("id", "name"));

        //then
        var orderProductStock = orderProductStockRepository.findById("id").get();
        assertEquals("name", orderProductStock.getName());

        //and
        var indexedProductStock = productStockIndex.findAll().get(0);
        assertEquals("name", indexedProductStock.getName());
    }
}
