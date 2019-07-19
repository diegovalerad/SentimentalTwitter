package servicio.listener;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import servicio.buscadortemas.TemasFactoria;
import servicio.controlador.Controlador;
import servicio.controlador.ControladorTwitter;
import servicio.modelo.Tema;

/**
 * Esta clase representa la tarea que se quiere automatizar al arranque del servicio.
 * En este caso, se automatiza la búsqueda de comentarios.
 * @author José Fernando
 */
public class Tarea extends TimerTask{
	
	/**
	 * Método que implementa la funcionalidad de la tarea.
	 * Busca comentarios y los persiste en base de datos.
	 */
	@Override
	public void run() {
		// Para inicializar los tipos de factorias
		Controlador.getUnicaInstancia(); 
		List<Tema> temas = TemasFactoria.getUnicaInstancia().getTemas();
		ControladorTwitter.getUnicaInstancia().buscarComentarios(temas);
		System.out.println("Tarea ejecutada " + new Date());
	}

}
