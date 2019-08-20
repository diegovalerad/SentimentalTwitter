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
	 * Comprueba si el login es correcto
	 * @param email Email del usuario
	 * @param password Contraseña del usuario
	 * @return Boolean indicando si los datos son correctos
	 */
	public boolean login(String email, String password);
	
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

	/**
	 * Obtiene los favoritos del usuario 
	 * @param email Correo del usuario
	 * @return Lista de favoritos del usuario
	 */
	public List<String> getFavoritos(String email);
}
