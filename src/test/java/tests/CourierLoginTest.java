package tests;
import client.Courier;
import client.Credentials;
import client.QAScooterAPIClient;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;


@TestMethodOrder(MethodOrderer.Alphanumeric.class)
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
    public void test1LoginSuccess() {

        Credentials credentials = Credentials.fromCourier(courier);
        ValidatableResponse response = client.loginCourier(credentials);
        int statusCode = response.extract().statusCode();
        courierId = response.extract().jsonPath().getInt("id");
        Assertions.assertEquals(200, statusCode);
        Assertions.assertNotNull(courierId);
    }

    @Test
    @DisplayName("Метод POST to /api/v1/courier/login - система вернёт ошибку, если неправильно указать логин или пароль")
    public void test2LoginWithWrongCredentials() {
        courier = new Courier("testCourierLoginJenyaWrong", "testPassword", "testName");
        Credentials credentials = Credentials.fromCourier(courier);
        ValidatableResponse response = client.loginCourier(credentials);
        int statusCode = response.extract().statusCode();
        Assertions.assertEquals(404, statusCode);
    }

    @Test
    @DisplayName("Метод POST to /api/v1/courier/login - если какого-то поля нет, запрос возвращает ошибку")
    public void test3LoginMissingFields() {
        // без пароля
        courier = new Courier("testCourierLoginJenyaWrong", "", "testName");
        Credentials credentials = Credentials.fromCourier(courier);
        ValidatableResponse response = client.loginCourier(credentials);
        int statusCode = response.extract().statusCode();
        Assertions.assertEquals(400, statusCode);

    }
}
