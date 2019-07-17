package servicio.tipos;

/**
 * 
 * Esta clase representa la información simplificada de un comentario. Se usa
 * cuando se quiere obtener un listado de comentarios de un tema en el servicio
 * Rest. Cada comentario tiene una URI del comentario con la información
 * completa dentro del servicio.
 * 
 * @author José Fernando
 *
 */
public class ComentarioResultado {

	private String imagen;
	private String id;
	private String fecha;
	private String autor;
	private String uri;
	private String texto;
	private int likes;
	private int retweets;
	private int popularidad;
	private int userPriority;

	/**
	 * Constructor vacío.
	 */
	public ComentarioResultado() {

	}

	/**
	 * Devuelve la URL de la imagen de perfil del autor del comentario.
	 * 
	 * @return URL de la imagen de perfil.
	 */
	public String getImagen() {
		return imagen;
	}

	/**
	 * Establece la URL de la imagen de perfil del autor del comentario.
	 * 
	 * @param imagen
	 *            URL de la imagen de perfil.
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	/**
	 * Devuelve el identificador del comentario.
	 * 
	 * @return identificador del comentario.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador del comentario.
	 * 
	 * @param id
	 *            identificador del comentario.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Devuelve la fecha de publicación del comentario.
	 * 
	 * @return Fecha de publicación del comentario.
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha de publicación del comentario.
	 * 
	 * @param fecha
	 *            fecha de publicación.
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * Devuelve el nombre del autor del comentario.
	 * 
	 * @return nombre del autor.
	 */
	public String getAutor() {
		return autor;
	}

	/**
	 * Establece el nombre del autor del comentario.
	 * 
	 * @param autor
	 *            autor del comentario.
	 */
	public void setAutor(String autor) {
		this.autor = autor;
	}

	/**
	 * Devuelve la URI del comentario dentro del servicio.
	 * 
	 * @return URI del comentario.
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * Establece la uri del comentario.
	 * 
	 * @param url
	 *            URI del comentario
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * Devuelve el texto del comentario del servicio.
	 * 
	 * @return Texto del comentario.
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * Establece el texto del comentario.
	 * 
	 * @param texto
	 *            Texto del comentario
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

	/**
	 * Devuelve un entero que indica la popularidad del comentario.
	 * 
	 * @return popularidad del comentario.
	 */
	public int getPopularidad() {
		return popularidad;
	}

	/**
	 * Establece la popularidad del comentario.
	 * 
	 * @param popularidad
	 *            Entero que indica la popularidad del comentario.
	 */
	public void setPopularidad(int popularidad) {
		this.popularidad = popularidad;
	}

	/**
	 * Devuelve la prioridad de usuario.
	 * 
	 * @return prioridad de usuario
	 */
	public int getUserPriority() {
		return userPriority;
	}

	/**
	 * Establece la prioridad de usuario.
	 * 
	 * @param userPriority
	 *            prioridad de usuario.
	 */
	public void setUserPriority(int userPriority) {
		this.userPriority = userPriority;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getRetweets() {
		return retweets;
	}

	public void setRetweets(int retweets) {
		this.retweets = retweets;
	}

}
