package xyz.torski.angs.stock.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductStockService {

    private final ProductStockRepository repository;
    private final ProductStockBroadcast broadcast;

    public ProductStock create(String name) {
        return repository.add(new ProductStock(name));
    }

    public List<ProductStock> list() {
        return repository.findAll();
    }

    public Optional<ProductStock> update(ProductStockUpdateRequest request) {
        return repository.findById(request.id)
                .map(productStock -> productStock.updateLatestDraft(request.name))
                .map(repository::update);
    }

    @RequiredArgsConstructor
    @Getter
    public static class ProductStockUpdateRequest {

        private final String id;
        private final String name;
    }

    public ProductStock publish(ProductStockPublishRequest request) {
        var stock = repository.findById(request.id).orElseThrow(); //fixme
        var event = stock.publish();
        broadcast.broadcastEvent(event);
        return stock;
    }

    public record ProductStockPublishRequest(String id) {
    }

}
