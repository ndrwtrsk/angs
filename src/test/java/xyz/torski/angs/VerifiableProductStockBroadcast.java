package xyz.torski.angs;

import xyz.torski.angs.stock.domain.ProductStockBroadcast;
import xyz.torski.angs.stock.domain.ProductStockPublishedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

public class VerifiableProductStockBroadcast implements ProductStockBroadcast {

    private final List<ProductStockPublishedEvent> broadcastEvents = new ArrayList<>();

    @Override
    public void broadcastEvent(ProductStockPublishedEvent event) {
        broadcastEvents.add(event);
    }

    public Optional<ProductStockPublishedEvent> lastEvent() {
        if (broadcastEvents.size() == 0) {
            return empty();
        }

        return Optional.ofNullable(broadcastEvents.get(broadcastEvents.size() - 1));
    }
}
