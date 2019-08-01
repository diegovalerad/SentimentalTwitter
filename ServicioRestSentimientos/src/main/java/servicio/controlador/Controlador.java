package servicio.controlador;

import org.apache.log4j.BasicConfigurator;

import servicio.algoritmos.AlgoritmosFactoria;
import servicio.modelo.Valoracion;

public class Controlador {
	private static Controlador unicaInstancia;
	
	private Controlador() {
		//Para evitar warning: WARN No appenders could be found for logger (edu.stanford.nlp.pipeline.StanfordCoreNLP)
		BasicConfigurator.configure();
		AlgoritmosFactoria.setAlgoritmosFactoria(AlgoritmosFactoria.STANFORD_CORENLP);
	}
	
	public static Controlador getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		return unicaInstancia;
	}
	
	public Valoracion analizarTexto(String texto) {
		Valoracion val = AlgoritmosFactoria.getUnicaInstancia().analizeText(texto);
		
		return val;
	}
}
