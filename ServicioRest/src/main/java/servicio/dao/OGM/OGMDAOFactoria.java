package servicio.dao.OGM;

import java.util.List;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;

import servicio.dao.ComentarioDAO;
import servicio.dao.DAOException;
import servicio.dao.DAOFactoria;
import servicio.dao.TemaDAO;

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
	
	/**
	 * Devuelve la implementación OGM del procesamiento de una búsqueda
	 * @param since
	 *            Fecha desde la que se buscan comentarios.
	 * @param until
	 *            Fecha hasta la que se buscan comentarios.
	 * @param temas
	 *            Temas de los que se buscan comentarios
	 * @param operadores
	 *            Operadores booleanos.
	 * @return consulta con los parámetros de búsuqeda.
	 */
	@Override
	public String procesarFecha(String since, String until, List<String> temas, List<String> operadores) {
		String query = ProcesadorBusqueda.procesarFecha(since, until, temas, operadores);
		return query;
	}

}
