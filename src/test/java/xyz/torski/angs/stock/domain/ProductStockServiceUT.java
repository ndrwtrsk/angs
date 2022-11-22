package xyz.torski.angs.stock.domain;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.torski.angs.VerifiableProductStockBroadcast;
import xyz.torski.angs.stock.domain.ProductStockService.ProductStockPublishRequest;
import xyz.torski.angs.stock.domain.ProductStockService.ProductStockUpdateRequest;
import xyz.torski.angs.stock.infra.InMemoryProductStockRepository;

import static org.junit.jupiter.api.Assertions.*;

class ProductStockServiceUT {

    private VerifiableProductStockBroadcast verifiableBroadcast = new VerifiableProductStockBroadcast();
    private InMemoryProductStockRepository repo = new InMemoryProductStockRepository();
    private ProductStockService service = new ProductStockService(repo, verifiableBroadcast);

    @BeforeEach
    public void resetService() {
        repo = new InMemoryProductStockRepository();
        verifiableBroadcast = new VerifiableProductStockBroadcast();

        service = new ProductStockService(repo, verifiableBroadcast);
    }

    @Test
    public void shouldCreateProductStock() {
        //when
        var newStock = service.create("newStock");

        //then
        assertNotNull(newStock);
        assertEquals("newStock", newStock.getName());
        assertEquals(ProductStock.Status.DRAFT_UNPUBLISHED, newStock.getStatus());

        var stockInRepo = repo.findById(newStock.getId()).get();
        assertEquals(newStock.getId(), stockInRepo.getId());
        assertEquals(newStock.getName(), stockInRepo.getName());
        assertEquals(newStock.getStatus(), stockInRepo.getStatus());
    }

    @Test
    public void shouldListAllStocks() {
        //given
        createStock();
        createStock();
        createStock();

        //when
        var allStock = service.list();

        //then
        assertEquals(3, allStock.size());
    }

    @Test
    public void shouldUpdateProductStockWithDraft() {
        //given
        var stockToUpdate = createStock();

        //when
        var updateRequest = new ProductStockUpdateRequest(stockToUpdate.getId(), "newStockName");
        service.update(updateRequest);

        //then
        var updatedStock = repo.findById(stockToUpdate.getId()).get();
        assertNotEquals("newStockName", updatedStock.getName());
        assertNotEquals(ProductStock.Status.PUBLISHED, updatedStock.getStatus());

        var latestDraft = updatedStock.latestDraft();
        assertEquals("newStockName", latestDraft.getName());
    }

    @Test
    public void shouldPublishStockAndBroadcast() {
        //given
        var stockToPublish = createStock();
        var updateRequest = new ProductStockUpdateRequest(stockToPublish.getId(), "publishedName");
        service.update(updateRequest);

        //when
        var publishedStock = service.publish(new ProductStockPublishRequest(stockToPublish.getId()));

        //then
        assertEquals(ProductStock.Status.PUBLISHED, publishedStock.getStatus());
        assertEquals("publishedName", publishedStock.getName());

        //and even was broadcast
        var lastPublishedEvent = verifiableBroadcast.lastEvent().get();
        assertEquals(publishedStock.getId(), lastPublishedEvent.getId());
        assertEquals(publishedStock.getName(), lastPublishedEvent.getName());

    }

    private ProductStock createStock() {
        return service.create(RandomStringUtils.randomAlphabetic(5));
    }


}