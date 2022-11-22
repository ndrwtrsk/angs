package xyz.torski.angs.stock.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductStockUT {

    @Test
    public void shouldCreateProductStock() {
        //when
        var stock = new ProductStock("stock");

        //then
        assertNotNull(stock.getId());
        assertEquals("stock", stock.getName());
        assertEquals(ProductStock.Status.DRAFT_UNPUBLISHED, stock.getStatus());
    }

    @Test
    public void shouldCreateNewDraftWhenUpdated() {
        //given
        var stock = stock();

        //when
        stock = stock.updateLatestDraft("updatedStock");

        //then
        assertNotEquals("updateStock", stock.getName(), "updating draft should not change current name!");
        assertEquals(ProductStock.Status.DRAFT_UNPUBLISHED, stock.getStatus());
        var latestDraft = stock.latestDraft();
        assertEquals("updatedStock", latestDraft.getName());
    }

    @Test
    public void publishingShouldChangeState() {
        //given
        var stock = stock();
        stock.updateLatestDraft("publishedName");

        //when
        var publishedEvent = stock.publish();

        //then
        assertEquals("publishedName", stock.getName());
        assertEquals(ProductStock.Status.PUBLISHED, stock.getStatus());

        assertEquals(stock.getId(), publishedEvent.getId());
        assertEquals(stock.getName(), publishedEvent.getName());
    }


    private ProductStock stock() {
        return new ProductStock("stock");
    }

}