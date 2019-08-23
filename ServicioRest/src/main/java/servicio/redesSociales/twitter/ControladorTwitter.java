package servicio.redesSociales.twitter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import servicio.controlador.Controlador;
import servicio.dao.ComentarioDAO;
import servicio.dao.DAOFactoria;
import servicio.modelo.Comentario;
import servicio.modelo.Tema;
import servicio.redesSociales.IRedSocial;
import twitter4j.Query;
import twitter4j.Query.ResultType;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.conf.ConfigurationBuilder;
import servicio.utils.ProcesadorFechas;
import servicio.utils.ProcesadorTexto;

/**
 * Controlador que hace uso de la API REST de Twitter.
 * 
 * @author José Fernando
 * @author Diego Valera Duran
 *
 */
public class ControladorTwitter implements IRedSocial {
	private Twitter twitterService;
	private List<String> specialUsers;
	private final int MAX_REPLIES_PER_COMMENT = 5;
	private final String redSocial = "TWITTER";
	
	@Override
	public String getRedSocial() {
		return redSocial;
	}

	/**
	 * Constructor. Crea la factoría y el servicio de Twitter.
	 */
	public ControladorTwitter() {
		inicializarServicio();
		inicializarUsuariosEspeciales();
	}
	
	/**
	 * Inicializa el servicio de twitter con información del fichero properties
	 */
	private void inicializarServicio() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		Properties props = new Properties();
		try {
			props.load(getClass().getResourceAsStream("/rrss/twitter/twitter4j.properties"));
			
			String oauthConsumerKey = props.getProperty("oauth.consumerKey");
			String oauthConsumerSecret = props.getProperty("oauth.consumerSecret");
			String oauthAccessToken = props.getProperty("oauth.accessToken");
			String oauthAcessTokenSecret = props.getProperty("oauth.accessTokenSecret");
			String tweetModeExtendedString = props.getProperty("tweetModeExtended");
			
			boolean tweetModeExtended = false;
			if (tweetModeExtendedString.equals("true")){
				tweetModeExtended = true;
			}
			
			cb.setDebugEnabled(true)
			  .setOAuthConsumerKey(oauthConsumerKey)
			  .setOAuthConsumerSecret(oauthConsumerSecret)
			  .setOAuthAccessToken(oauthAccessToken)
			  .setOAuthAccessTokenSecret(oauthAcessTokenSecret)
			  .setTweetModeExtended(tweetModeExtended);
			
			TwitterFactory tf = new TwitterFactory(cb.build());
			twitterService = tf.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Inicializa una lista de usuarios especiales para todos los usuarios.
	 * <p>
	 * Los usuarios especiales son usuarios cuyos comentarios tienen mayor
	 * prioridad que el resto, por lo que se mostrarán antes que los normales.
	 * <p>
	 * Si un usuario tiene usuarios favoritos: primero se muestran los favoritos, 
	 * después los comentarios de los usuarios especiales, y después el resto
	 */
	private void inicializarUsuariosEspeciales() {
		URL url = this.getClass().getClassLoader().getResource("rrss/twitter/specialUsers.txt");

		specialUsers = new LinkedList<String>();
		
		try {
			Path path = Paths.get(url.toURI());
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

			for (String user : lines) {
				specialUsers.add(user);
			}
		} catch (URISyntaxException e) {
			System.err.println("Controlador Twitter - Error al acceder al fichero de usuarios especiales: " + e);
		} catch (IOException e) {
			System.err.println("Controlador Twitter - Error al acceder a los datos del fichero de usuarios especiales: " + e);
		}
	}

	/**
	 * Realiza la búsqueda de comentarios.
	 */
	@Override
	public void buscarComentarios() {
		// Se recogen los temas de BD
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
	 * Realiza la búsqueda de comentarios sobre un tema en concreto
	 * @param tema
	 */
	@Override
	public void buscarComentarios(Tema tema) {
		List<Tema> temas = new LinkedList<Tema>();
		temas.add(tema);
		
		busquedaPorUsuarios(temas);
		busquedaPorTemas(temas);
	}

	/**
	 * Realiza la búsqueda de comentarios en Twitter.
	 * 
	 * @param temas
	 *            Temas sobre los que se realizan los comentarios.
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

	/**
	 * Realiza la búsqueda de comentarios de los usuarios especiales en Twitter
	 * @param temas Temas sobre los que buscar comentarios
	 */
	private void busquedaPorUsuarios(List<Tema> temas) {

		LinkedList<Comentario> comentarios = new LinkedList<Comentario>();

		for (String usuario : specialUsers) {

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
	
	/**
	 * Método que lanza una búsqueda de comentarios usando la API de twitter
	 * @param consulta Query con la que realizar la búsqueda
	 * @param t Tema sobre el que realizar la consulta
	 * @return Lista de comentarios obtenidos tras realizar la búsqueda
	 */
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
					
					// Obtenemos sus mejores respuestas (mas populares)
					List<Comentario> respuestas = obtenerMejoresRespuestas(status);
					c.getRespuestas().addAll(respuestas);
					
					comentarios.add(c);
					comentarios.addAll(respuestas);
				} else {
					// Si el comentario ya existe pero el tema es nuevo, lo añade y lo modifica en BD.
					if (!c.getTemas().contains(t)) {
						c.getTemas().add(t);
						comentarios.add(c);
					}
				}
			}
		} catch (TwitterException e) {

			System.err.println("Error al conectar con Twitter.");
		}
		
		return comentarios;
	}

	/**
	 * A partir de un objeto Status, se crea e inicializa un comentario
	 * @param status Objeto con información resultante de la búsqueda de comentarios
	 * @return Objeto comentario
	 */
	private Comentario initComment(Status status) {

		Comentario c = new Comentario();

		c.setCreador("@" + status.getUser().getScreenName());
		c.setFecha(ProcesadorFechas.procesarFecha(status.getCreatedAt()));
		c.setId(Long.toString(status.getId()));
		c.setTexto(ProcesadorTexto.eliminarUrls(status.getText()));
		c.setImagen(status.getUser().getOriginalProfileImageURL());
		int likes = status.getFavoriteCount();
		int retweets = status.getRetweetCount();
		c.setPopularidad(likes + retweets);

		for (URLEntity u : status.getURLEntities()) {
			c.getEnlaces().add(u.getURL());
		}
		
		String sentimiento = Controlador.getUnicaInstancia().getSentimiento(c.getTexto());
		c.setSentimiento(sentimiento);
		
		c.setRedSocial(redSocial);

		return c;
	}
	
	/**
	 * Obtiene entre 0 y {@link #MAX_REPLIES_PER_COMMENT} respuestas a un tweet, ordenadas por popularidad
	 * @param status
	 * @return
	 */
	private List<Comentario> obtenerMejoresRespuestas(Status status) {
		String screenName = status.getUser().getScreenName();
		long tweetID = status.getId();
		
		ArrayList<Status> respuestas = getReplies(screenName, tweetID);
		
		Collections.sort(respuestas, Collections.reverseOrder(new TweetsComparator()));
		
		int n = MAX_REPLIES_PER_COMMENT;
		if (respuestas.size() < MAX_REPLIES_PER_COMMENT)
			n = respuestas.size();
		
		LinkedList<Comentario> listaRespuestas = new LinkedList<Comentario>();
		for (int i = 0; i < n; i++) {
			Comentario c = initComment(respuestas.get(i));
			listaRespuestas.add(c);
		}
		return listaRespuestas;
	}
	
	/**
	 * Obtiene todas las respuestas a un tweet
	 * @param screenName Nombre de usuario del tweet
	 * @param tweetID ID de usuario del tweet
	 * @return
	 */
	private ArrayList<Status> getReplies(String screenName, long tweetID) {
		ArrayList<Status> replies = new ArrayList<>();

		try {
			Query query = new Query("to:" + screenName + " since_id:" + tweetID);
			query.setResultType(ResultType.mixed); // Populares + nuevos
			query.setCount(100);
			QueryResult results;

			results = twitterService.search(query);
			List<Status> tweets = results.getTweets();

			for (Status tweet : tweets)
				if (tweet.getInReplyToStatusId() == tweetID)
					replies.add(tweet);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return replies;
	}
}
