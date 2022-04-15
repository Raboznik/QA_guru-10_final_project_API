package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;


public class Specs {

    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplates())
            .basePath("/api")
            .contentType(JSON);

    public static ResponseSpecification code404 = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .build();

    public static ResponseSpecification code400 = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .build();

    public static ResponseSpecification code204 = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .build();

    public static ResponseSpecification code200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification code201 = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .build();
}