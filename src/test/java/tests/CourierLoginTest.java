package tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static utils.Constants.*;

public class CourierLoginTest extends BaseTest {

    @Test
    public void beforeLogin(){
       createCourier();
   }

    LoginCourier loginCourierBody = new LoginCourier("testCourierLoginJenya","testPassword");
    @Test
    @DisplayName("Метод POST to /api/v1/courier/login - курьер может авторизоваться,успешный запрос возвращает id")
    public void testLoginSuccess() {
        givenRequest()
                .body(loginCourierBody)
                .when()
                .post(loginCourier)
                .then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Метод POST to /api/v1/courier/login - система вернёт ошибку, если неправильно указать логин или пароль")
    public void testLoginWithWrongCredentials() {

        LoginCourier loginCourierBody = new LoginCourier("testCourierLoginJenya","testPasswordWrong");
        givenRequest()
                .body(loginCourierBody)
                .when()
                .post(loginCourier)
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Метод POST to /api/v1/courier/login - если какого-то поля нет, запрос возвращает ошибку")
    public void testLoginMissingFields() {
        // без пароля
        LoginCourierMissingFields loginCourierMissingFields = new LoginCourierMissingFields("testCourierLoginJenya");
        givenRequest()
                .body(loginCourierMissingFields)
                .when()
                .post(loginCourier)
                .then()
                .statusCode(400);
    }
    @Test
    @DisplayName("Метод DELETE to /api/v1/courier/{id} - удалить курьера")
    public void deleteCourier() {
        //авторизация для получения id
        Response loginResponse = givenRequest()
                .body(loginCourierBody)
                .when()
                .post(loginCourier);
        loginResponse.then().statusCode(200);
        int courierId = loginResponse.jsonPath().getInt("id");
        //удаление курьера по id
        Response deleteResponse = givenRequest()
                .when()
                .delete(createCourier + courierId);
        //проверка статуса и отсутствия курьера
        deleteResponse.then()
                .statusCode(anyOf(is(200), is(204)));
        //проверка, что курьер больше не существует
        givenRequest()
                .body(loginCourierBody)
                .when()
                .post(loginCourier)
                .then()
                .statusCode(404);
    }
}
