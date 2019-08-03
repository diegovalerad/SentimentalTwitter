package servicio.controlador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.json.JSONException;
import org.json.JSONObject;

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
	

	public String getSentiment(String texto) {
		String encodedText = encodeValue(texto);
		String completeUrl = baseURL + URL_method + queryText + "=" + encodedText + "&" + queryAlgorithm + "=" + queryAlgorithmDefault;
		
		String sentimiento = null;
		boolean errorObtenerSentimiento = false;

		JSONObject json;
		try {
			json = readJsonFromUrl(completeUrl);
			sentimiento = json.getString("sentimiento");
		} catch (IOException e) {
			System.err.println("Error al conectar con el servidor analizador: " + e);
			errorObtenerSentimiento = true;
		} catch (JSONException e) {
			System.err.println("Error al parsear con JSON la respuesta del servidor analizador: " + e);
			errorObtenerSentimiento = true;
		}
		if (errorObtenerSentimiento)
			sentimiento = "";
		
		return sentimiento;
	}
	
	
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			char c = (char) cp;
			if (c != '[' && c != ']')
			sb.append((char) cp);
		}
		return sb.toString();
	}

	private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	private static String encodeValue(String value) {
		try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex.getCause());
		}
	}
}
