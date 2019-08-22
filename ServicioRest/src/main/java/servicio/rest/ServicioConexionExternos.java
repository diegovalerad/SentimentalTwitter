package servicio.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import servicio.controlador.Controlador;

@Path("serviciosExternos")
@Produces(MediaType.APPLICATION_JSON)
public class ServicioConexionExternos {
	
	@GET
	@Path("isSentimentServiceConnected")
	@Produces(MediaType.APPLICATION_JSON)
	public Response isSentimentServiceConnected() {
		boolean connected = true;
		
		connected = Controlador.getUnicaInstancia().isSentimentServiceConnected();
		
		return Response.status(200).entity(connected).build();
	}
}
