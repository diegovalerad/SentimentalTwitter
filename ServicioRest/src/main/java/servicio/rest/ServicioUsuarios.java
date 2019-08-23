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

/**
 * Servicio REST con todas las operaciones relacionadas con los usuarios
 * @author Diego Valera Duran
 *
 */
@Path("usuarios")
@Produces(MediaType.APPLICATION_JSON)
public class ServicioUsuarios {

	/**
	 * Registra un usuario en la base de datos
	 * @param email Correo del usuario que se intenta registrar
	 * @param password Contraseña del usuario que se intenta registrar
	 * @return Una respuesta indicando si se ha creado ({@link javax.ws.rs.core.Response.Status.CREATED}) 
	 * 		o si ha habido algún error ({@link javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR})
	 */
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUsuario(@FormParam("email") String email, 
									@FormParam("password") String password) {

		boolean created = Controlador.getUnicaInstancia().crearUsuario(email, password);

		if (created)
			return Response.status(Response.Status.CREATED).build();
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * Se comprueba si los datos del usuario son correctos
	 * @param email Email del usuario
	 * @param password Contraseña del usuario
	 * @return Una respuesta indicando si se ha podido hacer login ({@link javax.ws.rs.core.Response.Status.OK}) 
	 * 			o si los datos son incorrectos ({@link javax.ws.rs.core.Response.Status.UNAUTHORIZED}) 
	 */
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

	/**
	 * Se intenta validar a un usuario
	 * @param email Correo del usuario
	 * @return Una respuesta indicando si se ha validado ({@link javax.ws.rs.core.Response.Status.CREATED}) 
	 * 		o si ha habido algún error ({@link javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR})
	 */
	@POST
	@Path("/validar/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response validar(@PathParam("email") String email) {
		boolean validado = Controlador.getUnicaInstancia().validarUsuario(email);

		if (validado)
			return Response.status(Response.Status.OK).build();
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * Se obtiene a un usuario
	 * @param email Correo del usuario
	 * @return Una respuesta con el objeto creado o indicando
	 * 		si no se ha encontrado en la base de datos ({@link javax.ws.rs.core.Response.Status.NOT_FOUND})
	 */
	@GET
	@Path("{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuario(@PathParam("email") String email) {
		Usuario usuario = Controlador.getUnicaInstancia().findUsuarioByEmail(email);

		if (usuario == null)
			return Response.status(Response.Status.NOT_FOUND).build();

		return Response.status(Response.Status.OK).entity(usuario).build();
	}

	/**
	 * Se obtienen los favoritos de un usuario
	 * @param email Correo del usuario
	 * @return Una respuesta con los favoritos o indicando
	 * 		si ha habido algún error ({@link javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR})
	 */
	@GET
	@Path("{email}/favoritos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFavoritos(@PathParam("email") String email) {
		List<Favorito> favoritos = Controlador.getUnicaInstancia().getFavoritos(email);

		if (favoritos == null)
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

		return Response.status(Response.Status.OK).entity(favoritos).build();
	}

	/**
	 * Se modifica un favorito del usuario. Si no estaba, se añade. Si estaba, se elimina.
	 * @param email Correo del usuario
	 * @param redSocial Red social del favorito a añadir.
	 * @param nombre Nombre del favorito en la red social
	 * @return Una respuesta indicando si se ha modificado ({@link javax.ws.rs.core.Response.Status.OK}) 
	 * 		o si ha habido algún error ({@link javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR})
	 */
	@PUT
	@Path("{email}/favoritos/{redSocial}/{nombre}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modificarFavorito(@PathParam("email") String email, 
									  @PathParam("redSocial") String redSocial,
									  @PathParam("nombre") String nombre) {

		boolean modificado = Controlador.getUnicaInstancia().modificarFavorito(email, redSocial, nombre);

		if (modificado)
			return Response.status(Response.Status.OK).build();
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * Se intenta actualizar la contraseña del usuario.
	 * @param email Correo del usuario
	 * @param password Contraseña nueva del usuario
	 * @return Una respuesta indicando si se ha modificado ({@link javax.ws.rs.core.Response.Status.OK}) 
	 * 		o si ha habido algún error ({@link javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR})
	 */
	@PUT
	@Path("{email}/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("email") String email, 
						   @FormParam("password") String password) {
		
		boolean updated = Controlador.getUnicaInstancia().updateUsuario(email, password);

		if (updated)
			return Response.status(Response.Status.OK).build();
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

}
