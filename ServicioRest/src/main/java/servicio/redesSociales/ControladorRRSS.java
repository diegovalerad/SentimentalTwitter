package servicio.redesSociales;

import java.util.LinkedList;
import java.util.List;

import servicio.modelo.Tema;
import servicio.redesSociales.twitter.ControladorTwitter;

/**
 * Clase que representa a un controlador de redes sociales.
 * <p>
 * Cada operación relacionada con las redes sociales las controla este controlador
 * @author Diego Valera Duran
 *
 */
public class ControladorRRSS {
	
	private static ControladorRRSS unicaInstancia;
	private List<IRedSocial> rrss;
	
	protected ControladorRRSS() {
		rrss = new LinkedList<IRedSocial>();
		
		ControladorTwitter twitter = new ControladorTwitter();
		rrss.add(twitter);
	}
	
	public static ControladorRRSS getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorRRSS();
		return unicaInstancia;
	}
	
	/**
	 * Busca los comentarios en todas las redes sociales y los inserta en
	 * la base de datos
	 */
	public void buscarComentarios() {
		for (IRedSocial rs : rrss)
			rs.buscarComentarios();
	}
	
	/**
	 * Busca los comentarios en todas las redes sociales sobre un tema en concreto 
	 * y los inserta en la base de datos
	 * @param tema Tema sobre el que buscar
	 */
	public void buscarComentarios(Tema tema) {
		for (IRedSocial rs : rrss)
			rs.buscarComentarios(tema);
	}
	
	/**
	 * Devuelve los nombres de todas las redes sociales
	 * @return Devuelve una lista de cadenas de texto con los nombres de las redes
	 * sociales, "TWITTER" por ejemplo.
	 */
	public List<String> getNombresRedesSociales(){
		List<String> listaRRSS = new LinkedList<String>();
		for (IRedSocial rs : rrss) {
			listaRRSS.add(rs.getRedSocial());
		}
		return listaRRSS;
	}
}
