package br.com.restassured.test.refectory;

import br.com.restassured.core.BaseTest;
import br.com.restassured.utils.GetIdUtils;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.is;


// refectory do testes para um modelo onde os testes são totalmentes independentes uns dos outros
public class SaldoTests extends BaseTest {

    @Test
    public void deveCalcularSaldoConta() {
        System.out.println("teste calculo do saldo");
        Integer CONTA_ID = GetIdUtils.getIdContaPeloNome("Conta para saldo");
        given()

                .when()
                .get("/saldo").
                then()
                .statusCode(200)
                .body("find{it.conta_id == "+CONTA_ID+"}.saldo", is("534.00"));

    }


}
