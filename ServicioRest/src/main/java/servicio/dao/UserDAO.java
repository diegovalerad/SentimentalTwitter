package servicio.dao;

import java.util.List;

import servicio.modelo.Usuario;

/**
 * Interfaz que declara los métodos necesarios para la creación y modificación de usuarios en la base de datos
 * @author Diego Valera Duran
 *
 */
public interface UserDAO {
	/**
	 * Persiste un usuario en la base de datos
	 * @param usuario Usuario a persistir
	 * @return Booleano indicando si se ha podido crear el usuario
	 */
	public boolean createUsuario(Usuario usuario);
	
	/**
	 * Elimina un usuario de la base de datos
	 * @param usuario Usuario a eliminir
	 */
	public void deleteUsuario(Usuario usuario);
	
	/**
	 * Encuentra a un usuario en la base de datos
	 * @param email Email del usuario
	 * @return Usuario encontrado
	 */
	public Usuario findUsuarioByEmail(String email);
}
