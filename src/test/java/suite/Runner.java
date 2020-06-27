package suite;

import br.com.restassured.core.BaseTest;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import testes.refectory.*;
import testes.testBasic.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

@RunWith(Suite.class)

@Suite.SuiteClasses({

        ApiRealTests.class,
        ContasTests.class,
        MovimentacoesTests.class,
        SaldoTests.class,
        AuthTests.class,



})
public class Runner extends BaseTest {

    @BeforeClass
    public static void login() {
        System.out.println("Before login...");
        //declarando login e senha
        Map<String, String> login = new HashMap();
        login.put("email", "rodrigolima.ads@gmail.com");
        login.put("senha", "123456");

        //fazendo login na aplicacao para gerar o token
        String TOKEN = given().
                body(login).
                when().post("/signin").
                then().statusCode(200).
                extract().path("token");
        requestSpecification.header("Authorization", "JWT " + TOKEN);
        RestAssured.get("/reset").then().statusCode(200);
    }

}
