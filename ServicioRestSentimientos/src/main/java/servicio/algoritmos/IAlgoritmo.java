package servicio.algoritmos;

import servicio.modelo.Algoritmo;
import servicio.modelo.Valoracion;

public interface IAlgoritmo {
	public Algoritmo getInfoAlgoritmo();
	
	/**
	 * @return Cadena con el string a usar para usarlo en la búsqueda REST
	 */
	public String getAlgoritmoQuery();
	
	public Valoracion analize(String text);
}
