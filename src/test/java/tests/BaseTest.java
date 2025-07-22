package tests;


import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static utils.Constants.createCourier;

public class BaseTest {
    protected static String baseUrl = "https://qa-scooter.praktikum-services.ru";

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = baseUrl;
    }

    protected RequestSpecification givenRequest() {
        return io.restassured.RestAssured.given()
                .header("Content-Type", "application/json");
    }

    static class CreateCourier {
        String login;
        String password;
        String firstName;

        CreateCourier(String login, String password, String firstName) {
            this.login = login;
            this.password = password;
            this.firstName = firstName;
        }
        public CreateCourier() {
        }
        public String getLogin() {
            return login;
        }
        public void setLogin(String login) {
            this.login = login;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public String getFirstName() {
            return firstName;
        }
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

    }

    static class LoginCourier {
        String login;
        String password;


        LoginCourier(String login, String password) {
            this.login = login;
            this.password = password;
        }
        public LoginCourier() {
        }
        public String getLogin() {
            return login;
        }
        public void setLogin(String login) {
            this.login = login;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
    }

    static class LoginCourierMissingFields {
        String login;


        LoginCourierMissingFields(String login) {
            this.login = login;

        }
        public LoginCourierMissingFields() {
        }
        public String getLogin() {
            return login;
        }
        public void setLogin(String login) {
            this.login = login;
        }


    }


    @DisplayName("Метод POST to /api/v1/courier - создать курьера")
    public void createCourier() {
        // Создаем курьера перед каждым тестом
        CreateCourier createCourierBody = new CreateCourier("testCourierLoginJenya","testPassword","testName");

        givenRequest()
                .body(createCourierBody)
                .when()
                .post(createCourier)
                .then()
                .statusCode(anyOf(is(201))); // Если уже создан, 409



    }
}