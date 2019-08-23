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
import servicio.algoritmos.IAlgoritmo;
import servicio.modelo.Algoritmo;
import servicio.modelo.Sentimiento;
import servicio.modelo.Valoracion;

/**
 * Algoritmo basado en el entrenamiento con un modelo de frases. Estas
 * frases tienen asociado un valor número, 0 o 1, dependiendo de si son 
 * negativas o positivas.
 * @author Diego Valera Duran
 *
 */
public class ControladorApacheOpenNLP implements IAlgoritmo {
	private String nombre;
	private String desc;
	private String algoritmoQuery;
	
	private DoccatModel model;
	private String modelLocation;
	
	public ControladorApacheOpenNLP() {
		nombre = "Apache OpenNLP";
		desc = "Algoritmo basado en el entrenamiento con un modelo de frases, las cuales tienen asociado si son positivas o negativas.";
		algoritmoQuery = "apacheOpenNP";
		
		modelLocation = "/apache/apache_opennlp_model.txt";
		trainModel();
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
	
	private void trainModel() {
		InputStream dataIn = null;
		try {
			File testf = new File( ControladorApacheOpenNLP.class.getResource( modelLocation ).toURI() );
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

	/**
	 * Clasifica un texto de entrada en positivo o negativo
	 * @param tweet Texto de entrada
	 * @return Sentimiento del texto
	 */
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
	public Valoracion analize(String text) {
		Sentimiento s = classifyNewTweet(text);
		Algoritmo a = new Algoritmo(nombre, desc);
		
		Valoracion val = new Valoracion(text, s, a);
		
		return val;
	}
}
