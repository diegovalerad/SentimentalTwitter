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
	private IAlgoritmo algoritmo_por_defecto;
	private List<String> querysAlgoritmosValidas;
	
	private Controlador() {
		//Para evitar warning: WARN No appenders could be found for logger (edu.stanford.nlp.pipeline.StanfordCoreNLP)
		BasicConfigurator.configure();
		
		algoritmos = new LinkedList<IAlgoritmo>();
		
		IAlgoritmo stanford = new ControladorStanford();
		IAlgoritmo diccionario = new ControladorDiccionario();
		IAlgoritmo apache = new ControladorApacheOpenNLP();
		
		algoritmos.add(stanford);
		algoritmos.add(diccionario);
		algoritmos.add(apache);
		
		querysAlgoritmosValidas = new LinkedList<String>();
		for (IAlgoritmo a : algoritmos) {
			querysAlgoritmosValidas.add(a.getAlgoritmoQuery());
		}
		
		algoritmo_por_defecto = stanford;
	}
	
	public static Controlador getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		return unicaInstancia;
	}
	
	public List<Valoracion> analizarTexto(String texto, String algoritmo) {
		List<Valoracion> valoraciones = new LinkedList<Valoracion>();
		
		if (algoritmo.equals("todos")) {
			for (IAlgoritmo a : algoritmos) {
				Valoracion v = a.analize(texto);
				valoraciones.add(v);
			}
		}else {
			boolean encontrado = false;
			int index = 0;
			while (index < algoritmos.size() && !encontrado) {
				IAlgoritmo a = algoritmos.get(index);
				if (a.getAlgoritmoQuery().equals(algoritmo)) {
					Valoracion v = a.analize(texto);
					valoraciones.add(v);
					encontrado = true;
				}
				index++;
			}
			if (!encontrado) {
				Valoracion v = algoritmo_por_defecto.analize(texto);
				valoraciones.add(v);
			}
		}
		
		return valoraciones;
	}
	
	public List<String> getQuerysAlgoritmosValidas(){
		List<String> lista = new LinkedList<String>();
		for (String query : querysAlgoritmosValidas) {
			lista.add(query);
		}
		return lista;
	}
}
