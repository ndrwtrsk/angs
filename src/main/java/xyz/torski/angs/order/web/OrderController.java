package xyz.torski.angs.order.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.torski.angs.order.domain.OrderService;

@RestController
@RequestMapping(path = "/cart")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CartView addProductToCart(@RequestBody AddToCartWebRequest request) {
        log.info("Received addProductToCart request: {}", request);
        var cart = orderService.addToCart(request.toDomain());
        return new CartView(cart);
    }

    @GetMapping(path = "/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalculatedCartView> findCart(@PathVariable String cartId) {
        log.info("Received findCart request: {}", cartId);
        var maybeCart = orderService.calculateCart(cartId).map(CalculatedCartView::fromCalculatedCart);
        return ResponseEntity.of(maybeCart);
    }

    @PutMapping(path = "/{cartId}/finalize", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OrderResultView finalizeOrder(@PathVariable String cartId,
                                         @RequestBody FinalizeOrderWebRequest request) {
        log.info("Received finalizeOrder request: {}", request);
        var orderResult = orderService.orderCart(request.toDomain());
        return OrderResultView.fromDomain(orderResult);
    }

}
