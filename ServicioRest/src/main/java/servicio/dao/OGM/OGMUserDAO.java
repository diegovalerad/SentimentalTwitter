package servicio.dao.OGM;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import servicio.dao.UserDAO;
import servicio.modelo.Favorito;
import servicio.modelo.Usuario;

/**
 * Implementación de la interfaz UserDAO para OGM.
 * @author Diego Valera Duran
 *
 */
public class OGMUserDAO implements UserDAO {
	
	private SessionFactory sessionFactory;
	
	public OGMUserDAO(SessionFactory sf) {
		sessionFactory = sf;
	}

	@Override
	public boolean createUsuario(Usuario usuario) {
		Session sesion = null;

		try {
			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}

			sesion.save(usuario);
			
			return true;
		} catch (Exception e) {
			System.err.println("OGMUserDAO - Error al crear un usuario: " + e);
			return false;
		}
	}

	@Override
	public void deleteUsuario(Usuario usuario) {
		Session sesion = null;

		try {
			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}
			
			List<String> listaFavoritosString = new LinkedList<String>();
			for (Favorito f : usuario.getUsuariosFavoritos()) {
				listaFavoritosString.add(f.getId());
			}
			
			String query = "MATCH (u:Usuario {email:\"" + usuario.getEmail() + "\"})" + 
						 	" DETACH DELETE u";
			sesion.query(query, Collections.emptyMap());
			
			for (String s : listaFavoritosString) {
				query = "MATCH (f:Favorito {id:\"" + s + "\"})" +
						" DETACH DELETE f";
				sesion.query(query, Collections.emptyMap());
			}
		} catch (Exception e) {
			System.err.println("OGMUserDAO - Error al eliminar un usuario: " + e);
		}
	}
	
	@Override
	public Usuario findUsuarioByEmail(String email) {
		Session sesion = null;
		Usuario usuario = null;

		try {
			synchronized (sessionFactory) {
				sesion = sessionFactory.openSession();
			}
			usuario = sesion.load(Usuario.class, email);
		} catch (Exception e) {
			System.err.println("OGMUserDAO - Error al buscar el usuario '" + email + "': " + e);
			return null;
		}

		return usuario;
	}

}
