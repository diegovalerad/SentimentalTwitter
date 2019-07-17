package servicio.dao;

/**
 * Clase que representa una excepción producida por un método del DAO.
 * @author José Fernando
 *
 */
public class DAOException extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param message mensaje de la excepción.
	 */
	public DAOException(String message) {
		super(message);
	}
}
