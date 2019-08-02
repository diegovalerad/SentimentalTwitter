package servicio.modelo;

public class Valoracion {
	private Sentimiento sentimiento;
	private Algoritmo algoritmo;

	public Valoracion(Sentimiento sentimiento, Algoritmo algoritmo) {
		this.sentimiento = sentimiento;
		this.algoritmo = algoritmo;
	}

	public Sentimiento getSentimiento() {
		return sentimiento;
	}

	public void setSentimiento(Sentimiento sentimiento) {
		this.sentimiento = sentimiento;
	}

	public Algoritmo getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(Algoritmo algoritmo) {
		this.algoritmo = algoritmo;
	}

}
