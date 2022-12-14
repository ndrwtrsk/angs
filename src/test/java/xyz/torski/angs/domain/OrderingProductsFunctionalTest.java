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
public class OrderingProductsFunctionalTest {

    @LocalServerPort
    private int port;

    @Test
    public void createNewProductsPublishThemAndThenMakeAnOrder() {
        //create and publish stock
        var stock1Id = createProductStock("stock1");
        var stock2Id = createProductStock("stock2");

        renameStock(stock1Id, "newStockName1");
        renameStock(stock2Id, "newStockName2");

        publishStock(stock1Id, "newStockName1");
        publishStock(stock2Id, "newStockName2");

        //search for created and published products
        searchAllProducts("newStockName1", "newStockName2");

        //make an order
        var cartId = addProductToCart(stock1Id, null);
        addProductToCart(stock2Id, cartId);

        viewCartContents(cartId, stock1Id, stock2Id);

        finalizeOrder(cartId);

        waitForEventsToComeThrough();

        verifyOrderHasBeenRealized(cartId);
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

    @SneakyThrows
    private String addProductToCart(String stockId, String cartId) {
        JSONObject object = new JSONObject();
        object.put("productStockId", stockId);
        object.put("cartId", cartId);

        String cartIdFromResponse = with().port(port)
                .body(object.toString())
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .put("/cart")
                .then()
                .statusCode(200)
                .extract()
                .path("cartId");

        return cartIdFromResponse;
    }

    private void viewCartContents(String cartId, String... expectedProductIds) {
        with().port(port)
                .accept(ContentType.JSON)
                .when()
                .get("/cart/"+cartId)
                .then()
                .statusCode(200)
                .body("cartId", equalTo(cartId))
                .body("totalPrice", equalTo(0))
                .body("products.id", contains(expectedProductIds));
    }

    @SneakyThrows
    private void finalizeOrder(String cartId) {
        JSONObject object = new JSONObject();
        object.put("userId", "userId");
        object.put("cartId", cartId);
        object.put("paymentDetails", "somePaymentDetails");

        with().port(port)
                .body(object.toString())
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .put("/cart/"+cartId+"/finalize")
                .then()
                .statusCode(202)
                .body("success", equalTo(true))
                .body("message", blankOrNullString());
    }

    @SneakyThrows
    private void waitForEventsToComeThrough() {
        Thread.sleep(100);
    }

    @SneakyThrows
    private void verifyOrderHasBeenRealized(String cartId) {
        with().port(port)
                .accept(ContentType.JSON)
                .when()
                .get("/cart/"+cartId)
                .then()
                .statusCode(200)
                .body("cartId", equalTo(cartId))
                .body("orderStatus", equalTo("SUCCESFUL"));
    }
}
