package xyz.torski.angs.domain;

import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderingProductsFT {

    @LocalServerPort
    private int port;

    @Test
    public void createNewProductsPublishThemAndThenMakeAnOrder() {
        var stock1Id = createProductStock("stock1");
        var stock2Id = createProductStock("stock2");

        renameStock(stock1Id, "newStockName1");
        renameStock(stock2Id, "newStockName2");

        publishStock(stock1Id, "newStockName1");
        publishStock(stock2Id, "newStockName2");

        searchAllProducts("newStockName1", "newStockName2");

        //add products to cart
        //view cart contents and status
        //finalize order
        //query order result
    }

    @SneakyThrows
    public String createProductStock(String name) {
        JSONObject object = new JSONObject();
        object.put("name", name);

        String productId = with().port(port)
                .body(object.toString())
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .post("/stock")
                .then()
                .statusCode(201)
                .extract()
                .path("id");
        return productId;
    }

    @SneakyThrows
    private void renameStock(String stockId, String newStockName) {
        JSONObject object = new JSONObject();
        object.put("id", stockId);
        object.put("newName", newStockName);

        with().port(port)
                .body(object.toString())
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .patch("/stock/"+stockId)
                .then()
                .statusCode(200);
    }

    @SneakyThrows
    private void publishStock(String stockId, String newExpectedName) {
        with().port(port)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .patch("/stock/"+stockId+"/publish")
                .then()
                .statusCode(200)
                .body("name", equalTo(newExpectedName));
    }

    @SneakyThrows
    private void searchAllProducts(String... expectedProductNames) {
        with().port(port)
                .accept(ContentType.JSON)
                .when()
                .get("/search")
                .then()
                .statusCode(200)
                .body("products.name", contains(expectedProductNames));
    }


}
