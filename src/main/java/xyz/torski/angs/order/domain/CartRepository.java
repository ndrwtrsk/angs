package xyz.torski.angs.order.domain;

import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findById(String id);

    Cart save(Cart cart);
}
