package servicio.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import servicio.modelo.Valoracion;

@Path("/analisis")
public class ServicioAnalisis {
	@Context 
	private UriInfo uriInfo;
	
	@GET
	@Path("analize")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response analize(@QueryParam("texto") String texto) {
		Valoracion val = new Valoracion();
		val.setNota(3);
		val.setExplicacion("la explicacion va a aqui");
		
		return Response.status(200).entity(val).build();
	}
}
