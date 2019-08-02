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
	
	private ConectorSentimentAnalizer() {}
	
	public static ConectorSentimentAnalizer getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ConectorSentimentAnalizer();
		return unicaInstancia;
	}

	public String getSentiment(String texto) {
		String baseUrl = "http://localhost:8080/ServicioRestSentimientos/rest/analisis/analize?texto=";

		String encodedQuery = encodeValue(texto);

		String completeUrl = baseUrl + encodedQuery;
		String sentimentAlgorithm = "default";
		completeUrl += "&algoritmo=" + sentimentAlgorithm;
		
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
