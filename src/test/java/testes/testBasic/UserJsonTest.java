package testes.testBasic;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.restassured.core.BaseTest;
import org.junit.Assert;
import org.junit.Test;


public class UserJsonTest {
	
	
	@Test
	public void verificarPrimeiroNivel() {
		given().
		when().get("http://restapi.wcaquino.me/users/1").
		then().
		statusCode(200).
		body("id", is(1)).
//		body("name", is("João da Silva")).
		body("age", greaterThan(18)); //valida que a idade eh maior que 18
	
	}

	@Test
	public void verificarSegundoNivel() {
		given().
		when().get("http://restapi.wcaquino.me/users/2").
		then().
		statusCode(200).
		body("name", containsString("Joaquina")). 
		body("endereco.rua", is("Rua dos bobos"))
		
		;
}

	@Test
	public void TestandoLista() {
		given().
		when().get("http://restapi.wcaquino.me/users/3").
		then().
		statusCode(200).
		body("name", containsString("Ana")).
		body("age", is(20)).
		body("filhos", hasSize(2)).
		body("filhos[0].name", is("Zezinho")).
		body("filhos[1].name", is("Luizinho")).
		body("filhos.name", hasItem("Luizinho")).
		body("filhos.name", hasItems("Luizinho","Zezinho"))
		
		
		;
	}
	@Test
	public void pageNaoEncontrada404() {
		
		given().
		when().get("http://restapi.wcaquino.me/users/4").
		then().
	    statusCode(404);
//		body("error", is("Usuário inexistente"));
		
}
	
	@Test
	public void testandoListRaiz() {
		
		given().
		when().get("http://restapi.wcaquino.me/users/").
		then().
	    statusCode(200).
		body("$", hasSize(3)).
//		body("name", hasItems("Maria Joaquina","João da Silva")).
		body("age[2]", is(20)).
		body("filhos.name", hasItem(Arrays.asList("Zezinho","Luizinho"))).
		body("salary", contains(1234.5678f, 2500, null)).
		body("salary[1]", is(2500))
		
		;
		
}

@Test 
  
public void TestesVerificacoesAvancadas() {
	
	given().
	when().get("http://restapi.wcaquino.me/users/").
	then().   
    statusCode(200).
	body("$", hasSize(3)). //Tamanho da lita raiz
	body("age.findAll{it <= 25}.size()", is(2)). //Retorna total de idade menor ou igual a 25 anos
	body("age.findAll{it <= 25 && it >20 }.size()", is(1)). //Retorna total de idade que estao entre 21 a 25 anos
	body("findAll{it.age <= 25 && it.age > 20}.name", hasItem("Maria Joaquina")). // retorna o nome q esta entre 21 a 25 anos
	body("findAll{it.age <= 25}[0].name", is("Maria Joaquina")).  // 0 buscar do inicio da lista para baixo(ordem crescente)
//	body("findAll{it.age <= 25}[-1].name", is("Ana Júlia")).  //-1 busca do fim da lista para o topo(ordem decrecente)
	body("find{it.age <= 25}.name", is("Maria Joaquina")).  //find busca sempre o primeiro item, msm tendo mais de um (Em ordem crescente)
	//body("findAll{it.name.length() > 10}.name", hasItems("Maria Joaquina", "João da Silva")). //busca por nomes com + de 10 caracteres
//	body("name.collect{it.toUpperCase()}",hasItem("JOÃO DA SILVA")). //transforma em caracter maiusculos
	body("age.collect{it * 2 }", hasItems(60,50,40)). // mutilplicando a idade por 2
	body("id.max()",is(3)). //buscar pelo id maior
	body("age.max()", is(30)).//buscar pela idade maior
	body("id.min()", is(1)). //buscar pelo id menor
	body("age.min()", is(20)). //buscar pela idade menor
	body("salary.min()",is(1234.5678f)). //buscar pelo salario menor ("f" indica que eh do tipo float)
	body("salary.findAll{it != null}.sum()",is(closeTo(3734.5678f, 0.01)));//com o closeTo, resolve!(ele da um desconto tanto pra + ou pra -)

	//body("salary.sum()",is(3734.5678f))   devido ter um salary vazio na lista, nao funciona
	//body("findAll{it.salary =! null}.sum()", is(3734.5678f)). //isso resolve o problema acima, mas agora tem o erro no calculo,(decimos de diferen?a)

	
	
}
	

@Test 
  
public void TesteJsonPathComJava() {
	ArrayList<String> names =
	given().
	when().get("http://restapi.wcaquino.me/users/").
	then().
    statusCode(200).
	body("$", hasSize(3)).
	extract().path("name.findAll{it.endsWith('lia')}");//
	Assert.assertEquals(1, names.size());  //valida que existe apenas um nome q inicia com nome Maria
System.out.println(names);
}
}