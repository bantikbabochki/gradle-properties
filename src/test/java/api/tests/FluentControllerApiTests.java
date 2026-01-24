package api.tests;

import api.controllers.FluentUserController;
import api.tests.base.BaseApiTest;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;

import static api.testdata.TestData.DEFAULT_USER;

@Feature("FluentControllerTests")
@Tag("API")
public class FluentControllerApiTests extends BaseApiTest {
    FluentUserController fluentUserController = new FluentUserController();
    @BeforeEach
    @AfterEach
    void clear() {
        fluentUserController.deleteUser(DEFAULT_USER.getUsername());
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
}
