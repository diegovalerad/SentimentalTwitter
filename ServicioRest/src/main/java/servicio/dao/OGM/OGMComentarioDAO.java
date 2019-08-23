package servicio.dao.OGM;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import servicio.dao.ComentarioDAO;
import servicio.modelo.Comentario;
import servicio.tipos.ComentarioResultado;

/**
 * Implementación de la interfaz ComentarioDAO para OGM.
 * @author José Fernando
 * @author Diego Valera Duran
 *
 */
public class OGMComentarioDAO implements ComentarioDAO{

	private SessionFactory sessionFactory;
	
	
	/**
	 * Constructor.
	 * @param sf Sesión de la factoría de Neo4j
	 */
	public OGMComentarioDAO(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	/**
	 * Persiste un comentario en base de datos.
	 * @param comentario comentario a persistir.
	 */
	@Override
	public void createComentario(Comentario comentario) {
		Session sesion = null;

		try {

			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}

			sesion.save(comentario);

		} catch (Exception e) {
			System.out.println("Error al crear el comentario.");
		}
	}
	
	/**
	 * Persiste una colección de comentarios en base de datos.
	 * @param comentarios lista de comentarios.
	 */
	@Override
	public void createComentarios(Collection<Comentario> comentarios) {
		Session sesion = null;

		try {

			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}

			sesion.save(comentarios);

		} catch (Exception e) {
			System.out.println("Error al crear los comentarios.");
		}
	}

	/**
	 * Modifica un comentario ya existente en base de datos.
	 * @param comentario comentario a modificar.
	 */
	@Override
	public void modifyComentario(Comentario comentario) {

		createComentario(comentario);
	}

	/**
	 * Modifica una colección de comentarios ya existentes en base de datos.
	 * @param comentarios lista de coemntarios.
	 */
	@Override
	public void modifyComentarios(Collection<Comentario> comentarios) {

		createComentarios(comentarios);
	}

	/**
	 * Elimina un comentario de base de datos.
	 * @param comentario comentario a eliminar.
	 */
	@Override
	public void deleteComentario(Comentario comentario) {
		
		Session sesion = null;

		try {

			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}

			sesion.delete(comentario);

		} catch (Exception e) {
			System.out.println("Error al eliminar el comentario.");
		}

	}

	/**
	 * Elimina una colección de comentarios existentes en base de datos.
	 * @param comentarios lista de comentarios.
	 */
	@Override
	public void deleteComentarios(Collection<Comentario> comentarios) {
		
		Session sesion = null;

		try {

			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}

			sesion.delete(comentarios);

		} catch (Exception e) {
			System.out.println("Error al eliminar los comentarios.");
		}
	}
	
	/**
	 * Devuelve todos los comentarios de la base de datos.
	 * @return colección de comentarios.
	 */
	@Override
	public Collection<Comentario> findAll() {
		
		Session sesion = null;
		Collection<Comentario> comentarios = null;
		
		try {
			
			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}
			
			comentarios = sesion.loadAll(Comentario.class);
			
		} catch (Exception e) {
			return null;
		}
		
		return comentarios;
	}
	
	/**
	 * Devuelve una lista de comentarios relacionados con un tema.
	 * @param tema identificador del tema.
	 * @return lista de coemntarios relacionados con el tema.
	 */
	@Override
	public List<ComentarioResultado> findByTema(String tema) {
		
		Session sesion = null;
		List<ComentarioResultado> comentarios = null;
		
		try {
			
			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}
			
			//información de comentarios relacionados con temas
			String query = "MATCH (c:Comentario)-[:RELATED_TO]-(t:Tema) WHERE t.id = '" + tema + 
					"' RETURN c.id, c.imagen, c.autor, c.fecha, c.mensaje, c.userPriority, c.popularidad, c.sentimiento, c.redSocial";
			
			//Ejecutamos consulta
			Result r = sesion.query(query, Collections.emptyMap());
			
			Iterator<Map<String, Object>> it = r.iterator();
			comentarios = new LinkedList<ComentarioResultado>();
			
			//Iteramos sobre el resultado
			while(it.hasNext()) {
				Map<String, Object> comentario = it.next();
				ComentarioResultado c = new ComentarioResultado();
				c.setAutor(comentario.get("c.autor").toString());
				c.setFecha(comentario.get("c.fecha").toString());
				c.setId(comentario.get("c.id").toString());
				c.setTexto(comentario.get("c.mensaje").toString());
				c.setImagen(comentario.get("c.imagen").toString());
				c.setUserPriority(Integer.valueOf(comentario.get("c.userPriority").toString()));
				c.setPopularidad(Integer.valueOf(comentario.get("c.popularidad").toString()));
				
				c.setSentimiento(comentario.get("c.sentimiento").toString());
				c.setRedSocial(comentario.get("c.redSocial").toString());
				
				comentarios.add(c);
			}
			
		} catch (Exception e) {
			return null;
		}
		
		return comentarios;
	}
	
	/**
	 * Devuelve un comentario.
	 * @param id identificador del comentario.
	 * @return comentario buscado.
	 */
	public Comentario findById(String id) {

		Session sesion = null;
		Comentario c = null;
		
		try {
			
			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}
			
			c = sesion.load(Comentario.class, id);
			
		} catch (Exception e) {
			return null;
		}

		return c;
	}

	@Override
	public List<ComentarioResultado> search(String query) {
		Session sesion = null;
		List<ComentarioResultado> comentarios = null;
		
		try {
			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}
			
			//Ejecutamos consulta
			Result r = sesion.query(query, Collections.emptyMap());
			
			Iterator<Map<String, Object>> it = r.iterator();
			
			comentarios = new LinkedList<ComentarioResultado>();
			
			//Iteramos sobre el resultado
			while(it.hasNext()) {
				Map<String, Object> comentario = it.next();
				ComentarioResultado c = new ComentarioResultado();
				
				c.setAutor(comentario.get("c.autor").toString());
				c.setFecha(comentario.get("c.fecha").toString());
				c.setId(comentario.get("c.id").toString());
				c.setTexto(comentario.get("c.mensaje").toString());
				c.setImagen(comentario.get("c.imagen").toString());
				c.setUserPriority(Integer.valueOf(comentario.get("c.userPriority").toString()));
				c.setPopularidad(Integer.valueOf(comentario.get("c.popularidad").toString()));				
				c.setSentimiento(comentario.get("c.sentimiento").toString());
				c.setRedSocial(comentario.get("c.redSocial").toString());
				comentarios.add(c);
			}
			
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
		
		return comentarios;
	}

}
