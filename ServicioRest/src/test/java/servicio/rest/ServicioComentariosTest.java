package servicio.rest;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ServicioComentariosTest {

	@BeforeClass
	public static void setup() {

		String port = System.getProperty("server.port");
		if (port == null) {
			RestAssured.port = Integer.valueOf(8080);
		} else {
			RestAssured.port = Integer.valueOf(port);
		}

		String basePath = System.getProperty("server.base");
		if (basePath == null) {
			basePath = "ServicioRest/rest/";
		}
		RestAssured.basePath = basePath;

		String baseHost = System.getProperty("server.host");
		if (baseHost == null) {
			baseHost = "http://localhost";
		}
		RestAssured.baseURI = baseHost;
	}

	@Test
	public void getPathErroneoTest() {
		given().when().get("themes").then().assertThat().statusCode(404);
	}

	@Test
	public void getListadoTemasTest() {

		given().when().get("temas").then().assertThat().statusCode(200);
	}

	@Test
	public void getTemaTest() {

		given().expect().body("nombre", equalTo("pathology")).statusCode(200).when()
				.get("temas/84c23968-3b58-4f7c-8070-0f0146669394");
	}

	@Test
	public void getTemaNoExistenteTest() {

		given().when().get("temas/oisdjfoig").then().assertThat().statusCode(404);
	}

	@Test
	public void getComentarioTest() {

		given().
		expect().body("creador", equalTo("@marcialangton")).statusCode(200).
		when().get("temas/84c23968-3b58-4f7c-8070-0f0146669394/1029284211918659584");
	}

	@Test
	public void getComentarioNoExistenteTest() {

		given().when().get("temas/oisdjfoig/321654").then().assertThat().statusCode(404);
	}
	
	@Test
	public void crearTemaExistenteTest() {
		
		given()
		.expect().statusCode(500)
		.when().post("temas/colorectal");
		
	}
	
	@Test
	public void crearTemaNuevoTest() {
		
		given()
		.expect().statusCode(201)
		.when().post("temas/lymphoma");
	}
	
	@Test
	public void buscarComentariosConsultaErroneaTest() {
		given().
		when().get("temas/search?since=null&cond=OR&cond=AND").
		then().assertThat().statusCode(400);
	}
	
	@Test
	public void buscarComentariosSinRespuestaTest() {
		given().when().get("temas/search?tema=84c23968-3b58-4f7c-8070-0f0146669394&tema=14cdc884-f10b-48dd-9ec4-49c50e0060ce&cond=AND")
		.then().assertThat().statusCode(404);
	}
	
	@Test
	public void buscarComentariosCorrectoTest() {
		given().when().get("temas/search?tema=84c23968-3b58-4f7c-8070-0f0146669394")
		.then().assertThat().statusCode(200);
	}
}
