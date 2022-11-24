package xyz.torski.angs.order.infra;

import org.springframework.stereotype.Component;
import xyz.torski.angs.order.domain.Product;
import xyz.torski.angs.order.domain.ProductRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryProductRepository implements ProductRepository {
    private final Map<String, Product> repo = new HashMap<>();

    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(repo.get(id));
    }

    public Product add(Product product) {
        repo.put(product.id(), product);
        return product;
    }
}
