package servicio.algoritmos.stanford.analyzer;

import java.util.Properties;

import org.ejml.simple.SimpleMatrix;


import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import servicio.algoritmos.stanford.model.SentimentClassification;
import servicio.algoritmos.stanford.model.SentimentResult;

public class SentimentAnalyzer {

	/*
	 * "Very negative" = 0 "Negative" = 1 "Neutral" = 2 "Positive" = 3
	 * "Very positive" = 4
	 */

	private Properties props;
	private StanfordCoreNLP pipeline;

	public void initialize() {
		 // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and sentiment
		props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		//String file = "stanfordES/model.ser.gz";
		//String model = getClass().getClassLoader().getResource(file).getFile(); 
		//props.setProperty("sentiment.model", model);
		pipeline = new StanfordCoreNLP(props);
	}

	public SentimentResult getSentimentResult(String text) {

		SentimentResult sentimentResult = new SentimentResult();
		SentimentClassification sentimentClass = new SentimentClassification();

		if (text != null && text.length() > 0) {
			
			// run all Annotators on the text
			Annotation annotation = pipeline.process(text);

			for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
				// this is the parse tree of the current sentence
				Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
				SimpleMatrix sm = RNNCoreAnnotations.getPredictions(tree);
				String sentimentType = sentence.get(SentimentCoreAnnotations.SentimentClass.class);

				sentimentClass.setVeryPositive((double)Math.round(sm.get(4) * 100d));
				sentimentClass.setPositive((double)Math.round(sm.get(3) * 100d));
				sentimentClass.setNeutral((double)Math.round(sm.get(2) * 100d));
				sentimentClass.setNegative((double)Math.round(sm.get(1) * 100d));
				sentimentClass.setVeryNegative((double)Math.round(sm.get(0) * 100d));
				
				System.out.println("very positive: " + sentimentClass.getVeryPositive() + "%");
				System.out.println("positive: " + sentimentClass.getPositive() + "%");
				System.out.println("neutral: " + sentimentClass.getNeutral() + "%");
				System.out.println("negative: " + sentimentClass.getNegative() + "%");
				System.out.println("very negative: " + sentimentClass.getVeryNegative() + "%");
				
				sentimentResult.setSentimentScore(RNNCoreAnnotations.getPredictedClass(tree));
				sentimentResult.setSentimentType(sentimentType);
				sentimentResult.setSentimentClass(sentimentClass);
			}

		}


		return sentimentResult;
	}
	
}