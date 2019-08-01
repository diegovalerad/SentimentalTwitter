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

@Path("/temas")
public class ServicioComentarios {

	@Context 
	private UriInfo uriInfo;
	
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
	
	@GET
	@Path("{idTema}/{idComentario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getComentario(@PathParam("idTema") String idT,@PathParam("idComentario") String idC) {
		
		Comentario c = Controlador.getUnicaInstancia().getComentarioById(idC);
		
		if(c == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		
		return Response.status(200).entity(c).build();		
	}
	
}
