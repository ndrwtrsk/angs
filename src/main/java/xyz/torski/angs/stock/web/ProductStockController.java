package xyz.torski.angs.stock.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.torski.angs.stock.domain.ProductStockService;


import static xyz.torski.angs.stock.web.ProductStockView.viewFromDomain;

/**
 * TODO Secure this controller using role based policy. Only authenticated and authorized back-office users should
 * be able to access these endpoints.
 */
@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
@Slf4j
public class ProductStockController {

    private final ProductStockService productStockService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductStockView createNewProductStock(@RequestBody CreateProductStockWebRequest request) {
        log.info("Received createNewProductStock {}", request);
        var newStock = productStockService.create(request.name());
        return viewFromDomain(newStock);
    }

    @PatchMapping(path = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductStockView> updateProductStock(@PathVariable String id,
                                                               @RequestBody UpdateProductStockWebRequest request) {
        log.info("Received updateProductStock {}", request);
        var maybeProductStock = productStockService.update(request.toDomain());
        var mappedResponse = maybeProductStock.map(ProductStockView::viewFromDomain);
        return ResponseEntity.of(mappedResponse);
    }

    @PatchMapping(path = "/{id}/publish", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductStockView> publishProductStock(@PathVariable String id) {
        log.info("Received publishProductStock {}", id);
        var request = new ProductStockService.ProductStockPublishRequest(id);
        var stock = productStockService.publish(request);
        var mappedResponse = stock.map(ProductStockView::viewFromDomain);
        return ResponseEntity.of(mappedResponse);
    }

}
