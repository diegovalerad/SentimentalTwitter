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

@Path("/analisis")
public class ServicioAnalisis {
	@Context 
	private UriInfo uriInfo;
	
	@GET
	@Path("analize")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response analize(
					@QueryParam("texto") String texto,
					@QueryParam("algoritmo") @DefaultValue("todos") String algoritmo) {
		List<Valoracion> valoraciones = Controlador.getUnicaInstancia().analizarTexto(texto, algoritmo);
		
		return Response.status(200).entity(valoraciones).build();
	}
}
