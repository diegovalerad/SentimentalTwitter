package servicio.controlador;

import servicio.algoritmos.AlgoritmosFactoria;
import servicio.modelo.Valoracion;

public class Controlador {
	private static Controlador unicaInstancia;
	
	private Controlador() {
		AlgoritmosFactoria.setAlgoritmosFactoria(AlgoritmosFactoria.STANFORD_CORENLP);
	}
	
	public static Controlador getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		return unicaInstancia;
	}
	
	public Valoracion analizarTexto(String texto) {
		System.out.println("Controlador, llega texto: " + texto);
		Valoracion val = AlgoritmosFactoria.getUnicaInstancia().analizeText(texto);
		
		return val;
	}
}
