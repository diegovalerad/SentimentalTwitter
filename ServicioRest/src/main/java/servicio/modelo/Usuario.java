package servicio.modelo;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

/**
 * Esta clase representa a las entidades del dominio 'Usuario' que van a ser
 * persistidas en base de datos.
 * 
 * @author Diego Valera Duran
 *
 */
@NodeEntity
public class Usuario {

	@Id
	@Property(name = "email")
	private String email;

	@Property(name = "password")
	private String password;

	@Relationship(type = "FAVORITES", direction = Relationship.OUTGOING)
	private List<Favorito> usuariosFavoritos = new ArrayList<Favorito>();

	@Property(name = "isAdmin")
	private boolean isAdmin;

	public Usuario() {
		isAdmin = false;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Favorito> getUsuariosFavoritos() {
		return usuariosFavoritos;
	}

	public void setUsuariosFavoritos(List<Favorito> usuariosFavoritos) {
		this.usuariosFavoritos = usuariosFavoritos;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

}
