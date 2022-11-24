package xyz.torski.angs.search.infra;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
    For this Walking skeleton this search suffices to be a simple list which can be retrieved.
    In a production implementation this would have to integrate with a search software like SOLR
    to boost search speed, filtering speed etc. Furthermore, whatever frontend there might be,
    it should integrate directly with search engine, not this service.
 */
@Component
public class ProductStockIndex { // todo think about renaming?

    private final List<IndexedProductStock> stock = new ArrayList<>();

    public void acceptAndIndexProductStock(IndexedProductStock productStock) {
        stock.add(productStock);
    }

    public List<IndexedProductStock> findAll() {
        return stock;
    }

}
