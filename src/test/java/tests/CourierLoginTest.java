package tests;

import io.qameta.allure.Step;
import org.junit.*;

import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest extends BaseTest {

    private String login = "testLoginAuth" + System.currentTimeMillis();
    private String password = "testPassword";

    @Before
    public void createCourier() {
        String body = "{ \"login\": \"" + login + "\", \"password\": \"" + password + "\", \"firstName\": \"AuthName\" }";
        givenRequest()
                .body(body)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);
    }

    @After
    public void deleteCourier() {
        //  удалить курьера по логину
    }

    @Test
    @Step("Метод POST to /api/v1/courier/login - курьер может авторизоваться,успешный запрос возвращает id")
    public void testLoginSuccess() {
        String body = "{ \"login\": \"" + login + "\", \"password\": \"" + password + "\" }";

        givenRequest()
                .body(body)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @Step("Метод POST to /api/v1/courier/login - система вернёт ошибку, если неправильно указать логин или пароль")
    public void testLoginWithWrongCredentials() {

        String body = "{ \"login\": \"" + login + "\", \"password\": \"wrong\" }";

        givenRequest()
                .body(body)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404);
    }

    @Test
    @Step("Метод POST to /api/v1/courier/login - если какого-то поля нет, запрос возвращает ошибку")
    public void testLoginMissingFields() {
        String body = "{ \"login\": \"" + login + "\" }"; // без пароля

        givenRequest()
                .body(body)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400);
    }
}
