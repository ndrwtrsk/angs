package xyz.torski.angs.order.web;

import xyz.torski.angs.order.domain.OrderProductStock;

import java.util.List;

public record ProductStockView(String id, String name) {
    public static List<ProductStockView> fromDomain(List<OrderProductStock> products) {
        return products.stream()
                .map(ProductStockView::fromDomain)
                .toList();
    }

    public static ProductStockView fromDomain(OrderProductStock product) {
        return new ProductStockView(product.getId(), product.getName());
    }
}
