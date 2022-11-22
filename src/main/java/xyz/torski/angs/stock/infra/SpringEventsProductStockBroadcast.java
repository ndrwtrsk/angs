package xyz.torski.angs.stock.infra;

import org.springframework.stereotype.Service;
import xyz.torski.angs.stock.domain.ProductStockBroadcast;
import xyz.torski.angs.stock.domain.ProductStockPublishedEvent;

@Service
class SpringEventsProductStockBroadcast implements ProductStockBroadcast {

    @Override
    public void broadcastEvent(ProductStockPublishedEvent event) {

    }
}
