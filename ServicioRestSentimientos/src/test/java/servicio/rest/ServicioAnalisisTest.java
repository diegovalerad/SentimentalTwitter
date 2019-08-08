package servicio.rest;

import static io.restassured.RestAssured.given;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;

public class ServicioAnalisisTest {

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
			basePath = "ServicioRestSentimientos/rest/";
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
	public void analizeTextTest() {
		given().when().get("analisis/analize?texto=The%20movie%20was%20boring").then().assertThat().statusCode(200);
	}
	
	@Test
	public void getApiParametersTest() {
		given().when().get("analisis/api-parameters").then().assertThat().statusCode(200);
	}

}
