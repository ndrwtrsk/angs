package xyz.torski.angs.stock.domain;

import java.util.List;
import java.util.Optional;

public interface ProductStockRepository {
    ProductStock add(ProductStock productStock);

    Optional<ProductStock> findById(String id);

    List<ProductStock> findAll();

    ProductStock update(ProductStock updateProductStock);
}
