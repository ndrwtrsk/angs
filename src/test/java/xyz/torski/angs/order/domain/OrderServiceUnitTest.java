package xyz.torski.angs.order.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.torski.angs.order.domain.payment.OrderPaymentResult;
import xyz.torski.angs.order.domain.request.AddToCartRequest;
import xyz.torski.angs.order.domain.request.FinalizeOrderRequest;
import xyz.torski.angs.order.infra.InMemoryCartRepository;
import xyz.torski.angs.order.infra.InMemoryProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceUnitTest {

    private InMemoryProductRepository stockRepository;

    private CartRepository cartRepository;

    private VerifiablePaymentService paymentService;

    private VerifiableOrderRealizationService orderRealizationService;

    private OrderService orderService;

    @BeforeEach
    public void resetBefore() {
        stockRepository = new InMemoryProductRepository();
        cartRepository = new InMemoryCartRepository();
        paymentService = new VerifiablePaymentService();
        orderRealizationService = new VerifiableOrderRealizationService();
        orderService = new OrderService(cartRepository, stockRepository, paymentService, orderRealizationService);
    }

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
        assertEquals("stock1", products.get(0).name());
        assertEquals("stock2", products.get(1).name());
        assertEquals("stock3", products.get(2).name());
    }

    @Test
    public void shouldFinalizeOrderAndRequestPayment() {
        //given
        addThreeProductsToOrderStockRepository();
        var cartId = addToCartProductsPresentInOrderStockRepository();

        //when
        var finalizeOrderRequest = new FinalizeOrderRequest("userId", cartId, "paymentDetails");
        var orderResult = orderService.orderCart(finalizeOrderRequest);

        //then
        assertTrue(orderResult.isSuccess());
        assertNull(orderResult.getMessage());

        //and
        var order = orderResult.getMadeOrder();
        assertNotNull(order);

        //and
        var paymentCommand = paymentService.getLastReceivedCommand();
        assertEquals(order.getId(), paymentCommand.orderId());
        assertEquals(cartId, paymentCommand.cartId());
        assertEquals(order.getUserId(), paymentCommand.userId());
        assertEquals("paymentDetails", paymentCommand.paymentDetails());

        //and cart has been updated with order details
        var cart = orderService.findCart(cartId).get();
        assertNotNull(cart.getOrder());
    }

    @Test
    public void shouldReturnFailedOrderResultIfRequestIsInvalid() {
        //when
        var requestWithoutUserId = new FinalizeOrderRequest(null, "cartId", "payment");
        var result1 = orderService.orderCart(requestWithoutUserId);
        //then
        assertFalse(result1.isSuccess());

        //when
        var requestWithoutCartId = new FinalizeOrderRequest("userId", null, "payment");
        var result2 = orderService.orderCart(requestWithoutCartId);
        //then
        assertFalse(result2.isSuccess());

        //when
        var requestWithoutPaymentDetails = new FinalizeOrderRequest("userId", "cartId", null);
        var result3 = orderService.orderCart(requestWithoutPaymentDetails);
        //then
        assertFalse(result3.isSuccess());
    }

    @Test
    public void shouldReturnedFailedOrderIfNoCartWasFoundForRequest() {
        //when
        var finalizeOrderRequest = new FinalizeOrderRequest("userId", "cartId", "paymentDetails");
        var orderResult = orderService.orderCart(finalizeOrderRequest);

        //then
        assertFalse(orderResult.isSuccess());
    }

    @Test
    public void shouldNotifyOrderRealizationServiceIfPaymentWasSuccesful() {
        //given
        addThreeProductsToOrderStockRepository();
        var cartId = addToCartProductsPresentInOrderStockRepository();

        var finalizeOrderRequest = new FinalizeOrderRequest("userId", cartId, "paymentDetails");
        var orderResult = orderService.orderCart(finalizeOrderRequest);

        //when
        var madeOrder = orderResult.getMadeOrder();
        var orderId = madeOrder.getId();
        var orderPaymentResult = new OrderPaymentResult(cartId, orderId, "userId", true, null);
        orderService.processOrderPaymentResult(orderPaymentResult);

        //then
        var lastCommand = orderRealizationService.getLastReceivedCommand();
        assertEquals(orderId, lastCommand.orderId());
        assertEquals("userId", lastCommand.userId());
        assertIterableEquals(List.of("stock1", "stock2", "stock3"), lastCommand.productsToDeliver());

        //and
        assertEquals(Order.OrderStatus.SUCCESFUL, madeOrder.getStatus());
    }

    @Test
    public void shouldNotNotifyOrderRealizationService() {
        //given
        addThreeProductsToOrderStockRepository();
        var cartId = addToCartProductsPresentInOrderStockRepository();

        var finalizeOrderRequest = new FinalizeOrderRequest("userId", cartId, "paymentDetails");
        var orderResult = orderService.orderCart(finalizeOrderRequest);

        //when
        var madeOrder = orderResult.getMadeOrder();
        var orderId = madeOrder.getId();
        var orderPaymentResult = new OrderPaymentResult(cartId, orderId, "userId", false, "some error");
        orderService.processOrderPaymentResult(orderPaymentResult);

        //then
        assertNull(orderRealizationService.getLastReceivedCommand());

        //and
        assertEquals(Order.OrderStatus.PAYMENT_FAILED, madeOrder.getStatus());
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


    private Product stock(String name) {
        return new Product(name, name);
    }

}