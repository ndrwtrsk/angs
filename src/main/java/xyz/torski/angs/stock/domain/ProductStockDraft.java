package xyz.torski.angs.stock.domain;

import lombok.Getter;

import java.time.LocalDateTime;

public class ProductStockDraft {

    @Getter
    private String name;
    private LocalDateTime createdAt = LocalDateTime.now();

    public ProductStockDraft(String name) {
        this.name = name;
    }
}
