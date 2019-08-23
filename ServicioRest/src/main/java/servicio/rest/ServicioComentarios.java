package servicio.rest;

import java.util.Collections;
import java.util.List;

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

import servicio.controlador.Controlador;
import servicio.modelo.Comentario;
import servicio.modelo.Tema;
import servicio.tipos.ComentarioResultado;
import servicio.tipos.ListadoComentarios;
import servicio.tipos.TemaResultado;
import servicio.utils.CommentsComparator;

/**
 * Servicio REST con todas las operaciones relacionadas con los comentarios y los temas
 * @author Diego Valera Duran
 *
 */
@Path("temas")
@Produces(MediaType.APPLICATION_JSON)
public class ServicioComentarios {

	@Context 
	private UriInfo uriInfo;
	
	/**
	 * Obtiene los temas del servicio
	 * @return Un objeto respuesta con los temas encontrados, o si ha 
	 * 		habido algún error ({@link javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR})
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTemas() {
 
		String path = uriInfo.getPath();
		Iterable<TemaResultado> temas = Controlador.getUnicaInstancia().getListadoTemas();
		
		if(temas == null)
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		
		if(temas.iterator().hasNext()) {
			for (TemaResultado t : temas) {
				t.setUri(path+"/"+t.getId());
			}
		}
		
		return Response.status(200).entity(temas).build();
	}
	
	/**
	 * Realiza una búsqueda de comentarios en el servicio con parámetros
	 * @param since Fecha de inicio
	 * @param until Fecha de fin
	 * @param temas Temas sobre los que buscar
	 * @param operadores Operadores
	 * @return Un objeto respuesta con una colección de comentarios encontrados.
	 * 			Si los parámetros de búsqueda están mal se devuelve un error
	 * 		({@link javax.ws.rs.core.Response.Status.BAD_REQUEST}) y si
	 * 			no se encuentran comentarios, un error de comentarios no encontrados
	 * 			({@link javax.ws.rs.core.Response.Status.NOT_FOUND})
	 */
	@GET
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response busqueda(@QueryParam("since") String since,
			@QueryParam("until") String until,
			@QueryParam("tema") List<String> temas,
			@QueryParam("cond") List<String> operadores) {
		
		String query = Controlador.getUnicaInstancia().procesarFecha(since, until, temas, operadores);
		
		if(query == null)
			return Response.status(Response.Status.BAD_REQUEST).build();
		
		List<ComentarioResultado> comentarios = Controlador.getUnicaInstancia().buscar(query);
		
		if(comentarios.isEmpty())
			return Response.status(Response.Status.NOT_FOUND).build();
				
		for (ComentarioResultado c : comentarios) {
			c.setUri("temas/"+temas.get(0) + "/" +c.getId());
		}
		
		Collections.sort(comentarios, Collections.reverseOrder(new CommentsComparator()));
		
		return Response.status(200).entity(comentarios).build();
	}
	
	/**
	 * Se busca y recogen comentarios sobre un tema del servicio
	 * @param id ID del tema a buscar
	 * @return Objeto respuesta con la colección de comentarios. Si no se encuentra
	 * el tema en el servicio, se devuelve un error ({@link javax.ws.rs.core.Response.Status.NOT_FOUND})
	 */
	@GET
	@Path("{idTema}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTemaById(@PathParam("idTema") String id) {
		
		Tema t = Controlador.getUnicaInstancia().getTemaById(id);
		
		if(t == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		
		List<ComentarioResultado> comentarios = Controlador.getUnicaInstancia().getComentariosByTema(t.getId());
		String path = uriInfo.getPath();
		
		for (ComentarioResultado c : comentarios) {
			c.setUri(path+"/"+c.getId());
		}
		
		ListadoComentarios listado = new ListadoComentarios();
		listado.setNombre(t.getNombre());
		listado.setDescripcion(t.getDescripcion());
		
		Collections.sort(comentarios, Collections.reverseOrder(new CommentsComparator()));
		
		listado.setLista(comentarios);
		
		return Response.status(200).entity(listado).build();
	}
	
	/**
	 * Se intenta crear un tema en el servicio
	 * @param nombre Nombre del tema a crear
	 * @param descripcion Descripción opcional del tema
	 * @return Objeto respuesta indicando si se ha podido crear ({@link javax.ws.rs.core.Response.Status.CREATED})
	 * 			o si ha habido algún error ({@link javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR})
	 */
	@POST
	@Path("{nombreTema}")
	public Response crearTema(@PathParam("nombreTema") String nombre,
			@QueryParam("descripcion") String descripcion) {
		
		Tema t = new Tema();
		t.setNombre(nombre);
		if(descripcion == null)
			t.setDescripcion("");
		else t.setDescripcion(descripcion);
		
		boolean created = Controlador.getUnicaInstancia().crearTema(t);
		
		if(created) {
			Controlador.getUnicaInstancia().inicializarComentariosDeRedesSociales(t);
			return Response.status(Response.Status.CREATED).build();
		}
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * Se intenta obtener un comentario sobre un tema
	 * @param idT ID del tema
	 * @param idC ID del comentario
	 * @return Objeto respuesta con el comentario encontrado, o un error ({@link javax.ws.rs.core.Response.Status.NOT_FOUND}) si
	 * 			no se encuentra el comentario
	 */
	@GET
	@Path("{idTema}/{idComentario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getComentario(@PathParam("idTema") String idT,
									@PathParam("idComentario") String idC) {
		
		Comentario c = Controlador.getUnicaInstancia().getComentarioById(idC);
		
		if(c == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		
		return Response.status(200).entity(c).build();		
	}
	
	/**
	 * Se intenta obtener la lista de redes sociales del servicio
	 * @return Objeto respuseta con la lista de redes sociales
	 */
	@GET
	@Path("redesSociales")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRedesSociales() {
		List<String> rrss = Controlador.getUnicaInstancia().getRedesSociales();
		
		return Response.status(200).entity(rrss).build();
	}
}
