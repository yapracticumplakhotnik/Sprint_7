package tests;
import client.Courier;
import client.Credentials;
import client.DeleteCourier;
import client.QAScooterAPIClient;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;



public class CourierLoginTest {
    private QAScooterAPIClient client;
    private Courier courier;

    private int courierId;

    @BeforeEach
    public void beforeLogin() {
        courier = new Courier("testCourierLoginJenya", "testPassword", "testName");
        client = new QAScooterAPIClient();
        client.createCourier(courier);

    }


    @Test
    @DisplayName("Метод POST to /api/v1/courier/login - курьер может авторизоваться,успешный запрос возвращает id")
    public void testLoginSuccess() {

        Credentials credentials = Credentials.fromCourier(courier);
        ValidatableResponse response = client.loginCourier(credentials);
        int statusCode = response.extract().statusCode();
        courierId = response.extract().jsonPath().getInt("id");
        Assertions.assertEquals(200, statusCode);
        Assertions.assertNotNull(courierId);
        DeleteCourier.deleteCourier();

    }

    @Test
    @DisplayName("Метод POST to /api/v1/courier/login - система вернёт ошибку, если неправильно указать логин или пароль")
    public void testLoginWithWrongCredentials() {
        courier = new Courier("testCourierLoginJenyaWrong", "testPassword", "testName");
        Credentials credentials = Credentials.fromCourier(courier);
        ValidatableResponse response = client.loginCourier(credentials);
        int statusCode = response.extract().statusCode();
        Assertions.assertEquals(404, statusCode);
        DeleteCourier.deleteCourier();

    }

    @Test
    @DisplayName("Метод POST to /api/v1/courier/login - если какого-то поля нет, запрос возвращает ошибку")
    public void testLoginMissingFields() {
        // без пароля
        courier = new Courier("testCourierLoginJenya", "", "testName");
        Credentials credentials = Credentials.fromCourier(courier);
        ValidatableResponse response = client.loginCourier(credentials);
        int statusCode = response.extract().statusCode();
        Assertions.assertEquals(400, statusCode);
        DeleteCourier.deleteCourier();

    }
}
