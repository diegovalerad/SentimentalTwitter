package servicio.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import servicio.buscadortemas.TemasFactoria;
import servicio.dao.ComentarioDAO;
import servicio.dao.DAOException;
import servicio.dao.DAOFactoria;
import servicio.dao.TemaDAO;
import servicio.dao.UserDAO;
import servicio.modelo.Comentario;
import servicio.modelo.Tema;
import servicio.modelo.Usuario;
import servicio.otrosServicios.ConectorSentimentAnalizer;
import servicio.redesSociales.ControladorRRSS;
import servicio.tipos.ComentarioResultado;
import servicio.tipos.TemaResultado;
import servicio.utils.PasswordAuthentication;

/**
 * Controlador del Servicio Rest. Se encarga de realizar las consultas a base de
 * datos a través del DAO.
 * 
 * @author José Fernando.
 *
 */
public class Controlador {

	private static Controlador unicaInstancia;
	private static boolean sentimentServiceConnected;

	/**
	 * Devuelve la instancia del controlador.
	 * 
	 * @return instancia del controlador.
	 */
	public static Controlador getUnicaInstancia() {

		if (unicaInstancia == null)
			unicaInstancia = new Controlador();

		return unicaInstancia;
	}

	/**
	 * Constructor. Configura las factorias y la conexión con el servicio de sentimientos
	 */
	private Controlador() {
		try {
			initBuscadorTemas();
			
			DAOFactoria.setDAOFactoria(DAOFactoria.OGM);
			TemasFactoria.setDAOFactoria(TemasFactoria.ARCH_MS);
			
			initSentimentServiceConexion();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Inicializa los datos del buscador de temas.
	 */
	private void initBuscadorTemas() {
		Properties props = new Properties();
		try {
			props.load(getClass().getResourceAsStream("/buscadorTemas/buscadorTemas.properties"));
			String user = props.getProperty("user");
			String pass = props.getProperty("pass");
			String url = props.getProperty("url");
			
			TemasFactoria.inicializar(user, pass, url);
		} catch (IOException e) {
			System.err.println("Controlador - Error al acceder al fichero de propiedades del buscador de temas: " + e);
		}
	}
	
	/**
	 * Inicializa los datos de la conexión con el servicio de sentimientos
	 */
	private void initSentimentServiceConexion() {
		sentimentServiceConnected = false;
		Properties props = new Properties();
		try {
			props.load(getClass().getResourceAsStream("/conectorSentimentAnalizer.properties"));
			String connectedString = props.getProperty("sentimentService_connected");
			if (connectedString.equals("true")) {
				sentimentServiceConnected = true;
				
				String baseURL = props.getProperty("sentimentService_baseURL");
				String URL_method = props.getProperty("sentimentService_URL_method");
				String queryText = props.getProperty("sentimentService_URL_query_text");
				String queryAlgorithm = props.getProperty("sentimentService_URL_query_algorithm");
				String queryAlgorithmDefault = props.getProperty("sentimentService_URL_query_algorithm_default");
				
				ConectorSentimentAnalizer.getUnicaInstancia().inicializar(baseURL, URL_method, queryText, queryAlgorithm, queryAlgorithmDefault);
			}
		} catch (IOException e) {
			System.err.println("Controlador - Error al acceder al acceder al fichero de propiedades del Servicio de sentimientos: " + e);
		}
	}
	
	public boolean isSentimentServiceConnected() {
		return sentimentServiceConnected;
	}

	/**
	 * Busca los temas en el buscador de temas elegido, asi como los comentarios
	 * a dichos temas, y los persiste en la base de datos.
	 */
	public void inicializarTemasYComentarios() {
		inicializarTemas();
		inicializarComentariosDeRedesSociales();
	}

	/**
	 * Inicializa los temas a usar, usando el servicio buscador de temas, no la base
	 * de datos del programa.
	 * 
	 * @return Lista de temas
	 */
	public void inicializarTemas() {
		List<Tema> listaTemas = TemasFactoria.getUnicaInstancia().getTemas();
		for (Tema t : listaTemas)
			crearTema(t);
	}

	/**
	 * Busca los comentarios de las redes sociales y los guarda en la base de datos
	 * 
	 */
	public void inicializarComentariosDeRedesSociales() {
		ControladorRRSS.getUnicaInstancia().buscarComentarios();
	}
	
	/**
	 * Busca los comentarios de las redes sociales sobre un tema en concreto y 
	 * los guarda en la base de datos
	 * 
	 */
	public void inicializarComentariosDeRedesSociales(Tema t) {
		ControladorRRSS.getUnicaInstancia().buscarComentarios(t);
	}

	/*
	 * Operaciones sobre Temas
	 */

	/**
	 * Persiste un tema en base de datos.
	 * 
	 * @param tema tema que se va a persistir.
	 */
	public boolean crearTema(Tema tema) {

		TemaDAO tDAO = DAOFactoria.getUnicaInstancia().getTemaDAO();

		Iterable<Tema> it = Controlador.getUnicaInstancia().getTemas();
		List<Tema> tExists = new LinkedList<Tema>();

		if (it == null)
			return false;

		it.forEach(tExists::add);

		if (tExists.contains(tema)) {
			return false;
		} else {
			tDAO.createTema(tema);
			return true;
		}
	}

	/**
	 * Devuelve una colección con todos temas en base de datos.
	 * 
	 * @return colección de temas.
	 */
	public Collection<Tema> getTemas() {

		TemaDAO tDAO = DAOFactoria.getUnicaInstancia().getTemaDAO();
		return tDAO.findAll();
	}

	/**
	 * Devuelve una colección de temas simplificados.
	 * 
	 * @return colección de temas resultados.
	 */
	public Collection<TemaResultado> getListadoTemas() {

		TemaDAO tDAO = DAOFactoria.getUnicaInstancia().getTemaDAO();
		return tDAO.getListadoTemas();
	}

	/**
	 * Devuelve un tema a partir de su ID.
	 * 
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
	 * 
	 * @param tema ID del tema.
	 * @return lista de comentarios.
	 */
	public List<ComentarioResultado> getComentariosByTema(String tema) {

		ComentarioDAO cDAO = DAOFactoria.getUnicaInstancia().getComentarioDAO();

		return cDAO.findByTema(tema);
	}

	/**
	 * Devuelve un comentario a partir de su ID.
	 * 
	 * @param id identificador del comentario.
	 * @return comentario encontrado.
	 */
	public Comentario getComentarioById(String id) {

		ComentarioDAO cDAO = DAOFactoria.getUnicaInstancia().getComentarioDAO();

		return cDAO.findById(id);
	}
	
	/**
	 * 
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
	public String procesarFecha(String since, String until, 
			List<String> temas, List<String> operadores) {
		
		return DAOFactoria.getUnicaInstancia().procesarFecha(since, until, temas, operadores);
	}

	/**
	 * Devuelve una lista de comentarios según la consulta.
	 * 
	 * @param query Consulta
	 * @return Comentarios resultado a la consulta.
	 */
	public List<ComentarioResultado> buscar(String query) {
		ComentarioDAO cDAO = DAOFactoria.getUnicaInstancia().getComentarioDAO();

		return cDAO.search(query);
	}
	
	/**
	 * Obtiene el sentimiento de una cadena de texto
	 * @param texto
	 * @return
	 */
	public String getSentimiento(String texto) {
		if (!sentimentServiceConnected)
			return "";
		String sentimiento = ConectorSentimentAnalizer.getUnicaInstancia().getSentiment(texto);
		return sentimiento;
	}
	
	/**
	 * Obtiene una lista de cadenas de texto con todas las redes sociales
	 * @return
	 */
	public List<String> getRedesSociales() {
		return ControladorRRSS.getUnicaInstancia().getNombresRedesSociales();
	}
	
	/**
	 * Persiste un usuario en la base de datos
	 * @param email Email del usuario
	 * @param password Contraseña del usuario
	 */
	public boolean crearUsuario(String email, String password) {
		UserDAO uDAO = DAOFactoria.getUnicaInstancia().getUserDAO();
		
		List<String> usuariosFavoritos = new ArrayList<String>();
		
		char[] passCharArray = password.toCharArray();
		
		PasswordAuthentication pa = new PasswordAuthentication();
		String token = pa.hash(passCharArray);
		
		Usuario usuario = new Usuario();
		usuario.setEmail(email);
		usuario.setPassword(token);
		usuario.setUsuariosFavoritos(usuariosFavoritos);
		
		return uDAO.createUsuario(usuario);
	}
	
	/**
	 * Intenta hacer login
	 * @param email Correo del usuario
	 * @param password Contraseña del usuario
	 * @return Si los datos son correctos
	 */
	public boolean login(String email, String password) {
		UserDAO uDAO = DAOFactoria.getUnicaInstancia().getUserDAO();
		
		Usuario usuario = uDAO.findUsuarioByEmail(email);
		if (usuario == null)
			return false;
		
		char[] passCharArray = password.toCharArray();
		String token = usuario.getPassword();
		
		PasswordAuthentication pa = new PasswordAuthentication();
		
		boolean login = pa.authenticate(passCharArray, token);
		return login;
	}
	
	/**
	 * Añade o elimina un favorito de la lista de personas favoritas del usuario.
	 * <br>
	 * Si la lista del usuario ya contenía el favorito, lo elimina.
	 * <br>
	 * Si la lista del usuario no lo contenía, lo añade.
	 * @param email Email del usuario que quiere modificar su lista
	 * @param redSocial Red social de la persona favorita
	 * @param nombre Nombre de la persona favorita
	 * @return Si se ha podido realizar con exito la modificación de la lista de personas favoritas
	 */
	public boolean modificarFavorito(String email, String redSocial, String nombre) {
		UserDAO uDAO = DAOFactoria.getUnicaInstancia().getUserDAO();
		
		Usuario usuario = uDAO.findUsuarioByEmail(email);
		if (usuario == null)
			return false;
		
		List<String> favoritos = usuario.getUsuariosFavoritos();
		String busqueda = redSocial + "_" + nombre;
		
		if (favoritos.contains(busqueda))
			favoritos.remove(busqueda);
		else
			favoritos.add(busqueda);
		
		uDAO.createUsuario(usuario);
		return true;
	}
	
	/**
	 * Busca a un usuario en la base de datos
	 * @param email Email del usuario
	 * @return Usuario u objeto NULO si no se encuentra
	 */
	public Usuario findUsuarioByEmail(String email) {
		UserDAO uDAO = DAOFactoria.getUnicaInstancia().getUserDAO();
		
		Usuario usuario = uDAO.findUsuarioByEmail(email);
		
		return usuario;
	}
	
	/**
	 * Busca las personas favoritas del usuario
	 * @param email Correo del usuario
	 * @return Lista de personas favoritas
	 */
	public List<String> getFavoritos(String email){
		UserDAO uDAO = DAOFactoria.getUnicaInstancia().getUserDAO();
		
		List<String> favoritos = uDAO.getFavoritos(email);
		
		return favoritos;
	}
	
	/**
	 * Actualiza la contraseña del usuario
	 * @param email Correo del usuario
	 * @param password Nueva contraseña
	 * @return true si se ha podido actualizar, false en caso contrario
	 */
	public boolean updateUsuario(String email, String password) {
		UserDAO uDAO = DAOFactoria.getUnicaInstancia().getUserDAO();
		
		Usuario usuario = uDAO.findUsuarioByEmail(email);
		if (usuario == null)
			return false;
		
		usuario.setPassword(password);
		
		return uDAO.createUsuario(usuario);
	}
	
}
