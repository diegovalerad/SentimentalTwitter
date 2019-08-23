package servicio.controlador;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import servicio.algoritmos.IAlgoritmo;
import servicio.algoritmos.apacheOpenNLP.ControladorApacheOpenNLP;
import servicio.algoritmos.basedDictionary.ControladorDiccionario;
import servicio.algoritmos.stanford.ControladorStanford;
import servicio.modelo.Valoracion;

/**
 * Controlador del servicio
 * @author Diego Valera Duran
 *
 */
public class Controlador {
	private static Controlador unicaInstancia;
	private List<IAlgoritmo> algoritmos;
	private IAlgoritmo algoritmo_por_defecto;
	private String queryTodosLosAlgoritmos;
	
	private Controlador() {
		//Para evitar warning: WARN No appenders could be found for logger (edu.stanford.nlp.pipeline.StanfordCoreNLP)
		BasicConfigurator.configure();
		
		algoritmos = new LinkedList<IAlgoritmo>();
		
		System.out.println("***********************************");
		System.out.println("ServicioRestSentimientos - Inicializando algoritmos");
		System.out.println("***********************************");
		
		IAlgoritmo stanford = new ControladorStanford();
		IAlgoritmo diccionario = new ControladorDiccionario();
		IAlgoritmo apache = new ControladorApacheOpenNLP();
		
		String stanfordModelES = "stanfordES/model.ser.gz";
		String stanfordModelES_nombre = "Stanford CorenNLP - Español";
		String stanfordModelES_desc = "Variante del algoritmo basado en Stanford CoreNLP, donde utilizamos un modelo propio creado a partir de palabras españolas.";
		String stanfordModelES_query = "stanford_ES";
		IAlgoritmo stanfordES = new ControladorStanford(stanfordModelES, stanfordModelES_nombre, stanfordModelES_desc, stanfordModelES_query);
		
		String stanfordModelEN = "stanfordEN/model.ser.gz";
		String stanfordModelEN_nombre = "Stanford CorenNLP - Inglés";
		String stanfordModelEN_desc = "Variante del algoritmo basado en Stanford CoreNLP, donde utilizamos un modelo propio creado a partir de palabras inglesas.";
		String stanfordModelEN_query = "stanford_EN";
		IAlgoritmo stanfordEN = new ControladorStanford(stanfordModelEN, stanfordModelEN_nombre, stanfordModelEN_desc, stanfordModelEN_query);
		
		System.out.println("***********************************");
		System.out.println("ServicioRestSentimientos - Fin de inicialización de algoritmos");
		System.out.println("***********************************");
		
		algoritmos.add(stanford);
		algoritmos.add(diccionario);
		algoritmos.add(apache);
		algoritmos.add(stanfordES);
		algoritmos.add(stanfordEN);
		
		algoritmo_por_defecto = stanford;
		
		queryTodosLosAlgoritmos = "todos";
	}
	
	public static Controlador getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		return unicaInstancia;
	}
	
	/**
	 * Analiza un texto para obtener su valoración
	 * @param texto Texto de entrada
	 * @param algoritmo Algoritmo a usar. Si el string es igual a {@link Controlador#queryTodosLosAlgoritmos}, se analiza el texto de 
	 * 			entrada usando todos los algoritmos posibles.
	 * @return Lista de valoraciones con los análisis sentimentales del texto de entrada
	 */
	public List<Valoracion> analizarTexto(String texto, String algoritmo) {
		List<Valoracion> valoraciones = new LinkedList<Valoracion>();
		
		if (algoritmo.equals(queryTodosLosAlgoritmos)) {
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
	
	/**
	 * @return Devuelve un array de 3 elementos con la información de los algoritmos. El array está compuesto por nombre, descripción y query para el servicio REST 
	 */
	public String[][] getInformacionAlgoritmosAndQuery() {
		String[][] lista = new String[algoritmos.size() + 1][];
		
		String[] todosLosAlgoritmos = new String[3];
		todosLosAlgoritmos[0] = "Todos";
		todosLosAlgoritmos[1] = "Se analiza el texto con todos los algoritmos y se devuelven todos los resultados";
		todosLosAlgoritmos[2] = queryTodosLosAlgoritmos;
		lista[0] = todosLosAlgoritmos;
		
		for (int i = 0; i < algoritmos.size(); i++) {
			lista[i+1] = algoritmos.get(i).getInfoAlgoritmo();
		}
		return lista;
	}
}
