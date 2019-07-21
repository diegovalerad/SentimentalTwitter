package servicio.redesSociales;

import servicio.modelo.Tema;

public interface IRedSocial {
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
