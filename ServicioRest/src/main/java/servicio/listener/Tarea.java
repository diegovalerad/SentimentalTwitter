package servicio.listener;

import java.util.Date;
import java.util.TimerTask;

import servicio.controlador.Controlador;

/**
 * Esta clase representa la tarea que se quiere automatizar al arranque del servicio.
 * En este caso, se automatiza la búsqueda de comentarios.
 * @author José Fernando
 * @author Diego Valera Duran
 */
public class Tarea extends TimerTask{
	
	/**
	 * Método que implementa la funcionalidad de la tarea.
	 * Busca comentarios y los persiste en base de datos.
	 */
	@Override
	public void run() {
		//Controlador.getUnicaInstancia().inicializarTemasYComentarios();
		System.out.println("Tarea ejecutada " + new Date());
	}

}
