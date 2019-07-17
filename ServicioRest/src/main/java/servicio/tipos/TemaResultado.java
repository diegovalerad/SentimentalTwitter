package servicio.tipos;

/**
 * Esta clase representa la información simplificada del tema.
 * Utilizada cuando se obtiene un listado de temas en el servicio Rest.
 * Contienen la URI dentro de la aplicación del tema con la información completa.
 * 
 * @author José Fernando
 *
 */
public class TemaResultado {

	private String id;
	private String uri;
	private String nombre;
	
	/**
	 * Constructor vacío.
	 */
	public TemaResultado() {
		
	}

	/**
	 * Devuelve el identificador del tema.
	 * 
	 * @return identificador del tema.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador del tema.
	 * 
	 * @param id identificador del tema.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Devuelve la URI del tema completo dentro de la aplicación.
	 * 
	 * @return URI del tema.
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * Establece la URI del tema.
	 * 
	 * @param uri URI del tema dentro de la aplicación.
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * Devuelve el nombre del tema.
	 * 
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
	
}
