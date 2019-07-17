package servicio.listener;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Esta clase constituye un oyente de la apliación web.
 * @author José Fernando
 *
 */
@WebListener
public class ServletListener implements ServletContextListener  {

	private Timer temporizador = new Timer(true);
	
	/**
	 * Este método se ejecuta al arrancar el servicio Web en el servidor.
	 * Establece un temporizador para ejecutar la búsqueda de comentarios.
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		temporizador.schedule(new Tarea(), 5000, 86400000);
	} 
	
	/**
	 * Este método se ejecuta al apagar el servicio Web en el servidor.
	 * Cancela el temporizador de la tarea.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

		temporizador.cancel();
	}
	
}
