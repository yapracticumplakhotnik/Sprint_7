package tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import static org.hamcrest.Matchers.*;
import static utils.Constants.*;

public class CourierCreationTest extends BaseTest {

    public  String courierLogin = "{\"login\": \" + testCourierLogin + \"";
    public String courierPassword = "\"password\": \" + testPassword + \"";
    public   String courierName = "\"firstName\": \" + testName + \"}";

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

    @Before
    @DisplayName("Метод POST to /api/v1/courier - курьера можно создать")
    public void createCourier() {
        // Создаем курьера перед каждым тестом
        CreateCourier createCourierBody = new CreateCourier(courierLogin,courierPassword,courierName);
        //бывшая реализация "{ \"login\": \"" + courierLogin + "\", \"password\": \"" + courierPassword + "\", \"firstName\": \"" + courierName + "\" }";

        givenRequest()
                .body(createCourierBody)
                .when()
                .post(createCourier)
                .then()
                .statusCode(anyOf(is(201), is(409))); // Если уже создан, 409
    }

    @Test
    @DisplayName("Метод POST to /api/v1/courier - запрос возвращает правильный код ответа и успешный запрос возвращает ok: true")
    public void testCreateCourierSuccess() {
        String newLogin = "newCourier" + System.currentTimeMillis();
        CreateCourier createCourierBody =  new CreateCourier(newLogin,courierPassword,courierName);
        //бывшая реализация "{ \"login\": \"" + newLogin + "\", \"password\": \"pass123\", \"firstName\": \"Name\" }";

        givenRequest()
                .body(createCourierBody)
                .when()
                .post(createCourier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));


    }

    @Test
    @DisplayName("Метод POST to /api/v1/courier - нельзя создать двух одинаковых курьеров")
    public void testCreateDuplicateCourier() {
        CreateCourier createCourierBody = new CreateCourier(courierLogin,courierPassword,courierName);
        //бывшая реализация "{ \"login\": \"" + courierLogin + "\", \"password\": \"" + courierPassword + "\", \"firstName\": \"" + courierName + "\" }";
        givenRequest()
                .body(createCourierBody)
                .when()
                .post(createCourier)
                .then()
                .statusCode(409); // ожидается конфликт
    }

    @Test
    @DisplayName("Метод POST to /api/v1/courier - если одного из полей нет, запрос возвращает ошибку")
    public void testCreateCourierMissingFields() {

        String body = "{ \"login\": \"" + System.currentTimeMillis() + "\" }";

        givenRequest()
                .body(body)
                .when()
                .post(createCourier)
                .then()
                .statusCode(400);
    }

    @After
    @Test
    @DisplayName("Удаление курьера")
    public void delete(){
        tearDown();
    }
}
