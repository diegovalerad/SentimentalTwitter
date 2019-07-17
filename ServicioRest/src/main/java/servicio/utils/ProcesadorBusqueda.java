package servicio.utils;

import java.util.Date;
import java.util.List;

/**
 * Clase con métodos útiles para procesar parámetros de búsqueda
 * 
 * @author José Fernando
 *
 */
public class ProcesadorBusqueda {

	/**
	 * 
	 * @param since
	 *            Fecha desde la que se buscan comentarios.
	 * @param until
	 *            Fecha hasta la que se buscan comentarios.
	 * @param temas
	 *            Temas de los que se buscan comentarios
	 * @param operadores
	 *            Operadores booleanos.
	 * @return consulta con los parámetros de búsuqeda.
	 */

	public static String procesarFecha(String since, String until, 
			List<String> temas, List<String> operadores) {

		String match = "";
		String where = "";
		String ret = " RETURN c.id, c.imagen, c.autor, c.fecha, c.mensaje, c.userPriority, c.popularidad";
		String desde = "";
		String hasta = "";
		
		if(temas == null || temas.size() == 0)
			return null;
		
		if(temas.size()-1 !=  operadores.size())
			return null;
		
		if(since != null) {
			Date d = ProcesadorFechas.parsearFecha(since);
			desde = ProcesadorFechas.procesarFecha(d);
		}
		
		if(until != null) {
			Date d = ProcesadorFechas.parsearFecha(until);
			hasta = ProcesadorFechas.procesarFecha(d);
		}

		if (temas.size() == 1) {

			match = "MATCH (c:Comentario)-[:RELATED_TO]-(t:Tema)";
			where = " WHERE t.id='" + temas.get(0) + "'";

		} else {

			// Match
			match = "MATCH (c:Comentario)";
			int i = 1;
			for (String tema : temas) {
				match += ", (t" + i + ":Tema {id:'" + tema + "'})";
				i++;
			}

			// Where
			where = " WHERE (";
			for (int j = 1; j<=temas.size();j++) {
				where += "(c)-[:RELATED_TO]-(t" + j + ")";
				if (j <= operadores.size()) {
					where += " " + operadores.get(j-1) + " ";
				}
			}
			where+= ")";
		}
		
		if(!desde.equals("") && !hasta.equals(""))
			where += " AND (c.fecha >= '" + desde + "' AND c.fecha <= '" + hasta + "')";
		else if(!desde.equals(""))
			where += " AND c.fecha >= '" + desde + "'";
		else if(!hasta.equals(""))
			where += " AND c.fecha <= '" + hasta + "'";

		return match + where + ret;
	}
}
