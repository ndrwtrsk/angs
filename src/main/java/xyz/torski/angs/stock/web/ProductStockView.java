package xyz.torski.angs.stock.web;

import xyz.torski.angs.stock.domain.ProductStock;

public record ProductStockView(String id, String name) {

    public static ProductStockView viewFromDomain(ProductStock stock) {
        return new ProductStockView(stock.getId(), stock.getName());
    }
}
