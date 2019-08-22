package servicio.dao;

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
	 * Valida un usuario
	 * @param usuario Usuario a validar
	 * @return Booleano indicando si se ha podido validar correctamente
	 */
	public boolean validarUsuario(Usuario usuario);
	
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
	 * Actualiza la contraseña del usuario
	 * @param usuario Usuario
	 * @param password Nueva contraseña
	 * @return true si se ha podido actualizar, false en caso contrario
	 */
	public boolean updateUsuario(Usuario usuario, String password);

	/**
	 * Añade o elimina un favorito de la lista de personas favoritas del usuario y lo persiste en la base de datos.
	 * <br>
	 * Si la lista del usuario ya contenía el favorito, lo elimina.
	 * <br>
	 * Si la lista del usuario no lo contenía, lo añade.
	 * @param usuario Usuario que quiere modificar su lista
	 * @param redSocial Red social de la persona favorita
	 * @param nombre Nombre de la persona favorita
	 * @return Si se ha podido realizar con exito la modificación de la lista de personas favoritas
	 */
	public boolean modificarFavorito(Usuario usuario, String redSocial, String nombre);
}
