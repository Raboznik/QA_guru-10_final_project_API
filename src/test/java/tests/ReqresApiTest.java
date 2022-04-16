package tests;

import models.LombokModel;
import models.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.Specs.*;
import static org.hamcrest.core.Is.is;

public class ReqresApiTest extends TestBase {

    @Test
    @DisplayName("[GET] user list")
    void getUserListTest() {

        given()
                .spec(requestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/userList.json"))
                .body(("total"), is(12))
                .body(("page"), is(2))
                .spec(code200);
    }

    @Test
    @DisplayName("[GET] single user")
    void getSingleUserTest() {
        responseLombok =
                given()
                        .spec(requestSpec)
                        .when()
                        .get("/users/10")
                        .then()
                        .body(matchesJsonSchemaInClasspath("schemas/singleUser.json"))
                        .spec(code200)
                        .extract()
                        .as(LombokModel.class);

        Integer expectedId = 10;
        String expectedFirstName = "Byron";
        String expectedLastName = "Fields";

        assertEquals(expectedId, responseLombok.getUser().getId());
        assertEquals(expectedFirstName, responseLombok.getUser().getFirstName());
        assertEquals(expectedLastName, responseLombok.getUser().getLastName());
    }

    @Test
    @DisplayName("[GET] single user not found")
    void getSingleUserNotFoundTest() {
        given()
                .spec(requestSpec)
                .when()
                .get("/users/13")
                .then()
                .spec(code404);
    }

    @Test
    @DisplayName("[GET] list resource")
    void getListResourceTest() {
        given()
                .spec(requestSpec)
                .when()
                .get("/unknown")
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/resourceList.json"))
                .body(("total"), is(12))
                .body(("data.name[0]"), is("cerulean"))
                .spec(code200)
                .extract()
                .response();
    }

    @Test
    @DisplayName("[GET] single resource")
    void getSingleResourceTest() {
        given()
                .spec(requestSpec)
                .when().get("/unknown/4")
                .then()
                .spec(code200)
                .body(matchesJsonSchemaInClasspath("schemas/singleResource.json"))
                .body(("data.id"), is(4))
                .body(("data.name"), is("aqua sky"))
                .extract()
                .response();
    }

    @Test
    @DisplayName("[GET] single resource not found")
    void getSingleResourceNotFoundTest() {
        given()
                .spec(requestSpec)
                .when()
                .get("/unknown/23")
                .then()
                .spec(code404);
    }

    @Test
    @DisplayName("[POST] create user")
    void postCreateUserTest() {
        user.setFirstName(cfgs.createName());
        user.setJob(cfgs.createJob());
        responseUser =
                given()
                        .spec(requestSpec)
                        .body(user)
                        .when()
                        .post("/user")
                        .then()
                        .spec(code201)
                        .extract()
                        .as(User.class);

        assertEquals(user.getFirstName(), responseUser.getFirstName());
        assertEquals(user.getJob(), responseUser.getJob());
    }

    @Test
    @DisplayName("[PUT] update user")
    void putUpdateUserTest() {
        user.setFirstName(cfgs.createName());
        user.setJob(cfgs.updateJob());
        responseUser =
                given()
                        .spec(requestSpec)
                        .body(user)
                        .when()
                        .put("/user/2")
                        .then()
                        .spec(code200)
                        .extract()
                        .as(User.class);

        assertEquals(user.getJob(), responseUser.getJob());
    }

    @Test
    @DisplayName("[PATCH] update user")
    void patchUpdateUserTest() {
        user.setFirstName(cfgs.createName());
        user.setJob(cfgs.updateJob());
        responseUser =
                given()
                        .spec(requestSpec)
                        .body(user)
                        .when()
                        .patch("/user/2")
                        .then()
                        .spec(code200)
                        .extract()
                        .as(User.class);

        assertEquals(user.getJob(), responseUser.getJob());
    }

    @Test
    @DisplayName("[DELETE] user")
    void deleteUserTest() {
        given()
                .spec(requestSpec)
                .when()
                .delete("/user/2")
                .then()
                .spec(code204);
    }

    @Test
    @DisplayName("[POST] register - successful")
    void postRegisterSuccessfulTest() {
        user.setEmail(cfgs.crateEmail());
        user.setPassword(cfgs.registerPassword());
        responseUser =
                given()
                        .spec(requestSpec)
                        .body(user)
                        .when()
                        .post("/register")
                        .then()
                        .spec(code200)
                        .extract()
                        .as(User.class);

        assertThat(responseUser.getId()).isNotNull();
        assertThat(responseUser.getToken()).isNotNull();
    }

    @Test
    @DisplayName("[POST] register - unsuccessful")
    void postRegisterUnsuccessfulTest() {
        user.setEmail("wrongEmail");
        responseUser =
                given()
                        .spec(requestSpec)
                        .body(user)
                        .when()
                        .post("/register")
                        .then()
                        .spec(code400)
                        .extract()
                        .as(User.class);

        String expectedError = "Missing password";

        assertEquals(expectedError, responseUser.getError());
    }

    @Test
    @DisplayName("[POST] login - successful")
    void postLoginSuccessfulTest() {
        user.setEmail(cfgs.crateEmail());
        user.setPassword(cfgs.loginPassword());
        responseUser =
                given()
                        .spec(requestSpec)
                        .body(user)
                        .when()
                        .post("/login")
                        .then()
                        .spec(code200)
                        .extract()
                        .as(User.class);

        assertThat(responseUser.getToken()).isNotNull();
    }

    @Test
    @DisplayName("[POST] login - unsuccessful")
    void postLoginUnsuccessfulTest() {
        user.setEmail("wrongEmail");
        responseUser = given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/login")
                .then()
                .spec(code400)
                .extract()
                .as(User.class);

        String expectedError = "Missing password";

        assertEquals(expectedError, responseUser.getError());
    }
}
