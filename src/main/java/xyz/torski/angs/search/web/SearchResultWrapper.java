package xyz.torski.angs.search.web;

import xyz.torski.angs.search.infra.IndexedProductStock;

import java.util.List;

public record SearchResultWrapper(List<IndexedProductStockView> products) {
    public static SearchResultWrapper fromIndexedProductStocks(List<IndexedProductStock> input) {
        var stockViews = input.stream().map(IndexedProductStockView::fromIndexedProductStock).toList();
        return new SearchResultWrapper(stockViews);
    }
}
