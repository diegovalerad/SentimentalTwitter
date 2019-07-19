package servicio.algoritmos;

import servicio.algoritmos.apacheOpenNLP.ApacheOpenNLPFactoria;
import servicio.algoritmos.stanford.StanfordFactoria;
import servicio.modelo.Valoracion;

public class AlgoritmosFactoria {
	private static AlgoritmosFactoria unicaInstancia;
	
	public final static int STANFORD_CORENLP = 1;
	public final static int APACHE_OPENNLP = 2;
	
	protected AlgoritmosFactoria() {}
	
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
			default:
				System.err.println("Tipo Factoria no encontrado.");
				break;
		}
	}
}
