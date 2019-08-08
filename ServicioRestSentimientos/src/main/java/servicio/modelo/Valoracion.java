package servicio.modelo;

/**
 * Clase que representa una Valoración.
 * <br>
 * Esta valoración, esta compuesta por el texto valorado, el sentimiento que tiene, 
 * así como el algoritmo que ha obtenido dicho sentimiento
 * @author Diego Valera Duran
 *
 */
public class Valoracion {
	private String texto;
	private Sentimiento sentimiento;
	private Algoritmo algoritmo;

	public Valoracion(String texto, Sentimiento sentimiento, Algoritmo algoritmo) {
		this.texto = texto;
		this.sentimiento = sentimiento;
		this.algoritmo = algoritmo;
	}
	
	public String getTexto() {
		return texto;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
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
