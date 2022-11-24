package xyz.torski.angs.order.domain;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(String id);

}
