package xyz.torski.angs.order.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.torski.angs.order.domain.payment.OrderPaymentResult;
import xyz.torski.angs.order.domain.payment.PaymentService;
import xyz.torski.angs.order.domain.realization.OrderRealizationService;
import xyz.torski.angs.order.domain.request.AddToCartRequest;
import xyz.torski.angs.order.domain.request.FinalizeOrderRequest;

import java.util.Optional;

import static xyz.torski.angs.order.domain.OrderResult.failedOrder;
import static xyz.torski.angs.order.domain.OrderResult.madeOrder;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository repo;

    private final ProductRepository productRepository;

    private final PaymentService paymentService;

    private final OrderRealizationService orderRealizationService;

    public Cart addToCart(AddToCartRequest request) {
        var cart = retrieveCart(request);
        return repo.save(cart.addToCart(request));
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

    public Optional<CalculatedCart> calculateCart(String cartId) {
        return findCart(cartId)
                .map(cart -> cart.calculateCart(productRepository));
    }

    public OrderResult orderCart(FinalizeOrderRequest request) {
        if (request.isCartIdEmpty()) {
            return failedOrder("Cart Id is empty.");
        }
        if (request.isUserIdEmpty()) {
            return failedOrder("User Id is empty.");
        }
        if (request.arePaymentDetailsEmpty()) {
            return failedOrder("Payment Details are empty.");
        }

        var cartId = request.cartId();

        var maybeCart = repo.findById(cartId);
        if (maybeCart.isEmpty()) {
            return failedOrder("No cart found.");
        }
        var cart = maybeCart.get();

        var orderPaymentCommand = cart.prepareOrder(request, productRepository);
        paymentService.requestPayment(orderPaymentCommand);

        return madeOrder(cart.getOrder());
    }

    public void processOrderPaymentResult(OrderPaymentResult result) {
        repo.findById(result.cartId())
                .flatMap(cart -> cart.processOrderPaymentResult(result))
                .ifPresent(orderRealizationService::requestRealization);
    }

    public void processOrderRealizationEvent(String cartId) {
        repo.findById(cartId)
                .ifPresent(Cart::orderRealized);
    }
}
