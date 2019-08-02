package servicio.modelo;

public class Algoritmo {
	private String nombre;
	private String descripcion;

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
