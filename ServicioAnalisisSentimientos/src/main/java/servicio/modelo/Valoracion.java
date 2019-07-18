package servicio.modelo;

public class Valoracion {
	private Sentimiento sentimiento;
	private String explicacion;
	
	public Valoracion() {}

	public Sentimiento getSentimiento() {
		return sentimiento;
	}

	public void setSentimiento(Sentimiento sentimiento) {
		this.sentimiento = sentimiento;
	}

	public String getExplicacion() {
		return explicacion;
	}

	public void setExplicacion(String explicacion) {
		this.explicacion = explicacion;
	}
}
