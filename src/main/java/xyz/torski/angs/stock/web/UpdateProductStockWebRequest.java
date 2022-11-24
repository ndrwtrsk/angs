package xyz.torski.angs.stock.web;

import xyz.torski.angs.stock.domain.ProductStockService;

public record UpdateProductStockWebRequest(String id, String newName) {

    public ProductStockService.ProductStockUpdateRequest toDomain() {
        return new ProductStockService.ProductStockUpdateRequest(this.id, this.newName);
    }
}
