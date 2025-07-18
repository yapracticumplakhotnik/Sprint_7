package tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static utils.Constants.*;


@RunWith(Parameterized.class)
public class OrderCreationTest extends BaseTest {

    @Parameterized.Parameters
    public static Collection<Object[]> colors() {
        return Arrays.asList(new Object[][]{
                {null}, // без цвета
                {"BLACK"},
                {"GREY"},
                {"BLACK,GREY"}
        });
    }

    private String color;

    public OrderCreationTest(String color) {
        this.color = color;
    }

    private String track;

    @Test
    @DisplayName("Метод POST to /api/v1/orders - создаём заказ используя параметризацию")
    public void testCreateOrder() {
        String body = "{"
                + "\"firstName\": \"Ivan\","
                + "\"lastName\": \"Petrov\","
                + "\"address\": \"Baker street\","
                + "\"metroStation\": 1,"
                + "\"phone\": \"+79991234567\","
                + "\"rentTime\": 5,"
                + "\"deliveryDate\": \"2023-10-10\","
                + "\"comment\": \"Test order\","
                + "\"color\": " + (color == null ? "[]" : "[\"" + color + "\"]")
                + "}";

        Response response = givenRequest()
                .body(body)
                .when()
                .post(createOrder);

        response.then().statusCode(201);
        track = response.then().extract().path("track").toString();
        Assert.assertNotNull("Track should not be null", track);
    }


}
