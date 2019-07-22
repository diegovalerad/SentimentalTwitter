package servicio.algoritmos.apacheOpenNLP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import servicio.algoritmos.AlgoritmosFactoria;
import servicio.modelo.Sentimiento;
import servicio.modelo.Valoracion;

/**
 * Algoritmo basado en el entrenamiento con un modelo de frases. Estas
 * frases tienen asociado un valor número, 0 o 1, dependiendo de si son 
 * negativas o positivas.
 * @author Diego Valera Duran
 *
 */
public class ApacheOpenNLPFactoria extends AlgoritmosFactoria {
	private DoccatModel model;
	private String modelLocation;
	
	public ApacheOpenNLPFactoria() {
		modelLocation = "/apache/apache_opennlp_model.txt";
		trainModel();
	}
	
	private void trainModel() {
		InputStream dataIn = null;
		try {
			File testf = new File( ApacheOpenNLPFactoria.class.getResource( modelLocation ).toURI() );
			dataIn = new FileInputStream(testf);
			ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
			ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
			// Specifies the minimum number of times a feature must be seen
			int cutoff = 2;
			int trainingIterations = 30;
			model = DocumentCategorizerME.train("en", sampleStream, cutoff, trainingIterations);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		} finally {
			if (dataIn != null) {
				try {
					dataIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private Sentimiento classifyNewTweet(String tweet){
		DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
		double[] outcomes = myCategorizer.categorize(tweet);
		String category = myCategorizer.getBestCategory(outcomes);

		if (category.equalsIgnoreCase("1")) // Positivo
			return Sentimiento.POSITIVO;
		else //Negativo
			return Sentimiento.NEGATIVO;
	}
	
	@Override
	public Valoracion analizeText(String text) {
		Sentimiento s = classifyNewTweet(text);
		
		Valoracion val = new Valoracion();
		val.setSentimiento(s);
		val.setExplicacion("");
		
		return val;
	}
}
