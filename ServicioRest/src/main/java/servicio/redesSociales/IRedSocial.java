package servicio.redesSociales;

import servicio.modelo.Tema;

/**
 * Interfaz que deben seguir e implementar todas las redes sociales del servicio
 * @author Diego Valera Duran
 *
 */
public interface IRedSocial {
	/**
	 * @return Devuelve el nombre de la red social
	 */
	public String getRedSocial();
	/**
	 * Realiza la búsqueda de comentarios y la inserción de estos en la base de datos.
	 */
	public void buscarComentarios();
	/**
	 * Realiza la búsqueda de comentarios sobre un tema en concreto y los inserto en la base de datos.
	 * @param tema
	 */
	public void buscarComentarios(Tema tema);
}
