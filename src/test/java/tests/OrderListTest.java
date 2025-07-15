package tests;

import io.qameta.allure.Step;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class OrderListTest extends BaseTest {

    @Test
    @Step("Метод POST to /api/v1/orders - в тело ответа возвращается список заказов")
    public void testGetOrderList() {
        givenRequest()
                .when()
                .get("/api/v1/orders")
                .then()
                .statusCode(200)
                .body("$", is(notNullValue()))
                .body("$", instanceOf(java.util.List.class)); // Проверка что возвращается список
    }
}
