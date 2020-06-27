package br.com.restassured.utils;

import io.restassured.RestAssured;

public class GetIdUtils {
    public static Integer getIdContaPeloNome(String nome){
        return RestAssured.get("/contas?nome="+nome).then().extract().path("id[0]");
    }

    public static Integer getIdMovPelaDescriscao(String desc){
        return RestAssured.get("/transacoes?descricao="+desc).then().extract().path("id[0]");
    }
}
