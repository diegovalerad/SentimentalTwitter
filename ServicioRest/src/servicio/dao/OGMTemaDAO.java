package servicio.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import servicio.modelo.Tema;
import servicio.tipos.TemaResultado;

/**
 * Implementación de la interfaz TemaDAO para OGM.
 * 
 * @author José Fernando
 *
 */
public class OGMTemaDAO implements TemaDAO {

	private SessionFactory sessionFactory;

	/**
	 * Constructor.
	 * 
	 * @param sf
	 *            Sesión de la factoría de Neo4j
	 */
	public OGMTemaDAO(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	/**
	 * Persiste un tema en base de datos.
	 * 
	 * @param tema
	 *            tema a persistir.
	 */
	@Override
	public void createTema(Tema tema) {

		Session sesion = null;

		try {

			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}

			sesion.save(tema);

		} catch (Exception e) {
			System.out.println("Error al crear el tema en base de datos.");
		}
	}

	/**
	 * Persiste una colección de temas en base de datos.
	 * 
	 * @param temas
	 *            lista de temas.
	 */
	@Override
	public void createTemas(Collection<Tema> temas) {

		Session sesion = null;

		try {

			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}

			sesion.save(temas);

		} catch (Exception e) {
			System.out.println("Error al crear los temas en base de datos.");
		}

	}

	/**
	 * Modifica un tema existente en base de datos.
	 * 
	 * @param tema
	 *            tema a modificar.
	 */
	@Override
	public void modifyTema(Tema tema) {

		createTema(tema);
	}

	/**
	 * Modifica una colección de temas existenes en base de datos.
	 * 
	 * @param temas
	 *            lista de temas a modificar.
	 */
	@Override
	public void modifyTemas(Collection<Tema> temas) {

		createTemas(temas);
	}

	/**
	 * Elimina un tema existente en base de datos.
	 * 
	 * @param tema
	 *            tema a eliminar.
	 */
	@Override
	public void deleteTema(Tema tema) {

		Session sesion = null;

		try {

			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}

			sesion.delete(tema);

		} catch (Exception e) {
			System.out.println("Error al eliminar el tema de base de datos.");
		}
	}

	/**
	 * Elimina una colección de temas existente en base de datos.
	 * 
	 * @param temas
	 *            lista de temas a eliminar.
	 */
	@Override
	public void deleteTemas(Collection<Tema> temas) {

		Session sesion = null;

		try {

			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}

			sesion.delete(temas);

		} catch (Exception e) {
			System.out.println("Error al eliminar los temas de base de datos.");
		}
	}

	/**
	 * Devuelve todos los temas existentes en base de datos.
	 * 
	 * @return colección de temas.
	 */
	@Override
	public Collection<Tema> findAll() {

		Session sesion = null;
		Collection<Tema> temas = null;

		try {

			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}

			temas = sesion.loadAll(Tema.class);

		} catch (Exception e) {
			System.out.println("Error buscar los temas en base de datos.");
			return null;
		}

		return temas;
	}

	/**
	 * Busca un tema por su nombre y lo devuelve.
	 * 
	 * @param nombre
	 *            nombre del tema.
	 * @return tema buscado.
	 */
	@Override
	public Tema findByNombre(String nombre) {

		Session sesion = null;
		Tema t = null;

		try {
			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}

			t = sesion.load(Tema.class, nombre);

		} catch (Exception e) {
			System.out.println("Error buscar el tema " + nombre + " en base de datos.");
			return null;
		}

		return t;
	}

	/**
	 * Devuelve una colección con todos los temas simplificados.
	 * 
	 * @return colección de temas.
	 */
	@Override
	public Collection<TemaResultado> getListadoTemas() {

		Session sesion = null;
		List<TemaResultado> temas = null;

		// Recogemos solo identificador y nombre
		String query = "MATCH (t:Tema) RETURN t.id, t.nombre";

		try {
			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}
			// Ejecutamos consulta
			Result r = sesion.query(query, Collections.emptyMap());
			Iterator<Map<String, Object>> it = r.iterator();
			temas = new LinkedList<TemaResultado>();

			// Iteramos sobre el resultado.
			while (it.hasNext()) {
				Map<String, Object> tema = it.next();
				TemaResultado t = new TemaResultado();
				t.setId(tema.get("t.id").toString());
				t.setNombre(tema.get("t.nombre").toString());
				temas.add(t);
			}
		} catch (Exception e) {
			System.out.println("Error buscar los temas en base de datos.");
			return null;
		}

		return temas;
	}

	/**
	 * Busca un tema por su ID y lo devuelve.
	 * 
	 * @param id
	 *            identificador del tema.
	 * @return tema buscado.
	 */
	@Override
	public Tema findById(String id) {

		Session sesion = null;
		Tema t = null;

		try {
			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}

			t = sesion.load(Tema.class, id);

		} catch (Exception e) {
			System.out.println("Error buscar el tema " + id + " en base de datos.");
			return null;
		}

		return t;
	}
}
