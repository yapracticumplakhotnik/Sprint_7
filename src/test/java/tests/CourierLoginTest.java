package tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import static org.hamcrest.Matchers.notNullValue;
import static utils.Constants.*;

public class CourierLoginTest extends BaseTest {

    private final String login = "testLoginAuth" + System.currentTimeMillis();
    private final String password = "testPassword";

    @Before
    public void createCourier() {
        String body = "{ \"login\": \"" + login + "\", \"password\": \"" + password + "\", \"firstName\": \"AuthName\" }";
        givenRequest()
                .body(body)
                .when()
                .post(createCourier)
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("Метод POST to /api/v1/courier/login - курьер может авторизоваться,успешный запрос возвращает id")
    public void testLoginSuccess() {
        String body = "{ \"login\": \"" + login + "\", \"password\": \"" + password + "\" }";

        givenRequest()
                .body(body)
                .when()
                .post(loginCourier)
                .then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Метод POST to /api/v1/courier/login - система вернёт ошибку, если неправильно указать логин или пароль")
    public void testLoginWithWrongCredentials() {

        String body = "{ \"login\": \"" + login + "\", \"password\": \"wrong\" }";

        givenRequest()
                .body(body)
                .when()
                .post(loginCourier)
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Метод POST to /api/v1/courier/login - если какого-то поля нет, запрос возвращает ошибку")
    public void testLoginMissingFields() {
        String body = "{ \"login\": \"" + login + "\" }"; // без пароля

        givenRequest()
                .body(body)
                .when()
                .post(loginCourier)
                .then()
                .statusCode(400);
    }

    @After
    @Test
    @DisplayName("Удаление курьера")
    public void deleteCourier(){
        tearDown();
    }
}
