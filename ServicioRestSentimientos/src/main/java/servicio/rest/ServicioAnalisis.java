package servicio.rest;

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
	public Response analize(@QueryParam("texto") String texto) {
		Valoracion val = Controlador.getUnicaInstancia().analizarTexto(texto);
		
		return Response.status(200).entity(val).build();
	}
}
