package tests;

import client.QAScooterAPIClient;
import client.Order;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class OrderCreationTest {
    private final QAScooterAPIClient client = new QAScooterAPIClient();

    static Object[][] orderDataProvider() {
        return new Object[][]{
                {new Order("Captain", "Black", 3, "123 Main St", "Krasnodar", "123456", 1, "for testing")},
                {new Order("John", "Red", 2, "Somewhere", "Moscow", "654321", 2, "Another test order")},
        };
    }

    @ParameterizedTest
    @MethodSource("orderDataProvider")
    public void createOrder_ShouldCreateOrder(Order order) {
        ValidatableResponse response = client.createOrder(order);
        int statusCode = response.extract().statusCode();
        Assertions.assertEquals(201, statusCode);
        Assertions.assertNotNull("Track should not be null", "track");
    }
}
