package xyz.torski.angs.order.domain;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class OrderService {

    private final CartRepository repo;

    public Cart addToCart(AddToCartRequest request) {
        var cart = retrieveCart(request);
        cart.addToCart(request);
        repo.save(cart);
        return cart;
    }

    private Cart retrieveCart(AddToCartRequest request) {
        if (request.noCartIdSpecified()) {
            return new Cart();
        } else {
            return repo.findById(request.getCartId())
                    .orElse(new Cart());
        }
    }

    public Optional<Cart> findCart(String cartId){
        return repo.findById(cartId);
    }
}
