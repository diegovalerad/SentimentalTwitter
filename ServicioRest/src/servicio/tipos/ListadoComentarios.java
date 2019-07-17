package servicio.tipos;

import java.util.LinkedList;
import java.util.List;

/**
 * Esta clase representa la información completa del tema.
 * Contiene la lista de comentarios del tema con la información simplificada y la URI de cada comentario. 
 * 
 * @author José Fernando
 *
 */
public class ListadoComentarios {

	private String nombre;
	private String descripcion;
	private List<ComentarioResultado> lista;
	
	/**
	 * Constructor vacío.
	 * Inicializa la lista de comentarios.
	 */
	public ListadoComentarios () {
		this.lista = new LinkedList<ComentarioResultado>();
	}

	/**
	 * Devuelve el nombre del tema. 
	 * @return nombre del tema.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del tema.
	 * 
	 * @param nombre nombre del tema.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve la descripción del tema.
	 * @return descripción del tema.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripción del tema-
	 * @param descripcion descripción del tema.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Devuelve la lista de comentarios del tema.
	 * @return lista de comentarios del tema.
	 */
	public List<ComentarioResultado> getLista() {
		return lista;
	}

	/**
	 * Establece la lista de comentariosd el tema.
	 * @param lista lista de comentarios.
	 */
	public void setLista(List<ComentarioResultado> lista) {
		this.lista = lista;
	}
	
}
