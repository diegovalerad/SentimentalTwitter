package servicio.algoritmos;

import servicio.modelo.Valoracion;

public interface IAlgoritmo {
	public String getNombre();
	public String getDescripcion();
	
	public Valoracion analize(String text);
}
