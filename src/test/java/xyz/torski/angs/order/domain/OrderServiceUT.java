package xyz.torski.angs.order.domain;

import org.junit.jupiter.api.Test;
import xyz.torski.angs.order.infra.InMemoryCartRepository;
import xyz.torski.angs.order.infra.InMemoryOrderProductStockRepository;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceUT {

    private InMemoryOrderProductStockRepository stockRepository = new InMemoryOrderProductStockRepository();
    private CartRepository cartRepository = new InMemoryCartRepository();
    private OrderService orderService = new OrderService(cartRepository, stockRepository);

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
        orderService.addToCart(secondRequest);

        var thirdRequest = addToCartRequest(cartId);
        orderService.addToCart(thirdRequest);

        return cartId;
    }

    private AddToCartRequest addToCartRequest(String cartId) {
        return new AddToCartRequest(cartId, "productStockId");
    }

    @Test
    public void shouldCalculateCart() {
        //given
        addThreeProductsToOrderStockRepository();
        var cartId = addToCartProductsPresentInOrderStockRepository();

        //when
        var calculatedCart = orderService.calculateCart(cartId).get();

        //then
        assertEquals(3, calculatedCart.cartSize());
        assertEquals(0, calculatedCart.totalCartPrice()); // TODO

        //and products are present
        var products = calculatedCart.getCartProducts();
        assertEquals("stock1", products.get(0).getName());
        assertEquals("stock2", products.get(1).getName());
        assertEquals("stock3", products.get(2).getName());
    }

    private void addThreeProductsToOrderStockRepository() {
        var stock1 = stock("stock1");
        var stock2 = stock("stock2");
        var stock3 = stock("stock3");

        stockRepository.add(stock1);
        stockRepository.add(stock2);
        stockRepository.add(stock3);
    }

    private String addToCartProductsPresentInOrderStockRepository() {
        var request1 = new AddToCartRequest(null, "stock1");
        var cartId = orderService.addToCart(request1).getId();

        var request2 = new AddToCartRequest(cartId, "stock2");
        orderService.addToCart(request2);
        var request3 = new AddToCartRequest(cartId, "stock3");
        orderService.addToCart(request3);

        return cartId;
    }


    private OrderProductStock stock(String name) {
        return new OrderProductStock(name, name);
    }

}