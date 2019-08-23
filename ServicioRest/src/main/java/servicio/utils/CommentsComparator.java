package servicio.utils;

import java.util.Comparator;

import servicio.tipos.ComentarioResultado;

/**
 * Clase que compara dos comentarios.
 * <p>
 * Un comentario es mejor que otro si tiene mayor prioridad.
 * <p>
 * Ante la misma prioridad, el que ha ocurrido antes
 * <p>
 * Ante la misma fecha, el que tiene mayor popularidad
 * @author Diego Valera Duran
 *
 */
public class CommentsComparator implements Comparator<ComentarioResultado> {

	@Override
	public int compare(ComentarioResultado o1, ComentarioResultado o2) {
		
		// prioridad usuario
		if(o1.getUserPriority() < o2.getUserPriority())
			return -1;
		if(o1.getUserPriority() > o2.getUserPriority())
			return 1;
		
		// fecha
		int dateResult = o1.getFecha().compareTo(o2.getFecha());
		if (dateResult != 0) {
			return dateResult;
		}
		
		// popularidad
		if(o1.getPopularidad() < o2.getPopularidad())
			return -1;
		if(o1.getPopularidad() > o2.getPopularidad())
			return 1;
		return 0;
	}

}
