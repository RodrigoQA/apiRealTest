package br.com.restassured.test.refectory;

import br.com.restassured.core.BaseTest;
import test.tests.Movimentacao;
import br.com.restassured.utils.DataUtils;
import br.com.restassured.utils.GetIdUtils;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


// refectory do testes para um modelo onde os testes são totalmentes independentes uns dos outros
public class MovimentacoesTests extends BaseTest {

    @Test
    public void deveInserirMovimentacaoComSucesso() {
        System.out.println("teste inserir movimentacao com sucesso!");
        Movimentacao mov = getMotimentacaoValida();

                given()
                .body(mov).
                        when()
                .post("/transacoes").
                        then()
                .statusCode(201);


    }

    @Test
    public void deveValidarCamposObrigatoriosMovimentacao() {
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
    public void NaodeveInserirMovimentacaoComDataFutura() {
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
    public void NaoDeveDeletarContaComMovimentacao() {
        System.out.println("teste deletar conta com movimentacao");
        given()

                .when()
                .pathParam("id", GetIdUtils.getIdContaPeloNome("Conta com movimentacao"))
                .delete("/contas/{id}")
                .then()
                .statusCode(500)
                .body("name", is("error"));

    }
    @Test
    public void DeveRemoverMovimentacao() {
        System.out.println("teste remover movimentacao ");
       Integer MOV_ID = GetIdUtils.getIdMovPelaDescriscao("Movimentacao para exclusao");
        given()

                .when()
                .pathParam("id", MOV_ID)
                .delete("/transacoes/{id}")
                .then()
                .statusCode(204);

    }
    private  Movimentacao getMotimentacaoValida(){
        Movimentacao mov = new Movimentacao();
        mov.setConta_id(GetIdUtils.getIdContaPeloNome("Conta para movimentacoes"));
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
