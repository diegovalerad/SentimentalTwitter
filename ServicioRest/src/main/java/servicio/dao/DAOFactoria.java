package servicio.dao;

import java.util.List;

import servicio.dao.OGM.OGMDAOFactoria;

/**
 * Esta clase define una factoria abstracta que devuelve todos los DAO de la aplicación.
 * @author José Fernando
 *
 */
public  class DAOFactoria {
	
	// Métodos factoria
	public ComentarioDAO getComentarioDAO() {return null;};
	public TemaDAO getTemaDAO() {return null;};
	public String procesarFecha(String since, String until, List<String> temas, List<String> operadores) {return null;}
	
	// Declaracion como constantes de los tipos de factoria
	public final static int OGM = 1;
	
	private static DAOFactoria unicaInstancia;
	
	/**
	 * Constructor.
	 */
	protected DAOFactoria() {
		
	}
	
	/**
	 * Devuelve la instancia de la factoría DAO.
	 * @return instancia de la factoría.
	 */
	public static DAOFactoria getUnicaInstancia() {
		if(unicaInstancia == null) 
			unicaInstancia = new DAOFactoria();
		return unicaInstancia;
	}
	
	/**
	 * Establece el tipo de factoría que se va a utilizar.
	 * @param tipo Tipo de factoría.
	 * @throws DAOException Excepción al crear la factoría.
	 */
	public static void setDAOFactoria(int tipo) throws DAOException {
		
		switch (tipo) {
			case OGM:{
				try {
					unicaInstancia = new OGMDAOFactoria();
				} catch(Exception e) {
					throw new DAOException(e.getMessage());
				}
				break;
			}
			default:
				System.err.println("Tipo Factoria no encontrado.");
				break;
		}
	}	
}