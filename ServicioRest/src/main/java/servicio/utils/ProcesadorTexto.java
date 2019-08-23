package servicio.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase con métodos útiles para procesar textos.
 * 
 * @author José Fernando
 * @author Diego Valera Duran
 *
 */
public class ProcesadorTexto {

	/**
	 * Eliminar urls de un texto.
	 * @param texto Texto que se va a parsear.
	 * @return texto sin urls.
	 */
	public static String eliminarUrls(String texto) {
		
		 String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";		
		 Pattern pattern = Pattern.compile(regex);
		 Matcher matcher = pattern.matcher(texto);
		 
		 if(matcher.find()) {
			 return matcher.replaceAll("");
		 }
		
		return texto;
	}
	
	public static String eliminarParentesis (String texto) {
		
		int first = texto.indexOf("(");
		int second = texto.indexOf(")");
		
		if(first == -1 || second == -1)
			return texto;
		
		String subString = texto.substring(first-1, second+1);
		
		return texto.replace(subString, "");
	}
}
