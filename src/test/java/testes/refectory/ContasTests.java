package testes.refectory;

import br.com.restassured.core.BaseTest;
import br.com.restassured.utils.GetIdUtils;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


// refectory do testes para um modelo onde os testes são totalmentes independentes uns dos outros
public class ContasTests extends BaseTest {

    @Test
    public void deveIncluirComSucesso() {
        System.out.println("teste incluir com sucesso! ");
          given()
                  .body("{\"nome\": \"Conta inserida\" }")
          .when()
                .post("/contas")
          .then()
                .statusCode(201);
    }
    @Test
    public void deveAlterarContaComSucesso() {
        System.out.println("teste alterar com sucesso!");
Integer CONTA_ID = GetIdUtils.getIdContaPeloNome("Conta para alterar");
        given()
                .body("{\"nome\": \"Conta alterada\"}").
                when()
                .pathParam("id",CONTA_ID)
                .put("/contas/{id}").
                then()
                .statusCode(200)
                .body("nome", is("Conta alterada"));
    }
    @Test
    public void t04_naoDeveIncluirContaExistente() {
        System.out.println("teste conta existente ");
        given()
                .body("{\"nome\": \"Conta mesmo nome\"}").
                when()
                .post("/contas/").
                then()
                .statusCode(400).
                body("error", is("Já existe uma conta com esse nome!"));
    }

}
