package servicio.algoritmos.stanford;

import servicio.algoritmos.AlgoritmosFactoria;
import servicio.algoritmos.stanford.analyzer.SentimentAnalyzer;
import servicio.algoritmos.stanford.model.SentimentResult;
import servicio.modelo.Sentimiento;
import servicio.modelo.Valoracion;

public class StanfordFactoria extends AlgoritmosFactoria {
	
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
		Sentimiento tipo = convertirSentimiento(sentimentResult.getSentimentType());
		String exp = "Sentimiento " + tipo + " con una puntuación de " + sentimentResult.getSentimentScore() + ".\n";
		
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
	
	public Valoracion analizeText(String text) {
		System.out.println("Stanford factoria, analizando texto: " + text);
		
		SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
		sentimentAnalyzer.initialize();
		SentimentResult sentimentResult = sentimentAnalyzer.getSentimentResult(text);

		System.out.println("Sentiment Score: " + sentimentResult.getSentimentScore());
		System.out.println("Sentiment Type: " + sentimentResult.getSentimentType());
		System.out.println("Very positive: " + sentimentResult.getSentimentClass().getVeryPositive()+"%");
		System.out.println("Positive: " + sentimentResult.getSentimentClass().getPositive()+"%");
		System.out.println("Neutral: " + sentimentResult.getSentimentClass().getNeutral()+"%");
		System.out.println("Negative: " + sentimentResult.getSentimentClass().getNegative()+"%");
		System.out.println("Very negative: " + sentimentResult.getSentimentClass().getVeryNegative()+"%");
		
		Valoracion val = new Valoracion();
		Sentimiento s = convertirSentimiento(sentimentResult.getSentimentType());
		String explicacion = obtenerExplicacion(sentimentResult);
		
		val.setSentimiento(s);
		val.setExplicacion(explicacion);
		
		return val;
	}
}
