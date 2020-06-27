package br.com.restassured.core;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;

public class BaseTest implements Constantes {

    @BeforeClass
    public static void setup(){
        System.out.println("Before setup...");
        RestAssured.baseURI = APP_BASE_URL;
        RestAssured.port = APP_PORT;
        RestAssured.basePath = APP_BASE_PATH;

        RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
        requestBuilder.setContentType(APP_CONTENT_TYPE);
        RestAssured.requestSpecification =requestBuilder.build();

        ResponseSpecBuilder responseBuilder = new ResponseSpecBuilder();
        responseBuilder.expectResponseTime(Matchers.lessThan(MAX_TIMEOUT));
        RestAssured.responseSpecification = responseBuilder.build();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }
}
