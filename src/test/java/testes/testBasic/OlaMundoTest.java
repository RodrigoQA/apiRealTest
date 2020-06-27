package testes.testBasic;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {

	@Test
	public void BaseRestAssured() {
		Response request = RestAssured.request(Method.GET, "http://restapi.wcaquino.me:80/ola");

		Assert.assertTrue(request.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue(request.getStatusCode()== 200);
		Assert.assertTrue("o resultado esperado deve ser 200",(request.getStatusCode() ==200));
		Assert.assertEquals(200, request.getStatusCode());
		ValidatableResponse validacao = request.then();
		validacao.statusCode(200);
	}

	@Test
	public void OutrasFormasDeRestAssured() {
		 get("http://restapi.wcaquino.me/ola").then().statusCode(200);  //Estrura em apenas uma linha

		//Estrutura do Girkin
		        given().
		               // Pr�-Condi��es
				when().get("http://restapi.wcaquino.me/ola").
				then().statusCode(200);

	}

	@Test
	public void ConhecendoMatchersHamcrest() {
		Assert.assertThat("Maria", Matchers.is("Maria"));
		Assert.assertThat(128, Matchers.is(128));              
		Assert.assertThat(128, Matchers.isA(Integer.class));  //verifica se eh do tipo inteiro 
		Assert.assertThat(128d, Matchers.isA(Double.class));  //verfica se eh do tipo Double
		Assert.assertThat(138d, Matchers.greaterThan(130d)); //verificar q 138 eh maior que 130
		Assert.assertThat(120d, Matchers.lessThan(125d));    //verificar q 120 eh menor que 125
	
//Validacao lista (obs:foi utilizido import static em assertThat e hasSize, nao sendo mais necessario utilizar o "Assert" e "Matchers")		
	List<Integer> impares = Arrays.asList(1,3,5,7,9); //List from 1 a 9 just impars
	assertThat(impares, Matchers.hasSize(5)); // valida qual tamanho da lista
	assertThat(impares, contains(1,3,5,7,9)); // (deve seguir a order e todos os valores dvem ser declarados)
	assertThat(impares, containsInAnyOrder(3,9,1,7,5));// em qualquer ordem, todos os valores dvem ser declarados
	assertThat(impares, hasItem(5)); //verifica apenas um valor da lista
	assertThat(impares, hasItems(5,7,9)); //verifica  valores da lista
	
	
	assertThat("Joao paulo",allOf(startsWith("Joao")));//buscar texto q inicia com "Jao"
	assertThat("maria",allOf(endsWith("ria"))); //,termina com "ina"
	assertThat("Joaquin",allOf(containsString("qui"))); // Contem "qui"
	
	}

   @Test
   
   public void devoValidarBody() {
	   given().
       when().get("http://restapi.wcaquino.me/ola").
       then().statusCode(200).
       body(Matchers.is("Ola Mundo!")).  //ou (com import) => body(is("Ola Mundo")); 
       body(containsString("Ola M")).
	   body(is(not (nullValue())));
   }


}




