package testes.testBasic;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.FilterableRequestSpecification;

import static io.restassured.RestAssured.requestSpecification;

public class OlaMundo {
public static void main(String[] args) {
	Response request = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
	//Obtendo como resposta do corpo(Body) da requisi��o
	System.out.println(request.getBody().asString());
	//obtendo resposta do StastusCode da requisi��o
	System.out.println(request.getStatusCode());
	//realizando uma validacao do StatusCode
   ValidatableResponse validacao = request.then();
   validacao.statusCode(200);

}

	
}
