package xyz.torski.angs.stock.domain;

public interface ProductStockBroadcast {

    void broadcastEvent(ProductStockPublishedEvent event);
}
