package servicio.rest;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import servicio.controlador.Controlador;
import servicio.modelo.Valoracion;

/**
 * Servicio REST con todas las operaciones relacionadas con el análisis sentimental de textos
 * @author Diego Valera Duran
 *
 */
@Path("/analisis")
public class ServicioAnalisis {
	@Context 
	private UriInfo uriInfo;
	
	/**
	 * Analiza un texto
	 * @param texto Texto a analizar
	 * @param algoritmo Algoritmo a usar. Si se usa la cadena "todos", se analiza el texto con todos los algoritmos
	 * @return Objeto respuesta con una lista de objetos {@link Valoracion}
	 */
	@GET
	@Path("analize")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response analize(
					@QueryParam("texto") String texto,
					@QueryParam("algoritmo") @DefaultValue("todos") String algoritmo) {
		List<Valoracion> valoraciones = Controlador.getUnicaInstancia().analizarTexto(texto, algoritmo);
		
		return Response.status(200).entity(valoraciones).build();
	}
	
	/**
	 * Obtiene los parámetros de la API
	 * @return Objeto respuesta con los parámetros de la API. Este objeto está compuesto de un array bi-dimensional
	 * 			con los parámetros que necesita cada algoritmo para ser utilizado por el servicio.
	 * <p>
	 * Cada array lo compone un algoritmo, y cada uno de estos lo forma un array que está compuesto por nombre, descripción y query para el servicio REST 
	 */
	@GET
	@Path("api-parameters")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getApiParameters() {
		String[][] api_info = Controlador.getUnicaInstancia().getInformacionAlgoritmosAndQuery();
		
		return Response.status(200).entity(api_info).build();
	}
}
