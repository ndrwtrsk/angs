package xyz.torski.angs.order.infra;

import xyz.torski.angs.order.domain.Cart;
import xyz.torski.angs.order.domain.CartRepository;

import java.util.HashMap;
import java.util.Optional;

public class InMemoryCartRepository implements CartRepository {

    private final HashMap<String, Cart> repo = new HashMap<>();

    @Override
    public Optional<Cart> findById(String id) {
        return Optional.ofNullable(repo.get(id));
    }

    @Override
    public Cart save(Cart cart) {
        repo.put(cart.getId(), cart);
        return cart;
    }
}
