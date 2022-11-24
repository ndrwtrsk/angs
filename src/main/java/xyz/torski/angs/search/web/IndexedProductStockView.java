package xyz.torski.angs.search.web;

import xyz.torski.angs.search.infra.IndexedProductStock;

public record IndexedProductStockView(String id, String name) {
    public static IndexedProductStockView fromIndexedProductStock(IndexedProductStock stock) {
        return new IndexedProductStockView(stock.id(), stock.name());
    }
}
