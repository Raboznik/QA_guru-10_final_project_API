package tests;

import config.Credentials;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    Credentials cfgs = ConfigFactory.create(Credentials.class);

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in";

    }
}
