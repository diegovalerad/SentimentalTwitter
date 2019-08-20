package servicio.modelo;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * Esta clase representa a las entidades del dominio 'Usuario' que van a ser persistidas en base de datos.
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

	@Property(name = "usuariosFavoritos")
	private List<String> usuariosFavoritos = new ArrayList<String>();

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

	public List<String> getUsuariosFavoritos() {
		return usuariosFavoritos;
	}

	public void setUsuariosFavoritos(List<String> usuariosFavoritos) {
		this.usuariosFavoritos = usuariosFavoritos;
	}
}
