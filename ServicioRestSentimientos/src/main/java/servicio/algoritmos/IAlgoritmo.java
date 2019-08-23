package servicio.algoritmos;

import servicio.modelo.Valoracion;

/**
 * Interfaz que deben seguir todos los algoritmos del servicio
 * @author Diego Valera Duran
 *
 */
public interface IAlgoritmo {
	
	/**
	 * @return Devuelve un array de 3 elementos con la información de los algoritmos. El array está compuesto por nombre, descripción y query para el servicio REST 
	 */
	public String[] getInfoAlgoritmo();
	
	/**
	 * @return Cadena con el string a usar para usarlo en la búsqueda REST
	 */
	public String getAlgoritmoQuery();
	
	/**
	 * Analiza un texto
	 * @param text Texto a analizar
	 * @return Se devuelve la valoración de un algoritmo sobre un texto
	 */
	public Valoracion analize(String text);
}
