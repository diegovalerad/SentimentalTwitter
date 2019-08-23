package servicio.modelo;

import java.util.UUID;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * Esta clase representa a las entidades del dominio 'Tema' que van a ser persistidas en base de datos.
 * Un tema es un asunto sobre el que tratarán los comentarios.
 * 
 * @author José Fernando
 * @author Diego Valera Duran
 *
 */
@NodeEntity
public class Tema {
	
	@Id
	String id;
	
	@Property(name="nombre")
	private String nombre;
	
	@Property(name="descripcion")
	private String descripcion;
	
	/**
	 * Constructor.
	 * Inicializa el identificador del tema.
	 */
	public Tema(){
		this.id = UUID.randomUUID().toString();
	}
	
	// GETTERS & SETTERS
	
	/**
	 * Devuelve el identificador del tema.
	 * 
	 * @return identificador del tema.
	 */
	public String getId(){
		return this.id;
	}
	
	/**
	 * Devuelve el nombre del tema.
	 * 
	 * @return nombre del tema.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 *  Establce la propiedad 'nombre' del tema.
	 *  
	 * @param nombre nombre del tema.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Devuelve la descripción del tema.
	 * 
	 * @return descripción del tema.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la propiedad 'descripcion' del tema.
	 * @param descripcion Descripción del tema.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == this) return true;
        if (!(obj instanceof Tema)) {
            return false;
        }

        Tema t = (Tema) obj;
        
        return this.nombre.toLowerCase().equals(t.getNombre().toLowerCase());
	}
	
	
	
}
