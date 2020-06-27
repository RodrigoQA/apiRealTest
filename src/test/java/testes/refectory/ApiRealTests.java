package testes.refectory;

import br.com.restassured.core.BaseTest;
import br.com.restassured.utils.DataUtils;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

// Estrategia onde os teste devem ser executado em sequencia, uma esteria onde um depende do outro.
public class ApiRealTests extends BaseTest {

    private static String CONTA_NOME = "Rodrigo" + System.nanoTime();
    private static Integer CONTA_ID;
    private static Integer MOV_ID;

    @Test
    public void t02_deveIncluirComSucesso_ComToken() {
        System.out.println("teste incluir com sucesso!");
        CONTA_ID = given()

                .body("{\"nome\": \""+CONTA_NOME+"\"}").
                 when()
                .post("/contas").
                then()
                .statusCode(201)
                .extract().path("id");
    }

    @Test
    public void t03_deveAlterarContaComSucesso() {
        System.out.println("teste alterar com sucesso!");
        given()
                .body("{\"nome\": \""+CONTA_NOME+"alterada\"}").
                when()
                .pathParam("id",CONTA_ID)
                .put("/contas/{id}").
                then()
                .statusCode(200)
                .body("nome", is(""+CONTA_NOME+"" +"alterada"));
    }

    @Test
    public void t04_naoDeveIncluirContaExistente() {
        System.out.println("teste conta existente ");
        given()
                .body("{\"nome\": \""+CONTA_NOME+"alterada\"}").
                when()
                .post("/contas/").
                then()
                .statusCode(400).
                body("error", is("Já existe uma conta com esse nome!"));
    }

    @Test
    public void t05_deveInserirMovimentacaoComSucesso() {
        System.out.println("teste inserir movimentacao com sucesso!");
        Movimentacao mov = getMotimentacaoValida();

      MOV_ID =  given()
                .body(mov).
                when()
                .post("/transacoes").
                then()
                .statusCode(201)
                .extract().path("id");

    }

    @Test
    public void t06_deveValidarCamposObrigatoriosMovimentacao() {
        System.out.println("teste validacao dos campos obrigatorios");

        given()
                .body("{}").
                when()
                .post("/transacoes").
                then()
                .statusCode(400)
                .body("$", hasSize(8))
                .body("msg", hasItems(
                        "Data da Movimentação é obrigatório",
                        "Data do pagamento é obrigatório",
                        "Descrição é obrigatório",
                        "Interessado é obrigatório",
                        "Valor é obrigatório",
                        "Valor deve ser um número",
                        "Conta é obrigatório",
                        "Situação é obrigatório"
                ));
    }

    @Test
    public void t07_naodeveInserirMovimentacaoComDataFutura() {
        System.out.println("teste movimentacao com data futura");
        Movimentacao mov = getMotimentacaoValida();
        mov.setData_transacao(DataUtils.getDataDiferencaDias(2));


        given()
                .body(mov).
                when()
                .post("/transacoes").
                then()
                .statusCode(400)
                .body("$", hasSize(1))
                .body("msg", hasItems("Data da Movimentação deve ser menor ou igual à data atual"));

    }


    @Test
    public void t08_naoDeveDeletarContaComMovimentacao() {
        System.out.println("teste deletar conta com movimentacao");
        given()

                .when()
                .pathParam("id", CONTA_ID)
                .delete("/contas/{id}")
                .then()
                .statusCode(500)
                .body("name", is("error"));

    }
    @Test
    public void t09_deveCalcularSaldoConta() {
        System.out.println("teste calculo do saldo");
        given()

                .when()
                .get("/saldo").
                then()
                .statusCode(200)
                .body("find{it.conta_id == "+CONTA_ID+"}.saldo", is("1000.00"));

}
    @Test
    public void t10_deveRemoverMovimentacao() {
        System.out.println("teste remover movimentacao ");
        given()

                .when()
                .pathParam("id", MOV_ID)
                .delete("/transacoes/{id}")
                .then()
                .statusCode(204);

    }

    public void t11_naoDeveAcessarApiSemToken() {
        System.out.println("teste acessar sem token");
        FilterableRequestSpecification req = (FilterableRequestSpecification) requestSpecification;
        req.removeHeader("Authorization");
        given()
                .when().get("/contas").
                then().statusCode(401);
    }
private  Movimentacao getMotimentacaoValida(){
    Movimentacao mov = new Movimentacao();
    mov.setConta_id(CONTA_ID);
    //mov.setUsuario_id(2788);
    mov.setDescricao("descricao mov2");
    mov.setEnvolvido("Envolvido");
    mov.setTipo("REC");
    mov.setData_transacao(DataUtils.getDataDiferencaDias(-1));
    mov.setData_pagamento(DataUtils.getDataDiferencaDias(5));
    mov.setValor(1000f);
    mov.setStatus(true);
    return mov;
}
}
