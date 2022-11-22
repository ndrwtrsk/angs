package xyz.torski.angs.stock.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class ProductStock {

    @Getter
    private String id;

    @Getter
    private String name;

    private List<ProductStockDraft> drafts = new ArrayList<>();

    @Getter
    private Status status = Status.DRAFT_UNPUBLISHED;

    public ProductStock(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        var firstDraft = new ProductStockDraft(name);
        drafts.add(firstDraft);
    }

    public ProductStock updateLatestDraft(String name) {
        drafts.add(new ProductStockDraft(name));
        status = Status.DRAFT_UNPUBLISHED;
        return this;
    }

    public ProductStockPublishedEvent publish() {
        if(drafts.isEmpty()) {
            return null; // should never be null
        }
        updateWithLastDraft();
        this.status = Status.PUBLISHED;
        return new ProductStockPublishedEvent(this.id, this.name);
    }

    private ProductStock updateWithLastDraft() {
        var lastDraft = latestDraft();
        this.name = lastDraft.getName();
        return this;
    }

    ProductStockDraft latestDraft() {
        return drafts.get(drafts.size() - 1);
    }

    public enum Status {
        DRAFT_UNPUBLISHED,
        PUBLISHED
    }
}
