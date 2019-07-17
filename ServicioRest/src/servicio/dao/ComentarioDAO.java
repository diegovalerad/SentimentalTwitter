package servicio.dao;

import java.util.Collection;
import java.util.List;

import servicio.modelo.Comentario;
import servicio.tipos.ComentarioResultado;

/**
 * Interfaz que declara los métodos necesarios para la persistencia y consulta de Comentarios.
 * @author José Fernando
 */
public interface ComentarioDAO {

	/**
	 * Persiste un comentario en base de datos.
	 * @param comentario comentario a persistir.
	 */
	public void createComentario(Comentario comentario);
	
	/**
	 * Persiste una colección de comentarios en base de datos.
	 * @param comentarios lista de comentarios.
	 */
	public void createComentarios(Collection<Comentario> comentarios);
	
	/**
	 * Modifica un comentario ya existente en base de datos.
	 * @param comentario comentario a modificar.
	 */
	public void modifyComentario(Comentario comentario);
	
	/**
	 * Modifica una colección de comentarios ya existentes en base de datos.
	 * @param comentarios lista de coemntarios.
	 */
	public void modifyComentarios(Collection<Comentario> comentarios);
	
	/**
	 * Elimina un comentario de base de datos.
	 * @param comentario comentario a eliminar.
	 */
	public void deleteComentario(Comentario comentario);
	
	/**
	 * Elimina una colección de comentarios existentes en base de datos.
	 * @param comentarios lista de comentarios.
	 */
	public void deleteComentarios(Collection<Comentario> comentarios);
	
	/**
	 * Devuelve todos los comentarios de la base de datos.
	 * @return colección de comentarios.
	 */
	public Collection<Comentario> findAll();
	
	/**
	 * Devuelve una lista de comentarios relacionados con un tema.
	 * @param tema identificador del tema.
	 * @return lista de coemntarios relacionados con el tema.
	 */
	public List<ComentarioResultado> findByTema(String tema);
	
	/**
	 * Devuelve un comentario.
	 * @param id identificador del comentario.
	 * @return comentario buscado.
	 */
	public Comentario findById(String id);

	/**
	 * Devuelve una lista de comentarios según la consulta.
	 * @param query Consulta
	 * @return Comentarios resultado a la consulta.
	 */
	public List<ComentarioResultado> search(String query);
}
