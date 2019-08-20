package servicio.redesSociales;

import java.util.LinkedList;
import java.util.List;

import servicio.modelo.Tema;
import servicio.redesSociales.twitter.ControladorTwitter;

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
	
	public void buscarComentarios() {
		for (IRedSocial rs : rrss)
			rs.buscarComentarios();
	}
	
	public void buscarComentarios(Tema tema) {
		for (IRedSocial rs : rrss)
			rs.buscarComentarios(tema);
	}
	
	public List<String> getNombresRedesSociales(){
		List<String> listaRRSS = new LinkedList<String>();
		for (IRedSocial rs : rrss) {
			listaRRSS.add(rs.getRedSocial());
		}
		return listaRRSS;
	}
}
