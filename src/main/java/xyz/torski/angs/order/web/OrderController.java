package xyz.torski.angs.order.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.torski.angs.order.domain.OrderService;

import java.util.Optional;

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

    @PostMapping(path = "/{cartId}/finalize")
    public void finalizeOrder(@PathVariable String cartId) {

    }

}
