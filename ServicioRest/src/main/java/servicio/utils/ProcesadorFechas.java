package servicio.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Clase con métodos útiles para dar formato a fechas
 * 
 * @author José Fernando
 * @author Diego Valera Duran
 *
 */
public class ProcesadorFechas {

	/**
	 * Convierte una fecha en una cadena con el formato dd 'de' MMMM 'de' yyyy.
	 * Se usa para dar formato de salida a una fecha de base de datos.
	 * 
	 * @param fecha La fecha que se va a formatear.
	 * @return Una cadena con la fecha formateada.
	 */
	public static String procesarFecha(Date fecha){
		
		   SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd", new Locale("es"));
		   return formateador.format(fecha);
	}
	
	/**
	 * Convierte una fecha en una cadena con el formato yyyy-MM-dd.
	 * Se usa para pasar una fecha al formato que acepta la API Rest de Twitter.
	 * 
	 * @param fecha La fecha que se va a formatear.
	 * @return Una cadena con la fecha formateada.
	 */
	public static String procesarFechaTwitter(Date fecha){
		
		   SimpleDateFormat formateador = new SimpleDateFormat("yyyy'-'MM'-'dd", new Locale("es"));
		   return formateador.format(fecha);
	}
	
	public static Date parsearFecha(String fecha) {
		
		SimpleDateFormat formateador = new SimpleDateFormat("MMM dd yyyy", new Locale("en"));
		String substr = fecha.substring(4, 15);

		try {
			return formateador.parse(substr);
		} catch (ParseException e) {
			return null;
		}
	}
}
