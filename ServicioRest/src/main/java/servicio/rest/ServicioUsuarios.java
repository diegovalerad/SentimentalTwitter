package servicio.rest;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import servicio.controlador.Controlador;
import servicio.modelo.Favorito;
import servicio.modelo.Usuario;

@Path("usuarios")
@Produces(MediaType.APPLICATION_JSON)
public class ServicioUsuarios {
	
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUsuario(@FormParam("email") String email,
									@FormParam("password") String password) {
		
		boolean created = Controlador.getUnicaInstancia().crearUsuario(email, password);
		
		if (created)
			return Response.status(Response.Status.CREATED).build();
		return Response.status(Response.Status.CONFLICT).build();
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("email") String email,
						  @FormParam("password") String password) {
		
		boolean login = Controlador.getUnicaInstancia().login(email, password);
		
		if (login)
			return Response.status(Response.Status.OK).build();
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}
	
	@POST
	@Path("/validar/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response validar(@PathParam("email") String email) {
		boolean validado = Controlador.getUnicaInstancia().validarUsuario(email);
		
		if (validado)
			return Response.status(Response.Status.OK).build();
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@PUT
	@Path("{email}/favorito/{redSocial}/{nombre}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modificarFavorito(@PathParam("email") String email,
									  @PathParam("redSocial") String redSocial,
									  @PathParam("nombre") String nombre) {
		
		boolean modificado = Controlador.getUnicaInstancia().modificarFavorito(email, redSocial, nombre);
		
		if (modificado)
			return Response.status(Response.Status.OK).build();
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@PUT
	@Path("{email}/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("email") String email,
							@FormParam("password") String password) {
		System.out.println("email: " + email);
		System.out.println("pass: " + password);
		
		boolean updated = Controlador.getUnicaInstancia().updateUsuario(email, password);
		
		if (updated)
			return Response.status(Response.Status.OK).build();
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@GET
	@Path("{email}/favorito")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFavoritos(@PathParam("email") String email) {
		List<Favorito> favoritos = Controlador.getUnicaInstancia().getFavoritos(email);
		
		if (favoritos == null)
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		
		return Response.status(Response.Status.OK).entity(favoritos).build();
	}
	
	@GET
	@Path("{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuario(@PathParam("email") String email) {
		Usuario usuario = Controlador.getUnicaInstancia().findUsuarioByEmail(email);
		
		if (usuario == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		
		return Response.status(Response.Status.OK).entity(usuario).build();
	}
}
