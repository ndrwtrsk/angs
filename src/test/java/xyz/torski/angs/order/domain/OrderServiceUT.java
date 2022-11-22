package xyz.torski.angs.order.domain;

import org.junit.jupiter.api.Test;
import xyz.torski.angs.order.infra.InMemoryCartRepository;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceUT {

    private CartRepository cartRepository = new InMemoryCartRepository();
    private OrderService orderService = new OrderService(cartRepository);

    @Test
    public void shouldCreateACartWhenPuttingFirstElement() {
        //given
        var request = addToCartRequestWithNoCartIdSpecified();

        //when
        var cart = orderService.addToCart(request);

        //then
        var retrievedCart = orderService.findCart(cart.getId()).get();
        assertNotNull(retrievedCart.getId());
    }

    private AddToCartRequest addToCartRequestWithNoCartIdSpecified() {
        return new AddToCartRequest("productStockId");
    }



}