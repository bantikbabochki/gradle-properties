package api.controllers;

import api.models.User;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static api.constant.CommonConstants.BASE_URI;
import static api.testdata.TestData.DEFAULT_USER;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class UserController {
    RequestSpecification requestSpecification;
    public static final String USER_ENDPOINT = "user";

    public UserController() {
        this.requestSpecification = given()
                .accept(JSON)
                .contentType(JSON)
                .baseUri(BASE_URI)
                .filter(new AllureRestAssured());
    }

    @Step("Create user")
    public Response createUser(User user) {
        return given(this.requestSpecification)
                .body(user)
                .when()
                .post(USER_ENDPOINT)
                .andReturn();
    }

    @Step("Create default user")
    public Response createDefaultUser() {
        return given(this.requestSpecification)
                .body(DEFAULT_USER)
                .when()
                .post(USER_ENDPOINT)
                .andReturn();
    }

    @Step("Update user")
    public Response updateUser(User user, String userNumber) {
        return given(this.requestSpecification)
                .body(user)
                .when()
                .put(USER_ENDPOINT + "/" + userNumber)
                .andReturn();
    }

    @Step("Get user")
    public Response getUser(String username) {
        return given(this.requestSpecification)
                .when()
                .get(USER_ENDPOINT + "/" + username)
                .andReturn();
    }

    @Step("Login user")
    public Response loginUser(String username, String password) {
        return given(this.requestSpecification)
                .when()
                .get(USER_ENDPOINT + "/login?username=" + username + "&password=" + password)
                .andReturn();
    }

    @Step("Logout user")
    public Response logoutUser() {
        return given(this.requestSpecification)
                .when()
                .get(USER_ENDPOINT + "/logout")
                .andReturn();
    }

    @Step("Delete user")
    public Response deleteUser(String username) {
        return given(this.requestSpecification)
                .when()
                .delete(USER_ENDPOINT + "/" + username)
                .andReturn();
    }
}
