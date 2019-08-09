package servicio.modelo;

/**
 * Clase que indica la información sobre el algoritmo: El nombre y la descripción.
 * @author Diego Valera Duran
 *
 */
public class Algoritmo {
	private String nombre;
	private String descripcion;
	
	public Algoritmo() {}

	public Algoritmo(String nombre, String desc) {
		this.nombre = nombre;
		this.descripcion = desc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
