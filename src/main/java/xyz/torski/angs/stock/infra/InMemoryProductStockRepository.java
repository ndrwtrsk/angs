package xyz.torski.angs.stock.infra;

import org.springframework.stereotype.Repository;
import xyz.torski.angs.stock.domain.ProductStock;
import xyz.torski.angs.stock.domain.ProductStockRepository;

import java.util.*;

@Repository
public class InMemoryProductStockRepository implements ProductStockRepository {

    private final Map<String, ProductStock> repo = new HashMap<>();

    @Override
    public ProductStock add(ProductStock productStock) {
        repo.put(productStock.getId(), productStock);
        return productStock;
    }

    @Override
    public Optional<ProductStock> findById(String id) {
        return Optional.ofNullable(repo.get(id));
    }

    @Override
    public List<ProductStock> findAll() {
        return repo.values().stream().toList();
    }

    @Override
    public ProductStock update(ProductStock updatedProductStock) {
        repo.put(updatedProductStock.getId(), updatedProductStock);
        return updatedProductStock;
    }

}
