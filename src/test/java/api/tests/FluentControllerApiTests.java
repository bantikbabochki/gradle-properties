package api.tests;

import api.controllers.FluentUserController;
import api.controllers.FluentUserControllerWithRetry;
import api.tests.base.BaseApiTest;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;

import static api.testdata.TestData.DEFAULT_USER;

@Feature("FluentControllerTests")
@Tag("API")
public class FluentControllerApiTests extends BaseApiTest {
    FluentUserController fluentUserController = new FluentUserController();
    //Чтобы очищались id-шники после создания
    //List<Integer> ids = new ArrayList<>();

    @BeforeEach
    @AfterEach
    void clear() {
        fluentUserController.deleteUser(DEFAULT_USER.getUsername());
        //Если бы было удаление юзера по id, то
        /*
        fluentUserController.deleteUser(DEFAULT_USER.getId());
        for(int id ^ ids) {
        fluentUserController.deleteUser(id);
         */
    }

    @Test
    @Tag("smoke")
    @DisplayName("Check add user is returns 200 status ok")
    void checkAddUserTest() {
        fluentUserController.addDefaultUser()
                .statusCodeIs(200);
    }

    @Test
    @Tag("extended")
    @DisplayName("Check user added correctly")
    void checkAddUserExtendedTest() {
        String expectedID = fluentUserController.addDefaultUser()
                .statusCodeIs(200)
                .getJsonValue("message");
        fluentUserController.getUser(DEFAULT_USER.getUsername())
                .statusCodeIs(200)
                .jsonValueIs("id", expectedID)
                .jsonValueIs("username", DEFAULT_USER.getUsername())
                .jsonValueIs("email", DEFAULT_USER.getEmail());
    }

    @Test
    void getUserWithRetryTest() throws InterruptedException {
        FluentUserControllerWithRetry fluentUserControllerWithRetry = new FluentUserControllerWithRetry();
        String expectedID = fluentUserController.addDefaultUser()
                .statusCodeIs(200)
                .getJsonValue("message");
        fluentUserControllerWithRetry.getUserWithRetry(DEFAULT_USER.getUsername())
                .statusCodeIs(200)
                .jsonValueIs("id", expectedID)
                .jsonValueIs("username", DEFAULT_USER.getUsername())
                .jsonValueIs("email", DEFAULT_USER.getEmail());
    }

    /*
        @Test
    @DisplayName("Create superhero without phone, check status code and response body")
    void checkSuperheroWithoutPhoneTest() throws InterruptedException {
        int id = Integer.parseInt(fluentSuperheroController.createSuperhero(SUPERHERO_WITHOUT_PHONE)
                .statusCodeIs(200)
                .getJsonValue("id"));

        Thread.sleep(3000);
        ids.add(id); !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        fluentSuperheroController.getSuperheroesById(id)
                .statusCodeIs(200)
                .jsonValueIs("birthDate", SUPERHERO_WITHOUT_PHONE.getBirthDate())
                .jsonValueIs("city", SUPERHERO_WITHOUT_PHONE.getCity())
                .jsonValueIs("fullName", SUPERHERO_WITHOUT_PHONE.getFullName())
                .jsonValueIs("gender", String.valueOf(SUPERHERO_WITHOUT_PHONE.getGender()))
                .jsonValueIs("id", String.valueOf(id))
                .jsonValueIs("mainSkill", SUPERHERO_WITHOUT_PHONE.getMainSkill())
                .jsonValueIs("phone", null);
    }
     */
}
