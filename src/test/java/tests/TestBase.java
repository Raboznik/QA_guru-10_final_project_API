package tests;

import config.Credentials;
import io.restassured.RestAssured;
import models.LombokModel;
import models.User;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    Credentials cfgs = ConfigFactory.create(Credentials.class);
    User user = new User();
    LombokModel responseLombok;
    User responseUser;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in";
    }
}
