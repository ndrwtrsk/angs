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

    @Test
    public void shouldAddItemsToCart() {
        //given
        var cartId = addThreeProductsToCart();

        //when
        var cart = orderService.findCart(cartId).get();

        //then
        assertEquals(3, cart.cartSize());
    }

    private String addThreeProductsToCart() {
        var firstRequest = addToCartRequestWithNoCartIdSpecified();
        var cartId = orderService.addToCart(firstRequest).getId();

        var secondRequest = addToCartRequest(cartId);
        var thirdRequest = addToCartRequest(cartId);

        orderService.addToCart(secondRequest);
        orderService.addToCart(thirdRequest);

        return cartId;
    }

    private AddToCartRequest addToCartRequest(String cartId) {
        return new AddToCartRequest(cartId, "productStockId");
    }



}