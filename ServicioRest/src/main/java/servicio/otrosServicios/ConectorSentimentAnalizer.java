package servicio.otrosServicios;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import servicio.modelo.Valoracion;

public class ConectorSentimentAnalizer {
	private static ConectorSentimentAnalizer unicaInstancia;
	
	private String baseURL;
	private String URL_method;
	private String queryText;
	private String queryAlgorithm;
	private String queryAlgorithmDefault;
	
	private ConectorSentimentAnalizer() {}
	
	public static ConectorSentimentAnalizer getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ConectorSentimentAnalizer();
		return unicaInstancia;
	}
	
	/**
	 * 
	 * Ejemplo: <p>
	 * http://localhost:8080/ServicioRestSentimientos/rest/analisis/analize/texto=Texto para analizar&algoritmo=stanford
	 * 
	 * @param baseURL URL base: http://localhost:8080/ServicioRestSentimientos/rest/analisis/
	 * @param URL_method Método de la URL: analize?
	 * @param queryText Query del texto a analizar: texto
	 * @param queryAlgorithm Query del algoritmo a usar: algoritmo
	 * @param queryAlgorithmDefault Algoritmo a usar: stanford
	 */
	public void inicializar(String baseURL, String URL_method, String queryText, String queryAlgorithm,
			String queryAlgorithmDefault) {
		
		this.baseURL = baseURL;
		this.URL_method = URL_method;
		this.queryText = queryText;
		this.queryAlgorithm = queryAlgorithm;
		this.queryAlgorithmDefault = queryAlgorithmDefault;
	}
	
	/**
	 * Realiza una petición al Servicio Analizador de sentimientos para analizar un texto y devuelve su sentimiento en forma de String
	 * @param texto Texto a analizar
	 * @return Cadena de texto con el sentimiento. Si no se ha podido realizar la petición, se devuelve una cadena vacía.
	 */
	public String getSentiment(String texto) {
		String encodedText = encodeValue(texto);
		String completeUrl = baseURL + URL_method + queryText + "=" + encodedText + "&" + queryAlgorithm + "=" + queryAlgorithmDefault;
		String sentimiento;
		List<Valoracion> valoraciones = getList(completeUrl, Valoracion.class);
		
		if (valoraciones == null)
			sentimiento = "";
		else {
			sentimiento = valoraciones.get(0).getSentimiento().toString();
		}
		
		return sentimiento;
	}
	
	/**
	 * Obtiene una lista de tipo T, como resultado de una petición a la URL
	 * @param <T> Tipo genérico T
	 * @param url URL a la que realizar la petición
	 * @param clazz Clase a utilizar sobre la genérica T.
	 * @return
	 */
	private static <T> List<T> getList(String url, Class<T> clazz) {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet(url);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writerWithDefaultPrettyPrinter();

		List<T> data = null;

		HttpResponse response;
		try {
			response = client.execute(getRequest);
			data = objectMapper.readValue(response.getEntity().getContent(),
					objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz));
		} catch (IOException ex) {
			System.err.println(ex);
		}
		return data;
	}
	
	/**
	 * Codificada un texto de entrada en UTF8
	 * @param texto Texto de entrada
	 * @return texto codificado
	 */
	private static String encodeValue(String texto) {
		try {
			return URLEncoder.encode(texto, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex.getCause());
		}
	}
}
