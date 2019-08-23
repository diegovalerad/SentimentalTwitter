package servicio.redesSociales.twitter;

import java.util.Comparator;

import twitter4j.Status;

/**
 * Clase que compara un tweet con otro.
 * <p>
 * Un tweet es mejor que otro si es más popular.
 * <p>
 * La popularidad de un tweet se calcula sumando sus retweets y sus favoritos
 * @author Diego Valera Duran
 *
 */
public class TweetsComparator implements Comparator<Status> {

	/**
	 * Compara dos objetos Status, con la información de un tweet.
	 * <p>
	 * Un tweet es mejor que otro si es más popular.
	 * <p>
	 * La popularidad de un tweet se calcula sumando sus retweets y sus favoritos
	 */
	@Override
	public int compare(Status o1, Status o2) {
		int o1Acum = o1.getRetweetCount() + o1.getFavoriteCount();
		int o2Acum = o2.getRetweetCount() + o2.getFavoriteCount();
		
		if (o1Acum < o2Acum)
			return -1;
		if (o1Acum > o2Acum)
			return 1;
		return 0;
	}

}
