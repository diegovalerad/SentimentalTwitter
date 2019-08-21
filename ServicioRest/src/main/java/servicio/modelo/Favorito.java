package servicio.modelo;

import java.util.UUID;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity
public class Favorito {

	@Id
	private String id;
	
	@Property(name="redSocial")
	private String redSocial;
	
	@Property(name="nombre")
	private String nombre;
	
	public Favorito() {
		this.id = UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRedSocial() {
		return redSocial;
	}

	public void setRedSocial(String redSocial) {
		this.redSocial = redSocial;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == this) return true;
        if (!(obj instanceof Tema)) {
            return false;
        }

        Favorito f = (Favorito) obj;
        
        if (this.redSocial.equals(f.getRedSocial())) {
        	if (this.nombre.toLowerCase().equals(f.getNombre().toLowerCase()))
        		return true;
        }
        
        return false;
	}
}
