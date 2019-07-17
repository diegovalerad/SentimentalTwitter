package servicio.dao;

import java.util.Collection;

import servicio.modelo.Tema;
import servicio.tipos.TemaResultado;

/**
 * Interfaz que declara los métodos necesarios para la persistencia y consulta de Temas.
 * @author José Fernando
 */
public interface TemaDAO {

	/**
	 * Persiste un tema en base de datos.
	 * @param tema tema a persistir.
	 */
	public void createTema(Tema tema);
	
	/**
	 * Persiste una colección de temas en base de datos.
	 * @param temas lista de temas.
	 */
	public void createTemas(Collection<Tema> temas);
	
	/**
	 * Modifica un tema existente en base de datos.
	 * @param tema tema a modificar.
	 */
	public void modifyTema(Tema tema);
	
	/**
	 * Modifica una colección de temas existenes en base de datos.
	 * @param temas lista de temas a modificar.
	 */
	public void modifyTemas(Collection<Tema> temas);
	
	/**
	 * Elimina un tema existente en base de datos.
	 * @param tema tema a eliminar.
	 */
	public void deleteTema(Tema tema);
	
	/**
	 * Elimina una colección de temas existente en base de datos.
	 * @param temas lista de temas a eliminar.
	 */
	public void deleteTemas(Collection<Tema> temas);
	
	/**
	 * Devuelve todos los temas existentes en base de datos.
	 * @return colección de temas.
	 */
	public Collection<Tema> findAll();
	
	/**
	 * Busca un tema por su nombre y lo devuelve.
	 * @param nombre nombre del tema.
	 * @return tema buscado.
	 */
	public Tema findByNombre(String nombre);
	
	/**
	 * Devuelve una colección con todos los temas simplificados.
	 * @return colección de temas.
	 */
	public Collection<TemaResultado> getListadoTemas();
	
	/**
	 * Busca un tema por su ID y lo devuelve.
	 * @param id identificador del tema.
	 * @return tema buscado.
	 */
	public Tema findById(String id);
}
