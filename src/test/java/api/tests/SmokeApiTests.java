package api.tests;

import api.controllers.UserController;
import api.models.AddUserResponse;
import api.models.User;
import api.tests.base.BaseApiTest;
import io.qameta.allure.Link;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static api.testdata.TestData.DEFAULT_USER;
import static api.testdata.TestData.INVALID_USER;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SmokeApiTests extends BaseApiTest {
    UserController userController = new UserController();

    @Test
    void exampleRestAssuredTest() {
        String jsonBody = """
                {
                  "id": 0,
                  "username": "test_22_04_001",
                  "firstName": "firstName",
                  "lastName": "lastName",
                  "email": "email@gmail.com",
                  "password": "test123",
                  "phone": "phone not exist",
                  "userStatus": 0
                }
                """;
        String url = "https://petstore.swagger.io/v2/user";

        ValidatableResponse response =
                given().
                        header("accept", "application/json").
                        header("Content-Type", "application/json").
                        body(jsonBody).
                        when().
                        post(url).
                        then();
        Response responseAsResponse = response.extract().response();
        int status = responseAsResponse.statusCode();
        String responseText = responseAsResponse.body().prettyPrint();
        assertEquals(200, status);
    }

    @Test
    void createUserControllerTest() {
        // Создаём экземпляр UserController
        UserController userController = new UserController();
        // Создаём пользователя
//        User user = new User(
//                0,
//                "username",
//                "firstName",
//                "lastName",
//                "email",
//                "password",
//                "phone",
//                0);
        User userBuilder = User.builder()
                .id(100L)
                .username("username")
                .firstName("firstNname")
                .lastName("lastName")
                .email("email")
                .password("password")
                .phone("phone")
                .userStatus(0)
                .build();

        // Вызываем метод контроллера
        Response response = userController.createUser(userBuilder);

        // Парсим ответ
        AddUserResponse createUserResponse = response.as(AddUserResponse.class);

        // Проверяем
        assertEquals(200, response.statusCode());
        assertEquals(200, createUserResponse.getCode());
        assertEquals("unknown", createUserResponse.getType());
        Assertions.assertFalse(createUserResponse.getMessage().isEmpty());
    }

    @Test
    void createUserControllerTest2() {
        // Вызываем метод контроллера
        Response response = userController.createDefaultUser();
        // Парсим ответ
        AddUserResponse createUserResponse = response.as(AddUserResponse.class);
        // Проверяем
        assertEquals(200, response.statusCode());
        assertEquals(200, createUserResponse.getCode());
        assertEquals("unknown", createUserResponse.getType());
        Assertions.assertFalse(createUserResponse.getMessage().isEmpty());
    }

    @Test @Tag("API") @Tag("User") @Tag("Positive")
    void createUserDefaultTest() {
        // Вызываем метод контроллера
        Response response = userController.createUser(DEFAULT_USER);
        // Парсим ответ
        AddUserResponse createUserResponse = response.as(AddUserResponse.class);
        // Проверяем
        assertEquals(200, response.statusCode());
        assertEquals(200, createUserResponse.getCode());
        assertEquals("unknown", createUserResponse.getType());
    }

    static Stream<User> users() {
        return Stream.of(DEFAULT_USER, INVALID_USER);
    }

    @Tag("API") @Tag("User")
    @Tag("Positive") @ParameterizedTest
    @MethodSource("users")
    void createUserParametrizedTest(User user) {
        Response response = userController.createUser(user);
        AddUserResponse createUserResponse = response.as(AddUserResponse.class);

        assertEquals(200, response.statusCode());
        assertEquals(200, createUserResponse.getCode());
        assertEquals("unknown", createUserResponse.getType());
        Assertions.assertFalse(createUserResponse.getMessage().isEmpty());
    }

    @Test @Tag("API") @Tag("User") @Tag("Positive")
    void getUserTest() {
        Response response = userController.createDefaultUser();
        assertEquals(200, response.statusCode());

        String username = DEFAULT_USER.getUsername();
        Response getUserResponse = userController.getUser(username);
        assertEquals(200, getUserResponse.statusCode());
        User receivedUser = getUserResponse.as(User.class);

        assertThat(receivedUser)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(DEFAULT_USER);
    }

    @Test @Tag("API") @Tag("User") @Tag("Negative")
    void getInvalidUserTest() {
        Response getUser = userController.getUser("hghhfjgfhfjgf");
        AddUserResponse getUserResponse = getUser.as(AddUserResponse.class);
        assertEquals(1, getUserResponse.getCode());
        assertEquals("error", getUserResponse.getType());
        assertEquals("User not found", getUserResponse.getMessage());
    }

    @Test @Tag("API") @Tag("User") @Tag("Positive")
    void updateUserTest() {
        Response userResponse = userController.createDefaultUser();
        // Парсим ответ
        AddUserResponse createUserResponse = userResponse.as(AddUserResponse.class);

        assertEquals(200, userResponse.statusCode());
        String userNumber = createUserResponse.getMessage();
        Long userId = Long.parseLong(userNumber);

        User updateUserData = User.builder()
                .id(userId)
                .username("Lana")
                .firstName("Lana")
                .lastName("Solana")
                .email("solana@mail.com")
                .password("321")
                .phone("8807687765677")
                .userStatus(0)
                .build();

        Response updateUserResponse = userController.updateUser(updateUserData, userNumber);
        AddUserResponse updateResponse = updateUserResponse.as(AddUserResponse.class);

        assertEquals(200, updateResponse.getCode());
        assertEquals("unknown", updateResponse.getType());

        Response getUserUpdate = userController.getUser(updateUserData.getUsername());
        User getUserUpdateResponse = getUserUpdate.as(User.class);

        assertThat(getUserUpdateResponse)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(updateUserData);

    }

    @Test
    @Tag("API")
    @Tag("User")
    @Tag("Positive")
    void loginUserTest() {
        Response response = userController.createDefaultUser();
        assertEquals(200, response.statusCode());

        String username = DEFAULT_USER.getUsername();
        String password = DEFAULT_USER.getPassword();

        Response loginUser = userController.loginUser(username, password);
        AddUserResponse loginUserResponse = loginUser.as(AddUserResponse.class);

        assertEquals(200, loginUserResponse.getCode());
        assertEquals("unknown", loginUserResponse.getType());
        assertThat(loginUserResponse.getMessage())
                .contains("logged in user session:");
    }

    @Test
    @Tag("API")
    @Tag("User")
    @Tag("Positive")
    void logoutUserTest() {
        Response response = userController.createDefaultUser();
        assertEquals(200, response.statusCode());

        String username = DEFAULT_USER.getUsername();
        String password = DEFAULT_USER.getPassword();

        Response loginUser = userController.loginUser(username, password);
        assertEquals(200, loginUser.statusCode());

        Response logoutUser = userController.logoutUser();
        AddUserResponse logoutUserResponse = logoutUser.as(AddUserResponse.class);

        assertEquals(200, logoutUserResponse.getCode());
        assertEquals("unknown", logoutUserResponse.getType());
        assertEquals("ok", logoutUserResponse.getMessage());
    }

    @Test
    @Tag("API")
    @Tag("User")
    @Tag("Positive")
    void deleteUserTest() {
        Response response = userController.createDefaultUser();
        assertEquals(200, response.statusCode());

        String username = DEFAULT_USER.getUsername();
        String password = DEFAULT_USER.getPassword();

        Response loginUser = userController.loginUser(username, password);
        assertEquals(200, loginUser.statusCode());

        Response deleteUser = userController.deleteUser(username);
        AddUserResponse deleteUserResponse = deleteUser.as(AddUserResponse.class);

        assertEquals(200, deleteUserResponse.getCode());
        assertEquals("unknown", deleteUserResponse.getType());
        assertEquals(username, deleteUserResponse.getMessage());
    }

    @Test
    @Tag("API")
    @Tag("User")
    @Tag("Negative")
    @Tag("defect")
    @Link("Jira-123")
    void deleteInvalidUserTest() {
        String username = "dgdgfdgfdghd";
        Response deleteUser = userController.deleteUser(username);
        assertEquals(404, deleteUser.statusCode());
    }
}
