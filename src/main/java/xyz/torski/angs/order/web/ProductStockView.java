package xyz.torski.angs.order.web;

import xyz.torski.angs.order.domain.Product;

import java.util.List;

public record ProductStockView(String id, String name) {
    public static List<ProductStockView> fromDomain(List<Product> products) {
        return products.stream()
                .map(ProductStockView::fromDomain)
                .toList();
    }

    public static ProductStockView fromDomain(Product product) {
        return new ProductStockView(product.id(), product.name());
    }
}
