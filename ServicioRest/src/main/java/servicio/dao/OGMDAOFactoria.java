package servicio.dao;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;

/**
 * Esta clase representa la factoría de NEO4j que hace uso de OGM.
 * @author José Fernando
 *
 */
public class OGMDAOFactoria extends DAOFactoria {

	private SessionFactory sessionFactory;
	
	/**
	 * Constructor.
	 * Establece la conexión con la base de datos.
	 * @throws DAOException -excepción al establecer conexión.
	 */
	public OGMDAOFactoria() throws DAOException {
		
		ClasspathConfigurationSource configurationSource = 
				new ClasspathConfigurationSource("/servicio/resources/neo4j.properties");
	    Configuration configuration = new Configuration.Builder(configurationSource).build();
	    sessionFactory = new SessionFactory(configuration, "servicio.modelo");	
	}
	
	/**
	 * Devuelve la implementación OGM de la interfaz DAO del Comentario.
	 */
	@Override
	public ComentarioDAO getComentarioDAO(){
		return new OGMComentarioDAO(sessionFactory);
	}
	
	/**
	 * Devuelve la implementación OGM de la interfaz DAO del Tema.
	 */
	@Override
	public TemaDAO getTemaDAO(){
		return new OGMTemaDAO(sessionFactory);
	}

}
