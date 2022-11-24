package xyz.torski.angs.search.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import xyz.torski.angs.search.infra.ProductStockIndex;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final ProductStockIndex index;

    /**
     * Simple endpoint that returns all data. Not efficient, but it's MVP and it should be able to deliver some kind of functionality.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SearchResultWrapper findAll() {
        return SearchResultWrapper.fromIndexedProductStocks(index.findAll());
    }

}
