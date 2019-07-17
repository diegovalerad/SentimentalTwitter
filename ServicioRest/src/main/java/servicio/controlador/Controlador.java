package servicio.controlador;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import servicio.dao.ComentarioDAO;
import servicio.dao.DAOException;
import servicio.dao.DAOFactoria;
import servicio.dao.TemaDAO;
import servicio.modelo.Comentario;
import servicio.modelo.Tema;
import servicio.tipos.ComentarioResultado;
import servicio.tipos.TemaResultado;

/**
 * Controlador del Servicio Rest.
 * Se encarga de realizar las consultas a base de datos a través del DAO.
 * 
 * @author José Fernando.
 *
 */
public class Controlador {

	private static Controlador unicaInstancia;

	/**
	 * Devuelve la instancia del controlador.
	 * @return instancia del controlador.
	 */
	public static Controlador getUnicaInstancia() {
		
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		
		return unicaInstancia;
	}

	/**
	 * Constructor.
	 * Configura la factoría DAO.
	 */
	private Controlador() {
		
		try {
			DAOFactoria.setDAOFactoria(DAOFactoria.OGM);
		} catch (DAOException e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * Operaciones sobre Temas
	 */
	
	/**
	 * Persiste un tema en base de datos.
	 * @param tema tema que se va a persistir.
	 */
	public boolean crearTema(Tema tema) {
		
		TemaDAO tDAO = DAOFactoria.getUnicaInstancia().getTemaDAO();
		
		Iterable<Tema> it = Controlador.getUnicaInstancia().getTemas();
		List<Tema> tExists = new LinkedList<Tema>();
		
		if(it == null)
			return false;
		
		it.forEach(tExists::add);
		
		if(tExists.contains(tema)) {
			return false;
		}else {
			tDAO.createTema(tema);
			return true;
		}
	}
	
	/**
	 * Devuelve una colección con todos temas en base de datos.
	 * @return colección de temas.
	 */
	public Collection<Tema> getTemas(){
		
		TemaDAO tDAO = DAOFactoria.getUnicaInstancia().getTemaDAO();
		return tDAO.findAll();
	}
	
	/**
	 * Devuelve una colección de temas simplificados.
	 * @return colección de temas resultados.
	 */
	public Collection<TemaResultado> getListadoTemas(){
		
		TemaDAO tDAO = DAOFactoria.getUnicaInstancia().getTemaDAO();
		return tDAO.getListadoTemas();
	}
	
	/**
	 * Devuelve un tema a partir de su ID.
	 * @param id identificador del tema.
	 * @return tema encontrado.
	 */
	public Tema getTemaById(String id) {
		
		TemaDAO tDAO = DAOFactoria.getUnicaInstancia().getTemaDAO();
		
		return tDAO.findById(id);
	}
	
	/*
	 * Operaciones sobre comentarios
	 */

	/**
	 * Devuelve una lista de de comentarios simplificados relacionados con un tema.
	 * @param tema ID del tema.
	 * @return lista de comentarios.
	 */
	public List<ComentarioResultado> getComentariosByTema(String tema) {
		
		ComentarioDAO cDAO = DAOFactoria.getUnicaInstancia().getComentarioDAO();
		
		return cDAO.findByTema(tema);
	}

	/**
	 * Devuelve un comentario a partir de su ID.
	 * @param id identificador del comentario.
	 * @return comentario encontrado.
	 */
	public Comentario getComentarioById(String id) {
		
		ComentarioDAO cDAO = DAOFactoria.getUnicaInstancia().getComentarioDAO();
		
		return cDAO.findById(id);
	}
	
	/**
	 * Devuelve una lista de comentarios según la consulta.
	 * @param query Consulta
	 * @return Comentarios resultado a la consulta.
	 */
	public List<ComentarioResultado> buscar(String query){
		
		ComentarioDAO cDAO = DAOFactoria.getUnicaInstancia().getComentarioDAO();
		
		return cDAO.search(query);
	}
 	
}
