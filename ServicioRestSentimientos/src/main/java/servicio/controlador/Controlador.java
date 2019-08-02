package servicio.controlador;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import servicio.algoritmos.IAlgoritmo;
import servicio.algoritmos.apacheOpenNLP.ControladorApacheOpenNLP;
import servicio.algoritmos.basedDictionary.ControladorDiccionario;
import servicio.algoritmos.stanford.ControladorStanford;
import servicio.modelo.Valoracion;

public class Controlador {
	private static Controlador unicaInstancia;
	private List<IAlgoritmo> algoritmos;
	
	private Controlador() {
		//Para evitar warning: WARN No appenders could be found for logger (edu.stanford.nlp.pipeline.StanfordCoreNLP)
		BasicConfigurator.configure();
		
		algoritmos = new LinkedList<IAlgoritmo>();
		
		System.out.println("Controlador - inicio algoritmos");
		long ti = System.currentTimeMillis();
		IAlgoritmo stanford = new ControladorStanford();
		IAlgoritmo diccionario = new ControladorDiccionario();
		IAlgoritmo apache = new ControladorApacheOpenNLP();
		long tf = System.currentTimeMillis();
		System.out.println("Controlador - fin algoritmos... Tiempo: " + (tf - ti));
		
		algoritmos.add(stanford);
		algoritmos.add(diccionario);
		algoritmos.add(apache);
	}
	
	public static Controlador getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		return unicaInstancia;
	}
	
	public List<Valoracion> analizarTexto(String texto) {
		System.out.println("Controlador - Texto a analizar: " + texto);
		List<Valoracion> valoraciones = new LinkedList<Valoracion>();
		long ti = System.currentTimeMillis();
		for (IAlgoritmo a : algoritmos) {
			Valoracion v = a.analize(texto);
			valoraciones.add(v);
		}
		long tf = System.currentTimeMillis();
		System.out.println("Controlador - fin algoritmos... Tiempo: " + (tf - ti));
		
		return valoraciones;
	}
}
