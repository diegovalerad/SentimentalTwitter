package servicio.algoritmos.stanford;

import servicio.algoritmos.AlgoritmosFactoria;
import servicio.algoritmos.stanford.analyzer.SentimentAnalyzer;
import servicio.algoritmos.stanford.model.SentimentResult;
import servicio.modelo.Sentimiento;
import servicio.modelo.Valoracion;

/**
 * Algoritmo que usa la API de Stanford CoreNLP para realizar
 * un análisis de sentimientos de un texto.
 * @author Diego Valera Duran
 *
 */
public class StanfordFactoria extends AlgoritmosFactoria {
	private SentimentAnalyzer sentimentAnalyzer;
	
	public StanfordFactoria() {
		sentimentAnalyzer = new SentimentAnalyzer();
		sentimentAnalyzer.initialize();
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
	
	private String obtenerExplicacion(SentimentResult sentimentResult) {
		String exp = "";
		
		double muyPositivo = sentimentResult.getSentimentClass().getVeryPositive();
		double positivo = sentimentResult.getSentimentClass().getPositive();
		double neutral = sentimentResult.getSentimentClass().getNeutral();
		double negativo = sentimentResult.getSentimentClass().getNegative();
		double muyNegativo = sentimentResult.getSentimentClass().getVeryNegative();
		
		exp += "Muy negativo: " + muyNegativo + "%\n";
		exp += "Negativo: " + negativo + "%\n";
		exp += "Neutral: " + neutral + "%\n";
		exp += "Positivo: " + positivo + "%\n";
		exp += "Muy positivo: " + muyPositivo + "%\n";
		
		return exp;
	}
	
	@Override
	public Valoracion analizeText(String text) {
		SentimentResult sentimentResult = sentimentAnalyzer.getSentimentResult(text);
		
		Valoracion val = new Valoracion();
		Sentimiento s = convertirSentimiento(sentimentResult.getSentimentType());
		String explicacion = obtenerExplicacion(sentimentResult);
		
		val.setSentimiento(s);
		val.setExplicacion(explicacion);
		
		return val;
	}
}
