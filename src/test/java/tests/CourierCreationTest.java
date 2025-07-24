package tests;

import client.Courier;
import client.Credentials;
import client.QAScooterAPIClient;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class CourierCreationTest  {

    @Test
    @DisplayName("Метод POST to /api/v1/courier - запрос возвращает правильный код ответа и успешный запрос возвращает ok: true")
    public void test1CreateCourierSuccess() {
        QAScooterAPIClient client = new QAScooterAPIClient();
        Courier courier = new Courier("testCourierLoginJenya","testPassword","testName");
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().statusCode(201).and().body("ok",equalTo(true));
    }

    @Test
    @DisplayName("Метод POST to /api/v1/courier - нельзя создать двух одинаковых курьеров - получим 409 код")
    public void test2CreateDuplicateCourier() {
        QAScooterAPIClient client = new QAScooterAPIClient();
        Courier courier = new Courier("testCourierLoginJenya","testPassword","testName");
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().statusCode(409);

    }

    @Test
    @DisplayName("Метод POST to /api/v1/courier - если одного из полей нет, запрос возвращает ошибку")
    public void test3CreateCourierMissingFields() {
        QAScooterAPIClient client = new QAScooterAPIClient();
        Courier courier = new Courier("testCourierLoginJenya","", "");
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().statusCode(400);

    }

    @Test
    @DisplayName("Метод DELETE to /api/v1/courier - удаление курьера")
    public void test4DeleteCourier() {
        //авторизуемся под курьером
        QAScooterAPIClient client = new QAScooterAPIClient();
        Courier courier = new Courier("testCourierLoginJenya", "testPassword", "testName");
        Credentials credentials = Credentials.fromCourier(courier);
        ValidatableResponse response = client.loginCourier(credentials);
        //возьмем id
        int courierId = response.extract().jsonPath().getInt("id");
        //удаляем курьера
        ValidatableResponse deleteResponse = client.deleteCourier(courierId);
        deleteResponse.assertThat().statusCode(200).and().body("ok",equalTo(true));

    }
}
