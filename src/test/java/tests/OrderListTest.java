package tests;


import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static utils.Constants.*;
import static org.hamcrest.Matchers.*;

public class OrderListTest extends BaseTest {

    @Test
    @DisplayName("Метод POST to /api/v1/orders - в тело ответа возвращается список заказов")
    public void testGetOrderList() {
        givenRequest()
                .when()
                .get(createOrder)
                .then()
                .statusCode(200)
                .body("$", is(notNullValue()))
                .body("$", instanceOf(java.util.List.class)); // Проверка что возвращается список
    }
}
