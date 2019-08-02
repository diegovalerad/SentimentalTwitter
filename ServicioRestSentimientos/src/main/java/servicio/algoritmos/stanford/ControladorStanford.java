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
	
	public ControladorStanford() {
		sentimentAnalyzer = new SentimentAnalyzer();
		sentimentAnalyzer.initialize();
	}
	
	@Override
	public Algoritmo getInfoAlgoritmo() {
		String nombre = "Stanford CoreNLP";
		String desc = "Algoritmo que usa la API de Stanford CoreNLP para realizar el análisis de sentimientos";
		Algoritmo a = new Algoritmo(nombre, desc);
		return a;
	}
	
	@Override
	public String getAlgoritmoQuery() {
		return "stanfordCoreNLP";
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
		Algoritmo a = getInfoAlgoritmo();
		Valoracion val = new Valoracion(s, a);
		
		return val;
	}
}
