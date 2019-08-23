package servicio.modelo;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

/**
 * Esta clase representa a las entidades del dominio 'Comentario' que van a ser persistidas en base de datos.
 * Un Comentario es una opinión o explicación acerca de un tema.
 * 
 * @author José Fernando
 * @author Diego Valera Duran
 *
 */
@NodeEntity
public class Comentario {

	@Id
	@Property(name = "id")
	private String id;
	
	@Property(name="mensaje")
	private String texto;
	
	@Property(name="autor")
	private String creador;
	
	@Property(name="fecha")
	private String fecha;
	
	@Property(name="imagen")
	private String imagen;
	
	@Property(name="popularidad")
	private int popularidad;
	
	@Property
	private int userPriority;

	@Property(name="enlaces")
	private List<String> enlaces = new ArrayList<String>();

	/*
	 * Un Comentario está relacionado con uno o varios temás mediante la relación RELATED TO.
	 * Esta relación no está dirigida hacia ningún lado por lo se puede navegar hacia ambos lados.
	 */
	@Relationship(type="RELATED_TO", direction=Relationship.UNDIRECTED)
	private List<Tema> temas = new ArrayList<Tema>();
	
	
	@Property(name="sentimiento")
	private String sentimiento;
	
	
	@Relationship(type="RELATED_TO", direction=Relationship.OUTGOING)
	private List<Comentario> respuestas = new ArrayList<Comentario>();
	
	@Property(name="redSocial")
	private String redSocial;
	
	/**
	 * Constructor vacío.
	 */
	public Comentario(){
		
		this.userPriority = 0;
	}
	
	/**
	 * Devuelve la lista de temas con los que está relacionado el comentario.
	 * 
	 * @return lista de temas con los que se relaciona el comentario.
	 */
	public List<Tema> getTemas() {
		return temas;
	}

	/**
	 * Establece la lista de temas con los que está relacionado el comentario.
	 * 
	 * @param temas lista de temas.
	 */
	public void setTemas(List<Tema> temas) {
		this.temas = temas;
	}

	/**
	 * Devuelve el identificador del comentario.
	 * 
	 * @return identificador del comentario.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Establece el identificador del comentario.
	 * 
	 * @param id identificador del comentario.
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Devuelve el texto del comentario.
	 * 
	 * @return texto del comentario.
	 */
	public String getTexto() {
		return texto;
	}
	
	/**
	 * Establece el texto del comentario.
	 * 
	 * @param texto texto del comentario.
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	/**
	 * Devuelve el nombre del autor del comentario.
	 * 
	 * @return autor del comentario.
	 */
	public String getCreador() {
		return creador;
	}
	
	/**
	 * Establece el nombre del autor del comentario.
	 * 
	 * @param creador autor del comentario.
	 */
	public void setCreador(String creador) {
		this.creador = creador;
	}
	
	/**
	 *  Devuelve una cadena con la fecha en la que se escribió el comentario.
	 *  
	 * @return fecha del comentario.
	 */
	public String getFecha() {
		return fecha;
	}
	
	/**
	 * Establece la fecha de creación del comentario.
	 * @param fecha
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	/**
	 * Devuelve la URL que contiene la imagen de perfil del usuario.
	 * @return url de la imagen de perfil del autor.
	 */
	public String getImagen() {
		return imagen;
	}

	/**
	 * Establece la URL con la imagen de perfil del autor.
	 * @param imagen url de la imagen de perfil.
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	/**
	 * Devuelve las URL's que contiene el comentario.
	 * @return enlaces del comentario.
	 */
	public List<String> getEnlaces() {
		return enlaces;
	}

	/**
	 * Establece las URL que contiene comentario.
	 * @param enlaces lista de enlaces que contiene el comentario.
	 */
	public void setEnlaces(List<String> enlaces) {
		this.enlaces = enlaces;
	}
	
	/**
	 * Devuelve un entero que indica la popularidad del comentario.
	 * @return popularidad del comentario.
	 */
	public int getPopularidad() {
		return popularidad;
	}

	/**
	 * Establece la popularidad del comentario.
	 * 
	 * @param popularidad Entero que indica la popularidad del comentario.
	 */
	public void setPopularidad(int popularidad) {
		this.popularidad = popularidad;
	}
	
	/**
	 * Devuelve la prioridad de usuario.
	 * @return prioridad de usuario
	 */
	public int getUserPriority() {
		return userPriority;
	}

	/**
	 * Establece la prioridad de usuario.
	 * @param userPriority prioridad de usuario.
	 */
	public void setUserPriority(int userPriority) {
		this.userPriority = userPriority;
	}
	
	public String getSentimiento() {
		return sentimiento;
	}
	
	public void setSentimiento(String sentimiento) {
		this.sentimiento = sentimiento;
	}

	public List<Comentario> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<Comentario> respuestas) {
		this.respuestas = respuestas;
	}

	public String getRedSocial() {
		return redSocial;
	}

	public void setRedSocial(String redSocial) {
		this.redSocial = redSocial;
	}
}
