package testes.testBasic;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;


import org.junit.Test;

public class VerbosTest {

	@Test
	public void SalvaUsuarioViaJson() {
		given().
		log().all().
		contentType("application/json").
		body("{ \"name\": \"Jose\", \"age\":50}").
		
		when().
		post(" http://restapi.wcaquino.me/users").
		
		then().
		log().all().
		statusCode(201).
		body("id", is(notNullValue())).
		body("name", is("Jose")).
		body("age",is(50));
			
	}

	@Test
	public void SalvaUsuarioViaJsonUsandoMap() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "joao bosco");
		params.put("age", 25);
		
		given().
		log().all().
		contentType("application/json").
	    body(params).
		when().
		post(" http://restapi.wcaquino.me/users").
		
		then().
		log().all().
		statusCode(201).
		body("id", is(notNullValue())).
		body("name", is("joao bosco")).
		body("age",is(25));
	}	
	
	@Test
	public void SalvaUsuarioViaJsonUsandoObjeto() {
		User user = new User("forma via objeto", 23);
		given().
		log().all().
		contentType("application/json").
	    body(user).
		when().
		post(" http://restapi.wcaquino.me/users").
		
		then().
		log().all().
		statusCode(201).
		body("id", is(notNullValue())).
		body("name", is("forma via objeto")).
		body("age",is(23));
	}
	
	

	@Test
	public void NaoDeveSalvaUsuario() {
		given().
		log().all().
		contentType("application/json").
		body("{\"age\":50}").
		
		when().
		post(" http://restapi.wcaquino.me/users").
		
		then().
		log().all().
		statusCode(400);
		//body("error", containsString("Name é um atributo obrigatório"));

		}
		
		
		@Test
		public void DeleteRemoverUsuario() {
			given().
			log().all().
			when().
			delete(" http://restapi.wcaquino.me/users/1")
					.then().
					log().all().
					statusCode(204);
		}
			@Test
			public void DeleteRemoverUsuarioInixistente() {
				given().
				log().all().
				when().
				delete(" http://restapi.wcaquino.me/users/100")
						.then().
						log().all().
						statusCode(400).
		                body("error", is("Registro inexistente"));
		}
	

		
	}
	


