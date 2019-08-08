package servicio.algoritmos.stanford;

import servicio.algoritmos.IAlgoritmo;
import servicio.algoritmos.stanford.analyzer.SentimentAnalyzer;
import servicio.algoritmos.stanford.model.SentimentResult;
import servicio.modelo.Algoritmo;
import servicio.modelo.Sentimiento;
import servicio.modelo.Valoracion;

/**
 * Algoritmo que usa la API de Stanford CoreNLP para realizar
 * un análisis de sentimientos de un texto.
 * @author Diego Valera Duran
 *
 */
public class ControladorStanford implements IAlgoritmo {
	private SentimentAnalyzer sentimentAnalyzer;
	private String nombre;
	private String desc;
	private String algoritmoQuery;
	
	public ControladorStanford(String customModelLocation, String nombre, String desc, String algoritmoQuery) {
		sentimentAnalyzer = new SentimentAnalyzer();
		sentimentAnalyzer.initialize(customModelLocation);
		this.nombre = nombre;
		this.desc = desc;
		this.algoritmoQuery = algoritmoQuery;
	}
	
	public ControladorStanford() {
		this(null, "Stanford CoreNLP", "Algoritmo que usa la API de Stanford CoreNLP para realizar el análisis de sentimientos", "stanford");
	}
	
	@Override
	public String[] getInfoAlgoritmo() {
		String info[] = new String[3];
		info[0] = nombre;
		info[1] = desc;
		info[2] = algoritmoQuery;
		return info;
	}
	
	@Override
	public String getAlgoritmoQuery() {
		return algoritmoQuery;
	}
	
	private Sentimiento convertirSentimiento(String sentimiento) {
		Sentimiento s = Sentimiento.NEUTRAL;
		switch (sentimiento) {
			case "Negative":
				s = Sentimiento.NEGATIVO;
				break;
			case "Very negative":
				s = Sentimiento.MUY_NEGATIVO;
				break;
			case "Neutral":
				s = Sentimiento.NEUTRAL;
				break;
			case "Positive":
				s = Sentimiento.POSITIVO;
				break;
			case "Very positive":
				s = Sentimiento.MUY_POSITIVO;
				break;
		}
		
		return s;
	}
	
	@Override
	public Valoracion analize(String text) {
		SentimentResult sentimentResult = sentimentAnalyzer.getSentimentResult(text);
		
		Sentimiento s = convertirSentimiento(sentimentResult.getSentimentType());
		Algoritmo a = new Algoritmo(nombre, desc);
		Valoracion val = new Valoracion(text, s, a);
		
		return val;
	}
}
