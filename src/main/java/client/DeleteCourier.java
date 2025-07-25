package client;

import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.equalTo;

public class DeleteCourier {

    public static void deleteCourier() {
        //авторизуемся под курьером
        QAScooterAPIClient client = new QAScooterAPIClient();
        Courier courier = new Courier("testCourierLoginJenya", "testPassword", "testName");
        Credentials credentials = Credentials.fromCourier(courier);
        ValidatableResponse response = client.loginCourier(credentials);
        //возьмем id
        int courierId = response.extract().jsonPath().getInt("id");
        //удаляем курьера
        ValidatableResponse deleteResponse = client.deleteCourier(courierId);
        deleteResponse.assertThat().statusCode(200).and().body("ok", equalTo(true));

    }
}
