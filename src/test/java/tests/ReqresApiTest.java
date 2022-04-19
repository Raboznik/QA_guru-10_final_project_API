package tests;

import models.UserModel;
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

    UserModel userModel = new UserModel();
    User user = new User();

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
        userModel = given()
                .spec(requestSpec)
                .when()
                .get("/users/10")
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/singleUser.json"))
                .spec(code200)
                .extract()
                .as(UserModel.class);

        Integer expectedId = 10;
        String expectedFirstName = "Byron";
        String expectedLastName = "Fields";

        assertEquals(expectedId, userModel.getUser().getId());
        assertEquals(expectedFirstName, userModel.getUser().getFirstName());
        assertEquals(expectedLastName, userModel.getUser().getLastName());
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
        user =
                given()
                        .spec(requestSpec)
                        .body(user)
                        .when()
                        .post("/user")
                        .then()
                        .spec(code201)
                        .extract()
                        .as(User.class);

        assertEquals(user.getFirstName(), user.getFirstName());
        assertEquals(user.getJob(), user.getJob());
    }

    @Test
    @DisplayName("[PUT] update user")
    void putUpdateUserTest() {
        user.setFirstName(cfgs.createName());
        user.setJob(cfgs.updateJob());
        user =
                given()
                        .spec(requestSpec)
                        .body(user)
                        .when()
                        .put("/user/2")
                        .then()
                        .spec(code200)
                        .extract()
                        .as(User.class);

        assertEquals(user.getJob(), user.getJob());
    }

    @Test
    @DisplayName("[PATCH] update user")
    void patchUpdateUserTest() {
        user.setFirstName(cfgs.createName());
        user.setJob(cfgs.updateJob());
        user =
                given()
                        .spec(requestSpec)
                        .body(user)
                        .when()
                        .patch("/user/2")
                        .then()
                        .spec(code200)
                        .extract()
                        .as(User.class);

        assertEquals(user.getJob(), user.getJob());
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
        user =
                given()
                        .spec(requestSpec)
                        .body(user)
                        .when()
                        .post("/register")
                        .then()
                        .spec(code200)
                        .extract()
                        .as(User.class);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getToken()).isNotNull();
    }

    @Test
    @DisplayName("[POST] register - unsuccessful")
    void postRegisterUnsuccessfulTest() {
        user.setEmail("wrongEmail");
        user =
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

        assertEquals(expectedError, user.getError());
    }

    @Test
    @DisplayName("[POST] login - successful")
    void postLoginSuccessfulTest() {
        user.setEmail(cfgs.crateEmail());
        user.setPassword(cfgs.loginPassword());
        user =
                given()
                        .spec(requestSpec)
                        .body(user)
                        .when()
                        .post("/login")
                        .then()
                        .spec(code200)
                        .extract()
                        .as(User.class);

        assertThat(user.getToken()).isNotNull();
    }

    @Test
    @DisplayName("[POST] login - unsuccessful")
    void postLoginUnsuccessfulTest() {
        user.setEmail("wrongEmail");
        user =
                given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/login")
                .then()
                .spec(code400)
                .extract()
                .as(User.class);

        String expectedError = "Missing password";

        assertEquals(expectedError, user.getError());
    }
}
