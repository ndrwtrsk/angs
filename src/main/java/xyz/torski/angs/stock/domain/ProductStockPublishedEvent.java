package xyz.torski.angs.stock.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProductStockPublishedEvent {
    private final String id;
    private final String name;
}
