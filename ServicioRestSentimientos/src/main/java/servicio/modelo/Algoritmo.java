package servicio.modelo;

public class Algoritmo {
	private String nombre;
	private String desc;

	public Algoritmo(String nombre, String desc) {
		this.nombre = nombre;
		this.desc = desc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
