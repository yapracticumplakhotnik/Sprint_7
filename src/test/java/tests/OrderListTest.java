package tests;
import client.QAScooterAPIClient;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class OrderListTest {
    private final QAScooterAPIClient client = new QAScooterAPIClient();

    @Test
    @DisplayName("Получение списка заказов")
    public void getOrdersPageByPage_ShouldReturnOrdersList() {
        ValidatableResponse response = client.getOrdersPageByPage();
        response
                .assertThat()
                .statusCode(200)
                .and()
                .body("orders", notNullValue())
                .body("orders.size()", greaterThanOrEqualTo(0));
    }
}