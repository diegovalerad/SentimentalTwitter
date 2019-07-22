package servicio.algoritmos.basedDictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import servicio.algoritmos.AlgoritmosFactoria;
import servicio.modelo.Sentimiento;
import servicio.modelo.Valoracion;

/**
 * Algoritmo basado en un diccionario de palabras, el cual contiene palabras junto
 * a un valor numerico entre -5 y 5, indicando lo negativo o positivo que es dicha 
 * palabra.
 * @author Diego Valera Duran
 *
 */
public class BasedDictionary extends AlgoritmosFactoria {
	private final String folder = "basedDictionary/";
	private final String stopWordsFile = folder + "stopwords.txt";
	private final String afinnFile = folder + "AFINN.txt";
	private List<String> stopWords;
	private Map<String, String> afinn;

	public BasedDictionary() {
		try {
			getStopWords();
		} catch (IOException e) {
			System.err.println("Error al acceder al fichero stop words");
		}
		try {
			getAFINN();
		} catch (IOException e) {
			System.err.println("Error al acceder al fichero AFINN");
		}
	}

	/**
	 * Método que obtiene una lista de palabras usadas normalmente, que no tienen
	 * ningún sentimiento asociado. Así evitamos analizarlas luego.
	 * @throws IOException Excepción en caso de no encontrar el fichero.
	 */
	private void getStopWords() throws IOException {
		stopWords = new ArrayList<String>();
		File file = new File(getClass().getClassLoader().getResource(stopWordsFile).getFile());

		BufferedReader stop = null;
		stop = new BufferedReader(new FileReader(file));
		String line = "";
		while ((line = stop.readLine()) != null) {
			stopWords.add(line);
		}
		stop.close();
	}

	/**
	 * Método que obtiene una lista de palabras con su sentimiento asociado. El sentimiento
	 * es un valor número entre -5 (muy negativo) y 5 (muy positivo). 
	 * @throws IOException Excepción en caso de no encontrar el fichero.
	 */
	private void getAFINN() throws IOException {
		afinn = new HashMap<String, String>();
		File file = new File(getClass().getClassLoader().getResource(afinnFile).getFile());
		BufferedReader in = null;
		in = new BufferedReader(new FileReader(file));

		String line = "";
		while ((line = in.readLine()) != null) {
			String parts[] = line.split("\t");
			afinn.put(parts[0], parts[1]);
		}
		in.close();
	}

	private List<String> SplitInPhrases(BreakIterator bi, String text) {
		List<String> phrases = new LinkedList<String>();
		bi.setText(text);

		int lastIndex = bi.first();
		while (lastIndex != BreakIterator.DONE) {
			int firstIndex = lastIndex;
			lastIndex = bi.next();

			if (lastIndex != BreakIterator.DONE) {
				String sentence = text.substring(firstIndex, lastIndex);
				phrases.add(sentence);
			}
		}
		return phrases;
	}

	private boolean wordIsNegative(String word) {
		if (word.equals("not"))
			return true;
		if (word.endsWith("n't"))
			return true;
		return false;
	}

	private int getSentimentValue(String text) {
		int tweetScoreGlobal = 0;

		BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
		List<String> phrases = SplitInPhrases(iterator, text);
		for (String phrase : phrases) {
			boolean negative = false;
			int tweetScoreLocal = 0;
			int nLocal = 1;

			Pattern p = Pattern.compile("[\\w']+");
			Matcher m = p.matcher(phrase);
			while (m.find()) {
				String word = phrase.substring(m.start(), m.end());
				String wordLowerCase = word.toLowerCase();
				if (wordIsNegative(wordLowerCase)) {
					negative = true;
				} else if (afinn.get(wordLowerCase) != null) {
					String wordscore = afinn.get(wordLowerCase);
					tweetScoreLocal += Integer.parseInt(wordscore);
					nLocal++;
				}
			}
			tweetScoreLocal /= nLocal;
			if (negative)
				tweetScoreLocal *= -1;
			tweetScoreGlobal += tweetScoreLocal;
		}
		tweetScoreGlobal /= phrases.size();

		return tweetScoreGlobal;
	}
	
	private Sentimiento sentimentValueToSentiment(int sentimentValue) {
		if (sentimentValue == 0)
			return Sentimiento.NEUTRAL;
		if (sentimentValue < 0) {
			if (sentimentValue == -5 || sentimentValue == -4)
				return Sentimiento.MUY_NEGATIVO;
			return Sentimiento.NEGATIVO;
		}
		if (sentimentValue == 5 || sentimentValue == 4)
			return Sentimiento.MUY_POSITIVO;
		return Sentimiento.POSITIVO;
	}

	@Override
	public Valoracion analizeText(String text) {
		int sentimentValue = getSentimentValue(text);
		
		Valoracion val = new Valoracion();
		Sentimiento s = sentimentValueToSentiment(sentimentValue);
		val.setSentimiento(s);
		val.setExplicacion("");
		
		return val;
	}
}
