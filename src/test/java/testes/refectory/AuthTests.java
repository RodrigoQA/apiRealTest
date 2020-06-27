package testes.refectory;

import br.com.restassured.core.BaseTest;
import io.restassured.specification.FilterableRequestSpecification;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.is;


// refectory do testes para um modelo onde os testes são totalmentes independentes uns dos outros
public class AuthTests extends BaseTest {

    @Test
    public void naoDeveAcessarApiSemToken() {
        System.out.println("teste acessar sem token");
        FilterableRequestSpecification req = (FilterableRequestSpecification) requestSpecification;
        req.removeHeader("Authorization");
        given()
                .when().get("/contas").
                then().statusCode(401);
    }
}
