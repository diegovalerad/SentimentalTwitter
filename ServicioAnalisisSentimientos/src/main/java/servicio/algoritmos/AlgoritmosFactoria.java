package servicio.algoritmos;

import servicio.algoritmos.apacheOpenNLP.ApacheOpenNLPFactoria;
import servicio.algoritmos.basedDictionary.BasedDictionary;
import servicio.algoritmos.stanford.StanfordFactoria;
import servicio.modelo.Valoracion;

public class AlgoritmosFactoria {
	private static AlgoritmosFactoria unicaInstancia;
	
	public final static int STANFORD_CORENLP = 1;
	public final static int APACHE_OPENNLP = 2;
	public final static int BASED_DICTIONARY = 3;
	
	protected AlgoritmosFactoria() {}
	
	/**
	 * Método que analiza un texto de entrada, y de vuelve una valoración
	 * @param text Texto de entrada
	 * @return Valoración
	 */
	public Valoracion analizeText(String text) {
		return null;
	}
	
	public static AlgoritmosFactoria getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AlgoritmosFactoria();
		return unicaInstancia;
	}
	
	public static void setAlgoritmosFactoria(int tipo) {
		switch (tipo) {
			case STANFORD_CORENLP:{
				unicaInstancia = new StanfordFactoria();
				break;
			}
			case APACHE_OPENNLP:{
				unicaInstancia = new ApacheOpenNLPFactoria();
				break;
			}
			case BASED_DICTIONARY:{
				unicaInstancia = new BasedDictionary();
				break;
			}
			default:
				System.err.println("Tipo Factoria no encontrado.");
				break;
		}
	}
}
