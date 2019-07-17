package servicio.controlador;

import java.util.LinkedList;
import java.util.List;

import servicio.dao.ComentarioDAO;
import servicio.dao.DAOFactoria;
import servicio.modelo.Comentario;
import servicio.modelo.Tema;
import twitter4j.Query;
import twitter4j.Query.ResultType;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import servicio.utils.ProcesadorFechas;
import servicio.utils.ProcesadorTexto;

/**
 * Controlador que hace uso de la API REST de Twitter.
 * 
 * @author José Fernando
 *
 */
public class ControladorTwitter {

	private static ControladorTwitter unicaInstancia;
	private Twitter twitterService;

	private static String[] users = { "NEJM", "WHO", "IARCWHO", "ASCO" };

	/**
	 * Devuelve la instancia del controlador.
	 * 
	 * @return instancia del controlador.
	 */
	public static ControladorTwitter getUnicaInstancia() {

		if (unicaInstancia == null)
			unicaInstancia = new ControladorTwitter();

		return unicaInstancia;
	}

	/**
	 * Constructor. Crea la factoría y el servicio de Twitter.
	 */
	private ControladorTwitter() {

		TwitterFactory tf = new TwitterFactory();
		twitterService = tf.getInstance();
	}

	/**
	 * Realiza la búsqueda de comentarios. Si no existen temas en base de datos los
	 * crea.
	 */
	public void buscarComentarios(List<Tema> temas) {

		// Se crean los temas que no existan ya en base de datos
		if(temas != null)
			crearTemas(temas);
		
		// Se recogen los temas de BD tras la posible creación de nuevos temas
		Iterable<Tema> it = Controlador.getUnicaInstancia().getTemas();
		List<Tema> tExists = new LinkedList<Tema>();
		if(it != null) {
			it.forEach(tExists::add);
			// Busqueda por temas y usuarios
			busquedaPorUsuarios(tExists);
						
			// Por cada tema se buscan tweets y se persisten
			busquedaPorTemas(tExists);
		}

	}

	/**
	 * Crea temas en base de datos. Comprueba si existen los temas de la lista en
	 * base de datos y persiste los que no existan.
	 * 
	 * @param temas
	 *            lista de temas
	 */
	private void crearTemas(List<Tema> temas) {

		// Si no existen se crean y se persisten todos los temas recuperados de ArchMS
		for (Tema t : temas) {
			Controlador.getUnicaInstancia().crearTema(t);
		}

	}

	/**
	 * Realiza la búsqueda de comentarios en Twitter.
	 * 
	 * @param temas
	 *            Temas sobre los que se realizan los comentarios.
	 * @return lista de comentarios encontrados.
	 */
	private void busquedaPorTemas(List<Tema> temas) {

		LinkedList<Comentario> comentarios = new LinkedList<Comentario>();

		// Buscamos por cada tema
		for (Tema t : temas) {

			// Tweets que contienen el nombre del tema
			Query query = new Query(t.getNombre() + " -filter:retweets"); // filtramos retweets
			query.setResultType(ResultType.popular); // Solo tweets populares
			query.setLang("en"); // En idioma español
			query.setCount(100); // Por defecto solo 15 resultados
			
			comentarios.addAll(lanzarBusqueda(query,t));	
		}

		ComentarioDAO cDAO = DAOFactoria.getUnicaInstancia().getComentarioDAO();
		cDAO.createComentarios(comentarios);
	}

	private void busquedaPorUsuarios(List<Tema> temas) {

		LinkedList<Comentario> comentarios = new LinkedList<Comentario>();

		for (String usuario : users) {

			for (Tema t : temas) {

				Query query = new Query("from:@" + usuario + " " + t.getNombre() + " -filter:retweets");
				query.setCount(100); // Por defecto solo 15 resultados

				comentarios.addAll(lanzarBusqueda(query,t));
			}
		}
		
		for(Comentario c : comentarios)
			c.setUserPriority(10);
		
		ComentarioDAO cDAO = DAOFactoria.getUnicaInstancia().getComentarioDAO();
		cDAO.createComentarios(comentarios);
	}
	
	private List<Comentario> lanzarBusqueda (Query consulta, Tema t) {
		
		LinkedList<Comentario> comentarios = new LinkedList<Comentario>();
		QueryResult result;

		try {
			
			result = this.twitterService.search(consulta);
			
			for (Status status : result.getTweets()) {

				String id = Long.toString(status.getId());
				Comentario c = Controlador.getUnicaInstancia().getComentarioById(id);

				if (c == null) { // Si no existe, se crea y se persiste en BD
					c = initComment(status);
					c.getTemas().add(t);
					comentarios.add(c);
				} else {
					// Si el comentario ya existe pero el tema es nuevo, lo añade y lo modifica en BD.
					if (!c.getTemas().contains(t)) {
						c.getTemas().add(t);
						comentarios.add(c);
					}
				}
			}
		} catch (TwitterException e) {

			System.out.println("Error al conectar con Twitter.");
		}
		
		return comentarios;
	}

	private Comentario initComment(Status status) {

		Comentario c = new Comentario();

		c.setCreador("@" + status.getUser().getScreenName());
		c.setFecha(ProcesadorFechas.procesarFecha(status.getCreatedAt()));
		c.setId(Long.toString(status.getId()));
		c.setTexto(ProcesadorTexto.eliminarUrls(status.getText()));
		c.setImagen(status.getUser().getOriginalProfileImageURL());
		c.setLikes(status.getFavoriteCount());
		c.setRetweets(status.getRetweetCount());
		c.setPopularidad(c.getLikes() + c.getRetweets());

		for (URLEntity u : status.getURLEntities()) {
			c.getEnlaces().add(u.getURL());
		}

		return c;
	}
}
