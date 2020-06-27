package br.com.wcaquino.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

public class EnvioDadosTest {
	
	@Test 
	public void deveEnviarValorViaQuery() {
		
	
given().
log().all().
when().
get("http://restapi.wcaquino.me/v2/users?format=json").
then().
log().all().
statusCode(200).
contentType(ContentType.JSON)
;
	
}

	@Test 
	public void deveEnviarValorViaQueryViaParams() {
		
	
 given().
log().all().
queryParam("name", "Maria Joaquina").
queryParam("format", "json").
when().
get("http://restapi.wcaquino.me/v2/users").
then().
log().all().
statusCode(200).

contentType(ContentType.JSON).
contentType(containsString("utf-8"))
;
	}

	@Test 
	public void  deveEnviarValorViaHeader() {
		
	
 given().
log().all().
accept(ContentType.XML).
when().
get("http://restapi.wcaquino.me/v2/users").
then().
log().all().
statusCode(200).

contentType(ContentType.XML)	
;

	

}




}

