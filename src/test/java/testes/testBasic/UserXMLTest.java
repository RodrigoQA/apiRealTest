package testes.testBasic;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;

public class UserXMLTest {


@BeforeClass
public static void setUp() {
	RestAssured.baseURI = "http://restapi.wcaquino.me";
	//RestAssured.port = 80;
	//RestAssured.BasePath = "";
}
@Test
	public void  TrabalhandoComXML() {
	
	given().
	when().
	get("/usersXML/3").
	then().
	statusCode(200).
	body("user.name", is("Ana Julia")).  
	body("user.@id", is("3")).
	body("user.filhos.name.size()", is(2)). //valida o tamanho da lista
	body("user.filhos.name[0]", is("Zezinho")). // 0 para buscar na ordem crecente
	body("user.filhos.name[-1]", is("Luizinho")). // -1 para buscar na ordem decrecente
	body("user.filhos.name", hasItem("Zezinho")). //valida 1 dos itens q esteja na lista
	body("user.filhos.name", hasItems("Zezinho","Luizinho")).  //valida alguns dos itens q esteja na lista
	
	//outras formas  rootPath/detachRootPath/appendRootPath
	statusCode(200).
	rootPath("user").
	body("name", is("Ana Julia")).  
	body("@id", is("3")).
	
	rootPath("user.filhos").
	body("name.size()", is(2)). //valida o tamanho da lista
	body("name[0]", is("Zezinho")). // 0 para buscar na ordem crecente
	body("name[-1]", is("Luizinho")). // -1 para buscar na ordem decrecente


	detachRoot("filhos").
	body("filhos.name", hasItem("Zezinho")). //valida 1 dos itens q esteja na lista
	
	
	appendRoot("filhos").
	body("name", hasItems("Zezinho","Luizinho"))  //valida alguns dos itens q esteja na lista
	;
	 	
}

@Test

public void PesquisasAvancadasCOmXML() {
		

	given().
	when().
	get("/usersXML").
	then().
	statusCode(200).
	body("users.user.size()", is(3)). //valida o tamanho da lista raiz (total de user)
	body("users.user.findAll{it.age.toInteger() <= 25}.size()",is (2)).
    body("users.user.@id",hasItems("1","2","3")).
    body("users.user.find{it.age == 25}.name", is("Maria Joaquina")). //buscar idade = 25 
    body("users.user.findAll{it.name.toString().contains('n')}.name", hasItems("Maria Joaquina","Ana Julia")).
    body("users.user.salary.find{it != null}.toDouble()",is(1234.5678d)).
	//body("users.user.salary.find{it != null}",is("1234.5678"))
    body("users.user.age.collect{it.toInteger() * 2}", hasItems(60,50,40))
    ;
}

@Test

public void PesquisasAvancadasComXMLEJava() {
		

	String nome = given().
	when().
	get("/usersXML").
	then().
	statusCode(200).
	extract().path("users.user.name.findAll{it.toString().startsWith('Maria')}");
System.out.println(nome);

}

@Test

public void PesquisasAvancadasComXPath() {
		
	given().
	when().
	get("/usersXML").
	then().
	statusCode(200).
	body(hasXPath("count(/users/user)",is("3"))).
	body(hasXPath("/users/user[@id = '1']")).
	body(hasXPath("//user[@id= '1']")).
	body(hasXPath("//name[text() = 'Luizinho']/../../name", is("Ana Julia"))).
	body(hasXPath("//name[text() ='Ana Julia']/following-sibling::filhos", allOf(containsString("Luizinho"),containsString("Zezinho")))).
 	body(hasXPath("/users/user/name", is("João da Silva"))).
    body(hasXPath("//name", is("João da Silva"))). //buscando pelo primeiro nome da page
    body(hasXPath("/users/user[2]/name", is("Maria Joaquina"))). //buscando pelo segundo nome da page
    body(hasXPath("count(/users/user/name[contains(.,'n')])", is("2"))).  //buscando todos q contem a letra n
    body(hasXPath("(/users/user[age < 24]/name)", is("Ana Julia"))).
    body(hasXPath("(/users/user[age > 20 and age < 30 ]/name)", is("Maria Joaquina")))
    ;
}

}